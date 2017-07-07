package com.suresh.projects.movieworld.stepdefs;

import java.util.ArrayList;
import java.util.List;

import com.suresh.projects.movieworld.MovieWorldApplicationTests;
import com.suresh.projects.movieworld.dto.ActorDto;
import com.suresh.projects.movieworld.dto.DirectorDto;
import com.suresh.projects.movieworld.dto.GenreDto;
import com.suresh.projects.movieworld.dto.MovieDto;
import com.suresh.projects.movieworld.util.CucumberScenarioContext;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import gherkin.formatter.model.DataTableRow;

public class MovieStepDefs extends MovieWorldApplicationTests {
	
	@Given("^movie$")
	public void movie(DataTable arg1) throws Throwable {
		List<DataTableRow> rows = arg1.getGherkinRows();
		MovieDto movie = new MovieDto();
		movie.setTitle(rows.get(1).getCells().get(0));
		movie.setYear(Integer.valueOf(rows.get(1).getCells().get(1)));
	    CucumberScenarioContext.put("movie", movie);
	}

	@Given("^directors$")
	public void directors(DataTable arg1) throws Throwable {
		List<DirectorDto> directors = new ArrayList<>();
		arg1.getGherkinRows().stream().skip(1).forEach((row)->{
			DirectorDto director = new DirectorDto();
			director.setName(row.getCells().get(0));
			directors.add(director);
		});
		CucumberScenarioContext.get("movie", MovieDto.class).getInfo().setDirectors(directors);
	}

	@Given("^actors$")
	public void actors(DataTable arg1) throws Throwable {
		List<ActorDto> actors = new ArrayList<>();
		arg1.getGherkinRows().stream().skip(1).forEach((row)->{
			ActorDto actor = new ActorDto();
			actor.setName(row.getCells().get(0));
			actors.add(actor);
		});
		CucumberScenarioContext.get("movie", MovieDto.class).getInfo().setActors(actors);
	}

	@Given("^genres$")
	public void genres(DataTable arg1) throws Throwable {
		List<GenreDto> genres = new ArrayList<>();
		arg1.getGherkinRows().stream().skip(1).forEach((row)->{
			GenreDto genre = new GenreDto();
			genre.setType(row.getCells().get(0));
			genres.add(genre);
		});
		CucumberScenarioContext.get("movie", MovieDto.class).getInfo().setGenres(genres);
	}

}
