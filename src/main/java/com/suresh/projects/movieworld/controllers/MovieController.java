package com.suresh.projects.movieworld.controllers;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {
	
	@GetMapping("/movies")
	public String findMovies() {
		try {
			String url = "jdbc:mysql://sureshrds.c5n3azxbdqmp.us-east-2.rds.amazonaws.com:3306/";
			String userName = "sureshrds";
			String password = "sureshrds";
			String dbName = "sureshrds";
			String driver = "com.mysql.jdbc.Driver";
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url + dbName, userName, password);	
			return "Test Success";

		} catch (Exception e) {
			e.printStackTrace();
			return "Test Failed";
		}
	}
}
