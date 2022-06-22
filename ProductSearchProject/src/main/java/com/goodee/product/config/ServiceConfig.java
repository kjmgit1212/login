package com.goodee.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.goodee.product.service.ProductService;
import com.goodee.product.service.ProductServiceImpl;

@Configuration
public class ServiceConfig {

	@Bean
	public ProductService productService() {
		return new ProductServiceImpl();
	}
	
}
