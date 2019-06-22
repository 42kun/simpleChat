<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<meta charset="utf-8">
<head>
	<title>注册界面</title>
	<link rel="stylesheet" type="text/css" href="css/userPage.css">
	<link rel="stylesheet" type="text/css" href="css/register.css">
	<script type="text/javascript" src="js/userPage.js"></script>
	<script type="text/javascript" src="js/verification.js"></script>
</head>
<body>
	<div id="head">
		<div id="logo" class="suit"><a href="login.html"><img src="img/main_logo.png" class="suit"></a></div>
		<div id="backlink" class="suit"><a class="a2" href="login.html"><p>登录</p></a></div>
		<div id="select1" class="suit" onmouseover="select()" onmouseout="unselect()"><img src="img/icon_top.png" class="suit">
			<p>选项▼</p>
			<ul>
				<li id="l1">.</li>
				<li id="l2"><a class="a2" href="login.jsp"><div>登录</div></a></li>
				<li id="l3"><a class="a2"><div>修改密码</div></a></li>
			</ul>
		</div>
	</div>
	<div id="mid">
		<div id="page">
			<p id="title">欢迎注册</p>
			<form action="register" method="post" id="registerForm">
				<div id="nickNameDiv" class="input">
					<i id="nickNameIcon" class="icon"></i>
					<input type="text" id="nickNameInput" name="nickName" placeholder="请输入昵称(仅支持数字字母下划线)">
				</div>
				<div id="passwordDiv" class="input">
					<i id="passwordIcon" class="icon"></i>
					<input type="password" id="passwordInput" name="password" placeholder="请输入密码">
				</div>
				<div id="confirmPasswordDiv" class="input">
					<i id="confirmPasswordIcon" class="icon"></i>
					<input type="password" id="confirmPasswordInput" placeholder="请再次输入密码">
				</div>
				<div id="emailDiv" class="input">
					<i id="emailIcon" class="icon"></i>
					<input type="text" id="emailInput" name="email" placeholder="请输入邮箱">
				</div>
				<div id="verificationDiv">
					<p>请输入验证码</p>
					<input type="text" id="verificationInput">
					<div id="verification"></div>
				</div>
				<div id="buttonDiv">
					<button type="button" id="buttonSubmit" name="buttonSubmit">
					注册</button>
				</div>
				<div id="others">
					<a id="loginLink" class="botLink" href="login.jsp">登录</a>
				</div>
			</form>
		</div>
	</div>
</body>
<script type="text/javascript" src="js/register.js?v=48"></script>
</html>