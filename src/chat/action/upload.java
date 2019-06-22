package chat.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.catalina.core.ApplicationPart;

/**
 * Servlet implementation class upload
 */
@WebServlet("/upload")
@MultipartConfig
public class upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public upload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ul:servlet构建");
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out=response.getWriter();
        //path这里用绝对路径
        String path="/usr/local/tomcat/webapps/work/files/upload/";
        Part p=request.getPart("file");
        ApplicationPart ap= (ApplicationPart) p;
        String fname1=ap.getSubmittedFileName();
        int path_idx=fname1.lastIndexOf("\\")+1;
        String fname2=fname1.substring(path_idx,fname1.length());
        p.write(path+fname2);
        System.out.print("ul:文件上传成功");
        out.append("文件传输成功");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
