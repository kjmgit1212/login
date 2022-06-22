package com.goodee.product.service;

import org.springframework.web.bind.annotation.RequestParam;

import com.goodee.product.domain.SearchVO;

public interface ProductService {

	public String searchProduct(@RequestParam SearchVO vo);
	
}
