package com.suresh.projects.movieworld.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.suresh.projects.movieworld.entities.MovieSetUp;
import com.suresh.projects.movieworld.entities.SetUpOperation;
import com.suresh.projects.movieworld.entities.SetUpStatus;
import com.suresh.projects.movieworld.repositories.MovieSetUpRepository;
import com.suresh.projects.movieworld.services.SetUpAsyncService;

@RestController
public class SetUpController {
	
	@Autowired private SetUpAsyncService setUpAsyncService; 
	@Autowired private MovieSetUpRepository setupRepository;

	@PostMapping("/setup")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void prepareMovies(HttpServletResponse response, UriComponentsBuilder uriComponentsBuilder) throws Exception {
		MovieSetUp setUp = setUpFor(SetUpOperation.CREATE);
		setUpAsyncService.createMovieSetup(setUp);
		final String location = uriComponentsBuilder.path("/setup"+"/"+setUp.getId()).build().encode().toString();
		response.setHeader("Location", location);
	}
	
	@GetMapping("/setup/{id}")
	public MovieSetUp getSetUpStatus(@PathVariable long id) {
		return setUpAsyncService.getSetUpStatus(id);
	}
	
	@DeleteMapping("/setup")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void clearData(HttpServletResponse response, UriComponentsBuilder uriComponentsBuilder) throws Exception {
		MovieSetUp setUp = setUpFor(SetUpOperation.DELETE);
		setUpAsyncService.deleteSetUp(setUp);
		final String location = uriComponentsBuilder.path("/setup"+"/"+setUp.getId()).build().encode().toString();
		response.setHeader("Location", location);
	}
	
	private MovieSetUp setUpFor(SetUpOperation op) {
		MovieSetUp setUp = new MovieSetUp();
		setUp.setOperation(op);
		setUp.setStatus(SetUpStatus.INPROGRESS);
		setupRepository.saveAndFlush(setUp);
		return setUp;
	}

}
