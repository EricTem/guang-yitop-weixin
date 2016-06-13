<%@page import="com.yitop.wechat.util.Configure"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
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
<script src="js/base.js"></script>
<script src="js/jquery-validate.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
var jssdkInfo = [ '${jssdkInfo.appId}', '${jssdkInfo.timestamp}',
				  '${jssdkInfo.nonceStr}', '${jssdkInfo.signature}',
				  ['chooseImage'] ];
$(document).ready(function() {
	apply(jssdkInfo);
});
function chooseImage(){
	wx.chooseImage({
	    count: 1, // 默认9
	    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
	    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
	    success: function (res) {
	        localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
	        var d = dialog({
                fixed: true,
                height:"340",
                width:"240",
                content: '<img style="width:100%; max-height:100%;" src="'+localIds+'" />',
                okValue: '确定',
                ok: function () {
                    alert("OK")
                },
                cancelValue: '取消',
                cancel: function () {
                }
            });
    	d.show();
	      //  var divshow = $("#guidelist");
         //var _tr = $("<img src='"+localIds+"'></img>");
	      // divshow.append(_tr);
	      //	uploadImage(orderId);
	    }
	});
}
</script>
</head>
<body>
<hr align=center width=300 color=#987cb9 size=1>
你好！你的openId = 
<div>${openId}</div>
<div onclick="chooseImage();">选择图片</div>
</body>
</html>