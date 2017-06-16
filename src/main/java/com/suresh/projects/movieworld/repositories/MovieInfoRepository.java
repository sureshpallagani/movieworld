package com.suresh.projects.movieworld.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.suresh.projects.movieworld.entities.MovieInfo;

@Repository
public interface MovieInfoRepository extends JpaRepository<MovieInfo, Long> {

}
