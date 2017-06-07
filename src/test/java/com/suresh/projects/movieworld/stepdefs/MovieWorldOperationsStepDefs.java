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
import com.suresh.projects.movieworld.util.PaginatedResponse;

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

	@Given("^movie that exists i want to set it in context for further scenarios in this feature\\.$")
	public void movie_that_exists_i_want_to_set_it_in_context_for_further_scenarios_in_this_feature() throws Throwable {
		ResponseEntity<PaginatedResponse> paginatedResponse = restTemplate.exchange("http://localhost:8080/movieworld/movies?page=0&size=1", HttpMethod.GET, null, PaginatedResponse.class);
		globalContext.setMovie(paginatedResponse.getBody().getContent().get(0));
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
		if (Boolean.valueOf(arg1) == Boolean.TRUE) {
			ResponseEntity<Movie> response = restTemplate.getForEntity("http://localhost:8080/movieworld/movies/"+globalContext.getMovie().getId(), Movie.class);
			CucumberScenarioContext.put("currentStatusCode", response.getStatusCode());
			CucumberScenarioContext.put("MovieInTest", response.getBody());
		} else {
			ResponseEntity<Object> response = restTemplate.getForEntity("http://localhost:8080/movieworld/movies/"+9999, Object.class);
			CucumberScenarioContext.put("currentStatusCode", response.getStatusCode());
		}
	}

	@Then("^Client should receive a movie in the response$")
	public void client_should_receive_a_movie_in_the_response() throws Throwable {
		Movie movie = CucumberScenarioContext.get("MovieInTest", Movie.class);
		assertEquals("Movie Ids didn't match",  globalContext.getMovie().getId(), movie.getId());
	}
	
	@When("^Client requests to update a movie by Id that exists \"([^\"]*)\"$")
	public void client_requests_to_update_a_movie_by_Id_that_exists(String arg1) throws Throwable {
		Movie movie = globalContext.getMovie();
		if (Boolean.valueOf(arg1) == Boolean.FALSE) {
			movie.setId(9999);
		}
		ResponseEntity<Object> responseMovie = restTemplate.exchange("http://localhost:8080/movieworld/movies/"+(Boolean.valueOf(arg1)?globalContext.getMovie().getId():9999), 
																	HttpMethod.PUT, 
																	new HttpEntity<Movie>(movie), 
																	Object.class);
		CucumberScenarioContext.put("currentStatusCode", responseMovie.getStatusCode());
	}


	@When("^The Movie id didn't match the id in uri$")
	public void the_Movie_id_didn_t_match_the_id_in_uri() throws Throwable {
		ResponseEntity<Object> responseMovie = restTemplate.exchange("http://localhost:8080/movieworld/movies/"+9999, 
																		HttpMethod.PUT, 
																		new HttpEntity<Movie>(movie), 
																		Object.class);
		CucumberScenarioContext.put("currentStatusCode", responseMovie.getStatusCode());
	}

	@When("^Client requests to delete a movie by Id that exists \"([^\"]*)\"$")
	public void client_requests_to_delete_a_movie_by_Id_that_exists(String arg1) throws Throwable {
		ResponseEntity<Object> response = restTemplate.exchange("http://localhost:8080/movieworld/movies/"+(Boolean.valueOf(arg1)?globalContext.getMovie().getId():9999), 
															HttpMethod.DELETE, 
															new HttpEntity<Movie>(movie), 
															Object.class);
		CucumberScenarioContext.put("currentStatusCode", response.getStatusCode());
	}

}
