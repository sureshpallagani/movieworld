package com.suresh.projects.movieworld.resources;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.suresh.projects.movieworld.dto.MovieDto;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MoviesResource {
	private List<MovieDto> movies;

	public List<MovieDto> getMovies() {
		return movies;
	}

	public void setMovies(List<MovieDto> movies) {
		this.movies = movies;
	}

	
}
