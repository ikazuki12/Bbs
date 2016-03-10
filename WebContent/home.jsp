<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>掲示板ホーム</title>
</head>
<body>
<c:if test="${ empty loginUser }">
<a href="login">ログイン</a>
</c:if>
<c:if test="${ not empty loginUser }">
	<a href="signup">ユーザー新規登録</a> /
	<a href="message">新規投稿</a> /
	<a href="logout">ログアウト</a>
<hr />
<c:if test="${ not empty errorMessages }">
			<ul>
				<c:forEach items="${ errorMessages }" var="message">
					<li><c:out value="${message}" /></li>
				</c:forEach>
			</ul>
			<c:remove var="errorMessages" scope="session" />
		</c:if>
<c:forEach items="${ messages }" var="message">
<form action="comment" method="post">
<input type="hidden" name="message_id" value="${ message.userId }">
<table class="messages">
	<tr>
		<td colspan="2"><c:out value="${ message.category }" /></td>
	</tr>
	<tr>
		<th><c:out value="${ message.subject }" /></th>
	</tr>
	<tr>
		<td colspan="2"><hr /></td>
	</tr>
	<tr>
		<td><c:out value="${ message.name }" />&nbsp;&nbsp;&nbsp;
		<fmt:formatDate value="${ message.insertDate }" pattern="yyyy年MM月dd日 HH時mm分" /></td>
	</tr>
	<tr>
		<td></td><td><c:out value="${ message.text }" /></td>
	</tr>
	<tr>
		<td colspan="2"><hr /></td>
	</tr>
	<tr>
		<td><div class="comment">コメント</div></td>
		<td>
	</tr>
	<c:forEach items="${ comments }" var="comment">
		<c:if test="${ comment.messageId == message.userId }">
			<tr>
				<td colspan="2"><hr /></td>
			</tr>

				<c:forEach items="${ users }" var="user">
					<c:if test="${ user.id == comment.userId }">
						<tr>
							<td><c:out value="${ user.name }"></c:out></td>
						</tr>
					</c:if>
				</c:forEach>
			<tr>
				<td><fmt:formatDate value="${ comment.insertDate }" pattern="yyyy年MM月dd日 HH:mm" /></td>
			</tr>
			<tr>
				<td><c:out value="${ comment.text }"></c:out></td>
			</tr>
		</c:if>
	</c:forEach>
	<tr>
		<td colspan="2"><hr /></td>
	</tr>
	<tr>
		<th>内容</th><td><textarea name="text" rows="10" cols="50"></textarea>
	</tr>
	<tr>
		<td>&nbsp;</td><td><input type="submit" value="コメントする"></td>
</table>
</form>
</c:forEach>
</c:if>
</body>
</html>