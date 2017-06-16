package com.suresh.projects.movieworld.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieInfoDto {

	private long id;
	private List<DirectorDto> directors;
	private Date release_date;
	private double rating;
	private List<GenreDto> genres;
	private String image_url;
	private String plot;
	private int rank;
	private int running_time_secs;
	private List<ActorDto> actors;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public List<DirectorDto> getDirectors() {
		return directors;
	}
	public void setDirectors(List<DirectorDto> directors) {
		this.directors = directors;
	}
	public Date getRelease_date() {
		return release_date;
	}
	public void setRelease_date(Date release_date) {
		this.release_date = release_date;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public List<GenreDto> getGenres() {
		return genres;
	}
	public void setGenres(List<GenreDto> genres) {
		this.genres = genres;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getPlot() {
		return plot;
	}
	public void setPlot(String plot) {
		this.plot = plot;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public int getRunning_time_secs() {
		return running_time_secs;
	}
	public void setRunning_time_secs(int running_time_secs) {
		this.running_time_secs = running_time_secs;
	}
	public List<ActorDto> getActors() {
		return actors;
	}
	public void setActors(List<ActorDto> actors) {
		this.actors = actors;
	}
	
}
