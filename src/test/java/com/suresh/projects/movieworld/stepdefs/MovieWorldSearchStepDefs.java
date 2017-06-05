package com.suresh.projects.movieworld.stepdefs;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.suresh.projects.movieworld.MovieWorldApplicationTests;
import com.suresh.projects.movieworld.entities.Movie;
import com.suresh.projects.movieworld.util.CucumberScenarioContext;
import com.suresh.projects.movieworld.util.PaginatedResponse;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class MovieWorldSearchStepDefs extends MovieWorldApplicationTests {
	ResponseEntity<List<Movie>> moviesResponse = null;
	ResponseEntity<PaginatedResponse> paginatedResponse = null;
	
	@When("^the client calls /movies$")
	public void the_client_calls_movies() throws Throwable {
		moviesResponse = restTemplate.exchange("http://localhost:8080/movieworld/movies", HttpMethod.GET, null, new ParameterizedTypeReference<List<Movie>>() { });
		CucumberScenarioContext.put("currentStatusCode", moviesResponse.getStatusCode());
	    assertNotNull(moviesResponse);
	}

	@Then("^the client receives status code of (\\d+)$")
	public void the_client_receives_status_code_of(int status) throws Throwable {
		HttpStatus currentStatusCode = CucumberScenarioContext.get("currentStatusCode", HttpStatus.class);
		assertThat("status code didn't match expected : "+ currentStatusCode.value(), currentStatusCode.value(), equalTo(status));
	}

	@Then("^client receives list of movies$")
	public void client_receives_list_of_movies() throws Throwable {
		assertThat("List not received", moviesResponse.getBody(), any(List.class));
	}

	@Given("^page is (\\d+) and size is (\\d+)$")
	public void page_is_and_size_is(int arg1, int arg2) throws Throwable {
		paginatedResponse = restTemplate.exchange("http://localhost:8080/movieworld/movies?page="+arg1+"&size="+arg2, HttpMethod.GET, null, PaginatedResponse.class);
		CucumberScenarioContext.put("lastPage", Integer.valueOf(paginatedResponse.getBody().getTotalPages() - 1));
	    assertNotNull(paginatedResponse);
	}

	@Then("^client receives (\\d+) movies$")
	public void client_receives_movies(int arg1) throws Throwable {
		assertThat("Should return " + arg1 + " elements", paginatedResponse.getBody().getNumberOfElements(), equalTo(arg1));
	}

	@Then("^is first page is \"([^\"]*)\" and is last page is \"([^\"]*)\"$")
	public void is_first_page_is_and_is_last_page_is(String arg1, String arg2) throws Throwable {
		assertThat("Is First Page expected as " + arg1 + " But received " + paginatedResponse.getBody().isFirst(), String.valueOf(paginatedResponse.getBody().isFirst()), equalTo(arg1));
		assertThat("Is Last Page expected as " + arg2 + " But received " + paginatedResponse.getBody().isLast(), String.valueOf(paginatedResponse.getBody().isLast()), equalTo(arg2));
	}

	@Then("^total pages should be (\\d+)$")
	public void total_pages_should_be(int arg1) throws Throwable {
		assertThat("Should receive " + arg1 + " pages, but got " + paginatedResponse.getBody().getTotalPages(), paginatedResponse.getBody().getTotalPages(), equalTo(arg1));
	}

	@Given("^page is last and size is (\\d+)$")
	public void page_is_last_and_size_is(int arg1) throws Throwable {
		paginatedResponse = restTemplate.exchange("http://localhost:8080/movieworld/movies?page="+CucumberScenarioContext.get("lastPage", Integer.class)+"&size="+arg1, HttpMethod.GET, null, PaginatedResponse.class);
	}

}
