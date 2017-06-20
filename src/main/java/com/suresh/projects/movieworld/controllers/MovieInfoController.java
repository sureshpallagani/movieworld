package com.suresh.projects.movieworld.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.suresh.projects.movieworld.dto.ActorDto;
import com.suresh.projects.movieworld.dto.DirectorDto;
import com.suresh.projects.movieworld.dto.GenreDto;
import com.suresh.projects.movieworld.dto.MovieInfoDto;
import com.suresh.projects.movieworld.exceptions.ApiException;
import com.suresh.projects.movieworld.services.MovieService;

@RestController
public class MovieInfoController {

	@Autowired
	private MovieService movieService;
	
	@PostMapping(value = "/movies/{id}/info", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Resource<MovieInfoDto> createMovieInfo(@RequestBody MovieInfoDto movieInfoDto) throws ApiException {
		Optional<MovieInfoDto> info = movieService.saveInfo(movieInfoDto);
		if (info.isPresent()) {
			return aSingleResource(info);
		} else {
			throw new ApiException(String.format("Movie with id %s doesn't exist", String.valueOf(movieInfoDto.getId())), HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping(value = "/movies/{id}/info", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Resource<MovieInfoDto> updateMovieInfo(@RequestBody MovieInfoDto movieInfoDto) throws ApiException {
		Optional<MovieInfoDto> info = movieService.saveInfo(movieInfoDto);
		if (info.isPresent()) {
			return aSingleResource(info);
		} else {
			throw new ApiException(String.format("Movie with id %s doesn't exist", String.valueOf(movieInfoDto.getId())), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/movies/{id}/info", produces = MediaType.APPLICATION_JSON_VALUE)
	public Resource<MovieInfoDto> getInfo(@PathVariable long id) throws ApiException {
		Optional<MovieInfoDto> info = movieService.getInfo(id);
		if (info.isPresent()) {
			return aSingleResource(info);
		} else {
			throw new ApiException(String.format("Movie with id %s doesn't exist", String.valueOf(id)), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/movies/{id}/actors", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ActorDto> getActorsByMovie(@PathVariable long id) throws ApiException {
		Optional<MovieInfoDto> info = movieService.getInfo(id);
		if (info.isPresent()) {
			return info.get().getActors();
		} else {
			throw new ApiException(String.format("Movie with id %s doesn't exist", String.valueOf(id)), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/movies/{id}/directors", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DirectorDto> getDirectorsByMovie(@PathVariable long id) throws ApiException {
		Optional<MovieInfoDto> info = movieService.getInfo(id);
		if (info.isPresent()) {
			return info.get().getDirectors();
		} else {
			throw new ApiException(String.format("Movie with id %s doesn't exist", String.valueOf(id)), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/movies/{id}/genres", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GenreDto> getGenresByMovie(@PathVariable long id) throws ApiException {
		Optional<MovieInfoDto> info = movieService.getInfo(id);
		if (info.isPresent()) {
			return info.get().getGenres();
		} else {
			throw new ApiException(String.format("Movie with id %s doesn't exist", String.valueOf(id)), HttpStatus.NOT_FOUND);
		}
	}

	private Resource<MovieInfoDto> aSingleResource(Optional<MovieInfoDto> info) throws ApiException {
		Resource<MovieInfoDto> movieInfo = new Resource<MovieInfoDto>(info.get()); 
		movieInfo.add(linkTo(methodOn(MovieInfoController.class, movieInfo).getGenresByMovie(movieInfo.getContent().getId())).withRel("genres"));
		movieInfo.add(linkTo(methodOn(MovieInfoController.class, movieInfo).getActorsByMovie(movieInfo.getContent().getId())).withRel("actors"));
		movieInfo.add(linkTo(methodOn(MovieInfoController.class, movieInfo).getDirectorsByMovie(movieInfo.getContent().getId())).withRel("directors"));
		movieInfo.add(linkTo(methodOn(MovieInfoController.class, movieInfo).getInfo(movieInfo.getContent().getId())).withSelfRel());
		return movieInfo;
	}
}
