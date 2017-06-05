package com.suresh.projects.movieworld.util;

import org.springframework.stereotype.Component;

import com.suresh.projects.movieworld.entities.Movie;

@Component
public class CucumberGlobalContext {
	private long movieIdInTest;
	private Movie movie;

	public long getMovieIdInTest() {
		return movieIdInTest;
	}

	public void setMovieIdInTest(long movieIdInTest) {
		this.movieIdInTest = movieIdInTest;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	
}
