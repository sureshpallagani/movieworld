package com.suresh.projects.movieworld.controllers;

import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
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
import com.suresh.projects.movieworld.entities.SetUpEnv;
import com.suresh.projects.movieworld.entities.SetUpOperation;
import com.suresh.projects.movieworld.entities.SetUpStatus;
import com.suresh.projects.movieworld.repositories.jpa.JpaMovieSetUpRepository;
import com.suresh.projects.movieworld.repositories.mongo.MongoMovieSetUpRepository;
import com.suresh.projects.movieworld.services.SetUpAsyncService;

@RestController
public class SetUpController {

	@Autowired private SetUpAsyncService setUpAsyncService; 
	@Autowired private JpaMovieSetUpRepository setupRepository;
	@Autowired private MongoMovieSetUpRepository mongoMovieSetupRepository;

	@PostMapping("/setup")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void prepareMovies(HttpServletResponse response, UriComponentsBuilder uriComponentsBuilder) throws Exception {
		MovieSetUp setUp = setUpFor(SetUpOperation.CREATE, SetUpEnv.RDS);
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
		MovieSetUp setUp = setUpFor(SetUpOperation.DELETE, SetUpEnv.RDS);
		setUpAsyncService.deleteSetUp(setUp);
		final String location = uriComponentsBuilder.path("/setup"+"/"+setUp.getId()).build().encode().toString();
		response.setHeader("Location", location);
	}
	
	@PostMapping("/mongo/setup")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void prepareMoviesForMongo(HttpServletResponse response, UriComponentsBuilder uriComponentsBuilder) throws Exception {
		MovieSetUp setUp = setUpFor(SetUpOperation.CREATE, SetUpEnv.MONGO);
		setUpAsyncService.createMovieSetupForMongo(setUp);
		response.setHeader("Location", uriComponentsBuilder.path("/mongo/setup"+"/"+setUp.getId()).build().encode().toString());
	}
	
	@GetMapping("/mongo/setup/{id}")
	public MovieSetUp getSetUpStatusOnMongo(@PathVariable long id) {
		return setUpAsyncService.getSetUpStatusOnMongo(id);
	}
	
	@DeleteMapping("/mongo/setup")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void clearMongoData(HttpServletResponse response, UriComponentsBuilder uriComponentsBuilder) throws Exception {
		MovieSetUp setUp = setUpFor(SetUpOperation.DELETE, SetUpEnv.MONGO);
		setUpAsyncService.deleteSetUpForMongo(setUp);
		response.setHeader("Location", uriComponentsBuilder.path("/mongo/setup"+"/"+setUp.getId()).build().encode().toString());
	}

	@PostMapping("/sqs/setup")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void prepareMoviesForSqs(HttpServletResponse response, UriComponentsBuilder uriComponentsBuilder) throws Exception {
		MovieSetUp setUp = setUpFor(SetUpOperation.CREATE, SetUpEnv.AWS_SQS);
		setUpAsyncService.createMovieSetupForSqs(setUp);
		response.setHeader("Location", uriComponentsBuilder.path("/sqs/setup"+"/"+setUp.getId()).build().encode().toString());
	}
	
	@GetMapping("/sqs/setup/{id}")
	public MovieSetUp getSetUpStatusOnSqs(@PathVariable long id) {
		return setUpAsyncService.getSetUpStatusOnSqs(id);
	}
	
	private MovieSetUp setUpFor(SetUpOperation op, SetUpEnv type) {
		MovieSetUp setUp = new MovieSetUp();
		setUp.setOperation(op);
		setUp.setStatus(SetUpStatus.INPROGRESS);
		setUp.setStartTime(DateTime.now().toDate());
		switch (type) {
		case MONGO:
			setUp.setId(mongoMovieSetupRepository.count() + 1);
			mongoMovieSetupRepository.insert(setUp);
			break;
		case RDS:
			setupRepository.saveAndFlush(setUp);
			break;
		case AWS_SQS:
			setupRepository.saveAndFlush(setUp);
			break;
		}
		return setUp;
	}

}
