<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<c:set value="${loginUser}" var="loginuser" /> 
   
<div class="topnav">
  <a href="/">Home</a>
  <a href="#">상품</a>
  <a href="/board/list">게시판</a>  
  
  <c:if test="${loginuser != null}" >
    <!-- <a class="chat" href="javascript:chatpop()">채팅</a>  -->
    <a class="chat" href="/chat">채팅</a>    
    <a class="chat" href="/chatList">채팅리스트</a>
    <a class="chat" href="/chat3">1:1채팅</a>
    <font color="white">${loginuser.name}님 반갑습니다.</font>
    <a class="login" href="/member/mypage.jsp">마이페이지</a>  
  	<a class="login" href="/member/logout" >로그아웃</a>
  </c:if>
  	
  <c:if test="${loginuser eq null}" >	
  	<a class="login" href="javascript:loginpop()" >로그인</a>
  </c:if>		
</div>












