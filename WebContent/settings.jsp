<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー編集</title>
</head>
<body>
<div class="menu">
	<a href="control">戻る</a>
</div>
<div class="user_name">
	<c:out value="${ loginUser.name }" />
</div>
<hr />
<div class="main">
<h3>社員編集</h3>
<c:if test="${ not empty errorMessages }">
	<div class="errorMessages">
		<ul>
			<c:forEach items="${ errorMessages }" var="message">
				<li><span><c:out value="${message}" /></span></li>
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope="session"/>
</c:if>
<form action="settings" method="post">
<table class="setting">
	<tr>
		<th>ログインID</th>
		<td><input type="text" name="login_id" value="${ user.loginId }" /></td>
	</tr>
	<tr>
		<th>パスワード</th>
		<td><input type="password" name="password"></td>
	</tr>
	<tr>
		<th>パスワード(確認用)</th><td><input type="password" name="password_check" /></td>
	</tr>
	<tr>
		<th>名前</th>
		<td><input type="text" name="name" value="${ user.name }"></td>
	</tr>
	<tr>
		<th>所属支店(本社含む)</th>
		<td>
			<select name="branch_id">
				<c:forEach items="${ branches }" var="branch">
					<c:choose>
						<c:when test="${ branch.id == user.branchId }">
							<option value="${ branch.id }" selected>${ branch.name }</option>
						</c:when>
						<c:otherwise>
							<option value="${ branch.id }">${ branch.name }</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<th>部署・役職</th>
		<td>
			<select name="position_id">
				<c:forEach items="${ positions }" var="position">
					<c:choose>
						<c:when test="${ position.id == user.positionId }">
							<option value="${ position.id }" selected>${ position.name }</option>
						</c:when>
						<c:otherwise>
							<option value="${ position.id }">${ position.name }</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</td>
	</tr>
</table>
<div class="submit">
	<input type="hidden" name="user_id" value="${ user.id }" />
	<input type="hidden" name="not_entered_password" value="${ user.password }" />
	<input type="submit" value="編集">
</div>
</form>
</div>
</body>
</html>