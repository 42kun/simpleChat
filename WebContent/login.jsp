<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<meta charset="utf-8">
<head>
	<title>登陆界面</title>
	<link rel="stylesheet" type="text/css" href="css/userPage.css">
	<link rel="stylesheet" type="text/css" href="css/login.css">
	<script type="text/javascript" src="js/userPage.js"></script>
	<script type="text/javascript" src="js/verification.js"></script>
	<script type="text/javascript">
		<%
			if(session.getAttribute("id")!=null){
				%>
				alert("您已登陆，即将跳转到对话窗口\n"+"您的id是:<%=session.getAttribute("id").toString()%>");
				location.assign("/work/chatPage.jsp");
				<%
			}
		%>
	</script>
</head>
<body>
	<div id="head">
		<div id="logo" class="suit"><a href="login.html"><img src="img/main_logo.png" class="suit"></a></div>
		<div id="backlink" class="suit"><a class="a2" href="login.html"><p>登录</p></a></div>
		<div id="select1" class="suit" onmouseover="select()" onmouseout="unselect()"><img src="img/icon_top.png" class="suit">
			<p>选项▼</p>
			<ul>
				<li id="l1">.</li>
				<li id="l2"><a class="a2" href="register.jsp"><div>注册</div></a></li>
				<li id="l3"><a class="a2"><div>修改密码</div></a></li>
			</ul>
		</div>
	</div>
	<div id="mid">
		<div id="page">
			<p id="title">请登录</p>
			<form action="login" method="post" id="loginForm">
				<div id="idDiv" class="input">
					<i id="idIcon" class="icon"></i>
					<input type="text" id="idInput" name="id" placeholder="用户id(昵称登陆无效)">
				</div>
				<div id="passwordDiv" class="input">
					<i id="passwordIcon" class="icon"></i>
					<input type="password" id="passwordInput" name="password" placeholder="请输入密码">
				</div>
				<div id="verificationDiv">
					<p>请输入验证码</p>
					<input type="text" id="verificationInput">
					<div id="verification"></div>
				</div>
				<div id="buttonDiv">
					<button type="button" id="buttonSubmit" name="buttonSubmit">
					登录</button>
				</div>
				<div id="others">
					<a id="forgetLink" class="botLink" href="">忘记密码</a></p>
					<a id="register" class="botLink" href="register.jsp">注册</a></p>
				</div>
			</form>
		</div>
	</div>
</body>
<script type="text/javascript" src="js/login.js"></script>
<script type="text/javascript">
	var loginCount = parseInt("<%=session.getAttribute("loginCount")%>");
	if(loginCount>=1){
		alert("用户名或密码错误");
	}
	if(loginCount>=3){
		isUseverification = true;
		document.getElementById("verificationDiv").style.display="block";
	}
</script>
</html>