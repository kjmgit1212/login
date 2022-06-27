package com.goodee.gdlibrary.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goodee.gdlibrary.domain.BookReplyDTO;
import com.goodee.gdlibrary.service.BookReplyService;

@Controller
public class BookReplyController {

	@Autowired
	private BookReplyService bookReplyService;
	
	@ResponseBody
	@PostMapping(value="/book/review", produces="application/json")
	public Map<String, Object> addReview(@RequestBody BookReplyDTO reply) {
		
	 	return bookReplyService.addReview(reply);
	}
	
	@ResponseBody
	@GetMapping(value="/book/replyList", produces="application/json")
	public Map<String, Object> list(@RequestBody Long BookNo) {
		return bookReplyService.bookReplyList(BookNo);
	}
	
}
