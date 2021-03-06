<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#paging {
		display: flex;
		justify-content: center;
	}
	#paging div {
		width: 32px;
		height: 20px;
		text-align: center;
	}
	.disable_link {
		color: lightgray;
	}
	.enable_link {
		cursor: pointer;
	}
	.now_page {
		border: 1px solid gray;
		color: limegreen;
		font-weight: 900;
	}
</style>
<script src="../resources/js/jquery-3.6.0.js"></script>
<script>
	$(function(){
		
		fnList();
		fnPagingLink();
		fnSearch();
	})
	
	
	/* 함수영역 */ 
	// 페이징 링크 처리(page 전역변수 값을 링크의 data-page값으로 바꾸고 fnList() 호출)
	function fnPagingLink(){
		$(document).on('click', '.enable_link', function(){
			page = $(this).data('page');
			fnList();
		})
	}
	
	// 1. 회원목록 + page 전역변수
	var page = 1;  // 초기화
	function fnList(){
		$.ajax({
			url: '${contextPath}/book/page/' + page,
			type: 'GET',
			dataType: 'json',
			success: function(obj){
				fnPrintMemberList(obj.books);
				fnPrintPaging(obj.p);
			}
		})
	}
	
	// 1-1) 회원 목록 출력
	function fnPrintMemberList(books){
		$('#bookInfo').empty();
		$.each(books, function(i, book){
			
			var tr = '<tr>';
			tr += '<td>' + book.bookNo + '</td>';
			tr += '<td><img src="' + book.bookImage + '"width=100px height=120px></td>';
			tr += '<td><a href="${contextPath}/book/detail?bookNo=' + book.bookNo + '">' + book.bookTitle + '</a></td>';
			tr += '<td>' + book.bookAuthor + '</td>';
			tr += '<td>' + book.bookField + '</td>';
			tr += '</tr>';
			$('#bookInfo').append(tr);
		})
	}
	
	// 1-2) 페이징 정보 출력
	function fnPrintPaging(p){
		
		$('#paging').empty();
		
		var paging = '';
		
		// ◀◀ : 이전 블록으로 이동
		if(page <= p.pagePerBlock){
			paging += '<div class="disable_link">◀◀</div>';
		} else {
			paging += '<div class="enable_link" data-page="' + (p.beginPage - 1) + '">◀◀</div>';
		}
		
		// ◀  : 이전 페이지로 이동
		if(page == 1){
			paging += '<div class="disable_link">◀</div>';
		} else {
			paging += '<div class="enable_link" data-page="' + (page - 1) + '">◀</div>';
		}
		
		// 1 2 3 4 5 : 페이지 번호
		for(let i = p.beginPage; i <= p.endPage; i++){
			if(i == page){
				paging += '<div class="disable_link now_page">' + i + '</div>';
			} else {
				paging += '<div class="enable_link" data-page="' + i + '">' + i + '</div>';
			}
		}
		
		// ▶  : 다음 페이지로 이동
		if(page == p.totalPage){
			paging += '<div class="disable_link">▶</div>';
		} else {
			paging += '<div class="enable_link" data-page="' + (page + 1) + '">▶</div>';
		}
		
		// ▶▶ : 다음 블록으로 이동
		if(p.endPage == p.totalPage){
			paging += '<div class="disable_link">▶▶</div>';
		} else {
			paging += '<div class="enable_link" data-page="' + (p.endPage + 1) + '">▶▶</div>';
		}
		
		$('#paging').append(paging);
		
	}
	
		function fnSearch(){
		$('#btnSearch').on('click', function(){
			$.ajax({
				url: '${contextPath}/book/search/',
				type: 'GET',
				data: $('#f').serialize(),
				dataType: 'json',
				success: function(obj){
					fnPrintMemberList(obj.books);
				}
			})
		})
	
	}
	
		function enterkey(){
			$("input[type=text]").on('keyup', function(e){
	            if(e.key==='Enter'||e.keyCode===13){
	                $("#btnSearch").trigger('click');
	            }
	        })
		}
	
</script>
</head>
<body>
		
		<a href="${contextPath}/book/insertBook">책추가하기</a>
		
		<form id="f">
			<div>
				자료검색 &nbsp; 
				<select name="column" id="column">
					<option value="">:::선택:::</option>
					<option value="BOOK_TITLE">제목</option>
					<option value="BOOK_AUTHOR">저자</option>
					<option value="BOOK_FIELD">분야</option>
				</select>
				<input type="text" id="query" name="query"  placeholder="검색어를 입력하세요"></input>
				<input type="button" id="btnSearch" value="검색" ></input>
				<input type="button" id="btnSearchAll" value="전체조회"></input>
			</div>
		</form>

		<table border="1">
		
		<thead>
			<tr>
				<td>순번</td>
				<td>도서이미지</td>
				<td>책제목</td>
				<td>저자</td>
				<td>분야</td>
			</tr>
		</thead>
		<tbody id="bookInfo">
			
		</tbody>
		<tfoot>
			<tr>
				<td colspan="5">
					<div id="paging"></div>
				</td>
			</tr>
		</tfoot>
	</table>
	
	<br>
	

</body>
</html>