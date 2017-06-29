package com.suresh.projects.movieworld.controllers;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import  java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
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
import com.suresh.projects.movieworld.dto.Movies;
import com.suresh.projects.movieworld.dto.PaginatedResponse;
import com.suresh.projects.movieworld.exceptions.ApiException;
import com.suresh.projects.movieworld.services.MovieService;

@RestController
public class MovieController {
	
	@Autowired
	private MovieService movieService;
	
	@GetMapping(value = "/movies")
	public Resource<Movies> findMovies(@RequestParam(value="page", required = false) Integer page, 
										@RequestParam(value="size", required = false) Integer size) throws Exception {
		if (Optional.ofNullable(page).isPresent() && Optional.ofNullable(size).isPresent()) {
			return aListOfResources(movieService.findPagenated(page, size));
		} else {
			return aListOfResources(movieService.findPagenated(1, 20));
		}
	}
	
	@GetMapping(value = "/movies/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Resource<MovieDto> findMovieById(@PathVariable long id) throws Exception {
		checkArgument(id > 0, "id is invalid");
		Optional<MovieDto> movieDto = movieService.findById(id);
		if (movieDto.isPresent()) {
			return aSingleResource(movieDto.get());
		} else {
			throw new ApiException("Move with Id: " + id + " doesn't exist", HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value = "/movies", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Resource<MovieDto> createMovie(@RequestBody @NotNull MovieDto movieDto) throws Exception {
		MovieDto movie = movieService.createMovie(movieDto);
		return aSingleResource(movie);
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

	private Resource<MovieDto> aSingleResource(MovieDto movieDto) throws Exception, ApiException {
		Resource<MovieDto> resource = new Resource<MovieDto>(movieDto);
		resource.add(linkTo(methodOn(MovieController.class, movieDto).findMovieById(movieDto.getId())).withSelfRel());
		resource.add(linkTo(methodOn(MovieInfoController.class, movieDto).getInfo(movieDto.getId())).withRel("info"));
		return resource;
	}
	
	private Resource<Movies> aListOfResources(PaginatedResponse movies) throws Exception, ApiException {
		Movies moviesAsResource = new Movies();
		moviesAsResource.setMovies(movies.getContent());
		Resource<Movies> resource = new Resource<Movies>(moviesAsResource);
		if (movies.getPage() > 0) {
			resource.add(linkTo(methodOn(MovieController.class).findMovies(movies.getPage(), movies.getSize())).withSelfRel());
			if (!movies.isFirst()) {
				resource.add(linkTo(methodOn(MovieController.class).findMovies(movies.getPage()-1, movies.getSize())).withRel("previousPage"));
				resource.add(linkTo(methodOn(MovieController.class).findMovies(1, movies.getSize())).withRel("firstPage"));
			}
			if (!movies.isLast()) {
				resource.add(linkTo(methodOn(MovieController.class).findMovies(movies.getPage()+1, movies.getSize())).withRel("nextPage"));
				resource.add(linkTo(methodOn(MovieController.class).findMovies(movies.getTotalPages()-1, movies.getSize())).withRel("lastPage"));

			}
		}
		return resource;
	}
}
