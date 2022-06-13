package com.goodee.ex05.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goodee.ex05.domain.MemberDTO;
import com.goodee.ex05.service.MemberService;
import com.goodee.ex05.service.MemberServiceImpl;

@Controller
public class MemberController {

	// 컨트롤러는 언제나 service를 호출
	// service를 field로 등록한다.
	
	// DI를 사용 안하는 경우
	// 개발자가 직접 bean을 생성하는 경우
	// private MemberService memberService = new MemberServiceImpl();
	
	// DI를 사용하는 경우
	// root-context.xml에 등록한 bean을 스프링이 가져오는 방법
	
	// 필드, 생성자, setter 방식중 필드방식 사용
	@Autowired
	private MemberService memberService;

		// 컨트롤러의 메소드는 JSP이름을 반환한다.
		// ajax는 JSP이름을 반환하는 것이 아닌 값을 반환하는 구조
		
		// 값을 반환하기 이해서는 
		// @responseBody 애너테이션이 필요함
		
		
		@GetMapping(value="/member/detail1", 
				produces = "text/plain; charset=UTF-8")	// text를 반환한다.(응답타입 : response.setContentType)
		@ResponseBody   // JSP이름이 아닌 값을 반환(Ajax라서), (텍스트, xml, json 등) 
		public String detail1(HttpServletRequest request) {
			
			String res = memberService.detail1(request);
			
			return res;			// memberservice의 detail1() 메소드에서 만든 텍스트를 member.jsp로 반환한다.
			
		}
		
		@GetMapping(value="/member/detail2", produces = "application/json; charset=UTF-8")
		@ResponseBody
		
		// 반환타입 MemberDTO는 jackson에 의해서 JSon 데이터로 반환
		public MemberDTO detail2(@RequestParam(value="id")String id, @RequestParam(value="pw") String pw) {
			
			MemberDTO member = memberService.detail2(id, pw);
			
			
			// Jackson이 하는일
			// 자바 객체 member를 자동으로 JSON형태로 변환해줌
			
			
			return member;		// 자바객체를 member.jsp로 반환하는데, 이때 jackson이 개입해서 member를 JSON형태로 변환해줌
								// JSON으로 바꿔서 전달하라고 produces에서 처리하면됨
		}
		
		
		@GetMapping(value="/member/detail3", produces = "application/json; charset=UTF-8")
		@ResponseBody
		// 반환타입 Map은 jackson에 의해 JSON으로 변환
		
		public Map<String, Object> detail3(MemberDTO member){	// setId, setPw가 파라미터 id, pw를 받아준다.
			
			Map<String, Object> res = memberService.detail3(member);
			
			return res;			// map을 반환하고 있지만 produces에서 json으로 반환하고 있다고 했기 때문에 jackson이 JSON데이터로 변환해줌
			
		}
		
		
		@PostMapping(value="/member/detail4", produces = "application/json; charset=UTF-8")
		@ResponseBody
		// JSON데이터가 요청의 본문에 포함된 상태로 컨트롤러로 왔음
		// 컨트롤러에서는 이런 데이터를 파라미터로 처리할 수 없음
		// 새로운 방법이 필요함
		// @RequestBody 애너테이션을 이용하면 요청의 본문에 포함된 데이터를 받을 수 있음
		// jackson사용하고 있기 때문에 컨트롤러로 전달된 JSON데이터는 MemberDTO 또는 Map으로 받으면 된다.
		
		public Map<String, Object> detail4(@RequestBody MemberDTO member){
			
			Map<String, Object> map = memberService.detail4(member);
			
			return map;
		}
		
		
		
}
