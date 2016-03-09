<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新規登録</title>
</head>
<body>
<c:if test="${ not empty errorMessages }">
	<div class="errorMessages">
		<ul>
			<c:forEach items="${ errorMessages }" var="message">
				<li><c:out value="${message}" />
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope="session"/>
</c:if>
<form action="signup" method="post">
<table>
	<tr>
		<th>ログインID</th><td><input type="text" name="login_id" value="${ editUser.loginId }" /></td>
	</tr>
	<tr>
		<th>パスワード</th><td><input type="password" name="password" /></td>
	</tr>
	<tr>
		<th>名前</th><td><input type="text" name="name" value="${ editUser.name }" /></td>
	</tr>
	<tr>
		<th>所属支店(本社含む)</th>
		<td>
			<select name="branch_id">
				<c:forEach items="${ branches }" var="branch">
				<option value="${ branch.id }">${ branch.name }</option>
				</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<th>所属先(部署・役職)</th>
		<td>
			<select name="position_id">
				<c:forEach items="${ positions }" var="position">
				<option value="${ position.id }">${ position.name }</option>
				</c:forEach>
			</select>
		</td>
	</tr>
</table>
<input type="submit" value="登録"><br />
</form>
<a href="./">戻る</a>
</body>
</html>