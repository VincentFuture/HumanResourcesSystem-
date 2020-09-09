package com.bbo.hrsys.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bbo.hrsys.po.Department;
import com.bbo.hrsys.po.Dict;
import com.bbo.hrsys.po.Employee;
import com.bbo.hrsys.po.User;
import com.bbo.hrsys.util.DBUtil;

public class EmployeeDao {
	
	//查询
	public List<Employee> query(Employee emp){
		//数据库操作工具
		DBUtil dbu = new DBUtil();
		//数据容器
		List<Employee> list = new ArrayList<>();
		//sql语句
		//StringBuffer sql = new StringBuffer("");
		StringBuffer sql = new StringBuffer("");
		sql.append("select e.employee_id eid,e.name ename,e.age, "+//#employee表数据
		"u.username uname, "+//#外连接用户表用户名数据
		"di_gender.title gender, di_city.title city, "+//#外连接字典 性别和城市数据 
		"de.name deptname "+//#外连接部门表 部门名称数据
		"from employee as e "+
		"left outer join user as u "+//#外连接用户表
		"on e.user_id=u.user_id "+
		"left outer join dict as di_gender "+//#外连接字典表  性别
		"on e.gender_id=di_gender.dict_id "+
		"left outer join dict as di_city "+//#外连接字典表 城市
		"on e.city_Id=di_city.dict_id "+
		"left outer join dept as de "+//#外连接部门表
		"on e.dept_id=de.dept_id "+
		" where 1=1 " );
		//条件查询
		if(emp.getEmployee_id()!=0)
			sql.append(" and e.employee_id = "+emp.getEmployee_id());
		if(emp.getName()!=null && !"".equals(emp.getName()))
			sql.append(" and e.name = '"+emp.getName()+"'");
		sql.append(" order by eid");
		//执行查询
		//ResultSet rs = dbu.query(sql.toString());
		ResultSet rs = dbu.query(sql.toString());
		//封装数据
		try {
			while(rs.next()) {
				//eid, ename, age, uname, gender, city, deptname	
				User uTemp = new User();
				Dict di_genderTemp = new Dict();
				Dict di_cityTemp = new Dict();
				Department deptTemp = new Department();
				Employee empTemp = new Employee();
				
				empTemp.setEmployee_id(rs.getInt("eid"));
				empTemp.setName(rs.getString("ename"));
				empTemp.setAge(rs.getInt("age"));
				//封装用户名
				uTemp.setUsername(rs.getString("uname"));
				empTemp.setUser_id(uTemp);
				//封装性别信息
				di_genderTemp.setTitle(rs.getString("gender"));
				empTemp.setGender(di_genderTemp);
				//封装城市信息
				di_cityTemp.setTitle(rs.getString("city"));
				empTemp.setCity(di_cityTemp);
				//封装部门信息
				deptTemp.setName(rs.getString("deptname"));
				empTemp.setDept_id(deptTemp);
				
				list.add(empTemp);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		dbu.close();
		return list;
	}
	//用于从表(dict)删除记录时主表是否已使用从表数据检查查询
	public boolean checkQuery(String ids) {
		
		DBUtil dbu = new DBUtil();
		String  sql = "select * from employee where city_Id in ("+
		ids+") or gender_id in ("+ids+")";
		ResultSet rs = dbu.query(sql);
		try {
			int rsrow = 0;
			while(rs.next()) {
				rsrow++;
				if(rsrow>0)
					break;
			}
				
			if(rsrow==0) {
				return true;//不存在外键记录 不允许从表记录删除
			}else
				return false;//处在外键记录  允许从表记录删除
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			dbu.close();
		}
		return false;//默认 不允许删除
	}
	
	//修改
	
	
	
	
	//删除
	public int del(Employee emp) {
		int rows = -1;
		DBUtil dbu = new DBUtil();
		String sql = "delete from employee where employee_id = ?";
		//数据联动删除薪资表的记录
		String sql_del_es = "delete from employee_salary where employee_id = ?";
		//执行sql
		rows = dbu.update(sql,emp.getEmployee_id());
		dbu.update(sql_del_es,emp.getEmployee_id());
		dbu.close();
		return rows;
	}
	//添加
	public int add(Employee emp) {
		int rows = -1;
		DBUtil dbu = new DBUtil();
		String sql = "insert into employee(name, age, gender_id, city_Id,dept_id, user_id) "
				+ "values(?,?,?,?,?,?)";
			
		//执行查询
		rows = dbu.update(sql,
				emp.getName(),
				emp.getAge(),
				emp.getGender().getDct_id(),
				emp.getCity().getDct_id(),
				emp.getDept_id().getDept_id(),
				emp.getUser_id().getUser_id());
		dbu.close();
		return rows;
	}
}
