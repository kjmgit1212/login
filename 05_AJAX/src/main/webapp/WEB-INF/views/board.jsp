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
		$('#btn1').on('click', function(){fnBoard1();})
		$('#btn2').on('click', function(){fnBoard2();})
		$('#btn3').on('click', function(){fnBoard3();})
		$('#btn4').on('click', function(){fnBoard4();})
	})
	
	function fnBoard1(){
		$('#result1').empty();
		$.ajax({
			url: '${contextPath}/board/detail1',
			type: 'get',
			data: $('#a').serialize(),
			
			dataType: 'json',
			success: function(obj){
				$('<ul>')
				.append($('<li>').text(obj.title))
				.append($('<li>').text(obj.hit))
				.appendTo('#result1');
			}, 
			error : function(jqXHR){
				$('#result').text(jqXHR.status + ' : ' + jqXHR.responseText);	
			}
			
		})
		
	}
	
	function fnBoard2(){
		$('#result1').empty();
		$.ajax({
			url: '${contextPath}/board/detail2',
			type: 'get',
			data: $('#a').serialize(),
			
			dataType: 'json',
			success: function(obj){
				$('<ul>')
				.append($('<li>').text(obj.title))
				.append($('<li>').text(obj.hit))
				.appendTo('#result1');
			}, 
			error : function(jqXHR){
				$('#result1').text(jqXHR.status + ' : ' + jqXHR.responseText);	
			}
			
		})
		
	}
	function fnBoard3(){
		$('#result1').empty();
		
		$.ajax({
			url :'${contextPath}/board/detail3',
			type:'post',
			data: JSON.stringify({
				'title' : $('#title').val(),
				'hit' : $('#hit').val()
			}),
			contentType: "application/json",
			success: function(obj){
				$('<ul>')
				.append($('<li>').text(obj.title))
				.append($('<li>').text(obj.hit))
				.appendTo('#result1');
			}, 
			error : function(jqXHR){
				$('#result1').text(jqXHR.status + ' : ' + jqXHR.responseText);	
			}
			
		})
		
	}
	function fnBoard4(){
		$('#result1').empty();
		
		$.ajax({
			url :'${contextPath}/board/detail4',
			type:'post',
			data: JSON.stringify({
				'title' : $('#title').val(),
				'hit' : $('#hit').val()
			}),
			contentType: "application/json",
			dataType:'json',
			success: function(obj){
				$('<ul>')
				.append($('<li>').text(obj.title))
				.append($('<li>').text(obj.hit))
				.appendTo('#result1');
			}, 
			error : function(jqXHR){
				$('#result1').text(jqXHR.status + ' : ' + jqXHR.responseText);	
			}
			
		})
		
	}
	
</script>
</head>
<body>
			<form id="a">
				<input type="text" id="title" name="title" placeholder="제목">
				<input type="text" id="hit" name="hit" placeholder="조회수">
				<input type="button" id="btn1" value="전송1">
				<input type="button" id="btn2" value="전송2">
				<input type="button" id="btn3" value="전송3">
				<input type="button" id="btn4" value="전송4">
			</form>
			<div id="result1"></div>


</body>
</html>