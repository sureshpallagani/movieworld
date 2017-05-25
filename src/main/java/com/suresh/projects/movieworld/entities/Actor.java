package com.suresh.projects.movieworld.entities;

import javax.persistence.Embeddable;

@Embeddable
public class Actor {
	private String name;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
