package com.suresh.projects.movieworld.controllers;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.suresh.projects.movieworld.entities.Movie;
import com.suresh.projects.movieworld.repositories.MovieRepository;

@RestController
public class MovieController {
	
	@Autowired
	private MovieRepository movieRepository;
	
	@GetMapping("/movies")
	public String findMovies() {
		try {
			List<Movie> movies = new ArrayList();
			Movie movie = new Movie();
			movie.setRatingId(1);
			movie.setTitle("Test Movie");
			movie.setYearReleased(2017);
			movie.setDateReleased(DateTime.now().toDate());
			movies.add(movie);
			Iterable<Movie> movies1 = movieRepository.findAll();
//			return String.valueOf(movie.getId());
//			String url = "jdbc:mysql://myrdsmysql.c5n3azxbdqmp.us-east-2.rds.amazonaws.com:3306/";
//			String userName = "myrdsmysql";
//			String password = "myrdsmysql";
//			String dbName = "myrdsmysql";
//			String driver = "com.mysql.jdbc.Driver";
//			Class.forName(driver);
//			Connection connection = DriverManager.getConnection(url + dbName, userName, password);	
			return "Test Success";
		} catch (Exception e) {
			e.printStackTrace();
			return "Test Failed";
		}
	}
	
	@PostMapping("/movies")
	public String createMovies(@RequestBody List<Movie> movies) {
		return "Created";
	}
}
