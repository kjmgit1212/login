package com.goodee.ex15.service;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goodee.ex15.domain.MemberDTO;
import com.goodee.ex15.mapper.MemberMapper;
import com.goodee.ex15.util.SecurityUtils;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberMapper memberMapper;
	
	
	@Override
	public Map<String, Object> idCheck(String id) {
		Map<String, Object> map = new HashMap<>();
		map.put("res", memberMapper.selectMemberById(id));
		
		return map;
	}
	
	@Override
	public Map<String, Object> emailCheck(String email) {
		Map<String, Object> map = new HashMap<>();
		map.put("res", memberMapper.selectMemberByEmail(email));
		return map;
	}
	
	
	@Override
	public Map<String, Object> sendAuthCode(String email) {
		// 인증코드
		String authCode = SecurityUtils.authCode(6);	// 6자리 인증코드
		System.out.println(authCode);
		// 이메일 보낼때 필요한 필수속성
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");   // 구글메일로 보낸다.
		props.put("mail.smtp.port", "587"); 			 // 구글메일 보내는 port
		props.put("mail.smtp.auth", "true"); 			 // 인증된 사용자.
		props.put("mail.smtp.starttls.enable", "true");  // TLS를 허용한다.
		
		// 메일을 보내는 사용자 정보
		final String USERNAME = "blesskk1890@gmail.com";
		final String PASSWORD = "tvpwnmcblcurbxzq";
		
		// 사용자 정보 javax.eamil Session에 저장
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);	
			}
		});

		/*
		 이메일 보내기
		 1. 사용자 정보는 구글 메일만 가능함
		 2. 가급적 구글 부계정을 만들어서 사용하길 권장
		 3. 구글에서 보안 수준이 낮은 앱 허용을 해야함
		 	https://support.google.com/accounts/answer/6010255
		 */
		
		// 이메일 전송하기
		try {
			
			Message message = new MimeMessage(session);
			
			message.setHeader("Content-Type", "text/plain; charset=UTF-8");
			message.setFrom(new InternetAddress(USERNAME, "인증코드 관리자"));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
			message.setSubject("인증요청 메일입니다");
			message.setText("인증번호는 : " + authCode + " 입니다");
			
			Transport.send(message);

		}catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<>();
		map.put("authCode", authCode);
		
		return map;
	}
	
	@Override
	public void signIn(HttpServletRequest request, HttpServletResponse response) {
		// 파라미터
		String id = SecurityUtils.xss(request.getParameter("id"));
		String pw = SecurityUtils.sha256(request.getParameter("pw"));  
		String name = SecurityUtils.xss(request.getParameter("id"));
		String email = SecurityUtils.xss(request.getParameter("email"));
		
		String location = request.getParameter("location");
		String promotion = request.getParameter("promotion");
		 // if(location.isEmpty() == false && promotion.isEmpty() == false)
		int agreeState = 1;	// 필수동의
		if(location.equals("location") && promotion.equals("promotion")) {
			agreeState = 4;  // 필수 + 위치 + 프로모션 동의
		}else if(location.equals("location") && promotion.isEmpty()) {
			agreeState = 2;	// 필수 + 위치
		}else if(location.isEmpty() && promotion.equals("promotion")) {
			agreeState = 3;		// 필수 + 프로모션
		}
		// memberDTO
		MemberDTO member = MemberDTO.builder()
							.id(id)
							.pw(pw)
							.name(name)
							.email(email)
							.agreeState(agreeState).build();
		
		// member테이블에 저장
		int res = memberMapper.insertMember(member);
		
		
		// 응답
		try {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			if(res == 1) {
				out.println("<script>");
				out.println("alert('회원가입되었습니다.')");
				out.println("location.href='" + request.getContextPath() + "'");
				out.println("</script>");
				out.close();
			} else {
				out.println("<script>");
				out.println("alert('회원가입에 실패했습니다.')");
				out.println("history.back()");
				out.println("</script>");
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public void signOut(HttpServletRequest request, HttpServletResponse response) {
		// 파라미터
		Optional<String> opt = Optional.ofNullable(request.getParameter("memberNo"));
		Long memberNo = Long.parseLong(opt.orElse("0"));
		
		//member 삭제
		int res = memberMapper.deleteMember(memberNo);
		
		// 응답
				try {
					response.setContentType("text/html");
					PrintWriter out = response.getWriter();
					if(res == 1) {
						out.println("<script>");
						out.println("alert('Good Bye')");
						out.println("location.href='" + request.getContextPath() + "'");
						out.println("</script>");
						out.close();
					} else {
						out.println("<script>");
						out.println("alert('탈퇴 실패했습니다.')");
						out.println("history.back()");
						out.println("</script>");
						out.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
		
		
		
		
	}
	
	@Override
	public void login(HttpServletRequest request) {
		
		// 파라미터
		String id = SecurityUtils.xss(request.getParameter("id"));
		String pw = SecurityUtils.sha256(request.getParameter("pw"));
		
		
		// MemberDTO	
		MemberDTO member = MemberDTO.builder()
						.id(id)
						.pw(pw).build();
		
		// ID/PW 일치하는 회원 조회
		MemberDTO login = memberMapper.selectMemberByIdPw(member);
		
		
		// ID/PW가 일치하는 회원 login 객체 session에 저장 & 로그인 기록 남기기
		if(login != null) {
			request.getSession().setAttribute("login", login);
			memberMapper.insertMemberLog(id);
		}
		
		
	}
	
	
	
	
	
}
