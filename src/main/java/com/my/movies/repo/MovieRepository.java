package com.my.movies.repo;

import com.my.movies.models.Director;
import com.my.movies.models.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MovieRepository extends MongoRepository<Movie, String> {

	List<Movie> findAllByYear(Integer year);
	List<Movie> findAllByDirector(Director director);
	List<Movie> findDistinctByRateGreaterThanEqual(Double rate);
}
