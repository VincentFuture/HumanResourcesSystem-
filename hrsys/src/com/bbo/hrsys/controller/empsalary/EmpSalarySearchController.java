package com.bbo.hrsys.controller.empsalary;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bbo.hrsys.po.Employee;
import com.bbo.hrsys.po.EmployeeSalary;
import com.bbo.hrsys.service.EmpSalaryService;

/**
 * Servlet implementation class EmpSalaryController
 */
@WebServlet("/searchEmpSalary")
public class EmpSalarySearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmpSalarySearchController() {
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
		//设置响应对象字串类型
		response.setContentType("application/json");
		//封装数据(客户端请求数据
		EmployeeSalary es = new EmployeeSalary();
		String esid = request.getParameter("esid");
		if(esid!=null && !"".equals(esid))
			es.setEsId(Integer.parseInt(esid));//编号
		String ss = request.getParameter("salaryStandard");
		if(ss!=null && !"".equals(ss))	
			es.setsStandard(Double.parseDouble(ss));//薪水
		//设置雇员对象
		Employee emp = new Employee();
		String ename = request.getParameter("ename");
		emp.setName(ename);
		if(emp.getName()!=null && !"".equals(emp.getName()))
			es.setEmp(emp);
		//实例化service对象并调用业务层方法
		EmpSalaryService service = new EmpSalaryService();
		String str = service.querySalary(es);
		
		//打印输出
		PrintWriter out = response.getWriter();
		out.println(str);
		//清空缓冲流中的数据
		out.flush();
	}

}
