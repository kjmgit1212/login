package com.goodee.ex05.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping("/")
	public String index() {		// ${contextPath} 요청이 오면, ${contextPath}/index
		return "index";
	}
	
	@GetMapping("/member")
	public String member() {
		return "member";
	}
	
	@GetMapping(value="/board")  // ${contextPath}/board 요청이 오면,
	public String board() {
		return "board";          // board.jsp로 이동하자.
	}
	
	@GetMapping(value="/product")  // ${contextPath}/product 요청이 오면,
	public String product() {
		return "product";          // product.jsp로 이동하자.
	}
	
	@GetMapping(value="/reservation")  // ${contextPath}/reservation 요청이 오면,
	public String reservation() {
		return "reservation";          // reservation.jsp로 이동하자.
	}
	@GetMapping(value="/openapi")  // ${contextPath}/openapi 요청이 오면,
	public String openapi() {
		return "openapi";          // openapi.jsp로 이동하자.
	}
	
}
