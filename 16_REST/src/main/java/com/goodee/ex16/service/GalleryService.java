package com.goodee.ex16.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface GalleryService {

	public void save(MultipartHttpServletRequest multipartRequest, HttpServletResponse response);
	
}
