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
 * Servlet implementation class UserDelController
 */
@WebServlet("/delUser")
public class UserDelController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserDelController() {
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
		//设置response对象解析格式为json
		response.setContentType("application/json");
		//封装数据(对象)
		//User user = new User();
		String uids = request.getParameter("checkids");
		/*if(uids != null && !"".equals(uids))
			user.setUser_id(Integer.parseInt(uids));*/
		//调用业务层方法
		UserService service = new UserService();
		String rs = service.del(uids);
		//输出传回结果
		PrintWriter out = response.getWriter();
		out.println(rs);
		out.flush();
		
	}

}
