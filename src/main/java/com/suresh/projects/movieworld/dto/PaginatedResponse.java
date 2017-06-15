package com.suresh.projects.movieworld.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.suresh.projects.movieworld.dto.MovieDto;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaginatedResponse {
	private List<MovieDto> content;
	private boolean last;
	private int totalPages;
	private long totalElements;
	private int size;
	private int sort;
	private int numberOfElements;
	private boolean first;
	public List<MovieDto> getContent() {
		return content;
	}
	public void setContent(List<MovieDto> content) {
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
	public long getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(long totalElements) {
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
