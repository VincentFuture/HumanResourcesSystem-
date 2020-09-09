package com.bbo.hrsys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bbo.hrsys.dao.DeptDao;
import com.bbo.hrsys.po.Department;
import com.google.gson.Gson;

public class DeptService {
	//声明dao层对象
	private DeptDao dao;
	public DeptService() {
		dao = new DeptDao();
	}
	//查询部门
	public String query(Department dept) {
		List<Department> list = dao.queryDept(dept);
		Gson gson = new Gson();
		return gson.toJson(list);
	}
	//修改部门
	public String update(Department dept) {
		Map<String, Object> map = new HashMap<>();
		
		//检查部门现有雇员数量
		int existCount  = dao.checkUpdate(dept);
		if(existCount <= dept.getCount()) {
			int rows = dao.update(dept);
			if(rows>0) {
				map.put("flag", true);
				map.put("msg", "操作成功");
			}else {
				map.put("flag", false);
				map.put("msg", "操作失败,请联系管理员");
			}
		}else {
			map.put("flag", false);
			map.put("msg", "该部门已存在雇员人数"+existCount+"无法修改,请检查部门人数或联系管理员");
		}
		
		Gson gson =new Gson();
		return gson.toJson(map);
	}
	//删除部门
	public String del(String dids) {
		
		// 声明返回数据封装容器
		Map<String, Object> map = new HashMap<>();
		
		//检查部门是否已存在雇员
		boolean check = dao.checkDel(dids);
		if(check) {
			int rows = dao.del(dids);
			if (rows > 0) {
				map.put("flag", true);
				map.put("msg", "部门操作成功");
			} else if (rows == -2) {
				map.put("flag", false);
				map.put("msg", "部门删除失败,删除的用户包含超级用户");
			} else {
				map.put("flag", false);
				map.put("msg", "操作失败,请联系管理员");
			}
		}else {
			map.put("flag", false);
			map.put("msg", "部门已有雇员,无法删除");
		}
		
		// 传回json格式字串
		Gson gson = new Gson();
		return gson.toJson(map);
	}
	//增加部门
	public String add(Department dept) {
			
			int rows = dao.add(dept);
			Map<String,Object> map = new HashMap<>();
			if(rows > 0) {
				map.put("flag", true);
				map.put("msg", "添加部门成功");
			}else {
				map.put("flag", false);
				map.put("msg", "添加部门失败,请检查用户名或联系管理员");
			}
			Gson gson = new Gson();
			return gson.toJson(map);
		}
}
