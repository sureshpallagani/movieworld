package com.suresh.projects.movieworld.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDto {
	
	private long id;
	private String title;
	private int year;
	private MovieInfoDto info;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	@JsonIgnore
	public MovieInfoDto getInfo() {
		return info;
	}
	public void setInfo(MovieInfoDto info) {
		this.info = info;
	}

}
