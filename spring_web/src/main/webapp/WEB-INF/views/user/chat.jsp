<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<title>Insert title here</title>
</head>
<body>
<c:set value="${loginUser}" var="loginuser" /> 
	<div>
	  <textarea rows="5" cols="30" id="msgArea"></textarea>
	  <br><input type="text" id="targetUser" placeholder="대화상대id">
	      <input type="text" id="chatMsg" placeholder="메세지를 입력하세요">	
	      <input type="button" value="전송" id="sendBtn">
	</div>
	
	
	<script>
	
	let url = "ws://localhost:8080/mychat";
	var userid = '${loginuser.id }';
	
	var ws;	
	
	connect();
	
	function connect() {
		ws = new WebSocket(url);
		
		
		ws.onopen = function(){
			console.log('연결생성');
			
			var msg = {
				type : "regiter",	
			    userid : userid
			};
			
			ws.send(JSON.stringify(msg));
		};
		
		ws.onmessage = function(e){
			console.log('받은 메세지: ' + e.data);
			//var data = e.data;
			//addMsg(data);
		};
		
		ws.onclose = function(){
			console.log('연결 종료');
		};
	}
	
	
	$(function(){		
		
		$('#sendBtn').on("click", function(){
			var chat = $("#msgArea").val();
			chat = chat + "\n[" + userid +"] " + $("#chatMsg").val();
			$("#msgArea").val(chat);
			sendMsg();
			$("#chatMsg").val("");
		});
	});
	
	//메시지 수신을 위한 서버에 id 등록
	function register(){
		
		console.log("사용자id:" + userid);
		var msg = {
			type:"register",
			userid: userid
		};
		
		ws.send(JSON.stringify(msg));
	}
	
	//메세지 전송
	function sendMsg(){
		var msg = {
			type : "chat",
			target : $("#targetUser").val(),
			message : $("#chatMsg").val() 
		};
		
		ws.send(JSON.stringify(msg));
	}
	
	//받은 메세지 채팅영역에 표시
	function addMsg(msg){
		alert(msg);
		var chat = $('#msgArea').val();
		chat = chat + "\n" + msg;
		$('#msgArea').val(chat);
	}
		
	</script>
</body>
</html>