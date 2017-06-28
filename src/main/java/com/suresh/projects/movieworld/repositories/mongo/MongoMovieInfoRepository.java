package com.suresh.projects.movieworld.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.suresh.projects.movieworld.entities.MovieInfo;

@Repository
public interface MongoMovieInfoRepository extends MongoRepository<MovieInfo, Long> {

}
