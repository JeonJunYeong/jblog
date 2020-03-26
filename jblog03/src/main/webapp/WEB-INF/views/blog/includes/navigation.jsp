<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

		<div id="extra">
			<div class="blog-logo">
				<img src="${pageContext.request.contextPath}/images/${param.logo }">
			</div>
		</div>


		<div id="navigation">
			<h2>카테고리</h2>
			<ul>
				<c:forEach items="${categoryList }" var="categoryList" varStatus="status">
					<li><a href="${pageContext.request.contextPath }/${id }/${categoryList.no }">${categoryList.name }</a></li>	
				</c:forEach>
				
			</ul>
		</div>