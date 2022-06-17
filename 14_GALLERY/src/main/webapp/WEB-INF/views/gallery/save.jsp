<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<script src="../resources/js/jquery-3.6.0.js"></script>
<script>
	$(function(){
		fnFileCheck();
		
	})

	// 첨부파일 사전점검(확장자, 크기)
	function fnFileCheck(){
		$('#files').on('change', function(){
			// 첨부 규칙
			var regExt = /(.*)\.(jpg|png|gif)$/;
			var maxSize = 1024 * 1024 * 10;	//하나당 최대 크기 10mb
			// 첨부 가져오기
			var files = $(this)[0].files;
			// 각 첨부 순회하기
			for(let i = 0; i < files.length; i++){
				// 확장자 체크
				if(regExt.test(files[i].name) == false){
					alert('이미지만 첨부할 수 있습니다.');
					$(this).val('');	// 첨부된 파일 모두 삭제
					return;
				}
				// 파일크기 검사
				if(files[i].size > maxSize){
					alert('10MB이하 파일만 첨부할 수 있습니다.');
					$(this).val('');	// 첨부된 파일 모두 삭제
					return;
				}
			}
		})
		
	}
	
	
	
</script>

</head>
<body>
	<%--
		파일첨부 폼 : method, enctype은 꼭 넣어야된다.
		multiple이 있으면 다중첨부 가능
	 --%>
	<h1>작성화면</h1>
	
	<form action="${contextPath}/gallery/save" method="post" enctype="multipart/form-data" id="f">
		<input type="text" name="writer" placeholder="작성자"><br>
		<input type="text" name="title" placeholder="제목"><br>
		<input type="text" name=content placeholder="내용"><br>
		첨부<br>
		<input type="file" name="files" id="files" multiple="multiple"><br>
		<button>작성완료</button> 
	</form>
	
	<hr>
	
	<div id="attached">
	
	</div>

	
</body>
</html>