<%@page import="com.yitop.wechat.util.Configure"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String basePath = Configure.serviceRootUrl +"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath %>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script src="js/jquery-2.1.4.min.js"></script>
<script src="js/jquery-validate.js"></script>
<script type="text/javascript">
</script>
</head>
<body>
<hr align=center width=300 color=#987cb9 size=1>
你好!
<a href="cxy/webNoValidAction/getWxUserInfo">获取getAccessToken</a>
</body>
</html>