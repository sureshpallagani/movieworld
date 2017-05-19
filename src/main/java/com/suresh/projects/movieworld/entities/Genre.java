package com.suresh.projects.movieworld.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Genre {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String type;
	@ManyToMany(mappedBy = "genres")
	private List<MovieInfo> movies;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<MovieInfo> getMovies() {
		return movies;
	}
	public void setMovies(List<MovieInfo> movies) {
		this.movies = movies;
	}
		
}
