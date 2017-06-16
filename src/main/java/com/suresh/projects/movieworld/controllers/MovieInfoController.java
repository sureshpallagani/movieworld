package com.suresh.projects.movieworld.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.suresh.projects.movieworld.dto.MovieInfoDto;
import com.suresh.projects.movieworld.exceptions.ApiException;
import com.suresh.projects.movieworld.services.MovieService;

@RestController
public class MovieInfoController {

	@Autowired
	private MovieService movieService;
	
	@PostMapping(value = "/movies/{id}/info", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public MovieInfoDto createMovieInfo(@RequestBody MovieInfoDto movieInfoDto) throws ApiException {
		Optional<MovieInfoDto> info = movieService.saveInfo(movieInfoDto);
		if (info.isPresent()) {
			return info.get();
		} else {
			throw new ApiException(String.format("Movie with id %s doesn't exist", String.valueOf(movieInfoDto.getId())), HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping(value = "/movies/{id}/info", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public MovieInfoDto updateMovieInfo(@RequestBody MovieInfoDto movieInfoDto) throws ApiException {
		Optional<MovieInfoDto> info = movieService.saveInfo(movieInfoDto);
		if (info.isPresent()) {
			return info.get();
		} else {
			throw new ApiException(String.format("Movie with id %s doesn't exist", String.valueOf(movieInfoDto.getId())), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/movies/{id}/info", produces = MediaType.APPLICATION_JSON_VALUE)
	public MovieInfoDto getInfo(@PathVariable long id) throws ApiException {
		Optional<MovieInfoDto> info = movieService.getInfo(id);
		if (info.isPresent()) {
			return info.get();
		} else {
			throw new ApiException(String.format("Movie with id %s doesn't exist", String.valueOf(id)), HttpStatus.NOT_FOUND);
		}
	}
}
