package com.suresh.projects.movieworld.dto;

import java.io.Serializable;

public class MovieDto implements Serializable {
	
	private long id;
	private String title;
	private int year;
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

}
