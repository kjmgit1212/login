package com.goodee.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goodee.product.domain.SearchVO;
import com.goodee.product.service.ProductService;

@Controller
public class ProductController {
		
	@Autowired
	private ProductService productService;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@ResponseBody
	@GetMapping(value="/product/search", produces = "application/json")
	public String search(SearchVO vo, Model model) {
	
		return productService.searchProduct(vo);
	}
	
}
