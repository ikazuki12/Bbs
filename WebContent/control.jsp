<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー管理</title>

<script type="text/javascript">
<!--

function stopped(){

    if(window.confirm('停止しますか？')){

    }
}

function stop(){

    if(window.confirm('停止しますか？')){

    }
}

// -->
</script>
</head>
<body>
<a href="signup">ユーザー新規登録</a>
<hr />
<h3>ユーザー一覧</h3>
<table class="control" border="1">
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
<br />
<a href="./">戻る</a>
</body>
</html>