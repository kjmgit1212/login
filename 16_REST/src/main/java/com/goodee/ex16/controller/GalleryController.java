package com.goodee.ex16.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GalleryController {

	@GetMapping("/gallery/management")
	public String management() {
		return "gallery/manage";
	}
	
}
