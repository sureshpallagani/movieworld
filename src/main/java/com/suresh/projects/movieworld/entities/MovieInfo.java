package com.suresh.projects.movieworld.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.util.StringUtils;

@SuppressWarnings("serial")
@Entity
public class MovieInfo implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private Movie movie;
	@ElementCollection
	@CollectionTable(name="director", joinColumns=@JoinColumn(name="movie_info_id"))
	private List<Director> directors;
	private Date release_date;
	private double rating;
	@ElementCollection
	@CollectionTable(name="genre", joinColumns=@JoinColumn(name="movie_info_id"))
	private List<Genre> genres;
	private String image_url;
	private String plot;
	private int rank;
	private int running_time_secs;
	@ElementCollection
	@CollectionTable(name="actor", joinColumns=@JoinColumn(name="movie_info_id"))
	private List<Actor> actors;

	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public List<Director> getDirectors() {
		return directors;
	}
	public void setDirectors(List<Director> directors) {
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
	public List<Genre> getGenres() {
		return genres;
	}
	public void setGenres(List<Genre> genres) {
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
		if (!StringUtils.isEmpty(plot) && plot.length() > 255) {
			this.plot = plot.substring(0, 255);
		} else {
			this.plot = plot;
		}
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
	public List<Actor> getActors() {
		return actors;
	}
	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}
	
}
