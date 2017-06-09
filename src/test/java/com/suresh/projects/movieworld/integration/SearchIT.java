package com.suresh.projects.movieworld.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;

import com.suresh.projects.movieworld.IntegrationTests;

public class SearchIT extends IntegrationTests {

	@Test
	public void shouldReturnAllMovies() throws Exception {
		mockMvc.perform(get("/movies"))
				.andDo(print())
				.andExpect(status().isOk());
	}
}
