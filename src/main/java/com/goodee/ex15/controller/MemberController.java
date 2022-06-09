package com.goodee.ex15.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goodee.ex15.domain.MemberDTO;
import com.goodee.ex15.service.MemberService;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("member/agreePage")
	public String agreePage() {
		return "member/agree";
	}
	
	@GetMapping("member/signInPage")
	public String signInPage(@RequestParam(required = false) String[] agreements, Model model) {
		model.addAttribute("agreements", agreements);
		return "member/signIn";
	}
	
	@ResponseBody
	@GetMapping(value="member/idCheck", produces = "application/json")
	public Map<String, Object> idCheck(@RequestParam String id){
			return memberService.idCheck(id);
			// {"res" : null}
			// {"res" : {"memberNo": 1, ~~~}}
	}
	
	@ResponseBody
	@GetMapping(value="/member/emailCheck", produces = "application/json")
	public Map<String, Object> emailCheck(@RequestParam String email){
		return memberService.emailCheck(email);
		// {"res" : null}
					// {"res" : {"memberNo": 1, ~~~}}
	}
	
	@ResponseBody
	@GetMapping(value="/member/sendAuthCode", produces = "application/json")
	public Map<String, Object> sendAuthCode(@RequestParam String email){
		return memberService.sendAuthCode(email);
	}
	
	@PostMapping("/member/signIn")
	public void signIn(HttpServletRequest request, HttpServletResponse response) {
		memberService.signIn(request, response);
		// insert문 이라서 redirect or response를 통한 직접이동이 필요함
	}
	
	@GetMapping("/member/signOut")
	public void signOut(HttpServletRequest request, HttpServletResponse response) {
		memberService.signOut(request, response);
	}
	
	
	@GetMapping("/member/loginPage")
	public String loginPage() {
		return "member/login";
	}
	
	// login() 메소드 수행 전에 prehandle메소드가 호출됨
	@PostMapping("/member/login")
	public void login(HttpServletRequest request) {	
		memberService.login(request);
	}
	
	// login() 메소드 수행 후에 posthandle메소드가 호출됨
	
	
	
}
