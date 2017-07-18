package com.suresh.projects.movieworld.services;

import java.util.List;
import java.util.concurrent.Future;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.suresh.projects.movieworld.entities.Movie;
import com.suresh.projects.movieworld.entities.MovieSetUp;
import com.suresh.projects.movieworld.entities.SetUpStatus;
import com.suresh.projects.movieworld.repositories.jpa.JpaMovieRepository;
import com.suresh.projects.movieworld.repositories.jpa.JpaMovieSetUpRepository;
import com.suresh.projects.movieworld.repositories.mongo.MongoMovieRepository;
import com.suresh.projects.movieworld.repositories.mongo.MongoMovieSetUpRepository;

@Service
public class SetUpAsyncService {

	@Autowired private JpaMovieSetUpRepository setupRepository;
	@Autowired private MongoMovieSetUpRepository mongoSetupRepo;
	@Autowired private AwsIntegrationService awsIntegrationService;
	@Autowired private JpaMovieRepository movieRepository;
	@Autowired private MongoMovieRepository mongoRepository;

	@Async
	public Future<Long> createMovieSetup(MovieSetUp setUp) throws Exception {
		List<Movie> movies = awsIntegrationService.getMovieDataFromS3();
		for(int i=0; i<movies.size(); i++) {
			if (i % 100 == 0) {
				movieRepository.saveAndFlush(movies.get(i));
			} else {
				movieRepository.save(movies.get(i));
			}
		}
		setUp.setEndTime(DateTime.now().toDate());
		setUp.setStatus(SetUpStatus.COMPLETED);
		setupRepository.saveAndFlush(setUp);
		return new AsyncResult<Long>(setUp.getId());
	}

	@Async
	public Future<Long> deleteSetUp(MovieSetUp setUp) throws Exception {
		List<Movie> movies = movieRepository.findAll();
		movies.parallelStream().forEach(m -> {movieRepository.delete(m);});
		setUp.setStatus(SetUpStatus.COMPLETED);
		setUp.setEndTime(DateTime.now().toDate());
		setupRepository.saveAndFlush(setUp);
		return new AsyncResult<Long>(setUp.getId());
	}

	@Async
	public void createMovieSetupForSqs(MovieSetUp setUp) {
//		List<Movie> movies = awsIntegrationService.getMovieDataFromS3();
		awsIntegrationService.createSqs();
		//TODO
	}

	@Async
	public void deleteSetUpForSqs(MovieSetUp setUp) {
		// TODO Auto-generated method stub
		
	}

	@Async
	public Future<Long> createMovieSetupForMongo(MovieSetUp setUp) {
		List<Movie> movies = awsIntegrationService.getMovieDataFromS3();
		long count = mongoRepository.count();
		movies.subList(0, 1000).forEach(m -> {
												m.setId(count + movies.indexOf(m));
												mongoRepository.insert(m);
												try {
													Thread.sleep(500); //give half second for each insert just not to max out IOPS to stay in free tier.
												} catch (InterruptedException e) {
													e.printStackTrace();
												}
											});
		setUp.setStatus(SetUpStatus.COMPLETED);
		setUp.setEndTime(DateTime.now().toDate());
		mongoSetupRepo.save(setUp);
		return new AsyncResult<Long>(setUp.getId());
	}

	@Async
	public Future<Long> deleteSetUpForMongo(MovieSetUp setUp) {
		while(mongoRepository.count() != 0) {
			Page<Movie> movies = mongoRepository.findAll(new PageRequest(0, 10));
			movies.getContent().forEach(m -> mongoRepository.delete(m));
		}
		setUp.setStatus(SetUpStatus.COMPLETED);
		setUp.setEndTime(DateTime.now().toDate());
		mongoSetupRepo.save(setUp);
		return new AsyncResult<Long>(setUp.getId());
	}

	@JmsListener(destination = "${aws.queue.name}")
	public void getMovie(String movieJson) throws Exception {
		
	}
	
	public MovieSetUp getSetUpStatus(long id) {
		return setupRepository.findOne(id);
	}

	public MovieSetUp getSetUpStatusOnMongo(long id) {
		return mongoSetupRepo.findOne(id);
	}

	public MovieSetUp getSetUpStatusOnSqs(long id) {
		return setupRepository.findOne(id);
	}

}
