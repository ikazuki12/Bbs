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
<div class="topmenu">
	<div class="menu">
		<a href="message">新規投稿</a> /
		<c:if test="${ loginUser.positionId == 1 }">
			<a href="control">ユーザー管理</a> /
		</c:if>
		<a href="logout">ログアウト</a>
		<c:if test="${ not empty getParameter }">
			 / <a href="./">戻る</a>
		</c:if>
	</div>
	<div class="user_name">
		<c:out value="${ loginUser.name }" />
	</div>
	<hr />
</div>
<h2>掲示板一覧test</h2>
<c:if test="${ not empty errorMessages }">
	<ul>
		<c:forEach items="${ errorMessages }" var="message">
			<li><span><c:out value="${message}" /></span></li>
		</c:forEach>
	</ul>
	<c:remove var="errorMessages" scope="session" />
</c:if>
<c:if test="${ not empty successMessage }">
	<ul class="success_messaeg">
		<li><span><c:out value="${successMessage}" /></span></li>
	</ul>
	<c:remove var="successMessage" scope="session" />
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
		<td colspan="2">
			<pre>カテゴリー[<c:out value="${ message.category }" />]</pre>
		</td>
		<td class="delete">
			<a class="delete" href="delete?message_id=${ message.messageId }&user_id=${ message.userId }">×</a>
		</td>
	</tr>
	<tr>
		<td>
			[<c:out value="${ message.messageId }" />]
		</td>
	</tr>
	<tr>
		<td nowrap>
			<c:out value="${ message.name }" />&nbsp;&nbsp;&nbsp;
			<fmt:formatDate value="${ message.insertDate }" pattern="yyyy年MM月dd日 HH時mm分" />
		</td>
	</tr>
	<tr>
		<td colspan="3">
			<hr />
		</td>
	</tr>
	<tr>
		<td class="category_text">
			件名<pre><c:out value="${ message.subject }" /></pre>
		</td>
	</tr>
	<tr>
		<td colspan="3">
			<hr />
		</td>
	</tr>
	<tr>
		<td colspan="3" class="message_text">
			本文<pre><c:out value="${ message.text }" /></pre>
		</td>
	</tr>
	<tr>
		<td colspan="3">
			<hr />
		</td>
	</tr>
	<tr>
		<td>
			<div class="comment">コメント</div>
		</td>

	</tr>
	<c:forEach items="${ comments }" var="comment">
		<c:if test="${ comment.messageId == message.messageId }">
		<tr>
			<td colspan="3">
				<table class="comments">
					<tr>
						<td>
							[<c:out value="${ comment.id }" />]
						</td>
						<td>&nbsp;</td>
						<td class="delete">
							<a class="delete"
								href="delete?message_id=${ message.messageId }&comment_id=${ comment.id }&user_id=${ comment.userId }">
								×
							</a>
						</td>
					</tr>
					<c:forEach items="${ users }" var="user">
						<c:if test="${ user.id == comment.userId }">
							<tr>
								<td nowrap>
									<c:out value="${ user.name }" />
									<fmt:formatDate value="${ comment.insertDate }" pattern="yyyy年MM月dd日 HH:mm" />
								</td>
							</tr>
						</c:if>
					</c:forEach>
					<tr>

					</tr>
					<tr>
						<td colspan="3"><hr /></td>
					</tr>
					<tr>
						<td colspan="3" class="comment_text">
							<pre><c:out value="${ comment.text }" /></pre>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		</c:if>
	</c:forEach>
	<tr>
		<td colspan="3"><hr /></td>
	</tr>
	<tr>
		<th>内容</th>
		<td class="input_comment">
			<textarea name="text" rows="10"></textarea>
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>
			<input type="submit" value="コメントする">
		</td>
	</tr>
</table>
</form>
</c:forEach>
</body>
</html>