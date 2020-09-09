package com.bbo.hrsys.controller.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bbo.hrsys.po.User;
import com.bbo.hrsys.service.UserService;

/**
 * Servlet implementation class UserAddController
 */
@WebServlet("/addUser")
public class UserAddController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserAddController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		//封装数据
		User user = new User();
		user.setUsername(request.getParameter("uname"));
		user.setPassword(request.getParameter("upass"));
		user.setIsused(Integer.parseInt(request.getParameter("isused")));
		//实例化service
		UserService service = new UserService();
		//调用方法
		String str = service.add(user);
		//打印结果
		PrintWriter out = response.getWriter();
		out.println(str);
		out.flush();
		
	}

}
