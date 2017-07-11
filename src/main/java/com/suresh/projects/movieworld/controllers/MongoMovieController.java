package com.suresh.projects.movieworld.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.util.StringUtils.isEmpty;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.suresh.projects.movieworld.entities.Movie;
import com.suresh.projects.movieworld.repositories.mongo.MongoMovieRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
public class MongoMovieController {

	@Autowired
	private MongoMovieRepository repository;
	
	@ApiOperation(value = "Search Movies", notes = "End-point for various search options on movies")
	@GetMapping(value = "/mongo/movies", produces = MediaType.APPLICATION_JSON_VALUE)
	public PagedResources<Resource<Movie>> findAll(Pageable pageable, PagedResourcesAssembler<Movie> assembler, 
													@ApiParam(value = "Can be one or more actors in the form of actor1|actor2|...", required = false) 
														@RequestParam(name = "actors", required = false) String actors,
													@ApiParam(value = "Can be one or more directors in the form of director1|director2|...", required = false) 
														@RequestParam(name = "directors", required = false) String directors,
													@ApiParam(value = "Can be one or more genres in the form of genre1|genre2|...", required = false) 
														@RequestParam(name = "genres", required = false) String genres,
													@ApiParam(value = "Find movies by year", required = false)
														@RequestParam(name="year", required = false) int year) throws Exception {
		if (year > 0) {
			return assembler.toResource(repository.findByYear(year, pageable));
		}
		if (!isEmpty(actors)) {
			QMovie movie = new QMovie();
			return assembler.toResource(repository.findAll(pageable));
		}
		return assembler.toResource(repository.findAll(pageable));
	}
	
	@ApiOperation(value = "Find Movie by Id")
	@GetMapping(value = "/mongo/movies/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Resource<Movie> findById(@PathVariable Long id) {
		Link selfLink = linkTo(methodOn(MongoMovieController.class).findById(id)).withSelfRel();
		return new Resource<Movie>(repository.findOne(id), selfLink);
	}
	
	@ApiOperation(value = "Create a movie")
	@PostMapping(value = "/mongo/movies", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Movie createMovie(@RequestBody @NotNull Movie movie) throws Exception {
		return repository.save(movie);
	}

	@ApiOperation(value = "Delete a movie")
	@DeleteMapping("/mongo/movies/{id}")
	public void deleteMovie(@PathVariable Long id) {
		repository.delete(id);
	}
	
}
