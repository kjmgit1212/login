package com.goodee.ex15.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	
	@GetMapping("/member/agreePage")
	public String agreePage() {
		return "member/agree";
	}
	
	@GetMapping("/member/signInPage")
	public String signInPage(@RequestParam(required=false) String[] agreements, Model model) {
		model.addAttribute("agreements", agreements);
		return "member/signIn";
	}
	
	@ResponseBody
	@GetMapping(value="/member/idCheck", produces="application/json")
	public Map<String, Object> idCheck(@RequestParam String id) {
		return memberService.idCheck(id);
		// {"res": null}
		// {"res": {"memberNo":1, ...}}
	}
	
	@ResponseBody
	@GetMapping(value="/member/emailCheck", produces="application/json")
	public Map<String, Object> emailCheck(@RequestParam String email) {
		return memberService.emailCheck(email);
		// {"res": null}
		// {"res": {"memberNo":1, ...}}
	}
	
	@ResponseBody
	@GetMapping(value="/member/sendAuthCode", produces="application/json")
	public Map<String, Object> sendAuthCode(@RequestParam String email) {
		return memberService.sendAuthCode(email);
	}
	
	@PostMapping("/member/signIn")
	public void signIn(HttpServletRequest request, HttpServletResponse response) {
		memberService.signIn(request, response);
	}
	
	@GetMapping("/member/signOut")
	public void signOut(HttpServletRequest request, HttpServletResponse response) {
		memberService.signOut(request, response);
	}
	
	@GetMapping("/member/loginPage")
	public String loginPage(@RequestParam(required = false) String url, Model model) {
		model.addAttribute("url", url);		// member/login.jsp로 url 속성 값을 전달한다.
		return "member/login";
	}
	
	// login() 메소드 수행 전에 LoginInterceptor의 preHandle() 메소드가 호출
	@PostMapping("/member/login")
	public void login(HttpServletRequest request, Model model) {
		// 아이디 비밀번호가 일치하는 회원 정보 가져오기
		MemberDTO loginMember = memberService.login(request);
		// 아이디, 비밀번호가 일치하는 회원이 있으면 성공 LoginInterceptor postHandle()에 회원정보 전달
		if(loginMember != null) {
			model.addAttribute("loginMember", loginMember);  // Model에 저장된 속성은 LoginInterceptor의 postHandle() 메소드의 ModelAndView 매개변수가 받는다.
		}
		// login() 메소드 수행 전에 LoginInterceptor의 postHandle()에 로그인 후 이동할 경로 전달
		model.addAttribute("url", request.getParameter("url"));
		
		// 로그인 유지 체크를 한경우
		// 1) check 상태 : 파라미터 keepLogin = "keep"
		// 2) uncheck 상태 : 파라미터 keepLogin 자체가 없음 => null   * ''과 null 은 다르다
		String keepLogin = request.getParameter("keepLogin");
		if(keepLogin != null && keepLogin.equals("keep")) {
			
			memberService.keepLogin(request);
			
		}
		
	}
	// login() 메소드 수행 후에 LoginInterceptor의 postHandle() 메소드가 호출
	
	// LoginInterceptor의 preHandle() 메소드에서 탈퇴자 조회 후 탈퇴자인 경우 처리
	@PostMapping("/member/reSignInPage")
	public String reSignInPage() {
		return "member/reSignIn";
	}
	
	@PostMapping("/member/reSignIn")
	public void reSignIn(HttpServletRequest request, HttpServletResponse response) {
		memberService.reSignIn(request, response);
	}
	
	
	@GetMapping("/board/savePage")
	public String savePage() {
		return "board/save";
	}
	
	// 컨트롤러에서 session을 선언해서 사용해도 괜찮음
	@GetMapping("/member/logOut")
	public String logOut(HttpSession session) {
		MemberDTO loginMember = (MemberDTO)session.getAttribute("loginMember");
		if(loginMember != null) {
			session.invalidate();
		}
		return "redirect:/";	// contextPath 이동과 같음
	}
	
	
	
	
	
	
	
}