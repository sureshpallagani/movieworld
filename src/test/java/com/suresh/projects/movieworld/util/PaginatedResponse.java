package com.suresh.projects.movieworld.util;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.suresh.projects.movieworld.entities.Movie;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaginatedResponse {
	private List<Movie> content;
	private boolean last;
	private int totalPages;
	private int totalElements;
	private int size;
	private int sort;
	private int numberOfElements;
	private boolean first;
	public List<Movie> getContent() {
		return content;
	}
	public void setContent(List<Movie> content) {
		this.content = content;
	}
	public boolean isLast() {
		return last;
	}
	public void setLast(boolean last) {
		this.last = last;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public int getNumberOfElements() {
		return numberOfElements;
	}
	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}
	public boolean isFirst() {
		return first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
	
}
