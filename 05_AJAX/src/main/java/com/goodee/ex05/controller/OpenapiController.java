package com.goodee.ex05.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goodee.ex05.service.OpenApiService;

@Controller
public class OpenapiController {

	@Autowired
	private OpenApiService openApiService;
	
	@ResponseBody
	@GetMapping(value="/dailyBoxOffice")
	public String dailyBoxOffiec(String language) {
		return openApiService.dailyBoxOffice(language);
	}
	
}