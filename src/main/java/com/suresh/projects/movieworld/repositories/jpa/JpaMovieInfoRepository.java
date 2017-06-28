package com.suresh.projects.movieworld.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.suresh.projects.movieworld.entities.MovieInfo;

@Repository
public interface JpaMovieInfoRepository extends JpaRepository<MovieInfo, Long> {

}
