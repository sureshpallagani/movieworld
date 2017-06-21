package com.suresh.projects.movieworld.dto;

import java.util.List;

import org.springframework.hateoas.Resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movies {
	private List<Resource<MovieDto>> movies;

	public Movies() {
		super();
	}

	public List<Resource<MovieDto>> getMovies() {
		return movies;
	}

	public void setMovies(List<Resource<MovieDto>> movies) {
		this.movies = movies;
	}

}
