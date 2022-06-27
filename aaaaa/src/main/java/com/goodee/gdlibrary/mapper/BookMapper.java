package com.goodee.gdlibrary.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.goodee.gdlibrary.domain.BookDTO;

@Mapper
public interface BookMapper {
		
		public int selectBookCount();
		
		public List<BookDTO> selectBookList(Map<String, Object> m);
		
		public void getBooksInfo(List<BookDTO> list);
		
		public List<BookDTO> searchBook(Map<String, Object> m);

		public BookDTO detailBook(Long bookNo);
		
}
