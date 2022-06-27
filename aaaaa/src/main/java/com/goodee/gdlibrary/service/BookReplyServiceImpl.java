package com.goodee.gdlibrary.service;

import java.util.HashMap;
import java.util.Map;

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
	public Map<String, Object> bookReplyList(Long BookNo) {
		System.out.println(BookNo);
		Map<String, Object> map = new HashMap<>();
		map.put("replyCount", bookReplyMapper.selectReplyCount(BookNo));
		map.put("replies", bookReplyMapper.selectReplyList(BookNo));
		System.out.println(map.get("replyCount"));
		System.out.println(map.get("replies"));
		return map;
	}
	
	
}
