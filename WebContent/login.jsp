<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ログイン</title>
</head>
<body>
<c:if test="${ not empty errorMessages }">
	<ul>
		<c:forEach items="${ errorMessages }" var="message">
			<li><span><c:out value="${message}" /></span></li>
		</c:forEach>
	</ul>
	<c:remove var="errorMessages" scope="session" />
</c:if>
<form action="login" method="post">
<table class="login">
	<tr>
		<td class="title" colspan="2">ログイン</td>
	</tr>
	<tr>
		<th><label for="login_id">ログインID</label></th>
		<td><input name="login_id" value="${ editUser }" /></td>
	</tr>
	<tr>
		<th><label for="password">パスワード</label></th>
		<td><input name="password" type="password" /></td>
	</tr>
	<tr>
		<th colspan="2"><input type="submit" value="ログイン"></th>
	</tr>
</table>
</form>
</body>
</html>