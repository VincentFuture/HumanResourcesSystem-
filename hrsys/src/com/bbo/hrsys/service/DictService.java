package com.bbo.hrsys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bbo.hrsys.dao.DictDao;
import com.bbo.hrsys.dao.EmployeeDao;
import com.bbo.hrsys.po.Dict;
import com.google.gson.Gson;

public class DictService {
	private DictDao dao = null;
	public DictService() {
		dao = new DictDao();
	}
	
	//增加
	public String add(Dict dict) {
		Map<String,Object> map = new HashMap<>();
		int rows = -1;
		rows = dao.add(dict);
		if(rows>0) {
			map.put("flag", true);
			map.put("msg", "字典添加成功");
		}else {
			map.put("flag", false);
			map.put("msg", "字典添加失败");
		}
		
		Gson gson = new Gson();
		return gson.toJson(map);
	}
	//删除
	public String del(String ids) {
		Map<String,Object> map = new HashMap<>();
		int rows = -1;
		
		//检查字典值是否被使用
		EmployeeDao empdao = new EmployeeDao();
		boolean check = empdao.checkQuery(ids);
		if(check) {
			//若返回true  允许操作
			rows = dao.del(ids);//dictdao执行删除操作
			if(rows>0) {
				map.put("flag", true);
				map.put("msg", "字典删除成功");
			}else {
				map.put("flag", false);
				map.put("msg", "字典删除失败");
			}
		}else {
			map.put("flag", false);
			map.put("msg", "该字典无法删除,请联系管理员");
		}
		
		Gson gson = new Gson();
		return gson.toJson(map);
	}
	//修改
	public String update(Dict dict) {
		Map<String,Object> map = new HashMap<>();
		int rows = -1;
		rows = dao.update(dict);
		if(rows>0) {
			map.put("flag", true);
			map.put("msg", "字典修改成功");
		}else {
			map.put("flag", false);
			map.put("msg", "字典修改失败");
		}
		
		Gson gson = new Gson();
		return gson.toJson(map);
	}
	//查询
	public String query(Dict dict) {
		List<Dict> list = dao.query(dict);
		Gson gson = new Gson();
		return gson.toJson(list);
	}

	public String queryType() {
		List<Dict> list = dao.queryType();
		Gson gson = new Gson();
		return gson.toJson(list);
	}
	
}
