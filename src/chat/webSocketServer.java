package chat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSONObject;


@ServerEndpoint(value="/webSocket/{id}")
public class webSocketServer {
	
	public static final HashMap<String,webSocketServer> connections = new HashMap<>();
	private String id;
	public static final long time = System.currentTimeMillis();
	public Session session;
	
	public webSocketServer() {
		System.out.println("ws:websocket构建成功");
		// TODO Auto-generated constructor stub
	}
	
	@OnOpen
	public void onOpen(@PathParam("id")String id,Session session) {
		System.out.println("ws:id="+id+"的websocket被打开");
		System.out.println("ws:time="+time);
		this.session = session;
		this.id = id;
		webSocketServer.connections.put(id, this);
		System.out.println("ws:"+id+" 已被置于集合中，集合为"+webSocketServer.connections);
		System.out.println("ws:从数据库中设置id="+id+"的用户为在线");
		chatAction cA = new chatAction();
		cA.setUserOnline(id, "1");
	}
	
	@OnClose
	public void onClose() {
		System.out.println("ws:"+"id为:"+this.id+"的连接已关闭");
		webSocketServer.connections.remove(id);
		System.out.println("ws:"+id+"已被从集合中移除");
		
		//双份备份？？？
		chatAction cA = new chatAction();
		cA.offlineUser(id);
	}
	@OnError
	public void onError(Session session,Throwable error) {
		error.printStackTrace();
	}
	
	@OnMessage
	public void onMessage(Session session, String message) {
		String tg = message.split("&@&")[0];
		String type = message.split("&@&")[1];
		String msg = message.split("&@&")[2];
		sendText(id,tg,msg,type);
	}
	
	protected void sendText(String id,String target,String msg,String msgType) {
		chatAction cA = new chatAction();
		cA.storeMsg(id, target, msg, msgType);
		System.out.println("hM:已存储数据");
		System.out.println(webSocketServer.connections);
		webSocketServer ws  = webSocketServer.connections.get(target);
		System.out.println("hM:已获得ws");
		System.out.println("hM:time="+webSocketServer.time);
		if(ws==null) {
			System.out.println("hM:tg = "+target+" connections= "+webSocketServer.connections);
			System.out.println("hM:"+target+"离线，消息将存储在数据库");
		}else {
			System.out.println("hM:tg = "+target+" connections= "+webSocketServer.connections);
			System.out.println("hM:"+target+"在线，handleMsg正在转发消息");
			try {
				Session session = ws.session;
				//生成json对象
				JSONObject obj = new JSONObject();
				obj.put("id", id);
				obj.put("type", msgType);
				obj.put("msg", msg);
				session.getBasicRemote().sendText(obj.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("hM:理论上对方在线，并且对方的session存在于字典中，但这里却出现了错误");
				e.printStackTrace();
			}
		}
	}
}
