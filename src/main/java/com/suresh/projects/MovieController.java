package com.suresh.projects;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {
	
	@GetMapping("/movies")
	public String findMovies() {
		return "Test Success";
	}
}
