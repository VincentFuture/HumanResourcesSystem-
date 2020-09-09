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
 * Servlet implementation class DeptSearchController
 */
@WebServlet("/searchDept")
public class DeptSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeptSearchController() {
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
		//声明dept对象封装前端发送的数据
		Department dept = new Department();
		//获取请求对象的表单数据
		String dept_id = request.getParameter("deptid");
		String deptname = request.getParameter("deptname");
		String deptcount = request.getParameter("deptcount");
		//封装数据
		if(dept_id!=null && !"".equals(dept_id))
			dept.setDept_id(Integer.parseInt(dept_id));
		if(deptname!=null && !"".equals(deptname))
			dept.setName(deptname);
		if(deptcount!=null && !"".equals(deptcount))
			dept.setCount(Integer.parseInt(deptcount));
		//实例化service
		DeptService service = new DeptService();
		//调用方法
		String str = service.query(dept);
		//打印结果
		PrintWriter out = response.getWriter();
		out.println(str);
		out.flush();
		
	}

}
