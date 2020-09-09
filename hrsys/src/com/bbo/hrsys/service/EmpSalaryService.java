package com.bbo.hrsys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bbo.hrsys.dao.EmployeeSaDao;
import com.bbo.hrsys.po.EmployeeSalary;
import com.google.gson.Gson;

public class EmpSalaryService {
	EmployeeSaDao dao = null;
	public EmpSalaryService() {
		//实例化dao层对象
		dao = new EmployeeSaDao();
	}
	//薪水查询业务
	public String querySalary(EmployeeSalary es){
		
		
		//调用dao层方法传回结果集
		List<EmployeeSalary> list = dao.querySalary(es);
		//创建gson对象
		Gson gson = new Gson();
		//转换json字串并返回
		return gson.toJson(list);
	}

	// 修改
	public String update(EmployeeSalary es) {
		Map< String, Object> map = new HashMap<>();
		int rows = -1;
		//检查无薪水记录的雇员
		if(es.getEsId()==0) {//无薪水记录
			//插入记录
			rows = dao.add(es);
			
		}else //否则执行修改
			rows = dao.update(es);
		if(rows>0) {
			map.put("flag", true);
			map.put("msg", "薪资修改成功");
		}else {
			map.put("flag", false);
			map.put("msg", "薪资修改失败,请联系管理员");
		}
		// 创建gson对象
		Gson gson = new Gson();
		// 转换json字串并返回
		return gson.toJson(map);
	}
	
}
