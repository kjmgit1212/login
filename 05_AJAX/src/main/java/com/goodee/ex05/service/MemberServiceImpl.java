package com.goodee.ex05.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.goodee.ex05.domain.MemberDTO;

public class MemberServiceImpl implements MemberService {

	@Override
	public String detail1(HttpServletRequest request) {	// 컨트롤러에서 받아온 request에는 id, pw가 들어있음
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		return "입력아이디 : " + id + " 입력비밀번호 : " + pw;		// controller로 반환하는 텍스트
	}
	
	@Override
	public MemberDTO detail2(String id, String pw) {	// 컨트롤러에서 받아온 id, pw
		
		MemberDTO member = new MemberDTO();
		member.setId(id);
		member.setPw(pw);
		
		
		
		return member;									// 컨트롤러에 반환하는 객체(bean)
	}
	
	
	@Override
	public Map<String, Object> detail3(MemberDTO member) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("id", member.getId());
		map.put("pw", member.getPw());
		
		return map;					// 컨트롤러에 반환하는 map
	}
	
	@Override
	public Map<String, Object> detail4(MemberDTO member) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("id", member.getId());
		map.put("pw", member.getPw());
		
		return map;	
	}
	
}
