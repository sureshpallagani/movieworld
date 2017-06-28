package com.suresh.projects.movieworld.services;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.suresh.projects.movieworld.entities.Movie;
import com.suresh.projects.movieworld.entities.MovieSetUp;
import com.suresh.projects.movieworld.entities.SetUpStatus;
import com.suresh.projects.movieworld.repositories.jpa.JpaMovieRepository;
import com.suresh.projects.movieworld.repositories.jpa.JpaMovieSetUpRepository;

@Service
public class SetUpAsyncService {

	@Autowired private JpaMovieSetUpRepository setupRepository;
	@Autowired private AwsS3FileService awsS3FileService;
	@Autowired private JpaMovieRepository movieRepository;

	@Async
	public Future<Long> createMovieSetup(MovieSetUp setUp) throws Exception {
		List<Movie> movies = awsS3FileService.getMovieDataFromS3();
		for(int i=0; i<movies.size(); i++) {
			if (i % 100 == 0) {
				movieRepository.saveAndFlush(movies.get(i));
			} else {
				movieRepository.save(movies.get(i));
			}
		}
		setUp.setStatus(SetUpStatus.COMPLETED);
		setupRepository.saveAndFlush(setUp);
		return new AsyncResult<Long>(setUp.getId());
	}
	
	public MovieSetUp getSetUpStatus(long id) {
		return setupRepository.findOne(id);
	}

	@Async
	public Future<Long> deleteSetUp(MovieSetUp setUp) throws Exception {
		List<Movie> movies = movieRepository.findAll();
		movies.parallelStream().forEach(m -> {movieRepository.delete(m);});
		setUp.setStatus(SetUpStatus.COMPLETED);
		setupRepository.saveAndFlush(setUp);
		return new AsyncResult<Long>(setUp.getId());
	}
}
