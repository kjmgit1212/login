<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="resources/js/jquery-3.6.0.js"></script>
<script>

	// 페이지 로드 이벤트
	$(function(){
		fnDailyBoxOffice();
	})
	
	// 함수
	function fnDailyBoxOffice(){
		$('#btnQuery').on('click', function(){
			$.ajax({
				url: '${contextPath}/dailyBoxOffice',
				type: 'get',
				data: 'language=' + $('#language').val(),
				dataType: 'json',
				success: function(result){
					$('#boxOffice').empty();
					$.each(result.boxOfficeResult.dailyBoxOfficeList, function(i, movie){
						$('<tr>')
						.append($('<td>').text(movie.rank + '(' + movie.rankInten + ')'))
						.append($('<td>').text(movie.movieNm))
						.append($('<td>').text(movie.openDt))
						.append($('<td>').text(movie.audiCnt + '명'))
						.append($('<td>').text(movie.audiAcc + '명'))
						.append($('<td>').text(movie.rankOldAndNew == 'NEW' ? '신규진입' : ''))
						.appendTo('#boxOffice');
					})
				},
				error: (jqXHR)=>{  // function(jqXHR){
					alert(jqXHR.status);
				}
			})
			})
		}
	

</script>
</head>
<body>

	<input type="text" name="language" id="language">
	<input type="button" value="조회" id="btnQuery">
	
	<hr>
	
	<table border="1">
		<thead>
			<tr>
				<td>순위</td>
				<td>영화명</td>
				<td>개봉일</td>
				<td>일일관객수</td>
				<td>누적관객수</td>
				<td>비고</td>
			</tr>
		</thead>
		<tbody id="boxOffice"></tbody>
	</table>	

</body>
</html>