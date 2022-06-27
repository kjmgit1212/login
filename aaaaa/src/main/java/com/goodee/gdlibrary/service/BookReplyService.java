package com.goodee.gdlibrary.service;

import java.util.Map;

import com.goodee.gdlibrary.domain.BookReplyDTO;

public interface BookReplyService {

	public Map<String, Object> addReview(BookReplyDTO reply);
	public Map<String, Object> bookReplyList(Long BookNo);
	
}
