package com.bbo.hrsys.controller.attendance;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bbo.hrsys.service.AttendanceService;

/**
 * Servlet implementation class AttendAddController
 */
@WebServlet("/genAttend")
public class AttendAddController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AttendAddController() {
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
		AttendanceService service = new AttendanceService();
		//封装数据
		String empId = request.getParameter("ename");
		String month = request.getParameter("month");
		String rs = "{\"msg\":\"签到失败\"}";
		if((empId!=null && !empId.equals(""))&&(month!=null&&!month.equals(""))) {
			rs = service.add(empId,month);
		}
		
		PrintWriter out = response.getWriter();
		out.println(rs);
		out.flush();
	}

}
