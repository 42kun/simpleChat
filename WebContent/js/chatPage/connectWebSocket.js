
function connect(id){
	var path = "ws://"+window.location.host+"/work/webSocket/";
	socket = new WebSocket(path+id);
	socket.onmessage = function(e){
		var msg = JSON.parse(e.data);
		//将从websocket中收到的消息传到historyMsg中
		nowAppend(msg.id,msg);
		//收到消息后的处理
		if(document.getElementById("messagePage_"+msg.id)!=null){
			if(msg.type=="0"){
				var element = document.createTextNode(msg.msg);
				addLeft(userObj[msg.id].nickName,userObj[msg.id].portrait,element);
				msgConvertEmoji("msgLeftDiv");
			}else{
				var element = generateImageMsg(msg.msg,msg.type);
				addLeft(userObj[msg.id].nickName,userObj[msg.id].portrait,element);
			}
		}
		getActiveUserMsg(msg.id);
	}
	socket.onclose = function(){
		socket = new WebSocket(path+id);
	}
}

function nowAppend(tg,msg){//tg强制对方，msg.id判断发消息源
	if(historyMsg == null){
		alert("未获取历史记录JSON对象");
		return;
	}
	if(historyMsg.noRecived==null){
		historyMsg.noRecived = {};
	}
	
	if(historyMsg.noRecived[tg]==null){
		historyMsg.noRecived[tg] = new Array();
	}

	//仅将对方置入信息
	historyMsg.noRecived[tg][historyMsg.noRecived[tg].length] = msg;
}

window.onbeforeunload = function() {
	if(socket!=null){
		socket.close();
	}
}

