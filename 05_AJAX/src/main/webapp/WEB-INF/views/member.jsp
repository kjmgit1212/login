<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="resources/js/jquery-3.6.0.js"></script>
<script>
	
	$(document).ready(function(){
		$('#btn1').on('click', function(){fnAjax1();})  // btn1을 클릭하면 fnAjax1() 실행
		$('#btn2').on('click', function(){fnAjax2();})  // btn2을 클릭하면 fnAjax2() 실행
		$('#btn3').on('click', function(){fnAjax3();})
		$('#btn4').on('click', fnAjax4)
	})
	
	
	// 요청데이터 : 파라미터
	function fnAjax1(){		// 호이스팅(선언부부터 먼저 읽어봄)
	
		$('#result').empty();
	
		$.ajax({
			/* 요청 프로퍼티 : url, type, data, contentType	*/
			url : '${contextPath}/member/detail1',						// 요청 URL(매핑), controller에서 찾기
			type : 'get',												// 요청메소드(get, post)
			data : 'id=' + $('#id').val() + '&pw=' + $('#pw').val(),	// membercontroller로 보내는 파라미터
			
			/* 응답 데이터 : dataType, success, error */
			dataType: 'text',	// 응답데이터 text(res)
			success: function(res){		// 응답데이터는 res에 전달된다.
				$('#result').text(res);
			}, 
			error : function(jqXHR){
				$('#result').text(jqXHR.status + ' : ' + jqXHR.responseText);	
			}
		})
		
	}
	
	function fnAjax2(){	// 응답데이터 : JSON {"id" : "admin"}, {"pw" : "123456"}
		
		$('#result').empty()
		$.ajax({
			// 요청
			url : '${contextPath}/member/detail2',
			type: 'get',
			data: $('#f').serialize(),	// <input name="id">에 입력된 값은 파라미터 id로 전달,<input name="pw">에 입력된 값은 파라미터 pw로 전달
			
			// 응답
			dataType : 'json',
			success: function(obj){
				$('<ul>')
				.append($('<li>').text(obj.id))
				.append($('<li>').text(obj.pw))
				.appendTo('#result');
			}, 
			error : function(jqXHR){
				$('#result').text(jqXHR.status + ' : ' + jqXHR.responseText);	
			}
			
		})

	}
	
		function fnAjax3(){
			$('#result').empty()
			
			$.ajax({
				url : '${contextPath}/member/detail3',
				type : 'get',
				data : $('#f').serialize(),
				
				dataType: 'json',
				success : function(obj){
					$('<ul>')
					.append($('<li>').text(obj.id))
					.append($('<li>').text(obj.pw))
					.appendTo('#result');
				}, 
				error : function(jqXHR){
					$('#result').text(jqXHR.status + ' : ' + jqXHR.responseText);	
				}
				
			})
			
		}
		
		// 요청데이터 JSON, 응답데이터 JSON
		function fnAjax4(){
			
			$('#result').empty();
			
			$.ajax({
			
				url : '${contextPath}/member/detail4',
				// JSON데이터를 서버로 보내고자 할때는 
				// JSON데이터를 주소창에 붙여서 보내지 못하므로 post방식으로 보내야함(get방식 사용불가)
				type : 'post',		// JSON데이터를 본문에 포함시켜서 보내는 post방식
				
				// JSON데이터를 만들어서 보낼때는 
				// JSON데이터를 문자열 형식으로 만들어서 보낸다.
				data : JSON.stringify({
					'id': $('#id').val(),
					'pw': $('#pw').val()
				}),
				// JSON데이터를 만들어서 보낼때는 보내는 데이터의 타입을 작성해야함
				// contentType 이라는 속성으로 작업
				// 요청 데이터 타입을 확인하는 건 자바 측이기 때문에
				// 자바가 사용하는 JSON의 타입인 'application/json'; 이라고 작성해야함
				contentType: 'application/json',
				
				// 응답
				dataType: 'json',
				success: function(obj){
					$('<ul>')
					.append($('<li>').text(obj.id))
					.append($('<li>').text(obj.pw))
					.appendTo('#result');
				}, 
				error : function(jqXHR){
					$('#result').text(jqXHR.status + ' : ' + jqXHR.responseText);	
				}
				
				
			})
	
		}
		
		
</script>
</head>
<body>
		
		<form id="f">
			<input type="text" name="id" id="id" placeholder="ID"><br>
			<input type="text" name="pw" id="pw" placeholder="Password"><br><br>
			<input type="button" value="전송1" id="btn1">
			<input type="button" value="전송2" id="btn2">
			<input type="button" value="전송3" id="btn3">
			<input type="button" value="전송4" id="btn4">
		</form>
		<hr>
		
		<div id="result"></div>
		
		
		
</body>
</html>