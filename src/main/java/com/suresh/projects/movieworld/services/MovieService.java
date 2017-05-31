package com.suresh.projects.movieworld.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.suresh.projects.movieworld.entities.Movie;
import com.suresh.projects.movieworld.repositories.MovieRepository;

@Service
public class MovieService {
	@Autowired
	private MovieRepository movieRepository;
	
	public Iterable<Movie> findAll() {
		return movieRepository.findAll();
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Movie createMovie(Movie movie) {
		return movieRepository.saveAndFlush(movie);
	}

	public Movie findById(long id) {
		return movieRepository.findOne(id);
	}

	public Movie updateMovie(Movie movie) {
		return movieRepository.saveAndFlush(movie);
	}

	public void deleteMovie(long id) {
		movieRepository.delete(id);
	}

	public Movie createMovies(List<Movie> movies) {
		movieRepository.save(movies);
		return null;
	}
	
	public Iterable<Movie> findPagenated(Integer page, Integer size) {
		return movieRepository.findAll(new PageRequest(page, size));
	}
}
