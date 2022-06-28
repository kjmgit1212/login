package com.goodee.gdlibrary.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.goodee.gdlibrary.domain.BookReplyDTO;

@Mapper
public interface BookReplyMapper {

	public int insertReview(BookReplyDTO reply);
	
	public List<BookReplyDTO> selectReplyList(Long bookNo);
	public int selectReplyCount(Long bookNo);
	public Double selectReplyRatingAverage(Long bookNo);

}
