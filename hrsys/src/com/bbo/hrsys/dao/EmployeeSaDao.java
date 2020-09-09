package com.bbo.hrsys.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bbo.hrsys.po.*;
import com.bbo.hrsys.util.DBUtil;

public class EmployeeSaDao {
	//雇员数据持久层
	
	//薪水查询
	public List<EmployeeSalary> querySalary(EmployeeSalary es){
		//声明容器
		List<EmployeeSalary> list = new ArrayList<>();
		//声明数据库操纵对象
		DBUtil dbu = new DBUtil();
		//sql语句
		StringBuffer sql = new StringBuffer("select es.* ,e.name ename,e.employee_id eid " + 
				"from employee_salary as es " + 
				"right outer join employee as e "+ 
				"on es.employee_id=e.employee_id ");
		
		//范围查找(最大值
		if(es!=null && es.getsStandard()!=0) {
			sql.append(" and salary_standard<"+es.getsStandard());
		}else {
			//记录编号精准查询
			if(es!=null && es.getEsId()!=0) {
				sql.append( "and employee_salary_id="+es.getEsId());
			}else if(es.getEmp()!=null && es.getEmp().getName()!=null && !"".equals(es.getEmp().getName())) {//姓名精准查询
				sql.append(" and employee.name='"+es.getEmp().getName()+"'");
			}
			
		}
			
		//执行查询
		ResultSet rs = dbu.query(sql.toString());
		//封装数据
		try {
			while(rs.next()) {
				EmployeeSalary temp = new EmployeeSalary();
				temp.setEsId(rs.getInt("employee_salary_id"));
				temp.setsStandard(rs.getInt("salary_standard"));
				//设置雇员姓名
				Employee emptemp = new Employee();
				emptemp.setName(rs.getString("ename"));
				emptemp.setEmployee_id(rs.getInt("eid"));
				temp.setEmp(emptemp);
				list.add(temp);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//关闭数据库连接
		dbu.close();
		return list;
	}
	//修改
	public int update(EmployeeSalary es) {
		int rows = -1;
		DBUtil dbu = new DBUtil();
		String sql = "update employee_salary set salary_standard=? where employee_salary_id=?";
		rows = dbu.update(sql, es.getsStandard(),es.getEsId());
		dbu.close();
		return rows;
	}
	public int add(EmployeeSalary es) {
		int rows = -1;
		DBUtil dbu  = new DBUtil();
		
		String sql = "insert into employee_salary(salary_standard, employee_id)"
				+ " values(?,?)";
		//执行sql
		rows = dbu.update(sql,es.getsStandard(),es.getEmp().getEmployee_id());

		dbu.close();
		return rows;
	}
	
}
