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
		$(function(){
			fnProductSearch();
		})
		
		function fnProductSearch(){
			$('#btnSearch').on('click', function(){
				$.ajax({
					url: '${contextPath}/product/search',
					data: $('#f').serialize(),
					type: 'get',
					dataType: 'json',
					success: function(obj){
						$('#productInfo').empty();
						var products = obj.items;
						var tr = '<tr>';
						$.each(products, function(i, product){
							tr += '<td><a href="'+ product.link + '">' + product.title + '</a></td>';
							tr += '<td><img src="' + product.image + '" width="150px" height="150px"></td>';
							tr += '<td>' + product.lprice + '</td>';
							tr += '</tr>';
							$('#productInfo').append(tr);
						})
						
					},
					error: function(jqXHR){
						alert(jqXHR.responseText);
					}
				})
				
			})
			
		}
		
</script>
</head>
<body>
	<h1>제품 검색 화면</h1>
	<form id="f">
		<label for="display">
			검색결과건수
			<select name="display" id="display">
				<option value="10">10</option>
				<option value="20">20</option>
				<option value="40">40</option>
				<option value="60">60</option>
				<option value="80">80</option>
				<option value="100">100</option>
			</select>
		</label><br>
		<label for="sim"><input type="radio" name="sort" id="sim" value="sim">유사도순</label>
		<label for="date"><input type="radio" name="sort" id="date" value="date">날짜순</label>
		<label for="asc"><input type="radio" name="sort" id="asc" value="asc">낮은가격순</label>
		<label for="dsc"><input type="radio" name="sort" id="dsc" value="dsc">높은가격순</label><br>
		<input type="text" name="query" id="query">
		<input type="button" value="검색" id="btnSearch">
	</form>

	<hr>

	<table border="1">
		<thead>
			<tr>
				<td>상품명</td>
				<td>썸네일</td>
				<td>최저가</td>
			</tr>
		</thead>
		<tbody id="productInfo"></tbody>
	</table>
	
</body>
</html>