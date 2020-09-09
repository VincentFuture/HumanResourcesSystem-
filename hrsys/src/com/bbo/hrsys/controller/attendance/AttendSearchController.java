package com.bbo.hrsys.controller.attendance;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bbo.hrsys.po.Attendance;
import com.bbo.hrsys.po.Employee;
import com.bbo.hrsys.service.AttendanceService;

/**
 * Servlet implementation class AttendSearchController
 */
@WebServlet("/searchAttend")
public class AttendSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AttendSearchController() {
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
		Attendance att = new Attendance();
		Employee emp = new Employee();
		AttendanceService service = new AttendanceService();
		//封装数据
		String ename = request.getParameter("ename");
		String adate = request.getParameter("adate");
		String isleave = request.getParameter("isleave");
		if(ename!=null && !"".equals(ename)) {
			emp.setName(ename); 
			att.setEmpid(emp);
		}
		if(adate!=null && !"".equals(adate))
			att.setAttend_date(adate);
		if(isleave!=null && !"".equals(isleave))
			att.setIsLeave(Integer.parseInt(isleave));
		
		String rs = service.query(att);
		PrintWriter out = response.getWriter();
		out.println(rs);
		out.flush();
	}

}
