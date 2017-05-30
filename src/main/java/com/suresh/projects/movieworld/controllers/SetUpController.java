package com.suresh.projects.movieworld.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suresh.projects.movieworld.repositories.MovieRepository;
import com.suresh.projects.movieworld.services.AwsS3FileService;

@RestController
public class SetUpController {
	
	@Autowired private AwsS3FileService awsS3FileService;
	@Autowired private MovieRepository movieRepository;

	@PostMapping("/setup")
	public void prepareMovies() {
		movieRepository.save(awsS3FileService.getMovieDataFromS3());
	}
	
	@DeleteMapping("/setup")
	public void clearData() {
		movieRepository.deleteAll();
	}
}
