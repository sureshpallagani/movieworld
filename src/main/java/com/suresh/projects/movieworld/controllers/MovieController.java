package com.suresh.projects.movieworld.controllers;

import static java.util.Collections.EMPTY_LIST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.suresh.projects.movieworld.entities.Movie;
import com.suresh.projects.movieworld.services.MovieService;

@RestController
public class MovieController {
	
	@Autowired
	private MovieService movieService;
	
	@GetMapping("/movies")
	public Iterable<Movie> findMovies() {
		try {
			return movieService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EMPTY_LIST;
	}
	
	@PostMapping("/movies")
	public Movie createMovies(@RequestBody Movie movie) {
		return movieService.createMovie(movie);
	}
	
	
}
