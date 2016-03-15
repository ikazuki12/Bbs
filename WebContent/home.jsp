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
	<div class="menu">
		<a href="message">新規投稿</a> /
		<a href="control">ユーザー管理</a> /
		<a href="logout">ログアウト</a>
		<c:if test="${ not empty getParameter }">
			 / <a href="./">戻る</a>
		</c:if>
	</div>
	<div class="user_name">
		<c:out value="${ loginUser.name }" />
	</div>
<hr />
<h2>掲示板一覧</h2>
<c:if test="${ not empty errorMessages }">
	<ul>
		<c:forEach items="${ errorMessages }" var="message">
			<li><span><c:out value="${message}" /></span></li>
		</c:forEach>
	</ul>
	<c:remove var="errorMessages" scope="session" />
</c:if>
<c:if test="${ not empty deleteMessage }">
	<ul class="delet_messaeg">
		<c:forEach items="${ errorMessages }" var="message">
			<li><span><c:out value="${message}" /></span></li>
		</c:forEach>
	</ul>
	<c:remove var="errorMessages" scope="session" />
</c:if>
<form action="messgeSelect" method="get">
	<table class="message_select">
		<tr>
			<td>カテゴリー</td><td><select name="category">
			<option value="all">全て</option>
				<c:forEach items="${ selectMessages }" var="message">
					<c:choose>
						<c:when test="${ editCategory == message.category }">
							<option value="${ message.category }" selected>${ message.category }</option>
						</c:when>
						<c:otherwise>
							<option value="${ message.category }">${ message.category }</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
		</select></td>
		</tr>
		<tr>
			<td>投稿日</td><td><input type="date" name="start_date" min="2016-01-01" value="${ editStartDate }"> ～
				<input type="date" name="end_date" min="2016-01-01" value="${ editEndDate }"></td>
		</tr>
		<tr>
			<td class="submit"><input type="submit" value="絞り込み"></td>
		</tr>
	</table>
</form>

<c:forEach items="${ messages }" var="message">
<form action="comment" method="post">
<input type="hidden" name="message_id" value="${ message.messageId }">
<table class="messages"  >
	<tr>
		<td><c:out value="${ message.messageId }" />.</td><td>&nbsp;</td><td class="delete"><a class="delete" href="?message_id=${ message.messageId }&user_id=${ message.userId }">×</a></td>
	</tr>
	<tr>
		<td colspan="2"><pre><c:out value="${ message.category }" /></pre></td>
	</tr>
	<tr>
		<th><c:out value="${ message.subject }" /></th>
	</tr>
	<tr>
		<td colspan="3"><hr /></td>
	</tr>
	<tr>
		<td nowrap><c:out value="${ message.name }" />&nbsp;&nbsp;&nbsp;
		<fmt:formatDate value="${ message.insertDate }" pattern="yyyy年MM月dd日 HH時mm分" /></td>
	</tr>
	<tr>
		<td colspan="3" class="message_text"><pre><c:out value="${ message.text }" /></pre></td>
	</tr>
	<tr>
		<td colspan="3"><hr /></td>
	</tr>
	<tr>
		<td><div class="comment">コメント</div></td>
		<td>
	</tr>
	<c:forEach items="${ comments }" var="comment">
		<c:if test="${ comment.messageId == message.messageId }">
			<tr>
				<td colspan="3"><hr /></td>
			</tr>
			<c:forEach items="${ users }" var="user">
				<c:if test="${ user.id == comment.userId }">
					<tr>
						<td><c:out value="${ user.name }"></c:out></td>
					</tr>
				</c:if>
			</c:forEach>
			<tr>
				<td nowrap><fmt:formatDate value="${ comment.insertDate }" pattern="yyyy年MM月dd日 HH:mm" /></td>
			</tr>
			<tr>
				<td colspan="3" class="comment_text"><pre><c:out value="${ comment.text }" /></pre></td>
			</tr>
		</c:if>
	</c:forEach>
	<tr>
		<td colspan="3"><hr /></td>
	</tr>
	<tr>
		<th>内容</th><td><textarea name="text" rows="10" cols="50"></textarea>
	</tr>
	<tr>
		<td>&nbsp;</td><td><input type="submit" value="コメントする"></td>
</table>
</div>
</form>
</c:forEach>
</c:if>
</body>
</html>