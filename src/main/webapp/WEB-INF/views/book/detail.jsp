<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<script src="../resources/js/jquery-3.6.0.js"></script>

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
		border: ;
	}
	
	
	
</style>
<script>
		//별점	
		//별점 마킹 모듈 프로토타입으로 생성
		
		
		$(function(){
			fnList();
			fnSaveReply();
			fnRecomBook();
			fnPagingLink();

		})	
		
		
		var page = 1;
		// 1. 댓글목록
		function fnList(){
			$.ajax({
				url: '${contextPath}/reply/list',
				type: 'GET',
				data: 'bookNo=${book.bookNo}' + '&page=' + page,
				dataType: 'json',
				success: function(obj){
					$('#replyCount').text(obj.replyCount);
					$('#replyRatingAverage').text(obj.replyRatingAverage);
					fnPrintReplyList(obj.replies, obj.p);
					fnPrintPaging(obj.p);
					
				}
			})
		}
		// 1-1 목록출력
		function fnPrintReplyList(replies, p){
			$('#reviewList').empty();
			console.log(p);
			$.each(replies, function(i, reply){
				var tr = '<tr>'
				var userRating = reply.bookRating
				
				tr += '<td>' + p.beginRecord++ + '</td>';
					switch(userRating){
					case 1 : tr += '<td class="userRating">★</td>'; break;
					case 2 : tr += '<td class="userRating">★★</td>'; break;
					case 3 : tr += '<td class="userRating">★★★</td>'; break;
					case 4 : tr += '<td class="userRating">★★★★</td>'; break;
					case 5 : tr += '<td class="userRating">★★★★★</td>'; break;
					}
				tr += '<td>' + reply.memberId + '</td>';
				tr += '<td>' + reply.bookReplyContent + '</td>';
				tr += '<td>' + reply.bookReplyCreated + '</td>';
				tr += '</tr>';
				$('#reviewList').append(tr);
				
			})
		}
		
		// 1-2 페이징 링크 처리
			function fnPagingLink(){
			$(document).on('click', '.enable_link', function(){
				page = $(this).data('page');
				fnList();
			})
		}
		
		// 1-3 목록출력
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
		
		// 2. 감상평등록
			function fnSaveReply(){
			$('#btnReg').on('click', function(){
				
				var review = JSON.stringify({
					bookNo:$('#bookNo').val(),
					bookReplyContent:$('#bookReplyContent').val(),
					bookRating: $(':radio[name="bookRating"]:checked').val()
				});
				$.ajax({
					url: '${contextPath}/reply/save',
					type: 'post',
					data: review,
					contentType: 'application/json',
					dataType: 'json',
					success: function(obj){
							if(obj.res > 0){
								alert('감상평이 등록되었습니다.');
								fnList();
								fnInit();
						}
					}
					
				})
			})
		}

			function fnInit(){
				$('#bookReplyContent').val('');
			}
		
		// 3. 추천책
			function fnRecomBook(){
			$.ajax({
				url: '${contextPath}/book/recomBook',
				type: 'get',
				dataType : 'json',
				success: function(obj){
					$.each(obj.recom, function(i, recom){
						var sp = '<span>';
						sp += '<ul><a href="${contextPath}/book/detail?bookNo=' + recom.bookNo + '"><img src="' + recom.bookImage + '" width=130px height=170px></a></ul>';
						sp += '<ul>' + recom.bookTitle + '</ul>';
						sp += '</span>'
						$('#recomeBook').append(sp);
					})
				}
				
			})
			
		}
		
	
</script>
<style>

	.contentGroup .no1{
		display: inline-flex;
		color: red;
		flex-direction: column;
		
	}
	.contentGroup .no2{
		display: inline-flex;
		flex-direction: column;
	}
		#contentReg fieldset{
		    display: inline-block;
		    direction: rtl;
		    border:0;
		}
		#contentReg input[type=radio]{
		    display: none;
		}
		#contentReg label{
		    font-size: 3em;
		    color: transparent;
		    text-shadow: 0 0 0 #f0f0f0;
		    font-size: 30px
		}
		#contentReg label:hover{
		    text-shadow: 0 0 0 rgba(250, 208, 0, 0.99);
		}
		#contentReg label:hover ~ label{
		    text-shadow: 0 0 0 rgba(250, 208, 0, 0.99);
		}
		#contentReg input[type=radio]:checked ~ label{
		    text-shadow: 0 0 0 rgba(250, 208, 0, 0.99);
		}
		
		.userRating{
			font-size: 3em;
		    color: transparent;
		    text-shadow: 0 0 0 rgba(250, 208, 0, 0.99);
		    font-size: 20px
		}
		
		#recomeBook{
			display: flex;
			text-align: center;
		}
		
		
		

</style>
</head>
<body>
		
			<div class="detailBookList">
				<span><img src="${book.bookImage}" width="200px" height="300px" ></span>
				
				<p>제목 : ${book.bookTitle}</p>
				<p>저자 : ${book.bookAuthor}</p>
				<p>출판사 : ${book.bookPublisher}</p>
				<p>분야 : ${book.bookField}</p>
				<p>isbn : ${book.bookIsbn}</p>
				
				<input type="button" value="대여하기" onclick="location.href='${contextPath}/book/detail?bookNo=${book.bookNo}'">

				<br>
					
			<h3>책소개</h3>
				<span>${book.bookDescription}</span>
				
			</div>
		
			<br><br>
			
			<h3>추천도서</h3>
			<div id="recomeBook"></div>
			
			<br>
			
			<div class="wrap">
    		<h3>한줄 감상평</h3>
			    <form id="contentReg">			 
			        <div class="review_rating">
			            <div>별점을 선택해 주세요.</div>
			            <div class="ratingStar">
			                <fieldset>
			               	    <input type="hidden" id="bookNo" name="bookNo" value="${book.bookNo}">
								<input type="radio" name="bookRating" value="5" id="rate1"><label
									for="rate1">★</label>
								<input type="radio" name="bookRating" value="4" id="rate2"><label
									for="rate2">★</label>
								<input type="radio" name="bookRating" value="3" id="rate3"><label
									for="rate3">★</label>
								<input type="radio" name="bookRating" value="2" id="rate4"><label
									for="rate4">★</label>
								<input type="radio" name="bookRating" value="1" id="rate5"><label
									for="rate5">★</label>
							</fieldset>
			            </div>
			        </div>
			        <div id="replyContent">
					  <span>
					  	 <label for="content"></label>
					  	 <textarea rows="3" cols="80" id="bookReplyContent" name="bookReplyContent" placeholder="한글 기준 50자까지 작성가능"></textarea>
					  </span>
					</div>
					  	<input type="button" id="btnReg" value="등록하기">
			    </form>
			</div>
			
			<h3>감상평</h3>
			
			<div id="replyInfo">
				<c:forEach items="${employees}" var="emp" varStatus="vs">
				<em>감상평 <span id="replyCount"></span>개 평균평점 <span id="replyRatingAverage"></span>점</em>
				</c:forEach>
				<input type="button" id="btnSearchAll" value="목록가기" onclick="location.href='${contextPath}/book/listPage'"/>
		   </div>
			
			<table class="replyList">
				<thead>

					<tr>
						<td>순번</td>
						<td>평점</td>
						<td>작성자</td>
						<td>내용</td>
						<td>작성일</td>
					</tr>
				</thead>
				<tbody id="reviewList">
				
				</tbody>
				<tfoot>
					<tr>
						<td colspan="5">
							<div id="paging"></div>
						</td>
					</tr>
				</tfoot>
			</table>
    	
    
     	
</body>
</html>