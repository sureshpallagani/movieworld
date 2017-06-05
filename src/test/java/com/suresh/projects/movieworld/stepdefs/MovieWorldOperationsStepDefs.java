package com.suresh.projects.movieworld.stepdefs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import com.suresh.projects.movieworld.MovieWorldApplicationTests;
import com.suresh.projects.movieworld.entities.Actor;
import com.suresh.projects.movieworld.entities.Director;
import com.suresh.projects.movieworld.entities.Genre;
import com.suresh.projects.movieworld.entities.Movie;
import com.suresh.projects.movieworld.entities.MovieInfo;
import com.suresh.projects.movieworld.util.CucumberScenarioContext;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class MovieWorldOperationsStepDefs extends MovieWorldApplicationTests {
	Movie response = null;
	Movie movie = null;
	
	@Before
	public void init() {
		movie = new Movie();
	}

	@Given("^movie details$")
	public void movie_details(List<Movie> arg1) throws Throwable {
	    movie.setTitle(arg1.get(0).getTitle());
	    movie.setYear(arg1.get(0).getYear());
	}

	@Given("^movie info to be$")
	public void movie_info_to_be(List<MovieInfo> arg1) throws Throwable {
		movie.setInfo(arg1.get(0));
	}
	@Given("^directors to be$")
	public void directors_to_be(List<Director> arg1) throws Throwable {
		movie.getInfo().setDirectors(arg1);
	}

	@Given("^actors to be$")
	public void actors_to_be(List<Actor> arg1) throws Throwable {
		movie.getInfo().setActors(arg1);
	}

	@Given("^genres to be$")
	public void genres_to_be(List<Genre> arg1) throws Throwable {
		movie.getInfo().setGenres(arg1);
	}

	@When("^the client calls POST /movies$")
	public void the_client_calls_POST_movies() throws Throwable {
		ResponseEntity<Movie> responseMovie = restTemplate.postForEntity("http://localhost:8080/movieworld/movies", 
																			movie, 
																			Movie.class);
		response = responseMovie.getBody();
		CucumberScenarioContext.put("currentStatusCode", responseMovie.getStatusCode());
		globalContext.setMovieIdInTest(responseMovie.getBody().getId());
		globalContext.setMovie(response);
	}

	@Then("^movie should have an id$")
	public void movie_should_have_an_id() throws Throwable {
		assertTrue(response.getId() > 0);
	}

	@Then("^movie should be$")
	public void movie_should_be(List<Movie> arg1) throws Throwable {
		assertEquals("Title didn't match", arg1.get(0).getTitle(), response.getTitle());
		assertEquals("Year didn't match", arg1.get(0).getYear(), response.getYear());
	}

	@When("^Client requests for a movie by Id that exists \"([^\"]*)\"$")
	public void client_requests_for_a_movie_by_Id_that_exists(String arg1) throws Throwable {
		ResponseEntity<Movie> responseMovie = restTemplate.getForEntity("http://localhost:8080/movieworld/movies/"+(Boolean.valueOf(arg1)?globalContext.getMovieIdInTest():9999), 
																		Movie.class);
		CucumberScenarioContext.put("currentStatusCode", responseMovie.getStatusCode());
		CucumberScenarioContext.put("MovieInTest", responseMovie.getBody());
	}

	@Then("^Client should receive a movie in the response$")
	public void client_should_receive_a_movie_in_the_response() throws Throwable {
		Movie movie = CucumberScenarioContext.get("MovieInTest", Movie.class);
		assertEquals("Movie Ids didn't match",  globalContext.getMovieIdInTest(), movie.getId());
	}
	
	@When("^Client requests to update a movie by Id that exists \"([^\"]*)\"$")
	public void client_requests_to_update_a_movie_by_Id_that_exists(String arg1) throws Throwable {
		ResponseEntity<Object> responseMovie = restTemplate.exchange("http://localhost:8080/movieworld/movies/"+(Boolean.valueOf(arg1)?globalContext.getMovieIdInTest():9999), 
																	HttpMethod.PUT, 
																	new HttpEntity<Movie>(globalContext.getMovie()), 
																	Object.class);
		CucumberScenarioContext.put("currentStatusCode", responseMovie.getStatusCode());
	}

	@When("^Client requests to delete a movie by Id that exists \"([^\"]*)\"$")
	public void client_requests_to_delete_a_movie_by_Id_that_exists(String arg1) throws Throwable {
		URI uri = new URI("http://localhost:8080/movieworld/movies/"+(Boolean.valueOf(arg1)?globalContext.getMovieIdInTest():9999));
		try {
			restTemplate.delete(uri);
		} catch (RestClientException e) {
			CucumberScenarioContext.put("currentStatusCode", HttpStatus.INTERNAL_SERVER_ERROR);
			throw new Exception(e);
		}
		CucumberScenarioContext.put("currentStatusCode", HttpStatus.OK);
	}

}
