<%@page import="chat.chatAction"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>聊天页面</title>
	<link rel="stylesheet" type="text/css" href="css/mainStyle.css?v=7">
	<link rel="stylesheet" type="text/css" href="css/chatPage/chatPageMainStyle.css">
	<link rel="stylesheet" type="text/css" href="css/chatPage/userList.css">
	<link rel="stylesheet" type="text/css" href="css/chatPage/messagePage.css">
	<link rel="stylesheet" type="text/css" href="css/chatPage/activeList.css">
	<script type="text/javascript" src="js/userPage.js"></script>

	<link rel="stylesheet" type="text/css" href="jQuery/emoji/css/jquery.mCustomScrollbar.min.css">
	<link rel="stylesheet" type="text/css" href="jQuery/emoji/css/jquery.emoji.css">

	<link rel="stylesheet" type="text/css" href="jQuery/upload/main.css">
	<!-- 测试 -->
		<%-- <%session.setAttribute("id", "30338959"); %> --%>
	<!-- /测试 -->
</head>
<body>
	<div id="head">
		<div id="logo" class="suit"><a href=""><img src="img/main_logo.png" class="suit"></a></div>
		<div id="myNickName" class="myNickName"></div>
		<div id="backlink" class="suit"><a class="a2" href="logout.jsp"><p>注销</p></a></div>
		<div id="select1" class="suit" onmouseover="select()" onmouseout="unselect()"><img src="img/icon_top.png" class="suit">
			<p>选项▼</p>
			<ul>
				<li id="l1">.</li>
				<li id="l2"><a class="a2"><div>修改密码</div></a></li>
				<li id="l3"><a class="a2"><div>查询历史</div></a></li>
				<li id="l4"><a class="a2"><div>个人首页</div></a></li>
			</ul>
		</div>
	</div>
	<div id="mid">
		<div id="page">
			<div id="userListDiv">
				<div id="userListInner">
					<div id="userListSearchInputDiv">
						<i id="userListSearchInputIcon"></i>
						<input type="text" name="userListSearchInput" id="userListSearchInput" placeholder="查找用户">
						<button type="button" id="userListRefresh"><i></i></button>
					</div>
					<p id="userListTitle">用户列表(全部)</p>
					<div id="userList">
					</div>
				</div>
			</div>
			<label class="resizeLable1"></label>
			<div id="activeListDiv">
				<div id="activeList">
					
				</div>
			</div>
			<label class="resizeLable1"></label>
			<div id="messagePageDiv">
				<div class="messagePageInner">
					<div id="messageList">
						<p id="messageListTitle">没有正在进行的聊天</p>
						<div id="messageListMsg">
						</div>
					</div>
					<div id="importDiv">
						<div id="importTools">
						</div>
						<div id="importMsgDiv">
							
							
				            
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="upload_form_cont" id="hiddenFileInput">
       <form id="upload_form" enctype="multipart/form-data" method="post" action="">
           <div>
               <div><label for="image_file">Please select file</label></div>
               <div><input type="file" name="file" id="file" onchange="fileSelected();" /></div>
           </div>
           <div>
           	   <img id="preview" />
               <input class="finput" type="button" id = "uploadButton" value="Upload" onclick="startUploading()" />
           </div>
           <div id="fileinfo">
               <div id="filename"></div>
               <div id="filesize"></div>
               <div id="filetype"></div>
               <div id="filedim"></div>
           </div>
           <div id="error"></div>
           <div id="error2">An error occurred while uploading the file</div>
           <div id="abort">The upload has been canceled by the user or the browser dropped the connection</div>
           <div id="warnsize">Your file is very big. We can't accept it. Please select more small file</div>

           <div id="progress_info">
               <div id="progress"></div>
               <div id="progress_percent">&nbsp;</div>
               <div class="clear_both"></div>
               <div>
                   <div id="speed">&nbsp;</div>
                   <div id="remaining">&nbsp;</div>
                   <div id="b_transfered">&nbsp;</div>
                   <div class="clear_both"></div>
               </div>
               <div id="upload_response"></div>
           </div>
       </form>
   </div>
</body>
<script src="http://cdn.bootcss.com/jquery/1.11.3/jquery.js"></script>
<script type="text/javascript" src="jQuery/emoji/js/jquery.mousewheel-3.0.6.min.js"></script>
<script type="text/javascript" src="jQuery/emoji/js/jquery.mCustomScrollbar.min.js"></script>
<script type="text/javascript" src="jQuery/emoji/js/jquery.emoji.js"></script>

<script type="text/javascript" src="jQuery/upload/script.js?v=1"></script>
<script type="text/javascript">

</script>

<!-- 判断是否登陆并注册id -->
<script type="text/javascript">
	//向数据库表示id已登陆
	/* 若没登陆，则转发到登陆页面 */
	<%	String id = null;
		if(session.getAttribute("id")==null){
			%>
			alert("请先登录");
			location.assign("/work/login.jsp");
			<%
		}else{
			id = session.getAttribute("id").toString();
			chatAction cA = new chatAction();
			cA.setUserOnline(id, "1");
		}
	%>
	var id = "<%=id%>";
</script>
<script type="text/javascript">
	var socket = null;
	var historyMsg = null;
	var userObj = null;
</script>
<script type="text/javascript" src="js/chatPage/connectWebSocket.js?v=12"></script>
<script type="text/javascript" src="js/chatPage/messagePage.js?v=111"></script>
<script type="text/javascript" src="js/chatPage/userList.js?v=211"></script>
<script type="text/javascript" src="js/chatPage/activeList.js?v=11"></script>
<script type="text/javascript">
	connect(id);
	updateUserList();
	/* 解析历史信息
		解析未接受信息，将其列在active表中
	
	用户双击左侧或单机右侧
		读取历史信息与未接受信息，将滚动条置于未接受信息头部
	
	用户发消息后回到
		要么重新生成HistoryMsg
		要么在打开时即删除未接受信息，将其添加到历史信息，同时删除type=3的分界线
		
	用户收到消息
		判断当前聊天框是否正确
		不对的话 置顶对应的activeList列表
		对的话，直接生成消息栏 */
</script>
</html>