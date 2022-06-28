package com.goodee.gdlibrary.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.goodee.gdlibrary.domain.BookReplyDTO;
import com.goodee.gdlibrary.mapper.BookReplyMapper;

public class BookReplyServiceImpl implements BookReplyService {

	@Autowired
	private BookReplyMapper bookReplyMapper;
	
	@Override
	public Map<String, Object> addReview(BookReplyDTO reply) {
		Map<String, Object> map = new HashMap<>();
		map.put("res", bookReplyMapper.insertReview(reply));
		
		return map;
	}
	
	@Override
	public Map<String, Object> bookReplyList(HttpServletRequest request) {
		Long bookNo = Long.parseLong(request.getParameter("bookNo"));
		Map<String, Object> map = new HashMap<>();
		map.put("replyCount", bookReplyMapper.selectReplyCount(bookNo));
		map.put("replies", bookReplyMapper.selectReplyList(bookNo));
		map.put("replyRatingAverage", bookReplyMapper.selectReplyRatingAverage(bookNo));

		return map;
	}
	
	
}
