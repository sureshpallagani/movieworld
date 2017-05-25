package com.suresh.projects.movieworld.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Genre {
	private String type;
	@Column(name="genre_description")
	private String description;

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
			
}
