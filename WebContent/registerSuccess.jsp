<%@page import="login.model.User"%>
<%@page import="login.action.userAction"%>
<%@page import="login.action.tools.idAction"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<meta charset="utf-8">
<head>
	<title>注册成功</title>
	<link rel="stylesheet" type="text/css" href="css/mainStyle.css">
	<link rel="stylesheet" type="text/css" href="css/registerSuccess.css">
	<script type="text/javascript" src="js/jsOperateFile.js"></script>
	<script type="text/javascript">
		<%
			/*
			如果没登陆，则返回登陆界面
			如果已登陆
				如果刚注册，则留在此页面
				如果老用户，则返回聊天页面
			*/
			if(session.getAttribute("id")==null){
				%>location.assign("/work/login.jsp");<%
			}else if(session.getAttribute("isJustRegisted")==null){
				%>location.assign("/work/chatPage.jsp");<%
			}
		%>
	</script>
</head>
<body>
	<div id="head">
		<div id="logo" class="suit"><a href="login.html"><img src="img/main_logo.png" class="suit"></a></div>
		<div id="backlink" class="suit"><a class="a2" href=""><p>退出登录</p></a></div>
	</div>
	<div id="mid">
		<div id="page">
			<p id="title">注册成功</p>
			<div id="main">
				<p id="msgFront">您的id为: <input type="text" id="idMsg" readonly="readonly"></p>
				<div id="buttonDouble">
					<button id="buttonSave"  class="blue">保存账户信息</button>
					<button id="buttonBack" class="orange" onclick="">个人主页</button>
				</div>
				<button id="buttonClose" class="blank" onclick="jumpToChatPage()">关闭页面并跳转到聊天室</button>
			</div>
		</div>
	</div>
	<!-- 测试 -->
	
	<!-- /测试 -->
</body>
<script type="text/javascript">
	document.getElementById("idMsg").value="<%=session.getAttribute("id")%>";
	var save = document.getElementById("buttonSave");
	save.onclick = function(){
		var msg = "欢迎注册本测试聊天系统 \n我们的网址是www.42kun.cn(本网站不支持ie) \n您的注册信息为: \n"
		var id = " <%=session.getAttribute("id")%>";
		<%
			userAction uA = new userAction();
			User user = uA.getUser((session.getAttribute("id").toString()));
		%>
		var nickName = " <%=user.getNickName()%>";
		var email = " <%=user.getEmail()%>";
		var data=msg+" \nid:"+id+" \n昵称:"+nickName+" \n邮箱:"+email;
		createFile("注册信息.txt",data);
	}
	function jumpToChatPage(){
		location.assign("/work/chatPage.jsp");
	}
</script>
</html>