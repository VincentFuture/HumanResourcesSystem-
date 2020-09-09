package com.bbo.hrsys.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bbo.hrsys.po.Dict;
import com.bbo.hrsys.util.DBUtil;

public class DictDao {
	//查询
	public List<Dict> query(Dict dict){
		//数据容器
		List<Dict> list = new ArrayList<>();
		DBUtil dbu = new DBUtil();
		StringBuffer sql = new StringBuffer("select dict_id, title, dict.dict_type_id,  typeName from dict " + 
				"join dicttype on dict.dict_type_id = dicttype.dict_type_id where 1=1 ");
		//条目id查询
		if(dict!=null && dict.getDct_id()!=0) {
			sql.append(" and dict.dict_id=");
			sql.append(dict.getDct_id());
		}
		//字典值模糊查询
		if(dict!=null && dict.getTitle()!=null && !"".equals(dict.getTitle())) {
			sql.append(" and title like '%");
			sql.append(dict.getTitle());
			sql.append("%'");
		}
		//字典类目id查询
		if(dict!=null && dict.getType_id()!=0) {
			sql.append(" and dict.dict_type_id=");
			sql.append(dict.getType_id());
		}
		
		
		//执行查询
		ResultSet rs = dbu.query(sql.toString());
		//封装数据
		try {
			while(rs.next()) {
				Dict temp = new Dict();
				temp.setDct_id(rs.getInt("dict_id"));
				temp.setTitle(rs.getString("title"));
				temp.setType_id(rs.getInt("dict_type_id"));
				temp.setType_name(rs.getString("typeName"));
				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		dbu.close();
		return list;
	}
	//删除
	public int del(String ids) {
		int rows = -1;
		DBUtil dbu = new DBUtil();
		String sql = "delete from dict where dict_id in ("+ids+")";
		rows = dbu.update(sql);
		dbu.close();
		return rows;
	}
	//修改
	public int update(Dict dict) {
		int rows = -1;
		DBUtil dbu = new DBUtil();
		StringBuffer sql = new StringBuffer("update dict set dict_id = "+dict.getDct_id());
		//修改字典值
		if(dict!=null && dict.getTitle()!=null&& !"".equals(dict.getTitle())) {
			sql.append(" ,title='");
			sql.append(dict.getTitle()+"'");
		}
		//修改字典类目
		/*if(dict != null && dict.getType_id()!=0) {
			sql.append(" ,dict_type_id=");
			sql.append(dict.getType_id());
		}*/
		sql.append(" where dict_id = "+dict.getDct_id());
		//执行修改
		rows = dbu.update(sql.toString());
		dbu.close();
		return rows;
		
	}
	//添加
	public int add(Dict dict) {
		int rows = -1;
		DBUtil dbu = new DBUtil();
		String sql = "insert into dict(title,dict_type_id) values(?,?)";
		rows = dbu.update(sql, dict.getTitle(),dict.getType_id());
		dbu.close();
		return rows;
	}
	public List<Dict> queryType() {
		List<Dict> dictTypeList = new ArrayList<>();
		DBUtil dbu = new DBUtil();
		String sql = "select * from dicttype";
		ResultSet rs = dbu.query(sql);
		//封装数据
		try {
			while(rs.next()) {
				Dict dictTypeTemp = new Dict();
				dictTypeTemp.setType_id(rs.getInt("dict_type_id"));
				dictTypeTemp.setType_name(rs.getString("typeName"));
				dictTypeList.add(dictTypeTemp);
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return dictTypeList;
	}
	
}
