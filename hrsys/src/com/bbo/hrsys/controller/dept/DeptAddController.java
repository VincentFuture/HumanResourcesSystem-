package com.bbo.hrsys.controller.dept;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bbo.hrsys.po.Department;
import com.bbo.hrsys.service.DeptService;

/**
 * Servlet implementation class DeptAddController
 */
@WebServlet("/addDept")
public class DeptAddController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeptAddController() {
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
		Department dept = new Department();
		dept.setName(request.getParameter("deptname"));
		dept.setCount(Integer.parseInt(request.getParameter("deptcount")));
		DeptService service = new DeptService();
		//调用方法
		String str = service.add(dept);
		//打印结果
		PrintWriter out = response.getWriter();
		out.println(str);
		out.flush();
	}

}
