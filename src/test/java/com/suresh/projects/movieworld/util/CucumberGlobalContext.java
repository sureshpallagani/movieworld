package com.suresh.projects.movieworld.util;

import org.springframework.stereotype.Component;

import com.suresh.projects.movieworld.dto.MovieDto;

@Component
public class CucumberGlobalContext {
	private long movieIdInTest;
	private MovieDto movie;
	private long newlyCreatedMovieId;

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

	public long getNewlyCreatedMovieId() {
		return newlyCreatedMovieId;
	}

	public void setNewlyCreatedMovieId(long newlyCreatedMovieId) {
		this.newlyCreatedMovieId = newlyCreatedMovieId;
	}
	
}
