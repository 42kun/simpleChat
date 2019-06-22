var userList = document.getElementById("userList");

//添加一个用户到userList中
function addUserListUser(tg,st1,st2,st3,st4){
	// st1为昵称 st2为在线状态 st3为签名 st4为头像url
	// 如果这个用户是我方，那么它会被强制添加到顶部，并会变色
	var user = document.createElement("div");
	var img = document.createElement("img");
	var p1 = document.createElement("p");
	var p2 = document.createElement("p");
	var p3 = document.createElement("p");
	user.id = "userListUser_"+tg;
	user.className = "userList_user";
	img.className ="userList_user_img";
	if(st4!=""){
		img.src=st4;
	}else{
		img.src = "files/portrait/rawPortrait.jpeg";
		userObj[tg].portrait = "files/portrait/rawPortrait.jpeg";
	}
	p1.className = "userList_user_msg1";
	p2.className = "userList_user_msg2";
	p3.className = "userList_user_msg3";

	p1.appendChild(document.createTextNode(st1));
	if(st2=="false"){
		p2.appendChild(document.createTextNode("[离线] "+st3));
	}else{
		p2.appendChild(document.createTextNode("[在线] "+st3));
	}
	p3.appendChild(document.createTextNode("ID:"+tg));

	user.appendChild(img);
	user.appendChild(p1);
	user.appendChild(p2);
	user.appendChild(p3);
	if(tg!=id){
		userList.appendChild(user);
	}else{
		var t = userList.firstChild;
		user.style.backgroundColor = "#00FFFF";
		userList.insertBefore(user,t);
	}
	
	var clickID;
	user.ondblclick = function() {
		clearTimeout(clickID);
		activeUser(tg);
	}
	user.onclick = function(){
		clearTimeout(clickID);
		clickID = setTimeout(function(){
//			alert("我被单击了");
		},250);
	}
	
}

//删除userList的所有用户
function removeUserListAllUser(){
	userList.innerHTML = "";
}

//更新userList用户列表
function updateUserList(){
	removeUserListAllUser()
	var xmlhttpU=new XMLHttpRequest();
	xmlhttpU.open("POST","getUsetList",true);
	xmlhttpU.send();
	xmlhttpU.onreadystatechange=function(){
		if (xmlhttpU.readyState==4 && xmlhttpU.status==200){
			userObj = JSON.parse(xmlhttpU.responseText);
			for(var u in userObj){
				addUserListUser(u,userObj[u].nickName,userObj[u].isOnline,userObj[u].signature,userObj[u].portrait);
			}
			getHistoryMsg(id);
			document.getElementById("myNickName").innerHTML="";
			document.getElementById("myNickName").appendChild(document.createTextNode(userObj[id].nickName));
		}
	}
}
document.getElementById("userListRefresh").onclick = function(){
	updateUserList();
	updateActiveList();
}



