package com.suresh.projects.movieworld.stepdefs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import com.suresh.projects.movieworld.entities.Movie;
import com.suresh.projects.movieworld.util.CucumberGlobalContext;
import com.suresh.projects.movieworld.util.CucumberScenarioContext;

import cucumber.api.java.en.When;

public class MongoMovieOperationsStepDefs {

	@Autowired protected TestRestTemplate testRestTemplate;
	@Autowired protected CucumberGlobalContext globalContext;

	@When("^Client requests for a movie by Id that exists in Mongo \"([^\"]*)\"$")
	public void client_requests_for_a_movie_by_Id_that_exists_in_Mongo(String arg1) throws Throwable {
		if (Boolean.valueOf(arg1) == Boolean.TRUE) {
			ResponseEntity<Movie> response = testRestTemplate.getForEntity("http://localhost:8080/movieworld/mongo/movies/610", Movie.class);
			CucumberScenarioContext.put("currentStatusCode", response.getStatusCode());
			CucumberScenarioContext.put("MovieInTest", response.getBody());
		} else {
			ResponseEntity<Object> response = testRestTemplate.getForEntity("http://localhost:8080/movieworld/movies/"+9999, Object.class);
			CucumberScenarioContext.put("currentStatusCode", response.getStatusCode());
		}
	}

}
