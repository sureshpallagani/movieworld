package com.suresh.projects.movieworld.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.suresh.projects.movieworld.RdsMySqlDbConnection;
import com.suresh.projects.movieworld.entities.Movie;

@RestController
public class MovieController {
	
	@Autowired
	private RdsMySqlDbConnection rdsMySqlConnection;
	
	@GetMapping("/movies")
	public String findMovies() {
		try {
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
