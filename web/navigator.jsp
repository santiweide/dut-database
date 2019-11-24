<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>NormalUser_Query</title>
	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/bootstrap.min.css">
	<!-- href：里面的路径是你导入在static文件夹里面下面bootstrap.min.css所在的路径，下面两个属性一样 -->
	<script type="text/javascript" src="<%=basePath%>/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/js/jquery-3.4.1.min.js"></script>

</head>

  <body>
    <div class="container">
		<form name="uploadForm" method="POST" enctype="MULTIPART/FORM-DATA" action="upload">
	     <h2 class="form-signin-heading">User::Navigator</h2>
	  
        <a class="btn btn-lg btn-primary btn-block" href="query.jsp" role="button">点我模糊查询</a>
	  	<a class="btn btn-lg btn-primary btn-block" href="login.html" role="button">返回登陆界面</a>
     
	</div> <!-- /container -->


    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>