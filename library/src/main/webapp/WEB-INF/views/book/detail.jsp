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
			fnList();
			fnSaveReply();
			fnInit();
			fnRecomBook();
		})	
	
		// 1. 댓글목록
		function fnList(){
			$.ajax({
				url: '${contextPath}/reply/list',
				type: 'get',
				data: 'bookNo=${book.bookNo}',
				dataType: 'json',
				success: function(obj){
					$('#reviewList').empty();
					$('#replyCount').text(obj.replyCount);
					$('#replyRatingAverage').text(obj.replyRatingAverage);
					$.each(obj.replies, function(i, reply){
						var tr = '<tr>'
						var userRating = reply.bookRating
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
			})
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

		// 추천책
		function fnRecomBook(){
			$.ajax({
				url: '${contextPath}/book/recomBook',
				type: 'get',
				dataType : 'json',
				success: function(obj){
					$.each(obj.recom, function(i, recom){
						var sp = '<span>';
						sp += '<ul><img src="' + recom.bookImage + '" width=150px height=170px></ul>';
						sp += '<ul>' + recom.bookTitle + '</ul>';
						sp += '</span>'
						$('#recomeBook').append(sp);
					})
				}
				
			})
			
		}
		
		
		
		function fnInit(){
			$('#bookReplyContent').val('');
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
			
			<h3>추천도서</h3>
			<div id="recomeBook"></div>
			
			<br>
			
			<h3>감상평</h3>
			
			<div>
		감상평 <span id="replyCount"></span>개 평균평점 <span id="replyRatingAverage"></span>점
		   </div>
			
			<table border="1">
				<thead>
					<tr>
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