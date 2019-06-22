package login.action;

import java.sql.Timestamp;

import login.model.User;
import mysql.mysqlControl;

/*一个对用户进行操作的类*/
public class userAction{
	
	/*初始化用户所有信息*/
	public void initializeUser(User user) {
		String id = user.getId();
		String nickName = user.getNickName();
		String passMD5 = user.getPasswordMD5();
		String email = user.getEmail();
		Object[] data = new Object[2];
		data[0] = id;
		data[1] = passMD5;
		try {
			mysqlControl mysc = new mysqlControl();
			mysc.insertValue("user", data);
		}catch (Exception e) {
			System.out.println("初始化用户所有信息错误");
			e.printStackTrace();
		}
		
		initializeUserOnline(id);
		initilizeUserDetails(id, nickName, email);
	}

	/*输入id，获取对应用户的密码*/
	public String getPasswordMD5(String id) {
		mysqlControl mysc = new mysqlControl();
		return mysc.getValue("user", "id", id.toString(), "password");
	}
	
	/*传入id，返回user对象*/
	public User getUser(String id) {
		User user = null;
		if(hasUser(id)) {
			user = new User();
		}else {
			return null;
		}
		mysqlControl mysc = new mysqlControl();
		String[] array1 = mysc.getValueArr("user", "id", id);
		String[] array2 = mysc.getValueArr("userDetails", "id", id);
		user.setId(array1[0]);
		user.setNickName(array2[1]);
		user.setPasswordMD5(array1[1]);
		user.setEmail(array2[2]);
		return user;
	}
	
	/*输入id，检测数据库中是否有这个人*/
	public boolean hasUser(String id) {
		mysqlControl mysc = new mysqlControl();
		return mysc.checkValue("user", "id", id.toString());
	}
	
	
	//在userOnline中初始化一个用户
	public void initializeUserOnline(String id) {
		Object[] data = new Object[3];
		data[0] = id;
		data[1] = 0;
		data[2] = new Timestamp(System.currentTimeMillis());
		try {
			mysqlControl mysc = new mysqlControl();
			mysc.insertValue("userOnline", data);
		}catch (Exception e) {
			System.out.println("初始化userOnline时插入对象失败");
			e.printStackTrace();
		}
	}
	
	//在userDetails中初始化一个用户
	public void initilizeUserDetails(String id,String nickName,String email) {
		Object[] data = new Object[7];
		data[0] = id;
		data[1] = nickName;
		data[2] = email;
		try {
			mysqlControl mysc = new mysqlControl();
			mysc.insertValue("userDetails", data);
		} catch (Exception e) {
			System.out.println("在userDetails中初始化一个用户错误");
			e.printStackTrace();
		}
	}
	
	
}
