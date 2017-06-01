package com.suresh.projects.movieworld.stepdefs;

import org.springframework.http.HttpEntity;

import com.suresh.projects.movieworld.MovieWorldApplicationTests;
import com.suresh.projects.movieworld.ScenarioContext;
import com.suresh.projects.movieworld.entities.Movie;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class MovieWorldOperationsStepDefs extends MovieWorldApplicationTests {

	Movie response = null;
	
	@When("^the client calls POST /movies$")
	public void the_client_calls_POST_movies() throws Throwable {
	    Movie movie = ScenarioContext.get("movie", Movie.class);
	    HttpEntity<Movie> entity = new HttpEntity<Movie>(movie);
	    response = restTemplate.postForObject("/movies", entity, Movie.class); 
	}

	@Then("^movie should have an id$")
	public void movie_should_have_an_id() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^movie should be$")
	public void movie_should_be(DataTable arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
	    // E,K,V must be a scalar (String, Integer, Date, enum etc)
	    throw new PendingException();
	}
	
	@Then("^movie info should be$")
	public void movie_info_should_be(DataTable arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
	    // E,K,V must be a scalar (String, Integer, Date, enum etc)
	    throw new PendingException();
	}
	
	@Then("^directors should be$")
	public void directors_should_be(DataTable arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
	    // E,K,V must be a scalar (String, Integer, Date, enum etc)
	    throw new PendingException();
	}
	
	@Then("^actors should be$")
	public void actors_should_be(DataTable arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
	    // E,K,V must be a scalar (String, Integer, Date, enum etc)
	    throw new PendingException();
	}
	
	@Then("^genres should be$")
	public void genres_should_be(DataTable arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
	    // E,K,V must be a scalar (String, Integer, Date, enum etc)
	    throw new PendingException();
	}

}
