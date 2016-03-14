<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新規投稿</title>
</head>
<body>
<h3>新規投稿</h3>
<hr />
<c:if test="${ not empty errorMessages }">
	<ul>
		<c:forEach items="${ errorMessages }" var="message">
			<li><c:out value="${message}" /></li>
		</c:forEach>
	</ul>
	<c:remove var="errorMessages" scope="session" />
</c:if>
<c:if test="${ empty loginUser }">
ログインしてください。
<a href="./">戻る</a>
</c:if>
<c:if test="${ not empty loginUser }">
	<form action="message" method="post">
		<table border="1">
			<tr>
				<th>件名</th>
				<td><input type="text" name="subject" size="50" value="${ editMessage.subject }"></td>
			</tr>
			<tr>
				<th>本文</th>
				<td><textarea rows="20" cols="80" name="text" >${ editMessage.text }</textarea></td>
			</tr>
			<tr>
				<th>カテゴリー</th>
				<td><input type="text" name="category" value="${ editMessage.category }" size="35"></td>
			</tr>
		</table>
		<input type="submit" value="投稿" />
	</form>
</c:if>
<a href="./">戻る</a>
</body>
</html>