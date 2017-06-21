package com.suresh.projects.movieworld.stepdefs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.suresh.projects.movieworld.MovieWorldApplicationTests;
import com.suresh.projects.movieworld.dto.ActorDto;
import com.suresh.projects.movieworld.dto.DirectorDto;
import com.suresh.projects.movieworld.dto.GenreDto;
import com.suresh.projects.movieworld.dto.MovieDto;
import com.suresh.projects.movieworld.dto.MovieInfoDto;
import com.suresh.projects.movieworld.resources.MoviesResource;
import com.suresh.projects.movieworld.util.CucumberScenarioContext;

import cucumber.api.DataTable;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class MovieWorldOperationsStepDefs extends MovieWorldApplicationTests {
	MovieDto response = null;
	MovieDto movie = null;
	MovieInfoDto info = null;
	
	@Before
	public void init() {
		movie = new MovieDto();
	}

	@Given("^movie that exists i want to set it in context for further scenarios in this feature\\.$")
	public void movie_that_exists_i_want_to_set_it_in_context_for_further_scenarios_in_this_feature() throws Throwable {
		ResponseEntity<MoviesResource> movies = restTemplate.exchange("http://localhost:8080/movieworld/movies?page=1&size=5", 
																					HttpMethod.GET, null, 
																					MoviesResource.class);
		globalContext.setMovie(movies.getBody().getMovies().get(0));
	}
	
	@Given("^movie details$")
	public void movie_details(List<MovieDto> arg1) throws Throwable {
	    movie.setTitle(arg1.get(0).getTitle());
	    movie.setYear(arg1.get(0).getYear());
	}
	
	@Given("^directors as$")
	public void directors_as(List<DirectorDto> arg1) throws Throwable {
		info = CucumberScenarioContext.get("movieInfo", MovieInfoDto.class);
		info.setDirectors(arg1);
		CucumberScenarioContext.put("movieInfo", info);
	}

	@Given("^actors as$")
	public void actors_as(List<ActorDto> arg1) throws Throwable {
		info = CucumberScenarioContext.get("movieInfo", MovieInfoDto.class);
		info.setActors(arg1);
		CucumberScenarioContext.put("movieInfo", info);
	}

	@Given("^genres as$")
	public void genres_as(List<GenreDto> arg1) throws Throwable {
		info = CucumberScenarioContext.get("movieInfo", MovieInfoDto.class);
	    info.setGenres(arg1);
		CucumberScenarioContext.put("movieInfo", info);
	}

	@Given("^movie info$")
	public void movie_info(DataTable arg1) throws Throwable {
		info = new MovieInfoDto();
		info.setId(globalContext.getNewlyCreatedMovieId());
		CucumberScenarioContext.put("movieInfo", info);
		SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
		MovieInfoDto movieInfo = CucumberScenarioContext.get("movieInfo", MovieInfoDto.class);
		List<String> cells = arg1.getGherkinRows().get(1).getCells();
		movieInfo.setRelease_date(sdf.parse(cells.get(0)));
		movieInfo.setRating(Double.valueOf(cells.get(1)));
		movieInfo.setImage_url(cells.get(2));
		movieInfo.setPlot(cells.get(3));
		movieInfo.setRank(Integer.parseInt(cells.get(4)));
		movieInfo.setRunning_time_secs(Integer.parseInt(cells.get(5)));
	}

	@When("^the client calls POST info$")
	public void the_client_calls_POST_movies_id_info() throws Throwable {
		ResponseEntity<MovieInfoDto> responseMovie = restTemplate.postForEntity("http://localhost:8080/movieworld/movies/"+info.getId()+"/info", 
				info, 
				MovieInfoDto.class);
		CucumberScenarioContext.put("currentStatusCode", responseMovie.getStatusCode());
	}

	@When("^the client calls POST /movies$")
	public void the_client_calls_POST_movies() throws Throwable {
		ResponseEntity<MovieDto> responseMovie = restTemplate.postForEntity("http://localhost:8080/movieworld/movies", 
																			movie, 
																			MovieDto.class);
		response = responseMovie.getBody();
		globalContext.setNewlyCreatedMovieId(response.getId());
		CucumberScenarioContext.put("currentStatusCode", responseMovie.getStatusCode());
	}

	@Then("^movie should have an id$")
	public void movie_should_have_an_id() throws Throwable {
		assertTrue(response.getId() > 0);
	}

	@Then("^movie should be$")
	public void movie_should_be(List<MovieDto> arg1) throws Throwable {
		assertEquals("Title didn't match", arg1.get(0).getTitle(), response.getTitle());
		assertEquals("Year didn't match", arg1.get(0).getYear(), response.getYear());
	}

	@When("^Client requests for a movie by Id that exists \"([^\"]*)\"$")
	public void client_requests_for_a_movie_by_Id_that_exists(String arg1) throws Throwable {
		if (Boolean.valueOf(arg1) == Boolean.TRUE) {
			ResponseEntity<MovieDto> response = restTemplate.getForEntity("http://localhost:8080/movieworld/movies/"+globalContext.getMovie().getId(), MovieDto.class);
			CucumberScenarioContext.put("currentStatusCode", response.getStatusCode());
			CucumberScenarioContext.put("MovieInTest", response.getBody());
		} else {
			ResponseEntity<Object> response = restTemplate.getForEntity("http://localhost:8080/movieworld/movies/"+9999, Object.class);
			CucumberScenarioContext.put("currentStatusCode", response.getStatusCode());
		}
	}

	@Then("^Client should receive a movie in the response$")
	public void client_should_receive_a_movie_in_the_response() throws Throwable {
		MovieDto movie = CucumberScenarioContext.get("MovieInTest", MovieDto.class);
		assertEquals("Movie Ids didn't match",  globalContext.getMovie().getId(), movie.getId());
	}
	
	@When("^Client requests to update a movie by Id that exists \"([^\"]*)\"$")
	public void client_requests_to_update_a_movie_by_Id_that_exists(String arg1) throws Throwable {
		MovieDto movie = globalContext.getMovie();
		if (Boolean.valueOf(arg1) == Boolean.FALSE) {
			movie.setId(9999);
		}
		ResponseEntity<Object> responseMovie = restTemplate.exchange("http://localhost:8080/movieworld/movies/"+(Boolean.valueOf(arg1)?globalContext.getMovie().getId():9999), 
																	HttpMethod.PUT, 
																	new HttpEntity<MovieDto>(movie), 
																	Object.class);
		CucumberScenarioContext.put("currentStatusCode", responseMovie.getStatusCode());
	}


	@When("^The Movie id didn't match the id in uri$")
	public void the_Movie_id_didn_t_match_the_id_in_uri() throws Throwable {
		ResponseEntity<Object> responseMovie = restTemplate.exchange("http://localhost:8080/movieworld/movies/"+9999, 
																		HttpMethod.PUT, 
																		new HttpEntity<MovieDto>(movie), 
																		Object.class);
		CucumberScenarioContext.put("currentStatusCode", responseMovie.getStatusCode());
	}

	@When("^Client requests to delete a movie by Id that exists \"([^\"]*)\"$")
	public void client_requests_to_delete_a_movie_by_Id_that_exists(String arg1) throws Throwable {
		ResponseEntity<Object> response = restTemplate.exchange("http://localhost:8080/movieworld/movies/"+(Boolean.valueOf(arg1)?globalContext.getMovie().getId():9999), 
															HttpMethod.DELETE, 
															new HttpEntity<MovieDto>(movie), 
															Object.class);
		CucumberScenarioContext.put("currentStatusCode", response.getStatusCode());
	}

}
