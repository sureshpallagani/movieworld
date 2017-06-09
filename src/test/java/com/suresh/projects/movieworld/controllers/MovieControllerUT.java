package com.suresh.projects.movieworld.controllers;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.suresh.projects.movieworld.entities.Movie;
import com.suresh.projects.movieworld.services.MovieService;

@RunWith(MockitoJUnitRunner.class)
public class MovieControllerUT {
	
	@InjectMocks private MovieController classToTest;
	@Mock private MovieService movieService;
	
	@Before
	public void setUp() {
		initMocks(classToTest);
	}

	@Test
	public void shouldFindMovies() {
		List<Movie> movies = new ArrayList<>();
		movies.add(new Movie());
		movies.add(new Movie());
		when(movieService.findAll()).thenReturn(movies);
		Iterable<Movie> actual = classToTest.findMovies(null, null);
		verify(movieService, never()).findPagenated(anyInt(), anyInt());
		Assert.assertEquals(actual, movies);
	}
	
	
}
