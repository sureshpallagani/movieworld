package com.suresh.projects.movieworld.controllers;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suresh.projects.movieworld.entities.Movie;
import com.suresh.projects.movieworld.repositories.MovieRepository;

@RestController
public class MovieController {
	
	@Autowired
	private MovieRepository movieRepository;
	
	@GetMapping("/movies")
	public String findMovies() {
		try {
			Movie movie = new Movie();
			movie.setRatingId(1);
			movie.setTitle("Test Movie 2");
			movie.setYearReleased(2017);
			movie.setDateReleased(DateTime.now().toDate());
			movieRepository.save(movie);
			Iterable<Movie> movies = movieRepository.findAll();
			return String.valueOf(movie.getId() + " " +movie.getTitle());
		} catch (Exception e) {
			e.printStackTrace();
			return "Test Failed";
		}
	}
	
//	@PostMapping("/movies")
//	public String createMovies(@RequestBody List<Movie> movies) {
//		return "Created";
//	}
}
