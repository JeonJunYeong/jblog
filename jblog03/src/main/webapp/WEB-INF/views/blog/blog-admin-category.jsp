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
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-3.4.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/ejs/ejs.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">


var id = '${id}';
var min = '${min}';
var size = 0;
var listItemTemplate = new EJS({
	url: "${pageContext.request.contextPath }/assets/js/ejs/list-item-template.ejs"
});
var listTemplate = new EJS({
	url: "${pageContext.request.contextPath }/assets/js/ejs/list-template.ejs"
});

var messageBox =function(title,message,callback){
	$('#dialog-message p').text(message);
	$('#dialog-message').attr('title', title).dialog({
		modal : true,
		buttons : {
			"확인" : function() {
				$(this).dialog("close");
			}
		},
		close : callback
	});
}

var deleteBox =function(data,len){

	 	$('#dialog-message p').text('삭제하시려면 확인을 눌러주세요');
	$('#dialog-message').attr('title', '카테고리 삭제').dialog({
		modal : true,
		buttons : {
			"확인" : function() {
				var no = data;
				var id ='${id}';
				var leng = len;
				var total = $('.admin-cat').find('tr').length;
				
				$.ajax({
					url: '${pageContext.request.contextPath }/apiblog/delete/'+no+'/'+id,
					async : true,
					type : 'delete',
					dataType : 'json',
					data: '',
					success : function(response){
					
					
					$(".admin-cat tr[data-no="+no+"]").remove();
					// var arr = $('.admin-cat tr').find("td").text();
					 if(total!=leng){
						
						 for(var i=1;i<(total-leng);i++){
							  if(leng>i){
								var nowNum= Number($(".admin-cat tr:eq("+i+")>td:eq(0)").text())-1;
								$(".admin-cat tr:eq("+i+")>td:eq(0)").html(nowNum);
								
							}else{
								break;
							}
						}   
						 
						 
					} 
					
					//$("#list-guestbook tr[data-no="+no+"]").remove();
						
						
					},
					error : function(xhr, status, e){
						console.error(status + ":"+ e);
					}
				});
				
			
				$(this).dialog("close");
			},
			"취소" : function() {
				$(this).dialog("close");
			}
		}
		
		
	}); 
}

var fetchList= function(){
	
	$.ajax({
		url: '${pageContext.request.contextPath }/apiblog/category-list/'+id,
		async : true,
		type : 'get',
		dataType : 'json',
		data : '',
		success : function(response){
			
			if(response.result != "success"){
				console.error(response.message);
				return ;
			}
		
			
			for(var i = 0;i<response.data.length;i++){
			
				if(response.data[i].count>0){
					response.data[i].delMsg='삭제 할 수 없습니다';
				}else{
					response.data[i].delMsg='삭제';
				}
				
				if(response.data[i].no==min){
					response.data[i].delMsg='기본카테고리입니다';
				}	
			}
			console.log(response);
			response.imgSource='${pageContext.request.contextPath}/assets/images/delete.jpg';
		
			var html = listTemplate.render(response);
		$(".admin-cat").append(html); 
		
		
		},
		error : function(xhr, status, e) {
			console.error(status + ":" + e);
			
		}
	});
}

$(function(){
	
	$('#add-form').submit(function(event){
		event.preventDefault();
		
		var vo = {};
		vo.name = $("#name").val();
		vo.description = $("#description").val();
		vo.id="${id}";
		
		
		if (vo.name == '') {

			messageBox('방명록 글 남기기', '카테고리 이름은 필수 항목 입니다.', function() {
				$('#name').focus();
			});
			return;
		}

		
		if (vo.description == '') {	

			messageBox('방명록 글 남기기', '설명은 필수 항목 입니다.', function() {
				$('#description').focus();
			});
			return;
		}
		
		console.log("N:"+vo.name+",D:"+vo.description+",ID:"+vo.id);
		
		$.ajax({
			url : '${pageContext.request.contextPath }/apiblog/category-add',
			async:true,
			type:'post',
			dataType:'json',
			contentType:'application/json',
			data :  JSON.stringify(vo),
			success : function(response)
			{
				response.data.imgSource='${pageContext.request.contextPath}/assets/images/delete.jpg';
				response.data.len= $('.admin-cat').find('tr').length;
				var html = listItemTemplate.render(response.data);
				
				$('.admin-cat tr').first().after(html); 
				$("#add-form")[0].reset();
				
				
				 
			},
			error : function(xhr, status, e) {
				console.error(status + ":" + e);
			}
		});
	});
	
	fetchList();
	
});

</script>

</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/blog/includes/header.jsp" >
			<c:param name="title" value="${blogVo.title }"/>
			<c:param name = "now" value="${blogVo.id }"/>
		</c:import>
		<div id="wrapper">
			<div id="content" class="full-screen">
				<c:import url="/WEB-INF/views/blog/includes/admin-menu.jsp"/>
		      	
		      	
		      	<table class="admin-cat">
		      		<tr>
		      			<th>번호</th>
		      			<th>카테고리명</th>
		      			<th>포스트 수</th>
		      			<th>설명</th>
		      			<th>삭제</th>      			
		      		</tr>
		      		
		      		<%--<c:set var="listCount" value ="${fn:length(list) }"/>
		      		 <c:forEach items="${list }" var="list" varStatus="status">
		      			<tr>
		      				<td>${listCount-status.index }</td>
		      				<td>${list.name }</td>
		      				<td>${list.count }</td>
		      				<td>${list.description }</td>
		      				<c:choose>
		      					<c:when test="${list.no>min }">
		      						<c:choose>
		      							<c:when test="${list.count==0 }">
		      								삭제 할 수 없습니다.
		      							</c:when>
		      							<c:otherwise>
		      								<td><a href="${pageContext.request.contextPath }/${blogVo.id }/delete/${list.no}"><img src="${pageContext.request.contextPath}/assets/images/delete.jpg"></a></td>
		      							</c:otherwise>
		      						</c:choose>
		      					</c:when>
		      					<c:otherwise>
		      						<td>기본 카테고리</td>
		      					</c:otherwise>
		      				</c:choose>
		      					
		      			</tr>
		      		</c:forEach> --%>
							  
				</table>
      		<form action="" <%-- "${pageContext.request.contextPath }/${id }/admin/category"  --%>id="add-form" method="post" >
      			<h4 class="n-c">새로운 카테고리 추가</h4>
		      	<table id="admin-cat-add">
		      		<tr>
		      			<td class="t">카테고리명</td>
		      			<td><input type="text" name="name" id ="name"></td>
		      		</tr>
		      		<tr>
		      			<td class="t">설명</td>
		      			<td><input type="text" name="description" id="description"></td>
		      		</tr>
		      		<tr>
		      			<td class="s">&nbsp;</td>
		      			<td><input type="submit" value="카테고리 추가"></td>
		      		</tr>      		      		
		      	</table> 
		      </form>
			</div>
			
			<div id="dialog-message" title="테스트입니다" style="display: none">
				<p>테스트입니다.</p>
			</div>
		
		</div>
		
		<c:import url="/WEB-INF/views/blog/includes/footer.jsp" />
	</div>
</body>
</html>