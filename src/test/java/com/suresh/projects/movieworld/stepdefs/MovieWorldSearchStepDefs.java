package com.suresh.projects.movieworld.stepdefs;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suresh.projects.movieworld.MovieWorldApplicationTests;
import com.suresh.projects.movieworld.dto.Movies;
import com.suresh.projects.movieworld.util.CucumberScenarioContext;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class MovieWorldSearchStepDefs extends MovieWorldApplicationTests {
	ResponseEntity<Resource<Movies>> paginatedResponse = null;
	private String urlAppend = "";
	RestTemplate rt = new RestTemplate();
	
	@Before
	public void setUp() {
		urlAppend = "";
	}
	
	@When("^the client calls /movies$")
	public void the_client_calls_movies() throws Throwable {
		paginatedResponse = getRestTemplate().exchange("http://localhost:8080/movieworld/movies"+urlAppend, HttpMethod.GET, null, new ParameterizedTypeReference<Resource<Movies>>() {});
		CucumberScenarioContext.put("currentStatusCode", paginatedResponse.getStatusCode());
	    assertNotNull(paginatedResponse);
	}

	@Then("^the client receives status code of (\\d+)$")
	public void the_client_receives_status_code_of(int status) throws Throwable {
		HttpStatus currentStatusCode = CucumberScenarioContext.get("currentStatusCode", HttpStatus.class);
		assertThat("status code didn't match expected : "+ currentStatusCode.value(), currentStatusCode.value(), equalTo(status));
	}

	@Then("^response should have \"([^\"]*)\" link$")
	public void response_should_have_link(String arg1) throws Throwable {
	    assertNotNull("Expected Link of type: "+arg1, paginatedResponse.getBody().getLink(arg1));
	}
	
	@Given("^page is (\\d+) and size is (\\d+)$")
	public void page_is_and_size_is(int arg1, int arg2) throws Throwable {
		urlAppend = "?page="+arg1+"&size="+arg2;
	}

	@Then("^client receives (\\d+) movies$")
	public void client_receives_movies(int arg1) throws Throwable {
		assertThat("Should return " + arg1 + " elements", paginatedResponse.getBody().getContent().getMovies().size(), equalTo(arg1));
	}

	protected RestTemplate getRestTemplate() {
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    mapper.registerModule(new Jackson2HalModule());

	    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
	    converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
	    converter.setObjectMapper(mapper);

	    List<HttpMessageConverter<?>> converterList = new ArrayList<HttpMessageConverter<?>>();
	    converterList.add(converter);
	    RestTemplate restTemplate = new RestTemplate(converterList);
	    return restTemplate;
	}

}
