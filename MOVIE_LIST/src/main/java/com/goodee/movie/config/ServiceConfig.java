package com.goodee.movie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.goodee.movie.service.MovieService;
import com.goodee.movie.service.MovieServiceImpl;

@Configuration
public class ServiceConfig {

	@Bean
	public MovieService movieService() {
		return new MovieServiceImpl();
	}
	
}
