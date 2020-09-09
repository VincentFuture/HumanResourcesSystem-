package com.bbo.hrsys.controller.dept;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bbo.hrsys.service.DeptService;

/**
 * Servlet implementation class DeptDelController
 */
@WebServlet("/delDept")
public class DeptDelController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeptDelController() {
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
		//封装数据(对象)
		//User user = new User();
		String dids = request.getParameter("checkids");
		/*if(uids != null && !"".equals(uids))
			user.setUser_id(Integer.parseInt(uids));*/
		//调用业务层方法
		DeptService service = new DeptService();
		String rs = service.del(dids);
		//输出传回结果
		PrintWriter out = response.getWriter();
		out.println(rs);
		out.flush();
	}

}
