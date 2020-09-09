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
 * Servlet implementation class EmpSalaryUpdateController
 */
@WebServlet("/updateEmpSalary")
public class EmpSalaryUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmpSalaryUpdateController() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置返回对象值类型
		response.setContentType("application/json");
		// 初始化对象
		EmployeeSalary es = new EmployeeSalary();
		// 获取参数
		String nsalary = request.getParameter("nsalary");
		if (nsalary != null && !"".equals(nsalary))
			es.setsStandard(Integer.parseInt(nsalary));
		
		Employee empid = new Employee();
		String eid = request.getParameter("eid");//雇员id
		if (eid != null && !"".equals(eid)) {
			empid.setEmployee_id(Integer.parseInt(eid));
			es.setEmp(empid);
		}
		String esid = request.getParameter("esid");//雇员薪水记录id
		if(esid != null && !"".equals(esid))
			es.setEsId(Integer.parseInt(esid));
		
		// 实例化service对象
		EmpSalaryService service = new EmpSalaryService();
		String rs = service.update(es);
		// 输出结果
		PrintWriter out = response.getWriter();
		out.println(rs);
		out.flush();

	}

}
