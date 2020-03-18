<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
 <%
	pageContext.setAttribute("newLine", "\n");

%> 
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/blog/includes/header.jsp" >
			<c:param name="title" value="${blogVo.title }"/>
		</c:import>
		<div id="wrapper">
			<div id="content">
				<div class="blog-content">
					<h4>${nowPost.title }</h4>
					<p>
						${fn:replace(nowPost.contents,newLine,"<br>") }
					<p>
				</div>
				<ul class="blog-list" >
					<c:forEach items="${postList }" var="postList" varStatus="status">
						<li><a href="${pageContext.request.contextPath}/${blogVo.id}/${nowPost.categoryNo}/${postList.no}">${postList.title }</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>

		
		<c:import url="/WEB-INF/views/blog/includes/navigation.jsp" >
			<c:param name = "id" value="${blogVo.id }"/>
			<c:param name ="logo" value ="${blogVo.logo }"/>
			<c:param name="categoryList" value="${categoryList }"/>
		</c:import>
		<c:import url="/WEB-INF/views/blog/includes/footer.jsp" />
	</div>
</body>
</html>