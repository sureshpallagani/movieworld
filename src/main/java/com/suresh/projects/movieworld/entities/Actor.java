package com.suresh.projects.movieworld.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Actor {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	@ManyToMany(mappedBy = "actors")
	private List<MovieInfo> movies;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<MovieInfo> getMovies() {
		return movies;
	}
	public void setMovies(List<MovieInfo> movies) {
		this.movies = movies;
	}
		
}
