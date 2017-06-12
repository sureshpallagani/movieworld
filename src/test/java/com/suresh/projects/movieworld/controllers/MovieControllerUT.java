package com.suresh.projects.movieworld.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.suresh.projects.movieworld.entities.Movie;
import com.suresh.projects.movieworld.exceptions.ApiException;
import com.suresh.projects.movieworld.services.MovieService;

@RunWith(MockitoJUnitRunner.class)
public class MovieControllerUT {
	
	@InjectMocks private MovieController classToTest;
	@Mock private MovieService movieService;
	@Rule public ExpectedException exception = ExpectedException.none();
	@Captor	ArgumentCaptor<Movie> movieCaptor;
	@Captor	ArgumentCaptor<Long> idCaptor;
	
	@Before
	public void setUp() {
		initMocks(classToTest);
	}

	@Test
	public void shouldFetchMovies() {
		List<Movie> movies = prepareTestMovies();
		when(movieService.findAll()).thenReturn(movies);
		Iterable<Movie> actual = classToTest.findMovies(null, null);
		verify(movieService, never()).findPagenated(anyInt(), anyInt());
		assertEquals(actual, movies);
	}

	@Test
	public void shouldFetchPaginatedMovies() {
		List<Movie> movies = prepareTestMovies();
		when(movieService.findPagenated(anyInt(), anyInt())).thenReturn(movies);
		Iterable<Movie> actual = classToTest.findMovies(2, 3);
		verify(movieService, never()).findAll();
		assertEquals(actual, movies);
	}
	
	@Test
	public void findById_ShouldThrowErrorForIdLessThanZero() throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("id is invalid");
		classToTest.findMovieById(-1);
		verify(movieService, never()).findById(any());
	}

	@Test
	public void findById_ShouldThrowNotFound() throws Exception {
		exception.expect(ApiException.class);
		exception.expectMessage("Move with Id: 10 doesn't exist");
		when(movieService.findById(10)).thenReturn(Optional.empty());
		classToTest.findMovieById(10);
	}
	
	@Test
	public void findById_ShouldReturnMovie() throws Exception {
		Movie expected = getTestMovie(10);
		when(movieService.findById(10)).thenReturn(Optional.<Movie>of(expected));
		Movie actual = classToTest.findMovieById(10);
		assertEquals(expected, actual);
	}
	
	@Test
	public void createMovie_ShouldCreateMovie() {
		Movie expected = getTestMovie(0);
		classToTest.createMovie(expected);
		verify(movieService).createMovie(movieCaptor.capture());
		assertEquals(expected, movieCaptor.getValue());
	}
	
	@Test
	public void updateMovie_ShouldThrowErrorIdIdLessThanZero() throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("id is invalid");
		classToTest.updateMovie(-1, new Movie());
		verify(movieService, never()).updateMovie(any(Movie.class));
	}
	
	@Test
	public void updateMovie_ShouldThrowErrorIfIdNotEqualsIdInMovie() throws Exception {
		Movie movieToUpdate = getTestMovie(10);
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Invalid request, check the arguments");
		classToTest.updateMovie(5, movieToUpdate);
		verify(movieService, never()).updateMovie(any(Movie.class));
	}
	
	@Test
	public void deleteMovie_ShouldErrorIfIdLessThanZero() throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("id is invalid");
		classToTest.deleteMovie(-1);
		verify(movieService, never()).deleteMovie(any());
	}
	
	@Test
	public void deleteMovie_ShouldDeleteMovie() throws Exception {
		classToTest.deleteMovie(10);
		verify(movieService).deleteMovie(idCaptor.capture());
		assertEquals(10,  idCaptor.getValue().longValue());
	}
	
	private List<Movie> prepareTestMovies() {
		List<Movie> movies = new ArrayList<>();
		for(int i=0; i<10; i++) {
			movies.add(new Movie());
		}
		return movies;
	}
	
	private Movie getTestMovie(long id) {
		Movie movie = new Movie();
		movie.setId(id);
		return movie;
	}
}
