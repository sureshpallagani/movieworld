package com.suresh.projects.movieworld.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.suresh.projects.movieworld.entities.Movie;
import com.suresh.projects.movieworld.repositories.MovieRepository;

import static java.util.Collections.EMPTY_LIST;
import static java.util.stream.StreamSupport.stream;
import static java.util.stream.Collectors.toList;

@RestController
@Transactional
public class MovieController {
	
	@Autowired
	private MovieRepository movieRepository;
	
	@GetMapping("/movies")
	public List<Movie> findMovies() {
		try {
			return stream(movieRepository.findAll().spliterator(), false)
								.sorted((m1, m2) -> Long.compare(m1.getYear(), m2.getYear()))
								.collect(toList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EMPTY_LIST;
	}
	
	@PostMapping("/movies")
	public Movie createMovies(@RequestBody Movie movie) {
		movieRepository.save(movie);
		return movie;
	}
	
	
}
