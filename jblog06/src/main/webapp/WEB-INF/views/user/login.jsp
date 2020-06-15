<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
</head>
<body>
	<div class="center-content">
		<h1 class="logo">JBlog</h1>
		
		<c:choose>
			<c:when test="${blog eq null }">
				<form class="login-form" method="post" action="${pageContext.request.contextPath }/user/auth">
		      		<label>아이디</label> <input type="text" name="id">
		      		<label>패스워드</label> <input type="password" name="password">
		      		<input type="submit" value="로그인">
		   
		      		<c:if test="${not empty userVo }">
								<p>
									로그인에 실패 했습니다.
								</p>
					</c:if>	
				</form>
			</c:when>
			<c:otherwise>
				<form class="login-form" method="post" action="${pageContext.request.contextPath }/user/blogauth">
		      		<label>아이디</label> <input type="text" name="id">
		      		<label>패스워드</label> <input type="password" name="password">
		      		<input type="hidden" name="blog" value="${blog }"/>
		      		<input type="hidden" name="now" value="${now }"/>
		      		<input type="submit" value="로그인">
		   
		      		<c:if test="${not empty userVo }">
								<p>
									로그인에 실패 했습니다.
								</p>
					</c:if>
		      		
				</form>
			</c:otherwise>
		</c:choose>
		
	</div>
</body>
</html>