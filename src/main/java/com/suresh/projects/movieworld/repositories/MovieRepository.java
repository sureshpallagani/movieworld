package com.suresh.projects.movieworld.repositories;

import org.springframework.data.repository.CrudRepository;

import com.suresh.projects.movieworld.entities.Movie;

public interface MovieRepository extends CrudRepository<Movie, Integer> {

}
