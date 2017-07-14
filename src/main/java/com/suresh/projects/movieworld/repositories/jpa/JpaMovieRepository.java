package com.suresh.projects.movieworld.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.suresh.projects.movieworld.entities.Movie;

@Repository
public interface JpaMovieRepository extends JpaRepository<Movie, Long> {

}
