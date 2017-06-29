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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.suresh.projects.movieworld.AwsPropertiesHelper;
import com.suresh.projects.movieworld.entities.Movie;

@Service
public class AwsS3FileService {
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
}
