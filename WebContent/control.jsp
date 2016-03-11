<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー管理</title>
</head>
<body>
<a href="signup">ユーザー新規登録</a>
<hr />
<h3>ユーザー一覧</h3>
<table class="control" border="1">
	<tr>
		<th>名前</th>
		<th>ログインID</th>
		<th>停止状態</th>
	</tr>
	<c:forEach items="${ users }" var="user">
		<tr>
			<td><a href="settings?user_id=${ user.id }"><c:out value="${ user.name }" /></a></td>
			<td><c:out value="${ user.loginId }" /></td>
			<c:if test="${ user.stopped }">
				<td><a href="?stopped=${ user.id }" onClick="alert('停止させますか？');">ON</a>
			</c:if>
			<c:if test="${ not user.stopped }">
				<td><a href="?stop=${ user.id }" onClick="alert('復活させますか？');">OFF</a>
			</c:if>
		</tr>
	</c:forEach>
</table>
</body>
</html>