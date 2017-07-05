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
import com.suresh.projects.movieworld.repositories.jpa.JpaMovieSetUpRepository;
import com.suresh.projects.movieworld.repositories.mongo.MongoMovieSetUpRepository;
import com.suresh.projects.movieworld.services.SetUpAsyncService;

@RestController
public class SetUpController {
	private static final String TYPE_RDS = "RDS";
	private static final String TYPE_MONGO = "MONGO";

	@Autowired private SetUpAsyncService setUpAsyncService; 
	@Autowired private JpaMovieSetUpRepository setupRepository;
	@Autowired private MongoMovieSetUpRepository mongoMovieSetupRepository;

	@PostMapping("/setup")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void prepareMovies(HttpServletResponse response, UriComponentsBuilder uriComponentsBuilder) throws Exception {
		MovieSetUp setUp = setUpFor(SetUpOperation.CREATE, TYPE_RDS);
		setUpAsyncService.createMovieSetup(setUp);
		final String location = uriComponentsBuilder.path("/setup"+"/"+setUp.getId()).build().encode().toString();
		response.setHeader("Location", location);
	}
	
	@PostMapping("/mongo/setup")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void prepareMoviesForMongo(HttpServletResponse response, UriComponentsBuilder uriComponentsBuilder) throws Exception {
		MovieSetUp setUp = setUpFor(SetUpOperation.CREATE, TYPE_MONGO);
		setUpAsyncService.createMovieSetupForMongo(setUp);
		final String location = uriComponentsBuilder.path("/mongo/setup"+"/"+setUp.getId()).build().encode().toString();
		response.setHeader("Location", location);
	}
	
	@GetMapping("/mongo/setup/{id}")
	public MovieSetUp getSetUpStatusOnMongo(@PathVariable long id) {
		return setUpAsyncService.getSetUpStatusOnMongo(id);
	}
	
	@GetMapping("/setup/{id}")
	public MovieSetUp getSetUpStatus(@PathVariable long id) {
		return setUpAsyncService.getSetUpStatus(id);
	}
	
	@DeleteMapping("/setup")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void clearData(HttpServletResponse response, UriComponentsBuilder uriComponentsBuilder) throws Exception {
		MovieSetUp setUp = setUpFor(SetUpOperation.DELETE, TYPE_RDS);
		setUpAsyncService.deleteSetUp(setUp);
		final String location = uriComponentsBuilder.path("/setup"+"/"+setUp.getId()).build().encode().toString();
		response.setHeader("Location", location);
	}
	
	private MovieSetUp setUpFor(SetUpOperation op, String type) {
		MovieSetUp setUp = new MovieSetUp();
		setUp.setOperation(op);
		setUp.setStatus(SetUpStatus.INPROGRESS);
		switch (type) {
		case "MONGO":
			setUp.setId(mongoMovieSetupRepository.count() + 1);
			mongoMovieSetupRepository.insert(setUp);
			break;
		default:
			setupRepository.saveAndFlush(setUp);
			break;
		}
		return setUp;
	}

}
