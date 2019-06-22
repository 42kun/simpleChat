

//清空messagePageDiv
function clearMessageDiv(){
	document.getElementById("messagePageDiv").innerHTML = "";
}

//生成聊天框
function createMessageDiv(tg){
	var idStr = "messagePage_"+tg;
	
	var messagePageInner = document.createElement("div");
	messagePageInner.id = idStr;
	messagePageInner.className = "messagePageInner";
	document.getElementById("messagePageDiv").appendChild(messagePageInner);
	
	var messageList = document.createElement("div");
	messageList.id = "messageList";
	
	var messageListTitle = document.createElement("p");
	messageListTitle.appendChild(document.createTextNode(userObj[tg].nickName));
	messageListTitle.id = "messageListTitle";
	
	var messageListMsg = document.createElement("div");
	messageListMsg.id = "messageListMsg";
	
	var importDiv = document.createElement("div");
	importDiv.id = "importDiv";
	
	var importTools = document.createElement("div");
	importTools.id = "importTools";
	
	var importMsgDiv = document.createElement("div");
	importMsgDiv.id = "importMsgDiv";
	
	var importMsg = document.createElement("div");
	importMsg.id = "importMsg";
	importMsg.contentEditable = "true";
	
	var emojiButton = document.createElement("button");
	emojiButton.id = "emojiButton";
	
	var imageButton = document.createElement("button");
	imageButton.id = "imageButton";
	
	var fileButton = document.createElement("button");
	fileButton.id = "fileButton";
	
	
	messagePageInner.appendChild(messageList);
	messageList.appendChild(messageListTitle);
	messageList.appendChild(messageListMsg);
	
	importTools.appendChild(emojiButton);
	importTools.appendChild(imageButton);
	importTools.appendChild(fileButton);
	
	messagePageInner.appendChild(importDiv);
	importDiv.appendChild(importTools);
	importDiv.appendChild(importMsgDiv);
	
	importMsgDiv.appendChild(importMsg);
	
	messagePageAddNoRecived(tg);
	
	//聊天框监听器函数
    $(importMsg).keydown(function($event){
        var keycode = window.event ? $event.keyCode : $event.which;
        var evt = $event || window.event;
        var inputTxt = $(this);
        // 回车-->发送消息
        if (keycode == 13 && !(evt.shiftKey)) {
        	/--发消息代码/
           	new sendMsg(tg,importMsg.innerHTML);
			importMsg.innerHTML="";
			/发消息代码--/
            return false;
        }
        //shift+回车-->换行
        if (evt.keyCode == 13 && evt.shiftKey) {
            inputTxt.html(inputTxt.html() + '<br>');
            woohecc.placeCaretAtEnd(inputTxt.get(0));
            return false;
        }
    });
	
    //绑定表情监听
    $("#importMsg").emoji({
        button: "#emojiButton",
        showTab: false,
        animation: 'slide',
        icons: [{
            name: "表情",
            path: "files/emoji/tieba/",
            maxNum: 50,
            excludeNums: [41, 45, 54],
            file: ".jpg"
        }]
    });
    
    imageButton.onclick = function(){
    	clearFileInput();
    	if(document.getElementById("importMsg").style.display!="none"){
	    	document.getElementById("importMsg").style.display="none";
	    	createFileImport(tg,"image");
    	}else{
    		document.getElementById("importMsg").style.display="block";
    	}
    }
    fileButton.onclick = function(){
    	clearFileInput();
    	if(document.getElementById("importMsg").style.display!="none"){
	    	document.getElementById("importMsg").style.display="none";
	    	createFileImport(tg,"file");
    	}else{
    		document.getElementById("importMsg").style.display="block";
    	}
    }
}

function createFileImport(tg,type){
	var fileInput = document.getElementById("hiddenFileInput").cloneNode(true);
	fileInput.tg=tg;
	fileInput.type=type;
	fileInput.id = "fileInput"
	var importMsgDiv = document.getElementById("importMsgDiv");
	importMsgDiv.appendChild(fileInput);
	fileInput.style.display = "block";
}
function clearFileInput(){
	var importMsgDiv = document.getElementById("importMsgDiv");
	var fileInput = importMsgDiv.getElementsByClassName("upload_form_cont");
	if(fileInput.length==0){
		return;
	}
	importMsgDiv.removeChild(fileInput[0]);
}
 
//仅发送文本消息
function sendMsg(tg,msg){
	//这里对文本消息进行解析
	//<br>替换为/n
	//<img class="xxx" src="****/2.jpg">替换为#tieba_2#
	msg = msg.replace(/(<br>)/g,"\n");
	msg = msg.replace(/<img[^\d]*(\d+).*?>/g,"#tieba_$1#");
	
	socket.send(tg+"&@&0"+"&@&"+msg);
	
	var element = addRight(userObj[tg].nickName,userObj[tg].portrait,document.createTextNode(msg));
	msgConvertEmoji("msgRightDiv");
	//json对象生成
	//nowAppend添加
	var msgt = {};
	msgt.id = id;
	msgt.type = 0;
	msgt.msg = msg;
	nowAppend(tg,msgt);
}

function sendNoMsg(tg,msg,type){
	socket.send(tg+"&@&"+type+"&@&"+msg);
	
	e = generateImageMsg(msg,type);
	addRight(userObj[id].nickName,userObj[id].portrait,e);
	var msgt = {};
	msgt.id = id;
	msgt.type = type;
	msgt.msg = finallyFileName;
	nowAppend(tg,msgt);
}


//添加左边聊天框
function addLeft(nickName,portrait,element){
	var msgLeftDiv = document.createElement("div");
	msgLeftDiv.className = "msgLeftDiv";
	
	var msgLeftHead = document.createElement("img");
	msgLeftHead.className = "msgLeftHead";
	msgLeftHead.src = portrait;
	
	var msgLeft = document.createElement("div");
	msgLeft.className = "msgLeft";
	
	msgLeftDiv.appendChild(msgLeftHead);
	msgLeftDiv.appendChild(msgLeft);
	msgLeft.appendChild(element);
	document.getElementById("messageListMsg").appendChild(msgLeftDiv);
	document.getElementById("messageListMsg").scrollTop = document.getElementById("messageListMsg").scrollHeight;
	
	return msgLeftDiv;
}

//添加中间聊天框
function addMsgTime(time){
	var t = document.createTextNode(time);
	var messageListUserTime = document.createElement("p");
	messageListUserTime.className = "messageListUserTime";
	
	messageListUserTime.appendChild(t);
	document.getElementById("messageListMsg").appendChild(messageListUserTime);
	document.getElementById("messageListMsg").scrollTop = document.getElementById("messageListMsg").scrollHeight;
}

//添加右边聊天框
function addRight(nickName,portrait,element){
	var msgRightDiv = document.createElement("div");
	msgRightDiv.className = "msgRightDiv";
	
	var msgRightHead = document.createElement("img");
	msgRightHead.className = "msgRightHead";
	msgRightHead.src = portrait;
	
	var msgRight = document.createElement("div");
	msgRight.className = "msgRight";
	
	msgRightDiv.appendChild(msgRightHead);
	msgRightDiv.appendChild(msgRight);
	msgRight.appendChild(element);
	document.getElementById("messageListMsg").appendChild(msgRightDiv);
	document.getElementById("messageListMsg").scrollTop = document.getElementById("messageListMsg").scrollHeight;
}

//在页面刚加载时初始化历史消息
function getHistoryMsg(){
	var xmlhttpH=new XMLHttpRequest();
	xmlhttpH.open("POST","getHistoryMsg",true);
	xmlhttpH.send();
	xmlhttpH.onreadystatechange=function(){
		if (xmlhttpH.readyState==4 && xmlhttpH.status==200){
			historyMsg = JSON.parse(xmlhttpH.responseText);
			initializeActiveList();
		}
	}
}

//刚生成时加载未接受消息
function messagePageAddNoRecived(tg){
	var noRecived = historyMsg.noRecived[tg];
	for(u in noRecived){
		var element;
		if(noRecived[u].type=="0"){
			element = document.createTextNode(noRecived[u].msg);
		}else{
			element = generateImageMsg(noRecived[u].msg,noRecived[u].type);
		}
		if(noRecived[u].id==id){
			addRight(userObj[id].nickName,userObj[id].portrait,element);
		}else{
			addLeft(userObj[tg].nickName,userObj[tg].portrait,element);
		}
		if(noRecived[u].type=="0"){
			msgConvertEmoji("msgRightDiv");
			msgConvertEmoji("msgLeftDiv");
		}
	}
}

//解析表情#tieba_1#至图片
function msgConvertEmoji(className){
	$("."+className).emojiParse({
        icons: [{
            path: "files/emoji/tieba/",
            file: ".jpg",
            placeholder: "#tieba_{alias}#"
        }]
    });
}

//生成图片或文件消息，e为消息字符串，type为消息类型
function generateImageMsg(e,type){
	var fileName = e;
	var img;
	if(type=="1"){
		img = new Image();
		img.src = "/download/"+fileName;
	}else if(type=="2"){
		var imgt = new Image();
		imgt.src = "img/file.png";
		var p = document.createElement("p");
		p.style.textAlign = "center";
		p.innerText = "[文件]:"+fileName;
		img = document.createElement("div");
		var b = document.createElement("button");
		b.className = "msgDownloadFileButton";
		b.innerText = "下载";
		b.onclick = function(){
			var $form = $('<form method="GET"></form>');
            $form.attr('action', '/download/'+e);
            $form.appendTo($('body'));
            $form.submit();
		}
		img.appendChild(imgt);
		img.appendChild(p);
		img.appendChild(b);
	}
	 return img;
}

//回车换行配套函数
var woohecc = {
	placeCaretAtEnd : function(el) {
        el.focus();
        if (typeof window.getSelection != "undefined"
            && typeof document.createRange != "undefined") {
            var range = document.createRange();
            range.selectNodeContents(el);
            range.collapse(false);
            var sel = window.getSelection();
            sel.removeAllRanges();
            sel.addRange(range);
        }
        else if (typeof document.body.createTextRange != "undefined") {
            var textRange = document.body.createTextRange();
            textRange.moveToElementText(el);
            textRange.collapse(false);
            textRange.select();
        }
    }
}

