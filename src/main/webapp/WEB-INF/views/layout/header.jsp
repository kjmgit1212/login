<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>로고</h1>
	<!-- 로그인 이전에 보이는 링크 -->
	<a href="${contextPath}/member/loginPage">로그인페이지</a>
	<a href="${contextPath}/member/agreePage">회원가입페이지</a>
	
	<!-- 로그인 이후에 보여줄 링크 -->
	<a href="${contextPath}/member/signOut?memberNo=1">회원탈퇴하기</a>
	<hr>
	
	
	
	
	
	
	
	
	
</body>
</html>