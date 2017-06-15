package com.suresh.projects.movieworld.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.suresh.projects.movieworld.dto.MovieDto;
import com.suresh.projects.movieworld.dto.PaginatedResponse;
import com.suresh.projects.movieworld.entities.Movie;
import com.suresh.projects.movieworld.exceptions.ApiException;
import com.suresh.projects.movieworld.repositories.MovieRepository;

@Service
public class MovieService {
	@Autowired
	private MovieRepository movieRepository;
	@Autowired
	private ModelMapper modelMapper;
	
	public List<MovieDto> findAll() {
		List<MovieDto> movieDtos = new ArrayList<>();
		movieRepository.findAll().forEach(m -> movieDtos.add(modelMapper.map(m, MovieDto.class)));
		return movieDtos;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public MovieDto createMovie(MovieDto movieDto) {
		Movie movie = movieRepository.saveAndFlush(modelMapper.map(movieDto, Movie.class));
		return modelMapper.map(movie, MovieDto.class);
	}

	public Optional<MovieDto> findById(long id) {
		Optional<Movie> movie = Optional.ofNullable(movieRepository.findOne(id));
		if (movie.isPresent()) {
			return Optional.of(modelMapper.map(movie.get(), MovieDto.class));
		}
		return Optional.ofNullable(null);
	}

	public MovieDto updateMovie(MovieDto movieDto) throws ApiException {
		if (movieRepository.exists(movieDto.getId())) {
			Movie movie = movieRepository.saveAndFlush(modelMapper.map(movieDto, Movie.class));
			return modelMapper.map(movie, MovieDto.class);
		} else {
			throw new ApiException("Movie with Id : " + movieDto.getId() + " doesn't exist to update", HttpStatus.NOT_FOUND);
		}
	}

	public void deleteMovie(long id) throws ApiException {
		if (movieRepository.exists(id)) {
			movieRepository.delete(id);
		} else {
			throw new ApiException("Movie with Id : " + id + " doesn't exist to delete", HttpStatus.NOT_FOUND);
		}
		
	}

	public Movie createMovies(List<MovieDto> movieDtos) {
		List<Movie> movies = new ArrayList<>();
		movieDtos.parallelStream().forEach((m) -> modelMapper.map(m, Movie.class));
		movieRepository.save(movies);
		return null;
	}
	
	public PaginatedResponse findPagenated(Integer page, Integer size) {
		PaginatedResponse paginatedResponse = new PaginatedResponse();
		Page<Movie> movies = movieRepository.findAll(new PageRequest(page, size));
		List<MovieDto> movieDtos = new ArrayList<>();
		movies.getContent().forEach((m) -> movieDtos.add(modelMapper.map(m, MovieDto.class)));
		modelMapper.map(movies, paginatedResponse);
		paginatedResponse.setContent(movieDtos);
		return paginatedResponse;
	}
	
}
