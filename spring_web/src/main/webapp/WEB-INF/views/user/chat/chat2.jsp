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
	  <button type="button" id="disconnectBtn">뒤로</button>
	</div>
	
	<div id="msgArea"></div>
	
	<div>	  	  
	  <input type="text" id="msg" name="msg" placeholder="메세지를 입력하세요">	
	  <input type="button" value="전송" id="sendBtn">
	</div>
	
	
	<script>	
	var userid = '${loginuser.id }';	
	var chatNo = '<c:out value="${chatNo}" />';	
	var ws;	
	
	console.log("채팅방번호:" + chatNo);
	
	connect();
	
	function connect() {
		ws = new WebSocket("ws://localhost:8070/chatServer");
				
		ws.onopen = function(){
			console.log('연결생성');
			sendMsg("enter", "");
			$('#msg').focus();
			var msg = {
				type : "regiter",	
			    userid : userid
			};
			
			//ws.send(JSON.stringify(msg));
		};
		
		ws.onmessage = function(e){
			console.log('서버로부터 받은 메세지: ' + e.data);
			addMsg(e.data);
		};
		
		ws.onclose = function(){
			console.log('연결 종료');
		};
	}
	
	//받은 메세지 채팅영역에 표시
	function addMsg(data){
		var tag = '<div style="margin-bottom:3px;">';
			tag += data;
			tag += '<span style="font-size:11px;color:#gray;">';
			tag += new Date().toLocaleTimeString();
			tag += '</span></div>';
			$('#msgArea').append(tag);
	}
	
				
	$('#sendBtn').on("click", function(){
		
		sendMsg("chat", $('#msg').val()); //메세지를 전송함
		
		$('#msg').val('');//메세지를 이제 지워줘야지
		$('#msg').focus();
	});
	
	$('#msg').keydown(function(){ //엔터 치면 되게
		if(event.keyCode == 13){ //enter키가 아스키코드로 13임
			sendMsg("chat", $('#msg').val()); //메세지를 전송함
			
			$('#msg').val('');//메세지를 이제 지워줘야지
			$('#msg').focus();
		}
	});
	
	//메세지 전송
	function sendMsg(step, msg){
		var msg = {
			step : step,
			chatNo : chatNo,
			userid : userid,
			msg : msg 
		};
		
		ws.send(JSON.stringify(msg));
	}
	
	//뒤로 버튼
	$('#disconnectBtn').click(function(){
		location.replace("/chatList");
		sendMsg("out", "");
		//서버 @OnClose()라는 어노텐션이 호출됨
		ws.close();
	});
		
	</script>
</body>
</html>