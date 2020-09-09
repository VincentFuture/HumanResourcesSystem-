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
 * Servlet implementation class UserSearchController
 */
@WebServlet("/searchUser")
public class UserSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserSearchController() {
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
		//获取数据
		User user = new User();
		response.setContentType("application/json");
		String user_id = request.getParameter("uid");
		if(user_id!=null && !"".equals(user_id)) {
			user.setUser_id(Integer.parseInt(user_id));
		}
		String uname = request.getParameter("uname");
		if(uname!=null && !"".equals(uname)) {
			user.setUsername(uname);
		}
		String isused = request.getParameter("isused");
		if(isused!=null && !isused.isEmpty()) {
			user.setIsused(Integer.parseInt(isused));
		}
		//实例化service
		UserService service = new UserService();
		//调用方法
		String str = service.search(user);
		//打印结果
		PrintWriter out = response.getWriter();

		out.println(str);
		out.flush();
	}

}
