package com.bbo.hrsys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bbo.hrsys.dao.UserDao;
import com.bbo.hrsys.po.User;
import com.google.gson.Gson;

public class UserService {
	//声明成员变量
	private UserDao dao ;
	//在构造方法中实例化dao对象
	public UserService() {	
		dao = new UserDao();
	}
	//登陆的功能  返回以个JSon格式的字符串
	/*
	 * JSON格式
	 * 一个：{"属性名":"值"，"属性名":"值","属性名":"值"}
	 * 一组：[{},{},{}]
	 */
	public String login(String uname,String upass) {
		//调用dao中相应的方法  并接受执行结果
		User us = dao.queryLogin(uname, upass);
		//将结果转换成JSON格式的字符串
		//声明并实例化一个Gson对象
		Gson gson = new Gson();
		//调用对象中相应的放法进行转换
		String strJSON = gson.toJson(us);
		//抛出字符串
		return strJSON;
	}
	//条件查询
	public String search(User user) {
		List<User> list = dao.queryUser(user);
		Gson gson = new Gson();
		return gson.toJson(list);
	}
	//修改用户
	public String update(User user) {
		int temp = dao.update(user);
		Map<String , Object> map = new HashMap<>();
		if(temp > 0) {
			map.put("flag",true);
			map.put("msg", "操作成功");
		}else {
			map.put("flag",false);
			map.put("msg", "操作失败或无权限，请联系管理员");
		}
		Gson gson = new Gson();
		return gson.toJson(map);
	}
	//添加用户
	public String add(User user) {
		
		int temp = dao.add(user);
		Map<String,Object> map = new HashMap<>();
		if(temp > 0) {
			map.put("flag", true);
			map.put("msg", "添加成功");
		}else {
			map.put("flag", false);
			map.put("msg", "添加失败,请检查用户名或联系管理员");
		}
		Gson gson = new Gson();
		return gson.toJson(map);
	}
	//删除用户
	public String del(String uids) {
		int rows = dao.del(uids);
		//声明返回数据封装容器
		Map<String,Object> map = new HashMap<>();
		if(rows>0) {
			map.put("flag", true);
			map.put("msg","用户已删除");
		}else if(rows==-2){
			map.put("flag",false);
			map.put("msg","删除失败,删除的用户包含超级用户");
		}else {
			map.put("flag",false);
			map.put("msg","用户删除失败,请联系管理员");
		}
		//传回json格式字串
		Gson gson = new Gson();
		return gson.toJson(map);
	}
	
}
