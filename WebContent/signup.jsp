<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新規登録</title>
</head>
<body>
<div class="menu">
	<a href="control">戻る</a>
</div>
<div class="user_name">
	<c:out value="${ loginUser.name }" />
</div>
<hr />
<c:if test="${ not empty errorMessages }">
	<ul>
		<c:forEach items="${ errorMessages }" var="message">
			<li><span><c:out value="${message}" /></span></li>
		</c:forEach>
	</ul>
	<c:remove var="errorMessages" scope="session" />
</c:if>
<div class="main">
<h3>社員登録</h3>
<form action="signup" method="post">
<table class="signup">
	<tr>
		<th>ログインID</th><td><input type="text" name="login_id" value="${ editUser.loginId }" /></td>
	</tr>
	<tr>
		<th>パスワード</th><td><input type="password" name="password" /></td>
	</tr>
	<tr>
		<th>パスワード(確認用)</th><td><input type="password" name="password_check" /></td>
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
		<th>部署・役職</th>
		<td>
			<select name="position_id">
				<c:forEach items="${ positions }" var="position">
				<option value="${ position.id }">${ position.name }</option>
				</c:forEach>
			</select>
		</td>
	</tr>
</table>
<div class="submit"><input type="submit" value="登録"></div>
</form>
</div>
</body>
</html>