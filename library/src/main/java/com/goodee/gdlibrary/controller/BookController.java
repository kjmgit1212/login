package com.goodee.gdlibrary.controller;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goodee.gdlibrary.service.BookService;
@Controller
public class BookController {
	@Autowired
	private BookService bookService;

	@GetMapping("/book/list")
	public String bookListPage() {
		return "book/list";
	}
	
	
	@ResponseBody
	@GetMapping(value="/book/page/{page}", produces="application/json")
	public Map<String, Object> bookList(@PathVariable(value="page", required=false) Optional<String> opt) {
		int page = Integer.parseInt(opt.orElse("1"));
		return bookService.bookList(page);
	}
	
	@GetMapping("book/insertBook")
	public String insertBook() {
		return "book/insert";
	}
	
	@GetMapping("book/insertApi")
	public String getBookInfo(HttpServletRequest request, Model model) {
		bookService.getBooksInfo();
		return "redirect:list";
	}
	
	@ResponseBody
	@GetMapping(value="/book/search", produces="application/json")
	public Map<String, Object> searchBook(HttpServletRequest request) {
		return bookService.searchBook(request);
		
	}
	
	
	@GetMapping("/book/detail")
	public String detailBook(HttpServletRequest request, Model model) {
		bookService.detailBook(request,model);
		return "book/detail";
	}
	
	@ResponseBody
	@GetMapping(value="book/recomBook" , produces="application/json")
	public Map<String, Object> recomBook(Model model){
		bookService.recomBook(model);
		return bookService.recomBook(model);
	}
	
}	

