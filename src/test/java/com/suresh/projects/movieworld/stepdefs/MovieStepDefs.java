package com.suresh.projects.movieworld.stepdefs;

import java.util.List;

import com.suresh.projects.movieworld.dto.MovieDto;
import com.suresh.projects.movieworld.util.CucumberScenarioContext;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import gherkin.formatter.model.DataTableRow;

public class MovieStepDefs {
	@Given("^movie$")
	public void movie(DataTable arg1) throws Throwable {
		List<DataTableRow> rows = arg1.getGherkinRows();
		MovieDto movie = new MovieDto();
		movie.setTitle(rows.get(1).getCells().get(0));
		movie.setYear(Integer.valueOf(rows.get(1).getCells().get(1)));
	    CucumberScenarioContext.put("movie", movie);
	}
//
//	@Given("^directors$")
//	public void directors(DataTable arg1) throws Throwable {
//		List<Director> directors = new ArrayList<>();
//		arg1.getGherkinRows().stream().skip(1).forEach((row)->{
//			Director director = new Director();
//			director.setName(row.getCells().get(0));
//			directors.add(director);
//		});
//		CucumberScenarioContext.get("movie", Movie.class).getInfo().setDirectors(directors);
//	}
//
//	@Given("^actors$")
//	public void actors(DataTable arg1) throws Throwable {
//		List<Actor> actors = new ArrayList<>();
//		arg1.getGherkinRows().stream().skip(1).forEach((row)->{
//			Actor actor = new Actor();
//			actor.setName(row.getCells().get(0));
//			actors.add(actor);
//		});
//		CucumberScenarioContext.get("movie", Movie.class).getInfo().setActors(actors);
//	}
//
//	@Given("^genres$")
//	public void genres(DataTable arg1) throws Throwable {
//		List<Genre> genres = new ArrayList<>();
//		arg1.getGherkinRows().stream().skip(1).forEach((row)->{
//			Genre genre = new Genre();
//			genre.setType(row.getCells().get(0));
//			genres.add(genre);
//		});
//		CucumberScenarioContext.get("movie", Movie.class).getInfo().setGenres(genres);
//	}
//
//	@Given("^movie info$")
//	public void movie_info(DataTable arg1) throws Throwable {
//		SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
//		MovieInfo movieInfo = CucumberScenarioContext.get("movie", Movie.class).getInfo();
//		List<String> cells = arg1.getGherkinRows().get(1).getCells();
//		movieInfo.setRelease_date(sdf.parse(cells.get(0)));
//		movieInfo.setRating(Double.valueOf(cells.get(1)));
//		movieInfo.setImage_url(cells.get(2));
//		movieInfo.setPlot(cells.get(3));
//		movieInfo.setRank(Integer.parseInt(cells.get(4)));
//		movieInfo.setRunning_time_secs(Integer.parseInt(cells.get(5)));
//		CucumberScenarioContext.get("movie", Movie.class).setInfo(movieInfo);
//	}
//
}
