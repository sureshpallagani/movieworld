package com.suresh.projects.movieworld.services;

import java.io.BufferedReader;
import java.io.FileReader;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;

public class AwsSqsIntegration {

	public static void main(String a[]) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("C:/Users/spallagani/.aws/accessKeys.csv"));
			br.readLine();
			String[] awsCredentials = br.readLine().split(",");
			br.close();
			BasicAWSCredentials creds = new BasicAWSCredentials(awsCredentials[0], awsCredentials[1]);
			AmazonSQS sqs = AmazonSQSClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(creds))
					.withRegion(Regions.US_EAST_2).build();
			CreateQueueRequest request = new CreateQueueRequest("MovieWorld").addAttributesEntry("DelaySeconds", "60")
					.addAttributesEntry("MessageRetentionPeriod", "86400");
			sqs.createQueue(request);
			System.out.println(sqs.getQueueUrl("MovieWorld").getQueueUrl());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}