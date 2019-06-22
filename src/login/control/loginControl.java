package login.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import login.action.userAction;
import login.action.tools.MD5;

/**
 * Servlet implementation class loginControl
 */
@WebServlet("/loginControl")
public class loginControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginControl() {
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
		HttpSession session = request.getSession();
		
		/*记录登陆次数，便于验证码的弹出*/
		if(session.getAttribute("loginCount")==null){
			session.setAttribute("loginCount", 0);
		}
		
		userAction uA = new userAction();
		String id = request.getParameter("id");
		String password=MD5.md5(request.getParameter("password"));
		String savedPassword = uA.getPasswordMD5(id);
		if(password.equals(savedPassword)) {
			System.out.println("登陆成功");
			/*将用户注册进session*/
			session.setAttribute("id", id.toString());
			/*将登录次数置零*/
			session.setAttribute("loginCount", 0);
			request.getRequestDispatcher("/chatPage.jsp").forward(request, response);
		}else {
			System.out.println("登陆失败");
			/*记录登录次数*/
			int loginCount = Integer.parseInt(session.getAttribute("loginCount").toString());
			loginCount++;
			session.setAttribute("loginCount", loginCount);
			
			/*跳转回登陆界面*/
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}

}
