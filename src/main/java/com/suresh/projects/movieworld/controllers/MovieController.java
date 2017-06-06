package com.suresh.projects.movieworld.controllers;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.ofNullable;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.suresh.projects.movieworld.entities.Movie;
import com.suresh.projects.movieworld.exceptions.ApiException;
import com.suresh.projects.movieworld.services.MovieService;

@RestController
public class MovieController {
	
	@Autowired
	private MovieService movieService;
	
	@GetMapping(value = "/movies")
	public Iterable<Movie> findMovies(@RequestParam(value="page", required = false) Integer page, 
										@RequestParam(value="size", required = false) Integer size) {
		if (ofNullable(page).isPresent() && ofNullable(size).isPresent()) {
			return movieService.findPagenated(page, size);
		} else {
			return movieService.findAll();
		}
	}
	
	@GetMapping("/movies/{id}")
	public Movie findMovieById(@PathVariable @NotNull long id) throws Exception {
		checkArgument(id > 0, "id is invalid");
		Optional<Movie> movie = movieService.findById(id);
		if (movie.isPresent()) {
			return movie.get();
		} else {
			throw new ApiException("Move with Id: " + id + " doesn't exist", HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/movies")
	public Movie createMovie(@RequestBody @NotNull Movie movie) {
		return movieService.createMovie(movie);
	}
	
	@PutMapping("/movies/{id}")
	public Movie updateMovie(@PathVariable @NotNull long id, @RequestBody Movie movie) throws ApiException {
		checkArgument(id > 0, "id is invalid");
		checkNotNull(movie);
		checkArgument(id == movie.getId(), "Invalid request, check the arguments", "");
		return movieService.updateMovie(movie);
	}
	
	@DeleteMapping("/movies/{id}")
	public String deleteMovie(@PathVariable @NotNull long id) throws ApiException {
		checkArgument(id > 0, "id is invalid");
		movieService.deleteMovie(id);
		return "Deleted Successfully";
	}
	
}
