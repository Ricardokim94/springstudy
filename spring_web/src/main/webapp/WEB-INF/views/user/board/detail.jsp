<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!DOCTYPE html>
<html>
<head>
	<link rel ="stylesheet" href="/css/board.css">
	<title>게시판</title>
	<script src="https://kit.fontawesome.com/236f0b5985.js" crossorigin="anonymous"></script>
</head>

			<%@ include file="../common.jsp" %>
			<%@ include file="../header.jsp" %>
			<%@ include file="../menu.jsp" %>



<div class="row">
  <div class="leftcolumn">
    <div class="card">
		
  <!--<form name="boardForm" method="post" action="/board/boardForm.jsp">-->
	   <div class ="logo">
			<i class="fa-solid fa-clipboard"></i>
		 	<a href="">★상세내용★</a>	
		 	
		 	<c:set value="${board}" var="board" />
		 	<c:set value="${loginUser}" var="user" />
		 	
		 	<c:if test="${user.id eq board.id }">
			 	<button class = "button1" style=float:right onclick="location.href='/boardDetail.bo?seqno=${board.seqno}&page=momdify'">수정</button>
			 	<button class = "button1" style=float:right onclick="del_confirm('${board.seqno}')">삭제</button>
		 	 </c:if>
		 	 
		 	 <script>
		 	 	function del_confirm(seqno){
		 	 		var rs = confirm('정말로 삭제하겠습니다?');
		 	 		if(rs){
		 	 			location.href="boardDelete.bo?seqno=" + seqno;
		 	 		}
		 	 	}
		 	 </script>
		 	 
		 	  <hr>
				 <div class = "cccc">	
					 ★번호 : ${board.seqno}<hr>
					 ★작성날짜 : ${board.wdate}<hr>
					 ★작성자 : ${board.name}<hr>
					 ★조회수 : ${board.count}<hr>
					 ★내용 :${board.content}<hr>
				 	<hr>
					 <!-- 댓글 등록 폼 -->
					 <div id = "replyInput">
					    <textarea id="comment" name="comment" placeholder="댓글작성" rows="2" cols="50"></textarea>
					    <button id="addReplyBtn">댓글등록</button>
					</div>
				
				</div>	
			 
				
				<!-- 첨부파일 -->
				<div>
					<c:set value="${board.attachfile}" var="attachfile" />
					<c:if test="${attachfile != null}">
						<c:forEach items="${attachfile}" var="file">
							
							<form name="filedown" method="post" action="/file/fileDown">
							  <input type="hidden" name ="filename" value="${file.fileName }">
							  <input type="hidden" name="savefilename" value="${file.saveFileName }">	
							  <input type="hidden" name ="filepath" value="${file.filePath }">
							  
								<c:set value="${file.filetype}" var = "filetype" />
								<!-- 파일타입이 이미지 인지 아닌지 뜯어내야됨 -->
								<c:set value="${fn:substring(filetype,0,fn:indexOf(filetype,'/')) }" var="type" />
								
								<c:if test="${type eq 'image' }">
									<c:set value="${file.thumbnail.fileName}" var="thumb_file" />
									<img src="/upload/thumbnail/${thumb_file}">
								</c:if>
								${file.fileName} (파일 사이즈 :${file.fileSize})
								<input type ="submit" value="다운로드">
							</form>
						</c:forEach>
					</c:if>
				</div>
				
				
				<!-- 댓글 리크스 출력 영역 -->				
				<div id="reply-list">
					<ul class="reply_ul">
						<li data-rno='45'><!-- data-변수이름 -->
							<div>작성자 | 작성일자 | 댓글내용</div>
						</li>
					</ul>
										
				</div>
				
				<div class = reply> 
			    <c:set value="${board.reply}" var="reply" />
			    <c:if test="${reply != null}">
			    	<c:forEach items="${reply}" var="r" >
			    		${r.comment} 
						${r.name}
						${r.wdate}
						<hr>
			    	</c:forEach>
			    </c:if>
		    	</div>  
		
		</div>
    
   
	
  </div>
 </div>
  <div class="rightcolumn">
    <div class="card">
      <h2>Save the endangered otter</h2>
      <!-- <div class="fakeimg" style="height:200px;">Image</div> -->
      <img src ="/img/기도.jpg">
    </div>
    <div class="card">
      <h3>Popular Post</h3>
      <div class="fakeimg"><p>Image</p></div>
      <div class="fakeimg"><p>Image</p></div>
      <div class="fakeimg"><p>Image</p></div>
    </div>
    <div class="card">
      <h3>Follow Me</h3>
      <p>Some text..</p>
    </div>
  </div>
</div>


<%@ include file="../footer.jsp" %>

<%@ include file="../member/login_modal.jsp" %>



  <!-- 로그인창 만들기  -->
<div id ="reply_modal">
	<div class ="modal-content">
		<h2>댓글</h2>
		<button id="modalCloseBtn" style="float: right; font_size:25px">&#10062;</button>
		
		<textarea name="content" style="width:300px; height:75px;"></textarea>
		
		<button id="replyModifyBtn">댓글수정</button>  
		<button id=replyDeleteBtn >댓글삭제</button> 
		</p>	
	</div>
</div>




<script type="text/javascript" src="/js/reply.js"></script>
<script>
/* 즉시실행함수 
 * (function(){
	 문장;
 })
 */
 var seqno = '<c:out value="${board.seqno}" />';
 var id = '<c:out value="${user.id}" />';
 
$(document).ready(function(){
	console.log(replyService);
	
	console.log("==========================");
	console.log("Reply get LIST");
	
	var modal = $("#reply_modal");
	var modal_content = modal.find("textarea[name='content']");
	
	modal.hide();
	
	/* 모달 닫기 버튼 */
	$("#modalCloseBtn").on("click", function(e){
		modal.hide();
	});
	//ul이라는 테그에 li부분이 클릭이 되면 실행이 되어라!
	$(".reply_ul").on("click", "li", function(e){
		var rno = $(this).data("rno");
		replyService.get(rno, function(data){
			console.log("REPLY GET DATA");
			console.log("댓글"+ rno + "번 내용 :" + data.content);
			modal_content.val(data.content);
			modal.data("rno", data.seqno);
		});
		modal.show();
	});
	
	//댓글 수정버튼 클릭 시
	$("#replyModifyBtn").on("click", function(e){
		console.log("댓글 수정 번호 : " + modal.data("rno"));
		console.log("댓글 수정 내용 : " + modal_content.val());

		var reply ={seqno : modal.data("rno"),
					content : modal_content.val()};
		
		 replyService.update(reply, function(result){
			alert(result);
			modal.hide();
			showList(1);
		 });
	});
	
	
	
	showList(1);
	function showList(page){
		replyService.getList({bno:seqno, page:1}, function(list){
			
			/* 댓글이 없는 경우 */
			if(list == null || list.length == 0){
				$(".reply_ul").html(""); //아무것도 안보이면됨
				return;
			}
			/* 댓글이 있는 경우 */
			var str ="";
			for(var i =0, len=list.length || 0; i< len; i++){
				console.log(list[i]);
				str += "<li data-rno='" + list[i].seqno +"'><div class='replyRow'>" + list[i].rn + " | " + list[i].id ;
				str += " | " + list[i].wdate + " | " + list[i].content + "</div></li>";
			}
			$(".reply_ul").html(str);
		});
	}
	
	
	
	$("#addReplyBtn").on("click", function(e){
		var comment = document.getElementById("comment").value;
		
		var reply = {
				boardNo : seqno,
				id:id,
				comment:comment
		};
		
		replyService.add(reply, function(result){
			alert("댓글이 등록되었습니다." + result);
			document.getElementById("comment").value = "";
			document.getElementById("newLine").innerHTML = "<li>" + reply.comment + "</li>";
		});
	});
	
});
</script>



</body>
</html>