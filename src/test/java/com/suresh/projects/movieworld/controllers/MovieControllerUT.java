package com.suresh.projects.movieworld.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.junit.After;
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
import org.springframework.hateoas.Resource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.suresh.projects.movieworld.dto.MovieDto;
import com.suresh.projects.movieworld.dto.Movies;
import com.suresh.projects.movieworld.dto.PaginatedResponse;
import com.suresh.projects.movieworld.exceptions.ApiException;
import com.suresh.projects.movieworld.services.MovieService;

@RunWith(MockitoJUnitRunner.class)
public class MovieControllerUT {
	
	@InjectMocks private MovieController classToTest;
	@Mock private MovieService movieService;
	@Rule public ExpectedException exception = ExpectedException.none();
	@Captor	ArgumentCaptor<MovieDto> movieCaptor;
	@Captor	ArgumentCaptor<Long> idCaptor;
	
	@Before
	public void setup() {
		initMocks(classToTest);
	    HttpServletRequest mockRequest = new MockHttpServletRequest();
	    ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
	    RequestContextHolder.setRequestAttributes(servletRequestAttributes);
	}

	@After
	public void teardown() {
	    RequestContextHolder.resetRequestAttributes();
	}	
	
	@Test
	public void shouldFetchMovies() throws Exception {
		List<MovieDto> movies = prepareTestMovies();
		PaginatedResponse expected = new PaginatedResponse();
		expected.setContent(movies.stream().map(m -> new Resource<MovieDto>(m)).collect(Collectors.toList()));
		when(movieService.findPagenated(anyInt(), anyInt())).thenReturn(expected);
		classToTest.findMovies(null, null);
		verify(movieService).findPagenated(eq(1), eq(20));
	}

	@Test
	public void shouldFetchPaginatedMovies() throws Exception {
		List<MovieDto> movies = prepareTestMovies();
		PaginatedResponse expected = new PaginatedResponse();
		expected.setContent(movies.stream().map(m -> new Resource<MovieDto>(m)).collect(Collectors.toList()));
		when(movieService.findPagenated(anyInt(), anyInt())).thenReturn(expected);
		Resource<Movies> actual = classToTest.findMovies(2, 3);
		assertEquals(expected.getContent(), actual.getContent().getMovies());
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
		MovieDto expected = getTestMovie(10);
		when(movieService.findById(10)).thenReturn(Optional.<MovieDto>of(expected));
		Resource<MovieDto> actual = classToTest.findMovieById(10);
		assertEquals(expected, actual.getContent());
	}
	
	@Test
	public void createMovie_ShouldCreateMovie() throws Exception {
		MovieDto expected = new MovieDto();
		expected.setId(0);
		when(movieService.createMovie(eq(expected))).thenReturn(expected);
		classToTest.createMovie(expected);
		verify(movieService).createMovie(movieCaptor.capture());
		assertEquals(expected, movieCaptor.getValue());
	}
	
	@Test
	public void updateMovie_ShouldThrowErrorIdIdLessThanZero() throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("id is invalid");
		classToTest.updateMovie(-1, new MovieDto());
		verify(movieService, never()).updateMovie(any(MovieDto.class));
	}
	
	@Test
	public void updateMovie_ShouldThrowErrorIfIdNotEqualsIdInMovie() throws Exception {
		MovieDto movieToUpdate = getTestMovie(10);
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Invalid request, check the arguments");
		classToTest.updateMovie(5, movieToUpdate);
		verify(movieService, never()).updateMovie(any(MovieDto.class));
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
	
	private List<MovieDto> prepareTestMovies() {
		List<MovieDto> movies = new ArrayList<>();
		for(int i=0; i<10; i++) {
			movies.add(new MovieDto());
		}
		return movies;
	}
	
	private MovieDto getTestMovie(long id) {
		MovieDto movie = new MovieDto();
		movie.setId(id);
		return movie;
	}
}
