package com.bbo.hrsys.controller.employee;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bbo.hrsys.po.Employee;
import com.bbo.hrsys.service.EmployeeService;

/**
 * Servlet implementation class EmpSearchController
 */
@WebServlet("/searchEmployee")
public class EmpSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmpSearchController() {
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
		//设置返回对象数据类型
		response.setContentType("application/json");
		//封装数据
		Employee emp = new Employee();
		String eid = request.getParameter("eid");//雇员id
		String ename = request.getParameter("ename");//雇员姓名
		if(eid!=null && !"".equals(eid)) {
			emp.setEmployee_id(Integer.parseInt(eid));
		}
		if(ename!=null && !"".equals(ename)) {
			emp.setName(ename);
		}
		//调用service层方法
		EmployeeService service = new EmployeeService();
		String rs = service.query(emp);
		PrintWriter out = response.getWriter();
		out.println(rs);
		out.flush();
	}

}
