package com.suresh.projects.movieworld.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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
import org.springframework.web.bind.annotation.RestController;

import com.suresh.projects.movieworld.entities.Movie;
import com.suresh.projects.movieworld.repositories.mongo.MongoMovieRepository;

@RestController
public class MongoMovieController {

	@Autowired
	private MongoMovieRepository repository;
	
	@GetMapping(value = "/mongo/movies", produces = MediaType.APPLICATION_JSON_VALUE)
	public PagedResources<Resource<Movie>> findAll(Pageable pageable, PagedResourcesAssembler<Movie> assembler) throws Exception {
		return assembler.toResource(repository.findAll(pageable));
	}
	
	@GetMapping(value = "/mongo/movies/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Resource<Movie> findById(@PathVariable Long id) {
		Link selfLink = linkTo(methodOn(MongoMovieController.class).findById(id)).withSelfRel();
		return new Resource<Movie>(repository.findOne(id), selfLink);
	}
	
	@PostMapping(value = "/mongo/movies", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Movie createMovie(@RequestBody @NotNull Movie movie) throws Exception {
		return repository.save(movie);
	}

	@DeleteMapping("/mongo/movies/{id}")
	public void deleteMovie(@PathVariable Long id) {
		repository.delete(id);
	}
	
}
