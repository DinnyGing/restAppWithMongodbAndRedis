package com.my.movies.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
//@ToString
@Builder
public class Movie {
	String id;
	String title;
	Director director;
	Integer year;
	Double rate;
	Duration duration;
}
