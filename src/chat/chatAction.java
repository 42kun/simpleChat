package chat;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import mysql.mysqlControl;

public class chatAction {
	/*
	 *	设定一个用户的在线与否（true/false）
	 *	
	 */
	
	//设定一个用户的在线与否（true/false）
	public void setUserOnline(String id,String isOnline) {
		mysqlControl mysc = new mysqlControl();
		mysc.alertValue("userOnline", "id", id, "isOnline", isOnline);
		System.out.println("已设定用户"+id+"在线状态为"+isOnline);
	}

	/*列出所有用户，返回json对象，包括用户名，在线状态，个人简介(暂不包括头像url)*/
	public JSONObject getAllUser() {
		JSONObject obj = new JSONObject();
		mysqlControl mysc = new mysqlControl();
		String[] idList = mysc.getResList("user","id");
		for(int i=0;i<idList.length;i++) {
			JSONObject t = new JSONObject();
			String id = idList[i];
			t.put("nickName", mysc.getValue("userDetails", "id", id, "nickName").toString());
			t.put("isOnline", mysc.getValue("userOnline","id",id,"isOnline").toString());
			t.put("signature", mysc.getValue("userDetails", "id", id, "signature").toString());
			t.put("portrait", mysc.getValue("userDetails", "id", id, "portrait").toString());
			obj.put(id, t);
		}
		return obj;
	}
	//在数据库中对用户做离线处理（仅会改变数据库）
	public void offlineUser(String id) {
		mysqlControl mysc = new mysqlControl();
		mysc.alertValue("userOnline", "id", id, "isOnline", "0");
		mysc.alertValue("userOnline", "id", id, "latestLeaveTime", (new Timestamp(System.currentTimeMillis()).toString()));
		System.out.println("已离线用户"+id);
	}
	
	//将一条消息插入消息存储数据库
	public void storeMsg(String id,String target,String msg,String flag) {
		mysqlControl mysc = new mysqlControl();
		String[] data = new String[5];
		data[0] = id;
		data[1] = target;
		data[2] = flag;
		data[3] = msg;
		data[4] = (new Timestamp(System.currentTimeMillis())).toString();
		mysc.insertValue("message", data);
	}
	
	//返回 history id msgs;noRecived id msgs
	public JSONObject getHistoryMsg(String id) {
		mysqlControl mysc = new mysqlControl();
		String latStr = mysc.getValue("userOnline", "id", id, "latestLeaveTime");//比我们时间+8小时
		long lT = convertTimeToLong(latStr);
		String latestLeaveTime = (new Timestamp(lT)).toString();
		JSONObject msgRes = new JSONObject();

		
		String sqlTime1 = "time<'"+latestLeaveTime+"'";
		String sqlTime2 = "time>='"+latestLeaveTime+"'";
		String sqlTarget = "(id="+id+" or target ="+id+")";
		
		String sql1 = sqlTime1 + " and " + sqlTarget; //列出所有历史消息
		String [][] msgHistoryStr = mysc.getResListSql("message",sql1);
		JSONObject msgHistory = new JSONObject();
		for(int i=0;i<msgHistoryStr.length;i++) {
			String [] msgl = msgHistoryStr[i];
			JSONObject msg = new JSONObject();
			msg.put("id", msgl[0]);
			msg.put("type", msgl[2]);
			msg.put("msg", msgl[3]);
			//控制id标识符为非我方
			String tg;
			if(msgl[0].equals(id)) {
				tg = msgl[1];
			}else {
				tg = msgl[0];
			}
			//如果是新出现的人,则将其添加到字典中
			if(msgHistory.get(tg)==null) {
				JSONArray jsonArray = new JSONArray();
				msgHistory.put(tg, jsonArray);
			}
			msgHistory.getJSONArray(tg).add(msg);
		}
		msgRes.put("history", msgHistory);
		
		String sql2 = sqlTime2 + " and " +sqlTarget;
		String [][] msgnoRecivedStr = mysc.getResListSql("message",sql2);
		JSONObject msgnoRecived = new JSONObject();
		for(int i=0;i<msgnoRecivedStr.length;i++) {
			String [] msgl = msgnoRecivedStr[i];
			JSONObject msg = new JSONObject();
			msg.put("id", msgl[0]);
			msg.put("type", msgl[2]);
			msg.put("msg", msgl[3]);
			//控制id标识符为非我方
			String tg;
			if(msgl[0].equals(id)) {
				tg = msgl[1];
			}else {
				tg = msgl[0];
			}
			//如果是新出现的人,则将其添加到字典中
			if(msgnoRecived.get(tg)==null) {
				JSONArray jsonArray = new JSONArray();
				msgnoRecived.put(tg, jsonArray);
			}
			msgnoRecived.getJSONArray(tg).add(msg);
		}
		msgRes.put("noRecived", msgnoRecived);
		
		System.out.println(msgRes.toString());
		return msgRes;
	}
	
	//将数据库取出的timestamp字符串转换为long
	public long convertTimeToLong(String ts) {
		SimpleDateFormat formattime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			System.out.println("nw:"+System.currentTimeMillis()+" "+(new Timestamp(System.currentTimeMillis()).toString()));
			System.out.println("ts:"+formattime.parse(ts).getTime()+" "+new Timestamp(formattime.parse(ts).getTime()).toString());
			return formattime.parse(ts).getTime()-3600000*(8);  //这里做一个小的改进，我们返回的时间是一小时前。
		} catch (ParseException e) {
			System.out.println("cA:将时间从timestamp转换为long发生错误");
			e.printStackTrace();
			return 0;
		}
	}
}
