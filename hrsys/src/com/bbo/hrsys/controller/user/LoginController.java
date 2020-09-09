package com.bbo.hrsys.controller.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bbo.hrsys.service.UserService;

/**
 * Servlet implementation class LoginServerlet name:此类的对象名
 * urlPatterns:给servlet对象分配url地址，必须以/开始，url地址必须是唯一的
 */
@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response: 处理前端的get请求 
	 *      request:请求对象
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response) 处理前端采用Post请求
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		// 通过request对象获取用户名和密码
		String uname = request.getParameter("uname");
		String upass = request.getParameter("upass");
		
		// 声明并实例化Userservice的对象
		UserService service = new UserService();
		// 调用Userservice对象中的登陆方法，并接收返回值
		String str = service.login(uname, upass);
		// 获取response对象中针对前端的输出流
		PrintWriter out = response.getWriter();
		// response.getWriter().append("Served at: ").append(request.getContextPath());

		// 将结果打印给前端
		out.println(str);
		out.flush();
	}

}
