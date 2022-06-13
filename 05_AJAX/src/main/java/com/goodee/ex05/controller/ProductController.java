package com.goodee.ex05.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goodee.ex05.domain.ProductDTO;
import com.goodee.ex05.service.ProductService;

@Controller
public class ProductController {

	private ProductService productService;
	
	@Autowired
	public void setService(ProductService productService) {
		this.productService = productService;
	}
	
	@ResponseBody
	@GetMapping(value="/product/list1", produces = "application/json; charset=UTF-8")
	public List<ProductDTO> list1(){
		return productService.list1();		// List 반환 값이 produces에 의해 json으로 반환	-> jackson이 개입
	}										// 반환값이 jsp이름이 아니라 값이다 @ResponseBody 애너테이션 추가
	
	@ResponseBody
	@GetMapping(value="/product/list2", produces = "application/json; charset=UTF-8")
	
	public List<Map<String, Object>> list2(){
		
		return productService.list2();
	}
	
	@ResponseBody
	@GetMapping(value="/product/list3", produces = "application/json; charset=UTF-8")
	public Map<String, Object> list3() {
		return productService.list3();
	//	List<ProductDTO> products = productService.list3();
	//	Map< String, Object> result = new HashMap<String, Object>();
	//	result.put("products", products);
	//	return result;
	}
	
	
}
