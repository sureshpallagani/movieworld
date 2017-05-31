package com.suresh.projects.movieworld.stepdefs;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.any;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.suresh.projects.movieworld.MovieworldApplicationTests;
import com.suresh.projects.movieworld.entities.Movie;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class MovieWorldStepDefs extends MovieworldApplicationTests {
	@Autowired private TestRestTemplate restTemplate;
	ResponseEntity<List<Movie>> moviesResponse = null;
	
	@When("^the client calls /movies$")
	public void the_client_calls_movies() throws Throwable {
		moviesResponse = restTemplate.exchange("http://localhost:8080/movieworld/movies", HttpMethod.GET, null, new ParameterizedTypeReference<List<Movie>>() { });
	    assertNotNull(moviesResponse);
	}

	@Then("^the client receives status code of (\\d+)$")
	public void the_client_receives_status_code_of(int status) throws Throwable {
		HttpStatus currentStatusCode = moviesResponse.getStatusCode();
		assertThat("status code didn't match expected : "+ currentStatusCode.value(), currentStatusCode.value(), equalTo(status));
	}

	@Then("^client receives list of movies$")
	public void client_receives_list_of_movies() throws Throwable {
		assertThat("List not received", moviesResponse.getBody(), any(List.class));
	}

}
