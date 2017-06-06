package com.suresh.projects.movieworld.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.suresh.projects.movieworld.entities.Movie;
import com.suresh.projects.movieworld.exceptions.ApiException;
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

	public Optional<Movie> findById(long id) {
		return Optional.ofNullable(movieRepository.findOne(id));
	}

	public Movie updateMovie(Movie movie) throws ApiException {
		if (movieRepository.exists(movie.getId())) {
			return movieRepository.saveAndFlush(movie);
		} else {
			throw new ApiException("Movie with Id : " + movie.getId() + " doesn't exist to update", HttpStatus.NOT_FOUND);
		}
	}

	public void deleteMovie(long id) throws ApiException {
		if (movieRepository.exists(id)) {
			movieRepository.delete(id);
		} else {
			throw new ApiException("Movie with Id : " + id + " doesn't exist to delete", HttpStatus.NOT_FOUND);
		}
		
	}

	public Movie createMovies(List<Movie> movies) {
		movieRepository.save(movies);
		return null;
	}
	
	public Iterable<Movie> findPagenated(Integer page, Integer size) {
		return movieRepository.findAll(new PageRequest(page, size));
	}
	
}
