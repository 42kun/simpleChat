package login.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import login.action.userAction;
import login.action.tools.*;
import login.model.User;


/**
 * Servlet implementation class registerControl
 */
@WebServlet("/registerControl")
public class registerControl extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public registerControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*将注册信息保存到数据库并转发给下一个页面*/
		HttpSession se = request.getSession();
		if(se.getAttribute("id")!=null) {
			request.getRequestDispatcher("/ChatPage.jsp").forward(request, response);
			return;
		}
		
		//将信息保存到数据库
		userAction uA = new userAction();
		User user = new User();
		String id = idAction.idAcquire();
		user.setId(id);
		user.setNickName(request.getParameter("nickName"));
		user.setPasswordMD5(MD5.md5(request.getParameter("password")));
		user.setEmail(request.getParameter("email"));
		uA.initializeUser(user);
		
		/*将id 保存到session*/
		se.setAttribute("id", id);
		System.out.println("rg:已将id放置在session中，id="+se.getAttribute("id"));
		/*为了正确的registerSuccess.jsp页面而设置的一个判断是否刚注册的值*/
		se.setAttribute("isJustRegisted","1");
		
		
		
		//将用户信息转发到下一个页面
		request.setAttribute("user", user);
		request.getRequestDispatcher("/registerSuccess.jsp").forward(request, response);
	}

}
