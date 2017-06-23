package com.suresh.projects.movieworld.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.suresh.projects.movieworld.entities.MovieSetUp;

@Repository
public interface MovieSetUpRepository extends JpaRepository<MovieSetUp, Long> {

}
