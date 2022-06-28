<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="../resources/js/jquery-3.6.0.js"></script>
<script>
		$(function(){
			// 1. api로 가져오기
			fnGetBookInfo();
			
		})
		
		//1. api로 가져오기
		function fnGetBookInfo(){
			
			$('#getBookInfo').on('submit',function(){
				
			})
			
		}
	
</script>
</head>
<body>
		
		<form action="${contextPath}/book/insertApi" id="getBookInfo">
			<input type="text" name="query" id="query">
			<button>api 정보받아오기</button>
		</form>

</body>
</html>