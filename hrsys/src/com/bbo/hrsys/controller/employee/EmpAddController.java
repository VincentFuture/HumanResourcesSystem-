package com.bbo.hrsys.controller.employee;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bbo.hrsys.po.Department;
import com.bbo.hrsys.po.Dict;
import com.bbo.hrsys.po.Employee;
import com.bbo.hrsys.po.User;
import com.bbo.hrsys.service.EmployeeService;

/**
 * Servlet implementation class EmpAddController
 */
@WebServlet("/addEmployee")
public class EmpAddController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmpAddController() {
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
		Employee emp = new Employee();
		//{"emp_id":"","emp_name":"test","emp_age":"20","emp_gender":"1","emp_city":"南昌","emp_uname":"alice","emp_dept":"市场部"}
		//String emp_id = request.getParameter("emp_id");//雇员id
		String emp_name = request.getParameter("emp_name");//雇员姓名
		String emp_age =  request.getParameter("emp_age");//雇员年龄
		
		String emp_gender = request.getParameter("emp_gender");//雇员性别
		if(emp_gender==null||"".equals(emp_gender))//性别为空
			emp_gender = "0";
		String emp_city = request.getParameter("emp_city");//雇员所在城市
		if(emp_city==null||"".equals(emp_city))//所在城市为空
			emp_city = "0";
		String emp_uname =  request.getParameter("emp_uname");//雇员所使用账号用户名
		if(emp_uname==null||"".equals(emp_uname))//账号用户名为空
			emp_uname = "0";
		String emp_dept = request.getParameter("emp_dept");//雇员所属部门
		if(emp_dept==null||"".equals(emp_dept))//所属部门为空
			emp_dept = "0";
		
		emp.setName(emp_name); //雇员姓名
		emp.setAge(Integer.parseInt(emp_age));//雇员年龄
		Dict genderIdTemp = new Dict();
		genderIdTemp.setDct_id(Integer.parseInt(emp_gender));
		emp.setGender(genderIdTemp);//gender_id 雇员性别
		Dict cityIdTemp = new Dict();
		cityIdTemp.setDct_id(Integer.parseInt(emp_city));
		emp.setCity(cityIdTemp);//city_id  雇员城市
		User userIdTemp = new User();
		userIdTemp.setUser_id(Integer.parseInt(emp_uname));
		emp.setUser_id(userIdTemp);// user_id 用户登陆账户名
		Department deptIdTemp = new Department();
		deptIdTemp.setDept_id(Integer.parseInt(emp_dept));
		emp.setDept_id(deptIdTemp);//dept_id 部门名称
		
		//调用service层方法
		EmployeeService service = new EmployeeService();
		String rs = service.add(emp); 
		//打印结果
		PrintWriter out = response.getWriter();
		out.println(rs);
		out.flush();
	}

}
