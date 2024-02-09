package com.my.movies.services;

import com.my.movies.dao.MovieDAO;
import com.my.movies.models.Director;
import com.my.movies.models.Movie;
import com.my.movies.repo.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MovieService {
	private MovieRepository movieRepository;

	public Movie createMovie(MovieDAO movieDAO){
		Movie movie = Movie.builder().title(movieDAO.getTitle()).year(movieDAO.getYear()).director(movieDAO.getDirector())
				.duration(movieDAO.getDuration()).rate(movieDAO.getRate()).build();
		return movieRepository.save(movie);
	}
	public Movie updateMovie(MovieDAO movieDAO, String id){
		Movie movie = movieById(id);
		movie.setTitle(movieDAO.getTitle());
		movie.setYear(movieDAO.getYear());
		movie.setRate(movieDAO.getRate());
		movie.setDuration(movieDAO.getDuration());
		movie.setDirector(movieDAO.getDirector());
		return movieRepository.save(movie);
	}
	public boolean deleteMovie(String id){
		if(movieRepository.findById(id).isPresent()) {
			movieRepository.delete(movieById(id));
			return true;
		}
		return false;
	}

	public Movie movieById(String id){
		return movieRepository.findById(id).orElse(null);
	}
	public List<Movie> allMovies(Double rate, Integer year, Director director){
		return switch (chooseParams(rate, year, director)) {
			case "rate" -> allMoviesByRateGatherThen(rate);
			case "year" -> allMoviesByYear(year);
			case "director" -> allMoviesByDirector(director);
			default -> movieRepository.findAll();
		};
	}
	private String chooseParams(Double rate, Integer year, Director director){
		String choice = "";
		if(rate != -1){
			choice = "rate";
		}
		else if(year != -1){
			choice = "year";
		}
		else if(director != null){
			choice = "director";
		}
		return choice;
	}
	public List<Movie> allMoviesByYear(Integer year){
		return movieRepository.findAllByYear(year);
	}
	public List<Movie> allMoviesByRateGatherThen(Double rate){
		return movieRepository.findDistinctByRateGreaterThanEqual(rate);
	}
	public List<Movie> allMoviesByDirector(Director director){
		return movieRepository.findAllByDirector(director);
	}
}
