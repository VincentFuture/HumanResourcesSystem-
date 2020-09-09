package com.bbo.hrsys.controller.employee;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bbo.hrsys.po.Employee;
import com.bbo.hrsys.po.User;
import com.bbo.hrsys.service.EmployeeService;
import com.bbo.hrsys.service.UserService;

/**
 * Servlet implementation class EmpDeleteController
 */
@WebServlet("/delEmployee")
public class EmpDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmpDeleteController() {
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
		//
		response.setContentType("application/json");
		//封装数据
		Employee emp = new Employee();
		String emp_id = request.getParameter("eid");
		if(emp_id != null && !"".equals(emp_id)) {
			emp.setEmployee_id(Integer.parseInt(emp_id));
		}
		//用户id
		String uname = request.getParameter("uname");//从请求对象中获取uname
		if(uname != null && !"".equals(uname)) {
			User usertemp = new User();//通过uname查询uid
			usertemp.setUsername(uname);
			//获取对应id
			UserService service = new UserService();
			String uidtemp = service.search(usertemp);
			if(uidtemp != null && !"".equals(uidtemp)) {
				//截取user_id字段
				int beginIndex = uidtemp.indexOf("user_id\":")+9;
				int endIndex = uidtemp.indexOf(",");
				String uid =  uidtemp.substring(beginIndex, endIndex);
				usertemp.setUser_id(Integer.parseInt(uid));
				emp.setUser_id(usertemp);
			}
		}
		//调用service层方法
		EmployeeService service = new EmployeeService();
		String rs = service.del(emp); 
		//打印结果
		PrintWriter out = response.getWriter();
		out.println(rs);
		out.flush();
	}

}
