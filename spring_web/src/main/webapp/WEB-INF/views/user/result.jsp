<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	결과 페이지
</h1>
 ${msg} <br>
 아이디 :${member.id } <br>
 이름 : ${member.name }
 게시물 제목 : ${board.title}

</body>
</html>
