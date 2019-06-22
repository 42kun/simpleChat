var activeList = document.getElementById("activeList");
//激活用户,
//	如果用户在激活列表中,则将其置顶并生成对话框
//	如果用户不在激活列表中,则生成用户(无最近信息)，将其置顶并生成对话框
function activeUser(tg){
	var t = document.getElementById("activeListUser_"+tg);
	if(t==null && tg!=id){
		t = addActiveListUser(tg,userObj[tg].nickName,userObj[tg].isOnline,userObj[tg].portrait);
	}
	topActiveUser(t);
	clearMessageDiv();
	createMessageDiv(tg);
}

//向列表中添加一个用户
function addActiveListUser(id,st1,st2,st3){
	//st1昵称 st2在线状态 st3头像url
	var user = document.createElement("div");
	user.className = "activeList_user";
	user.id = "activeListUser_"+id;
	
	var img = document.createElement("img");
	img.className = "activeList_user_img";
	
	var p1 = document.createElement("p");
	p1.className = "activeList_user_msg1";
	
	var p2 = document.createElement("p");
	p2.className = "activeList_user_msg2";
	
	var p3 = document.createElement("p");
	p3.className = "activeList_user_msg3";
	var msgt;
	if(historyMsg.noRecived[id]!=null){
		var msg = historyMsg.noRecived[id][historyMsg.noRecived[id].length-1];
		if(msg.type=="0"){
			msgt = msg.msg;
		}
	}
	if(msgt != null){
		p3.appendChild(document.createTextNode(msgt));
	}
	
	p1.appendChild(document.createTextNode(st1));
	if(st2=="false"){
		p2.appendChild(document.createTextNode("[离线]"));
	}else{
		p2.appendChild(document.createTextNode("[在线]"));
	}
	img.src = st3;
	
	user.appendChild(img);
	user.appendChild(p1);
	user.appendChild(p2);
	user.appendChild(p3);
	
	activeList.appendChild(user);
	return user;
}

//此函数只会把activeList内所有用户的在线状态重新生成一遍
//仅参考userList
function updateActiveList(){
	var user = activeList.getElementsByClassName("activeList_user");
	for(var u=0;u<user.length;u++){
		var idStr = user[u].id;
		var id = idStr.split("_")[1];
		var isOnline;
		if(userObj[id].isOnline=="false"){
			isOnline = "[离线]";
		}else{
			isOnline = "[在线]";
		}
		var isOnline = document.createTextNode(isOnline);
		var p2 = user[u].getElementsByClassName("activeList_user_msg2")[0];
		p2.innerHTML="";
		p2.appendChild(isOnline);
	}
}

//清空所有活跃用户列表
function removeActiveListAllUser(){
	activeList.innerHTML = "";
}

//active初始化函数
function initializeActiveList(){
	if(activeList.getElementsByClassName("activeList_user").length==0){
		var history = historyMsg.noRecived;
		for(u in history){
			addActiveListUser(u,userObj[u].nickName,userObj[u].isOnline,userObj[u].portrait);
			getActiveUserMsg(u);
		}
	}else{
		updateActiveList();
	}
}

//寻找对应tg的activeUser对象是否在activeList中，如果在，则更新其消息并置顶，如果不在，则生成新对象且置顶
//会顺便更新一遍在线状态
function getActiveUserMsg(tg){
	var msgt;
	if(historyMsg.noRecived[tg]!=null){
		var msg = historyMsg.noRecived[tg][historyMsg.noRecived[tg].length-1];
		if(msg.type=="0"){
			msgt = msg.msg;
		}
	}
	var user = document.getElementById("activeListUser_"+tg);
	topActiveUser(user);
	if(user==null){
		user = addActiveListUser(tg,userObj[tg].nickName,userObj[tg].isOnline,userObj[tg].portrait);
	}
	topActiveUser(user);
	var p1 = user.getElementsByClassName("activeList_user_msg2")[0];
	p1.innerHTML = "";
	p1.appendChild(document.createTextNode("[在线]"));
	var p2 = user.getElementsByClassName("activeList_user_msg3")[0];
	p2.innerHTML = "";
	p2.appendChild(document.createTextNode(msgt));
	updateActiveList();
}

//将activeListUser置顶
function topActiveUser(element){
	var first=activeList.firstChild;
	if(first==null || element==null){
		return;
	}
	if(element.id==first.id){
		return;
	}
	var t = element.cloneNode(true);
	activeList.removeChild(element);
	activeList.insertBefore(t,first);
}

