<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>测试</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <meta name="apple-mobile-web-app-status-bar-style" content="black">
  <meta name="apple-mobile-web-app-capable" content="yes">
  <meta name="format-detection" content="telephone=no">
</head>
<body>
<OBJECT ID="OCX" CLASSID="CLSID:876b45b2-5c31-499f-a28b-11eef25c6746" codebase="${ctx}/static/cardrealize.ocx" height="0" width="0"></OBJECT>
		
<div style="width:200px;height:100px;float:left;">
	<button onclick="connect()">connect</button>
	<br/>
	<button onclick="putKey()">putKey</button>
	<br/>
	<button onclick="read()">read</button>
	<br/>
	<button onclick="write1()">write</button>
	<br/>			
</div>		 
<script src="${ctx}/static/jquery-1.8.0.min.js"></script>
</body>
<script>
  jQuery(function ($) {
	//connect();
	//var oxcCard = document.getElementByIdx("card");
	//if(oxcCard == null) {   
	  //alert("Sorry.");
	//}
  });
  
  function connect(){
	var data = OCX.OCX_Connect();
	alert(data);
  }
	
  function read(){				
	var data = OCX.OCX_Read3();
	alert(data);
  }
	
  function write1(){
	$.getJSON('${ctx}/writeCard', function(result) {
      if(result.success) {
        var data = OCX.OCX_Write(result.data);
        alert(data);
      } else {
		alert("Sorry.");
	  }
    });
  }
	
  function putKey(){
  	$.getJSON('${ctx}/putKey', function(result) {
      if(result.success) {
        var data = OCX.OCX_PutKey(result.data.first, result.data.second);
        alert(data);
      } else {
		alert("Sorry.");
	  }
    });  
  }
</script>
</html>