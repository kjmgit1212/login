package com.goodee.gdlibrary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.goodee.gdlibrary.service.BookReplyService;
import com.goodee.gdlibrary.service.BookReplyServiceImpl;
import com.goodee.gdlibrary.service.BookService;
import com.goodee.gdlibrary.service.BookServiceImpl;

@Configuration
public class ServiceConfig {

	@Bean
	public BookService bookService() {
		return new BookServiceImpl();
	}
	
	@Bean
	public BookReplyService bookReplyService() {
		return new BookReplyServiceImpl();
	}
	
}

