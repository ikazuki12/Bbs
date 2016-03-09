<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ログイン</title>
</head>
<body>
<c:if test="${ not empty errorMessages }">
	<ul>
		<c:forEach items="${ errorMessages }" var="message">
			<li><c:out value="${message}" /></li>
		</c:forEach>
	</ul>
	<c:remove var="errorMessages" scope="session" />
</c:if>
<form action="login" method="post">
<table>
	<tr>
		<th><label for="login_id">ログインID</label></th>
		<td><input name="login_id" value="${ editUser }" /></td>
	</tr>
	<tr>
		<th><label for="password">パスワード</label></th>
		<td><input name="password" type="password" /></td>
	</tr>
</table>
	<input type="submit" value="ログイン"><br />
	<a href="./">戻る</a>
</form>
</body>
</html>