package com.bbo.hrsys.service;

import java.util.List;

import com.bbo.hrsys.dao.AttendanceDao;
import com.bbo.hrsys.po.Attendance;
import com.google.gson.Gson;

public class AttendanceService {
	private AttendanceDao dao;
	public AttendanceService() {
		dao = new AttendanceDao();
	}
	//用于绘制图表的查询方法
	public String searchDataForEchars(int eid) {
		Gson gson = new Gson();
		return gson.toJson(dao.searchForEchars(eid));
	}
	//记录查询方法
	public String query(Attendance att) {
		List<Attendance> list = dao.query(att);
		Gson gson = new Gson();
		return gson.toJson(list);
	}
	/**
	 * 自动生成签到记录
	 * @param empId
	 * @param m
	 * @return
	 */
	public String add(String empId,String m) {
		List<Attendance> list = dao.add(empId,m);
		Gson gson = new Gson();
		return gson.toJson(list);
	}
}
