package com.goodee.movie.controller;



import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.goodee.movie.service.MovieService;

@Controller
public class MovieController {

	@Autowired
	private MovieService movieService;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/movie")
	public String movie(HttpServletRequest request, Model model) {
		movieService.dailyBoxOffice(request, model);
		movieService.dailyBoxOffice(request, model);
		return "movie";
	}
	
	
	
	
	
	
	
}