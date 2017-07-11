package com.suresh.projects.movieworld.repositories.mongo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.suresh.projects.movieworld.entities.Movie;

@Repository
public interface MongoMovieRepository extends MongoRepository<Movie, Long>, QueryDslPredicateExecutor<Movie> {

	Page<Movie> findByYear(int year, Pageable pageable);

}
