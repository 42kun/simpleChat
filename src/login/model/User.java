package login.model;

public class User {
	private String id;//用户唯一id号
	private String nickName;//用户昵称
	private String passwordMD5;//用户密码（md5加密）
	private String email;//用户邮箱
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id=id;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String n) {
		this.nickName=n;
	}
	public String getPasswordMD5() {
		return passwordMD5;
	}
	public void setPasswordMD5(String p) {
		this.passwordMD5=p;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String e) {
		this.email=e;
	}
	public String toString() {
		return "id= "+id+"<br>nickName= "+nickName+"<br>email= "+email+"<br>";
	}
}
