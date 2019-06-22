var nickNameInput = document.getElementById("nickNameInput");
var nickNameInputWarnStr = "输入包含非法字符(仅允许字母/数字/下划线)"
var nickNameInputWarn = new warn(nickNameInput,nickNameInputWarnStr);
var nickNameInputIsRight = false;
nickNameInput.onblur = function(){
	if(nickNameInput.value==""){
		return;
	}
	var isNormalStrReg = /^[0-9a-zA-Z_]{1,}$/;
	if(isNormalStrReg.test(nickNameInput.value)){
		nickNameInputWarn.hidden();
		nickNameInputIsRight =true;
	}else{
		nickNameInputWarn.show();
		nickNameInputIsRight = false;
	}
}
nickNameInput.onfocus = function(){
	nickNameInputWarn.hidden();
}

var passwordInput = document.getElementById("passwordInput");
var passwordInputIsRight = false;
passwordInput.onblur = function(){
	if(passwordInput.value!=""){
		passwordInputIsRight=true;
	}else{
		passwordInputIsRight=false
	}
}

var confirmPasswordInput = document.getElementById("confirmPasswordInput");
var confirmPasswordInputWarnStr = "密码不匹配"
var confirmPasswordInputWarn = new warn(confirmPasswordInput,confirmPasswordInputWarnStr);
var confirmPasswordInputIsRight = false;
confirmPasswordInput.onblur = function(){
	if(confirmPasswordInput.value==""){
		return;
	}
	if(confirmPasswordInput.value!=passwordInput.value){
		confirmPasswordInputIsRight = false;
		confirmPasswordInputWarn.show();
	}else{
		confirmPasswordInputIsRight = true;
	}
}
confirmPasswordInput.onfocus = function(){
	confirmPasswordInputWarn.hidden();
}

var emailInput = document.getElementById("emailInput");
var emailInputWarnStr = "邮箱格式错误"
var emailInputWarn = new warn(emailInput,emailInputWarnStr);
var emailInputIsRight = false;
emailInput.onblur = function(){
	if(emailInput.value==""){
		return;
	}
	var checkEmailStr = /(\S)+[@]{1}(\S)+[.]{1}(\w)+/;
	if(checkEmailStr.test(emailInput.value)){
		emailInputIsRight = true;
		emailInputWarn.hidden();
	}else{
		emailInputIsRight = false;
		emailInputWarn.show();
	}
}
emailInput.onfocus = function(){
	emailInputWarn.hidden();
}

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
	if(verificationInput.value==""){
		alert("请输入验证码");
		return false;
	}else if(!verificationIsRight){
		alert("验证码错误");
		window.location.reload();
		return false;
	}else if(!nickNameInputIsRight){
		alert("昵称错误");
		return false;
	}else if(!passwordInputIsRight){
		alert("密码不可为空");
		return false;
	}else if(!confirmPasswordInputIsRight){
		alert("密码不匹配");
		return false;
	}else if(!emailInputIsRight){
		alert("邮箱错误");
		return false;
	}
	document.getElementById("registerForm").submit();
	return true;
}