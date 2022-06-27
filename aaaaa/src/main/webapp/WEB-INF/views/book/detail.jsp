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
		//별점	
		//별점 마킹 모듈 프로토타입으로 생성
		
		
		$(function(){
			fnReviewList();
			fnRating();
			fnList();
		})	

		// 1. 댓글목록
		var page = 1;  // 초기화
		function fnList(){
			$.ajax({
				url: '${contextPath}/book/replyList',
				type: 'GET',
				data: 'bookNo=${book.bookNo}',
				contentType: "application/json; charset=utf-8",
				success: function(obj){
					alert('ddd')
				}
			})
		}
		
		function fnPrintMemberList(res){
			$.each(res, function(i, review){
				var tr = '<tr>';
				tr += '<td>' + review.bookReplyNo + '</td>';
				tr += '<td>' + review.memberId + '</td>';
				tr += '<td>' + review.bookReplyContent + '</td>';
				tr += '<td>' + review.bookReplyCreated + '</td>';
				tr += '<td>' + review.bookRating + '</td>';
				tr += '</tr>';
				$('#reviewList').append(tr);
			})
		}
		
		
		function fnReviewList(){
			$('#btnReg').on('click', function(){
				var review = JSON.stringify({
					bookNo:$('#bookNo').val(),
					bookReplyContent:$('#bookReplyContent').val(),
					bookRating: $(':radio[name="bookRating"]:checked').val()
				});
				$.ajax({
					url: '${contextPath}/book/review',
					type: 'post',
					data: review,
					contentType: 'application/json',
					dataType: 'json',
					success: function(obj){
							if(obj.res > 0){
								alert('감상평이 등록되었습니다.');
								
							}else{
								alert('감상평 등록에 실패했습니다.');
							}
					}
				
			})
		})
	}
	
		function fnRating(){
			
			
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
		

</style>
</head>
<body>
	
			<div>
				<img src="${book.bookImage}" width="200px" height="300px" >
				<dl>
					<dt>${book.bookTitle}</dt>
					<dt>${book.bookAuthor}</dt>
					<dt>${book.bookPublisher}</dt>
					<dt>${book.bookPubdate}</dt>
					<dt>${book.bookField}</dt>
					
					<br>
					
					<h3>책소개</h3>
					<dt>${book.bookDescription}</dt>
				</dl>
			</div>
			<br><br>
	
	
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
			
			<br><br>
			
			<table border="1">
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
				
				</tfoot>
			</table>
    	
    
     	
</body>
</html>