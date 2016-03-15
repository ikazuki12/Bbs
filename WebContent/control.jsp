<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー管理</title>

<script type="text/javascript">
<!--

function stopped(){

    if(window.confirm('停止してもよろしいですか？')){

    }
}

function stop(){

    if(window.confirm('復活してもよろしいですか？')){

    }
}

// -->
</script>
</head>
<body>
<div class="menu">
	<a href="signup">新規登録</a> /
	<a href="./">戻る</a>
</div>
<div class="user_name">
	<c:out value="${ loginUser.name }" />
</div>
<hr />
<div class="main">
<h3>社員一覧</h3>
<table class="control">
	<tr>
		<th>名前</th>
		<th>ログインID</th>
		<th>所属支店</th>
		<th>役職</th>
		<th>ユーザー</th>
	</tr>
	<c:forEach items="${ users }" var="user">
		<tr>
			<td><a href="settings?user_id=${ user.id }"><c:out value="${ user.name }" /></a></td>
			<td><c:out value="${ user.loginId }" /></td>
			<td>
				<c:forEach items="${ branches }" var="branch">
					<c:if test="${ branch.id == user.branchId }">
						<c:out value="${ branch.name }" />
					</c:if>
				</c:forEach>
			</td>
			<td>
				<c:forEach items="${ positions }" var="position">
					<c:if test="${ position.id == user.positionId }">
						<c:out value="${ position.name }" />
					</c:if>
				</c:forEach>
			</td>
			<c:if test="${ user.stopped }">
				<td><a href="?stopped=${ user.id }" onClick="stopped()">ON</a>
			</c:if>
			<c:if test="${ not user.stopped }">
				<td><a href="?stop=${ user.id }" onClick="stop()">OFF</a>
			</c:if>
		</tr>
	</c:forEach>
</table>
</div>
</body>
</html>