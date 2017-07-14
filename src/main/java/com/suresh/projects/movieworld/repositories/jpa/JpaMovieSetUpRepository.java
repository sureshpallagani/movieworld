package com.suresh.projects.movieworld.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.suresh.projects.movieworld.entities.MovieSetUp;

@Repository
public interface JpaMovieSetUpRepository extends JpaRepository<MovieSetUp, Long> {

}
