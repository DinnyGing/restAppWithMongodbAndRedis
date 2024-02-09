package com.my.movies.dao;

import com.my.movies.models.Director;
import com.my.movies.models.Duration;
import lombok.Data;

@Data
public class MovieDAO {
	String title;
	Director director;
	Integer year;
	Double rate;
	Duration duration;
}
