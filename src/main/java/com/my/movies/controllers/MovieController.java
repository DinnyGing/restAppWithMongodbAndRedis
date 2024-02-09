package com.my.movies.controllers;

import com.my.movies.dao.MovieDAO;
import com.my.movies.models.Director;
import com.my.movies.models.Movie;
import com.my.movies.services.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/movies")
public class MovieController {
	MovieService movieService;

	@GetMapping("/")
	@Cacheable(value = "movies")
	public List<Movie> getAll(@RequestParam(value = "rate", defaultValue = "-1") Double rate,
							  @RequestParam(value = "year", defaultValue = "-1") Integer year,
							  @RequestBody(required = false) Director director){
		return movieService.allMovies(rate, year, director);
	}
	@GetMapping("/{id}")
	@Cacheable(value = "movie", key = "#id")
	public Movie getOne(@PathVariable String id){
		return movieService.movieById(id);
	}
	@DeleteMapping("/{id}")
	@Caching(
			evict = {@CacheEvict(value = "movies", allEntries = true),
					@CacheEvict(cacheNames = "movie", key = "#id", beforeInvocation = true)}
	)
	public ResponseEntity deleteOne(@PathVariable String id){
		return ResponseEntity.ok(movieService.deleteMovie(id));
	}
	@PutMapping("/create")
	@Caching(evict = @CacheEvict(value = "movies", allEntries = true))
	public ResponseEntity createMovie(@RequestBody MovieDAO movie){
		return ResponseEntity.ok(movieService.createMovie(movie));
	}
	@PostMapping("/update/{id}")
	@Caching(
			evict = {@CacheEvict(value = "movies", allEntries = true)},
			put = {@CachePut(value = "movie", key = "#id")}
	)
	public Movie updateMovie(@PathVariable String id, @RequestBody MovieDAO movie){
		return movieService.updateMovie(movie, id);
	}
}
