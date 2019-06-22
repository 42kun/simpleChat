package chat.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import chat.chatAction;

/**
 * Servlet implementation class getHistoryMsg
 */
@WebServlet("/getHistoryMsg")
public class getHistoryMsg extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getHistoryMsg() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 返回格式:id msgarray(在msgarray中id为我即为对方发送的消息,id不是我即为我发送的消息,即id为发送方)
		 * 对于历史离线分界线,消息为 -↑-以上为历史消息-↑-
		 * 我们设定,一旦两条消息之间间隔超过四个小时,即插入一条时间
		 * 
		 * (注意,当你在实时聊天时,也要对historyMsg进行更新)
		 */
		
		HttpSession session = request.getSession();
		String id = session.getAttribute("id").toString();
		chatAction cA = new chatAction();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter write = response.getWriter();
		//System.out.println("gH:"+cA.getHistoryMsg(id).toString());
		write.append(cA.getHistoryMsg(id).toString());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
