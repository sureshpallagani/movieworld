package com.suresh.projects.movieworld;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;

@Component
public class AwsPropertiesHelper {

	@Value("${aws.bucket.name}") private String awsBucketName;
	@Value("${aws.region}") private String awsRegion;
	@Value("${movies.data.file}") private String moviesDataFile;
	@Value("${aws.queue.name}") private String awsQueueName;
	@Value("${movies.json.local}") private String localJsonFile;
	
	public AWSStaticCredentialsProvider getAwsCredentials() {
		//Setup your AWS access key and secret access key as environment variables.
		BasicAWSCredentials creds = new BasicAWSCredentials(System.getenv("aws_access_key_id"), System.getenv("aws_secret_access_key"));
		return new AWSStaticCredentialsProvider(creds);
	}

	public String getAwsBucketName() {
		return awsBucketName;
	}

	public String getAwsRegion() {
		return awsRegion;
	}

	public String getMoviesDataFile() {
		return moviesDataFile;
	}

	public String getAwsQueueName() {
		return awsQueueName;
	}

	public String getLocalJsonFile() {
		return localJsonFile;
	}
	
}
