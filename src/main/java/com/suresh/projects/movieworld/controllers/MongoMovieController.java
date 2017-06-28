package com.suresh.projects.movieworld.controllers;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.suresh.projects.movieworld.entities.Movie;
import com.suresh.projects.movieworld.repositories.mongo.MongoMovieRepository;

@RestController
public class MongoMovieController {

	@Autowired
	private MongoMovieRepository repository;
	
	@GetMapping(value = "/mongo/movies", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Movie> findAll() {
		return repository.findAll();
	}
	
	@PostMapping(value = "/mongo/movies", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Movie createMovie(@RequestBody @NotNull Movie movie) throws Exception {
		return repository.save(movie);
	}

}
