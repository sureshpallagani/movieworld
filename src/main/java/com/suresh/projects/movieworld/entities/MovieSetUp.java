package com.suresh.projects.movieworld.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@SuppressWarnings("serial")
@Entity
public class MovieSetUp implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Enumerated(EnumType.STRING)
	private SetUpOperation operation;
	@Enumerated(EnumType.STRING)
	private SetUpStatus status;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public SetUpOperation getOperation() {
		return operation;
	}
	public void setOperation(SetUpOperation operation) {
		this.operation = operation;
	}
	public SetUpStatus getStatus() {
		return status;
	}
	public void setStatus(SetUpStatus status) {
		this.status = status;
	}
	
}
