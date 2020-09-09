package com.bbo.hrsys.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bbo.hrsys.po.User;
import com.bbo.hrsys.util.*;

public class UserDao {
	//登陆功能
	public User queryLogin(String uname,String upass) {
		User us = null;
		//准备数据容器和sql语句
		String sql = "select * from user where username = ? and password = ? and isused = 1";
		//声明并实例化DButil对象
		DBUtil dbu = new DBUtil();
		//调用DBUtil对象中的相应方法，来执行sql语句并接受结果
		ResultSet rs = dbu.query(sql,uname,upass);
		//处理结果
		try {
			if(rs.next()) {
				us = new User();
				us.setUser_id(rs.getInt("user_id"));
				us.setUsername(rs.getString("username"));
				us.setPassword(rs.getString("password"));
				us.setIsused(rs.getInt("isused"));
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		//调用DBUtil对象中的关闭方法，对数据库连接资源进行释放
		dbu.close();
		//返回User对象
		return us;
	}
	//查询用户的方法，能查询全部，以及模糊查询
	public List<User> queryUser(User us){
		//准备容器和sql语句
		List<User> list = new ArrayList<>();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select * from user where 1=1 ");
		if(us!=null && us.getUsername()!=null) {
			sqlBuffer.append(" and username like '%");
			sqlBuffer.append(us.getUsername());
			sqlBuffer.append("%' ");
		}
		if(us!=null && us.getIsused()!=0) {
			sqlBuffer.append(" and isused=");
			sqlBuffer.append(us.getIsused());
		}
		if(us!=null && us.getUser_id()!=0) {
			sqlBuffer.append(" and user_id=");
			
			sqlBuffer.append(us.getUser_id());
		}
		//实例化DBUtil对象
		DBUtil dbu = new DBUtil();
		//调用DBUtil对象中相应的方法，来执行sql语句并接收结果
		ResultSet rs = dbu.query(sqlBuffer.toString());
		//处理结果
		try {
			while(rs.next()) {
				User ustemp = new User();
				ustemp.setUser_id(rs.getInt("user_id"));
				ustemp.setUsername(rs.getString("username"));
				ustemp.setPassword(rs.getString("password"));
				ustemp.setIsused(rs.getInt("isused"));
				list.add(ustemp);
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		dbu.close();
		return list;
	}
	//修改用户信息
	public int update(User user) {
		int temp = -1;
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("update user set user_id=");
		strBuffer.append(user.getUser_id());
		if(user.getUsername()!=null) {
			strBuffer.append(String.format(" ,username='%s'",user.getUsername()));
		}
		if(user.getPassword()!=null) {
			strBuffer.append(String.format(" ,password='%s'",user.getPassword()));
		}
		if(user.getIsused()!=0) {
			strBuffer.append(String.format(" ,isused='%d'",user.getIsused()));
		}
		strBuffer.append(" where user_id=");
		strBuffer.append(user.getUser_id());
		//实例化DButil
		DBUtil dbu = new DBUtil();
		
		temp = dbu.update(strBuffer.toString());
		
		dbu.close();
		
		return temp;
	}
	//添加用户的方法
	public int add(User user) {
		//数据容器sql
		int temp = -1;
		String sql = "insert into user(username,password,isused) values(?,?,?)";
		//DBUtil实例化
		DBUtil dbu = new DBUtil();
		//执行sql
		temp = dbu.update(sql, user.getUsername(),user.getPassword(),user.getIsused());
		dbu.close();
		return temp;
	}
	//删除用户
	public int del(String uids) {
		int rows=-1;//受影响的行数
		//声明数据库操作对象
		DBUtil dbu = new DBUtil();
		//完成sql语句
		String sql = "delete from user where user_id in ("+uids+")";
		rows = dbu.update(sql);
		dbu.close();
		return rows;
	}
}
