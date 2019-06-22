/*顶部选择框*/
function select(){
	var x=document.getElementById("select1")
	var xp = x.getElementsByTagName("p")[0];
	var xu = x.getElementsByTagName("ul")[0];
	x.style.backgroundColor="rgb(40,43,47)";
	xp.innerHTML="选项▲";
	xu.style.display="block";
}

function unselect(){
	var x=document.getElementById("select1")
	var xp = x.getElementsByTagName("p")[0];
	var xu = x.getElementsByTagName("ul")[0];
	x.style.backgroundColor="rgb(27,30,33)";
	xp.innerHTML="选项▼";
	xu.style.display="none";
}
/*顶部选择框结束*/


/*一个提示框类,输入需提示元素与消息,调用show即可显示,hidden即可隐藏*/
function warn(element,message){
	var warnDiv = document.createElement("div");
	var warnP = document.createElement("p");
	var text = document.createTextNode(message);

	if(element.parentNode.style.position=="static"){
		element.parentNode.style.position="relative";
	}

	warnDiv.setAttribute("id","warnDiv");
	warnP.appendChild(text);
	warnDiv.appendChild(warnP);
	element.parentNode.appendChild(warnDiv);

	warnDiv.style.width;
	warnDiv.style.top = (parseInt(element.offsetTop) + parseInt(element.offsetHeight)/2 - parseInt(warnDiv.offsetHeight)/2)+"px";
	warnDiv.style.left = (parseInt(element.offsetLeft)+parseInt(element.offsetWidth) + 10)+"px";
	this.show = function(){
		warnDiv.style.visibility="visible"
		
	}
	this.hidden = function(){
		warnDiv.style.visibility="hidden";
	}
}