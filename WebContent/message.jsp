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
<c:if test="${ not empty loginUser }">
	<form action="massage" method="post">
		<table border="1">
			<tr>
				<th>件名</th>
				<td><input type="text" name="subject" size="50"></td>
			</tr>
			<tr>
				<th>本文</th>
				<td><textarea rows="25" cols="100" name="text"></textarea></td>
			</tr>
			<tr>
				<th>カテゴリー</th>
				<td><input type="text" name="category"></td>
			</tr>
		</table>
		<input type="submit" value="投稿" />
	</form>
</c:if>
</body>
</html>