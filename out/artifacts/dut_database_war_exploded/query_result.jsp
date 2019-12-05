<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Result</title>
	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/bootstrap.min.css">
	<!-- href：里面的路径是你导入在static文件夹里面下面bootstrap.min.css所在的路径，下面两个属性一样 -->
	<script type="text/javascript" src="<%=basePath%>/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/js/jquery-3.4.1.min.js"></script>


</head>

<body>  
	 <table class="table table-striped">
	  <caption>查询结果共有<%= request.getAttribute("TOT") %> 条 ;第 <%=request.getAttribute("THIS") %>页显示<%= request.getAttribute("CURNUM") %> 条</caption>
	  <thead>
	    <tr>
	      <th>ID</th>
	      <th>姓名</th>
	      <th>电话</th>
	      <th>QQ</th>
	      <th>Email</th>
	    </tr>
	  </thead>
	  <tbody>
	    <%= request.getAttribute("DATA") %>
	  </tbody>
	</table>
	<ul class="pagination">
    <li><a href="?page=<%=request.getAttribute("PREV")%>">&laquo;</a></li>
    <%= request.getAttribute("PDATA") %>
    <li><a href="?page=<%=request.getAttribute("NEXT")%>">&raquo;</a></li>
	</ul>
	
	<form  name="pageSizeForm" method="Get" action="dispatcher">
	<table>
		<tr>
		<th><input type="text" class="form-control" placeholder="输入每页条数"  name="pageSize"></th>
		<th><button type="submit" class="btn btn-lg btn-primary btn-block" >设置</button></th>
		<th><a class="btn btn-lg btn-primary btn-block" href="query.jsp" role="button">返回搜索界面</a></th>
		</tr>
	</table>
	</form>
	
	

	
</body>
</html>