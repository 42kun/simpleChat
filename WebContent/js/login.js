var isUseverification = false;

var idInput = document.getElementById("idInput");
var idInputWarnStr = "id输入不合法(请输入数字)"
var idInputWarn = new warn(idInput,idInputWarnStr);
var idInputIsRight = false;

idInput.onblur = function(){
	if(idInput.value==""){
		return;
	}
	var isAllNumberReg = /^[0-9]*$/
	if(!isAllNumberReg.test(idInput.value)){
		idInputIsRight = false;
		idInputWarn.show();
	}else{
		idInputIsRight = true;
		idInputWarn.hidden();
	}
}
idInput.onfocus =function(){
	idInputWarn.hidden();
}

var passwordInput = document.getElementById("passwordInput");

var verificationInput = document.getElementById("verificationInput");
var verificationIsRight = false;
verificationInput.onblur = function(){
	if(verificationInput.value==verificationCode){
		verificationIsRight=true;
	}else{
		verificationIsRight=false;
	}
}

var buttonSubmit = document.getElementById("buttonSubmit");
buttonSubmit.onclick = function(){
	if(verificationInput.value==""&&isUseverification){
		alert("请输入验证码");
		return false;
	}else if(!verificationIsRight&&isUseverification){
		alert("验证码错误");
		window.location.reload();
		return false;
	}else if(!idInputIsRight){
		alert("id输入不合法");
		return false;
	}else if(passwordInput.value==""){
		alert("请输入密码");
		return false;
	}
	document.getElementById("loginForm").submit();
	return true;
}