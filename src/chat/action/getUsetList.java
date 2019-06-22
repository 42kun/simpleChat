package chat.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import chat.chatAction;

/**
 * Servlet implementation class getUsetList
 */
@WebServlet("/getUsetList")
public class getUsetList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getUsetList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*返回所有用户的json对象*/
		System.out.println("sl:getUserList执行");
		chatAction cA = new chatAction();
		JSONObject obj = cA.getAllUser();
		System.out.println("sl:"+obj.toJSONString());
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter write = response.getWriter();
		write.append(obj.toString());
	}

}
