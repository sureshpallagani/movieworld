package com.suresh.projects.movieworld.controllers;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import  java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.suresh.projects.movieworld.dto.MovieDto;
import com.suresh.projects.movieworld.dto.PaginatedResponse;
import com.suresh.projects.movieworld.exceptions.ApiException;
import com.suresh.projects.movieworld.services.MovieService;

@RestController
public class MovieController {
	
	@Autowired
	private MovieService movieService;
	
	@GetMapping(value = "/movies", produces = MediaType.APPLICATION_JSON_VALUE)
	public PaginatedResponse findMovies(@RequestParam(value="page", required = false) Integer page, 
										@RequestParam(value="size", required = false) Integer size) {
		if (Optional.ofNullable(page).isPresent() && Optional.ofNullable(size).isPresent()) {
			return movieService.findPagenated(page, size);
		} else {
			PaginatedResponse response = new PaginatedResponse();
			response.setContent(movieService.findAll());
			return response;
		}
	}
	
	@GetMapping(value = "/movies/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public MovieDto findMovieById(@PathVariable long id) throws Exception {
		checkArgument(id > 0, "id is invalid");
		Optional<MovieDto> movieDto = movieService.findById(id);
		if (movieDto.isPresent()) {
			return movieDto.get();
		} else {
			throw new ApiException("Move with Id: " + id + " doesn't exist", HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value = "/movies", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public MovieDto createMovie(@RequestBody @NotNull MovieDto movieDto) {
		return movieService.createMovie(movieDto);
	}
	
	@PutMapping(value = "/movies/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateMovie(@PathVariable long id, @RequestBody MovieDto movieDto) throws ApiException {
		checkArgument(id > 0, "id is invalid");
		checkNotNull(movieDto);
		checkArgument(id == movieDto.getId(), "Invalid request, check the arguments", "");
		movieService.updateMovie(movieDto);
	}
	
	@DeleteMapping("/movies/{id}")
	public void deleteMovie(@PathVariable long id) throws ApiException {
		checkArgument(id > 0, "id is invalid");
		movieService.deleteMovie(id);
	}
	
}
