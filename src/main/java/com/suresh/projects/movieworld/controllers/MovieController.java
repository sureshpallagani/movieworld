package com.suresh.projects.movieworld.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {
	
	@GetMapping("/movies")
	public String getAllMovies() {
		return "Movies List Test";
	}
}
