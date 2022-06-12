package com.goodee.ex15.interceptor;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.goodee.ex15.domain.SignOutMemberDTO;
import com.goodee.ex15.service.MemberService;
import com.goodee.ex15.util.SecurityUtils;

public class LoginInterceptor implements HandlerInterceptor{
	@Autowired
	private MemberService memberService;
	
	
	// @postMapping("member/login")요청이전에 처리
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 반환타입이 true면 @postMapping("member/login") 요청을 수행
		// 반환타입이 false면 @postMapping("member/login") 요청을 수행안함 => 작업을 구현해야함
		
		// 로그인 된 정보가 있다면 기존 로그인 정보를 제거
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") != null) {
			session.removeAttribute("loginMember");
		}
		
		// 탈퇴한 회원인지 확인
		String id = SecurityUtils.xss(request.getParameter("id"));
		SignOutMemberDTO member = memberService.findSignOutMember(id);
		if(member != null) {	// 탈퇴한 회원
			request.setAttribute("member", member);
			request.getRequestDispatcher("/member/reSignInPage").forward(request, response);	// 탈퇴한 회원의 정보를 가지고 재가입 체이지로 이동
			
			return false;
		}
		return true;
	
	}

	// @postMapping("member/login")요청이후에 처리
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		// ModelAndView를 Map으로 변환하고 loginMember를 추출
		Map<String, Object> map = modelAndView.getModel();
		Object loginMember = map.get("loginMember");
		Object url = map.get("url");
		
		// loginMember가 있다면(로그인 성공) session에 저장
		if(loginMember != null) {
			// session에 로그인 정보 저장
			HttpSession session = request.getSession();
			session.setAttribute("loginMember", loginMember);
			
			// 로그인 유지를 체크한 사용자는 "keepLogin"이라는 쿠키 이름에 
			// sessionId 값을 저장해 둔다.
			String keepLogin = request.getParameter("keepLogin");
			if(keepLogin != null && keepLogin.equals("keep")) {
				// keepLogin cookie 생성
				Cookie cookie = new Cookie("keepLogin", session.getId());
				
				cookie.setMaxAge(60 * 60 *24 * 7);	// 초단위(7일)
				
				// keepLogin cookie 저장하기
				response.addCookie(cookie);
				
			}
			// 로그인 유지를 체크하지 않은 사용자는 keepLogincookie를 제거한다.
			else {
				// keepLogin 쿠키 제거하기
				Cookie cookie = new Cookie("keepLogin", "");
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
			// 로그인 이후 이동
			if(url.toString().isEmpty()) {	// 로그인 이전 화면 정보가 없으면 contextPath로 이동
				response.sendRedirect(request.getContextPath());
			}else {	// 로그인 이전 화면 정보가 있으면 해당 화면으로 이동
				response.sendRedirect(url.toString());
			}
			
		}
		// loginMember가 있다면(로그인 실패) 로그인 화면으로 돌려보내기
		else {
			if(url.toString().isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/member/loginPage");
			}else {
				response.sendRedirect(request.getContextPath() + "/member/loginPage?=url" + url.toString());
			}
		}
	}
	
	
}
