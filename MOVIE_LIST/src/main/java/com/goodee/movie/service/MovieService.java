package com.goodee.movie.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface MovieService {
	public void dailyBoxOffice(HttpServletRequest request, Model model);
}
