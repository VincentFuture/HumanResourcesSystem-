package com.bbo.hrsys.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bbo.hrsys.po.Department;
import com.bbo.hrsys.util.DBUtil;
public class DeptDao {
	//查询
	public List<Department> queryDept(Department dept) {
		List<Department> list = new ArrayList<>();
		DBUtil dbu = new DBUtil();
		StringBuffer sql = new StringBuffer("select * from dept where 1=1");
		if(dept!=null && dept.getDept_id()!=0) {
			sql.append(String.format(" and dept_id=%d", dept.getDept_id()));
		}
		if(dept!=null && dept.getName()!=null && !"".equals(dept.getName()) ) {
			sql.append(" and name like '%"
					+ dept.getName()+
					"%'");
		}
		//接收结果
		ResultSet rs = dbu.query(sql.toString());
		try {
			while(rs.next()) {
				Department temp = new Department();
				temp.setDept_id(rs.getInt("dept_id"));
				temp.setName(rs.getString("name"));
				temp.setCount(rs.getInt("count"));
				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbu.close();
		return list;
	}
	//修改信息
	public int update(Department dept) {
		int rows=-1;
		DBUtil dbu  = new DBUtil();
		String sql = "update dept set count=? where dept_id=?";
		rows = dbu.update(sql,dept.getCount(),dept.getDept_id());
		dbu.close();
		return rows;
	}
	//增加部门
	public int add(Department dept) {
		// 数据容器sql
		int rows = -1;
		String sql = "insert into dept(name,count) values(?,?)";
		// DBUtil实例化
		DBUtil dbu = new DBUtil();
		// 执行sql
		rows = dbu.update(sql, dept.getName(),dept.getCount());
		dbu.close();
		return rows;
	}
	//删除部门
	public int del(String dids) {
		int rows=-1;//受影响的行数
		//声明数据库操作对象
		DBUtil dbu = new DBUtil();
		//完成sql语句
		String sql = "delete from dept where dept_id in ("+dids+")";
		rows = dbu.update(sql);
		dbu.close();
		return rows;
	}
	public boolean checkAdd(int dept_id) {
		DBUtil dbu = new DBUtil();
		String sql = "select " + 
				" (select dept.count from dept where dept.dept_id = "+dept_id+") > " + 
				" (select count(employee_id) from employee where dept_id = "+dept_id+") ";
		
		ResultSet rs = dbu.query(sql);
		//检查数据
		try {
			if(rs.next()) {
				if(rs.getInt(1)==1) {
					return true;
				}else {
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbu.close();
		}
		return false;
	}
	//检查部门是否已经包含雇员
	public boolean checkDel(String dids) {
		DBUtil dbu =  new DBUtil();
		String sql = "select * from employee where dept_id in ("+dids+")";
		ResultSet rs = dbu.query(sql);
		try {
			if(rs.next()) {
				return false;
			}else {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			 dbu.close();
		}
		return false;
	}
	//检查待修改部门定编数量是否大于部门现已存在的雇员数量
	public int checkUpdate(Department dept) {
		DBUtil dbu =  new DBUtil();
		String sql = "select count(employee_id) from employee where dept_id = "+dept.getDept_id();
		ResultSet rs = dbu.query(sql);
		try {
			if(rs.next()) {
				return rs.getInt(1);//返回部门现有的雇员数量
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbu.close();
		}
		return 0;
	}

}
