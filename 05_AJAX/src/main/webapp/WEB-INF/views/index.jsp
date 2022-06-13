<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	
	<a href="${contextPath}/member">회원관리</a>
	
	<br><hr>
	
	<a href="${contextPath}/board">게시판관리</a>

	<br><hr>

	<a href="${contextPath}/product">제품관리</a>
	
	<br><hr>

	<a href="${contextPath}/reservation">예약관리</a>
	
	<br><hr>

	<a href="${contextPath}/openapi">영화박스오피스</a>

</body>
</html>