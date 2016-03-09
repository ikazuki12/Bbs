<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ホーム</title>
</head>
<body>
<c:if test="${ empty loginUser }">
<a href="login">ログイン</a>
</c:if>
<c:if test="${ not empty loginUser }">
	<c:if test="${ loginUser.positionId == 1 }">
		<a href="signup">ユーザー新規登録</a>
	</c:if>
	<a href="message">新規投稿</a>
	<a href="logout">ログアウト</a>
</c:if>

</body>
</html>