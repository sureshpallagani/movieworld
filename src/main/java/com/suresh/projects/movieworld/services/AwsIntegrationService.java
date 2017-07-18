package com.suresh.projects.movieworld.services;

import static java.util.Collections.EMPTY_LIST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.suresh.projects.movieworld.AwsPropertiesHelper;
import com.suresh.projects.movieworld.entities.Movie;

@Service
public class AwsIntegrationService {
	@Autowired private AwsPropertiesHelper awsPropertiesHelper;
	
	@SuppressWarnings("unchecked")
	public List<Movie> getMovieDataFromS3() {
		//Using US East 2 region. This needs to be changed accordingly.
		try {
			AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(awsPropertiesHelper.getAwsCredentials()).withRegion(Regions.US_EAST_2).build();
			S3Object o = s3.getObject(awsPropertiesHelper.getAwsBucketName(), awsPropertiesHelper.getMoviesDataFile());
			S3ObjectInputStream s3ois = o.getObjectContent();
			ObjectMapper mapper = new ObjectMapper();
			CollectionType javaType = mapper.getTypeFactory().constructCollectionType(List.class, Movie.class);
			return mapper.readValue(s3ois, javaType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EMPTY_LIST;
	}
	
	/**
	 * First checks if there is already a queue with name 'MovieWorld', if yes uses that queue else creates a new queue with that name.
	 */
	public void createSqs() {
		AmazonSQS sqs = AmazonSQSClientBuilder.standard().withCredentials(awsPropertiesHelper.getAwsCredentials()).withRegion(Regions.US_EAST_2).build();
		CreateQueueRequest request = new CreateQueueRequest(awsPropertiesHelper.getAwsQueueName())
				.addAttributesEntry("DelaySeconds", "60")
				.addAttributesEntry("MessageRetentionPeriod", "86400");
		try {
			sqs.createQueue(request);
		} catch (AmazonSQSException e) {
			if (!e.getErrorCode().equals("QueueAlreadyExists")) {
				throw e;
			}
		}
		String queue_url = sqs.getQueueUrl(awsPropertiesHelper.getAwsQueueName()).getQueueUrl();
		System.out.println(queue_url);
		sqs.deleteQueue(queue_url);
	}
}
