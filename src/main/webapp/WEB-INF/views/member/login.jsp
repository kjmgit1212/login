<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- jquery cookie -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.min.js" integrity="sha512-3j3VU6WC5rPQB4Ld1jnLV7Kd5xr+cq9avvhwqzbH/taCRNURoeEpoPBK9pDyeukwSxwRPJ8fDgvYXd6SkaZ2TA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<!-- jquery -->
<script src="../resources/js/jquery-3.6.0.js"></script>
<script>
    // 페이지 로드
	$(function(){
		fnLogin();	
	})
	
	
	
	// 함수
	function fnLogin(){
    	$('#f').on('submit', function(event){
    		
    		// 아이디 비밀번호 확인
    		if($('#id').val() == '' || $('#pw').val() == ''){
    			alert('아이디와 비밀번호를 입력하세요');
    			event.preventDefault();
    			return false;
    		}
    		// 아이디 저장 체크유무
    		// 아이디 저장은 쿠키를 이용한다.
    		if($('#rememberId').is(':checked')){
    			$.cookie('rememberId', $('#id').val());		// 입력한 아이디를 쿠키에 rememberId로 저장
    		}else{
    			$.cookie('rememberId', '');
    		}
    			// 서브밋 수행
    			return true;
    	})
    	
    }
    
    
    
</script>
</head>
<body>
	<jsp:include page="../layout/header.jsp"></jsp:include>
	
	<h3>로그인</h3>
	
	<form id="f" action="${contextPath}/member/login" method="post">
		
		아이디<br>
		<input type="text" name="id" id="id"><br><br>
		
		비밀번호<br>
		<input type="password" name="pw" id="pw"><br><br>
		
		<button>로그인</button><br><br>
		
		<label for="rememberId"><input type="checkbox" id="rememberId">아이디 저장</label>
		<label for="keepLogin"><input type="checkbox" name="keepLogin" id="keepLogin">로그인 유지</label>
	
	</form>
	
	<div>
		<a href="${contextPath}/member/findIdPage">아이디 찾기</a> |
		<a href="${contextPath}/member/findPwPage">비밀번호 찾기</a> |
		<a href="${contextPath}/member/agreePage">회원가입</a>
	</div>
	
	
	
	
	
	
	
</body>
</html>