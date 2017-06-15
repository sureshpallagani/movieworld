package com.suresh.projects.movieworld.util;

import org.springframework.stereotype.Component;

import com.suresh.projects.movieworld.dto.MovieDto;

@Component
public class CucumberGlobalContext {
	private long movieIdInTest;
	private MovieDto movie;

	public long getMovieIdInTest() {
		return movieIdInTest;
	}

	public void setMovieIdInTest(long movieIdInTest) {
		this.movieIdInTest = movieIdInTest;
	}

	public MovieDto getMovie() {
		return movie;
	}

	public void setMovie(MovieDto movie) {
		this.movie = movie;
	}
	
}
