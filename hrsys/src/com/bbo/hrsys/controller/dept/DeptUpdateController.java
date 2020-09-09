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
 * Servlet implementation class DeptUpdateController
 */
@WebServlet("/updateDept")
public class DeptUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeptUpdateController() {
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
		//设置返回对象值类型
		response.setContentType("application/json");
		//初始化对象
		Department dept = new Department();
		//获取参数
		String ncount = request.getParameter("ncount");
		if(ncount!=null && !"".equals(ncount))
			dept.setCount(Integer.parseInt(ncount));
		String id = request.getParameter("did");
		if(id!=null && !"".equals(id))
			dept.setDept_id(Integer.parseInt(id));
		//实例化service对象
		DeptService service = new DeptService();
		String rs = service.update(dept);
		//输出结果
		PrintWriter out = response.getWriter();
		out.println(rs);
		out.flush();
	}

}
