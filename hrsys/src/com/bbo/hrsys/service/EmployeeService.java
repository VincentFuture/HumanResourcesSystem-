package com.bbo.hrsys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bbo.hrsys.dao.DeptDao;
import com.bbo.hrsys.dao.EmployeeDao;
import com.bbo.hrsys.po.Employee;
import com.google.gson.Gson;

public class EmployeeService {
	private EmployeeDao dao;
	public EmployeeService() {
		dao = new EmployeeDao();
	}
	public String query(Employee emp) {
		List<Employee> list = dao.query(emp);
		Gson gson = new Gson();
		return gson.toJson(list);
	}
	
	public String del(Employee emp) {
		int rows = -1;
		//创建返回消息容器
		Map<String , Object> map = new HashMap<>();
		rows = dao.del(emp);
		if(rows > 0) {
			map.put("flag", true);
			map.put("msg", "操作成功");
		}else {
			map.put("flag", false);
			map.put("msg", "操作失败,请联系管理员");
		}
		//返回json格式字串
		Gson gson = new Gson();
		return gson.toJson(map);
	}
	public String add(Employee emp) {
		int rows = -1;
		//创建返回消息容器
		Map<String , Object> map = new HashMap<>();
		//检查部门是否允许继续添加雇员(部门实际雇员数量<部门定编)
		DeptDao deptDao = new DeptDao();
		boolean check = deptDao.checkAdd(emp.getDept_id().getDept_id());
		if(check) {
			rows = dao.add(emp);
			if(rows > 0) {
				map.put("flag", true);
				map.put("msg", "操作成功");
			}else {
				map.put("flag", false);
				map.put("msg", "操作失败,请联系管理员");
			}
		}else {
			map.put("flag", false);
			map.put("msg", "部门人数已满");
		}
		
		//返回json格式字串
		Gson gson = new Gson();
		return gson.toJson(map);
		
		
	}
}
