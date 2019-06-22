package login.action.tools;

import mysql.mysqlControl;

public class idAction{
	//检测id是否已被占用
	public static boolean idIsOccupated(String id) {
		mysqlControl mysc = new mysqlControl();
		if(mysc.checkValue("user", "id", id)) {
			return true;
		}else {
			return false;
		}
	}
	//获取一个随机的id
	public static String idAcquire() {
		String id;
		do {
			id = ((int)(Math.random()*100000000)-1)+"";
		}while(idIsOccupated(id));
		System.out.println("生成的随机id为"+id);
		return id;
	}
	
}
