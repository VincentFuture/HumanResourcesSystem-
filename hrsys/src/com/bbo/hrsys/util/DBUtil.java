package com.bbo.hrsys.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;



public class DBUtil {
	//声明属性变量
	private Connection conn;
	private PreparedStatement prep;
	
	//构造方法中：导入驱动，并且给conn赋值
	
	public DBUtil(){
		//0.读取配置文件
		Properties pp=new  Properties();
		//加载配置文件
		
		try{
			pp.load(DBUtil.class.getResourceAsStream("db.properties"));
			//1.加载驱动
			Class.forName(pp.getProperty("driver"));
			System.out.println("驱动加载成功");
			//2.创建链接
			conn=DriverManager.getConnection(pp.getProperty("url"),
				pp.getProperty("user"),
				pp.getProperty("pass"));
			System.out.println("链接数据库成功");
			
			
		}catch (IOException | ClassNotFoundException | SQLException e){
			e.printStackTrace();
			
		}
	}

	//执行增删改的方法
	public int update(String sql) {
		//1.建立接受结果的容器
		int temp=-1;
		try
		{
			//2.实例化执行sql语句的对象
			prep=conn.prepareStatement(sql);
			//3.执行sql语句并接收结果
			temp=prep.executeUpdate();
			
		}catch (SQLException e){
			// TODO Auto-generated cath block
			e.printStackTrace();
		}
		//4.抛出结果
		return temp;
		
	}
	//动态参数的增删改方法   object... arr-->可变参数
	public int update(String sql,Object ... arr){
		//1.定义接收结果的容器
		int temp=-1;
		try{
			//2.实例化执行sql语句的对象
			prep=conn.prepareStatement(sql);
			//3. 给问号赋值
			for (int i=0;i<arr.length;i++){
				prep.setObject(i+1,arr[i]);
				
			}
			//输出sql
			System.out.println("prep>>"+prep);
		
			//4.执行sql语句并接收结果
			temp=prep.executeUpdate();
		}catch (SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//5.抛出结果
		return temp;
		
	}
	
	//执行查询的方法
	public ResultSet query(String sql){
		//1.定义接收结果的容器
		ResultSet rs=null;
		try {
			//2.实例化执行sql语句的对象
			prep=conn.prepareStatement(sql);
			//
			System.out.println("prep>>"+prep);
			//3.执行并接收结果
			rs=prep.executeQuery();
			
		}catch (SQLException e){
			//TODO Auto-gebnerated catch block
			e.printStackTrace();
			
		}
		//4.抛出结果
		return rs;
		
	}
	//测试方法
	
	
	
	
	
	
	//绑定动态参数的查询方法
	public ResultSet  query (String sql,Object ... arr){
		//1.容器
		ResultSet rs=null;
		try{
			//2.实例化
			prep=conn.prepareStatement(sql);
			//3.给问号赋值
			for(int i=0;i<arr.length;i++){
				prep.setObject(i+1,arr[i]);
				
			}
			System.out.println("prep>>"+prep);
			//4.执行
			rs=prep.executeQuery();
			
		}catch (SQLException e){
			e.printStackTrace();
		}
		//5.抛出结果
		return rs;
		
	}
	//关闭方法
	public void close(){
		//先关闭prep
		try{
			if(prep!=null && !prep.isClosed()){
				prep.close();
			}
			//在关闭conn
			if (conn!=null&&!conn.isClosed()){
				conn.close();
			}
			
		}catch (SQLException  e){
			//TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
}


