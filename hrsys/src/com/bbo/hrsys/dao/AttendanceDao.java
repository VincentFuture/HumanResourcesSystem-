package com.bbo.hrsys.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bbo.hrsys.po.Attendance;
import com.bbo.hrsys.po.Employee;
import com.bbo.hrsys.util.DBUtil;
import com.bbo.hrsys.util.DateUtil;

public class AttendanceDao {
	//数据图表查询
	public Map<String,List<String>> searchForEchars(int eid){
		Map<String , List<String>> map = new HashMap<>();
		List<String> date_list = new ArrayList<>();//存放日期
		List<String> attend_list = new ArrayList<>();//存放上班打卡时间
		List<String> out_list = new ArrayList<>();//下班打卡时间
		List<String> leave_list = new ArrayList<>();//请假 
		//组合容器
		map.put("data", date_list);
		map.put("intime", attend_list);
		map.put("outtime", out_list);
		map.put("isleave",leave_list);
		String sql = "select * from attendance where employee_id =?";
		
		DBUtil dbu = new DBUtil();
		
		ResultSet rs = dbu.query(sql,eid);
		//封装数据
		try {
			while(rs.next()) {
				date_list.add(rs.getString("attendance_date"));
				attend_list.add(rs.getString("clock_in"));
				out_list.add(rs.getString("clock_out"));
				leave_list.add(Integer.toString(rs.getInt("isLeave")));
			}
		}catch (Exception e) {
			e.printStackTrace();	
		}
		dbu.close();
		return map;
	}
	//用于记录查询的方法
	public List<Attendance> query(Attendance att){
		List<Attendance> list = new ArrayList<>();
		DBUtil dbu = new DBUtil();
		StringBuffer sql = new StringBuffer("select attendance.*,employee.name ename "
				+ "from attendance,employee "
				+ "where attendance.employee_id=employee.employee_id ");
		//条件查询attendance_id, attendance_date, employee_id, isLeave, clock_in, clock_out
		if(att.getIsLeave()!=0) {
			sql.append(" and attendance.isLeave='"+att.getIsLeave()+"'");
		}
		if(att.getAttend_date()!=null && !att.getAttend_date().equals("")) {
			sql.append(" and attendance.attendance_date='"+att.getAttend_date()+"'");
		}
		if(att.getEmpid()!=null&&att.getEmpid().getName()!=null && !att.getEmpid().getName().equals("")) {
			sql.append(" and employee.name='"+att.getEmpid().getName()+"'");
		}
		//执行查询
		ResultSet rs = dbu.query(sql.toString());
		//封装数据
		try {
			while(rs.next()) {
				Attendance atttemp = new Attendance();
				atttemp.setAttend_id(rs.getInt("attendance_id"));
				Employee emptemp = new Employee();
				emptemp.setEmployee_id(rs.getInt("employee_id"));
				emptemp.setName(rs.getString("ename"));
				atttemp.setEmpid(emptemp);
				atttemp.setAttend_date(rs.getString("attendance_date"));
				atttemp.setIsLeave(rs.getInt("isLeave"));
				atttemp.setClock_in(rs.getString("clock_in"));
				atttemp.setClock_out(rs.getString("clock_out"));
				list.add(atttemp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbu.close();
		return list;
	}
	//生成打卡记录
	public List<Attendance> add(String empId,String m) {
		String ename=null;
		DBUtil dbu = new DBUtil();
		List<Attendance> list = new ArrayList<>();
		SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd kk:mm");
		String sql = "INSERT INTO attendance (attendance_date,"
				+ " employee_id, "
				+ "isLeave,"
				+ "clock_in, "
				+ "clock_out) "
				+ "VALUES (?, '"+empId+"',?,?,?) ";
		//获取雇员姓名
		ResultSet rs =dbu.query("select name from employee where employee_id=?",empId);
		try {
			if(rs.next()) 
				ename = rs.getString("name");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String attendance_date = null;
		String isLeave = null;
		String clock_in = "";
		String clock_out = "";
		int month = Integer.parseInt(m);
		GregorianCalendar gc = new GregorianCalendar(2020, month-1,1) ;
		int actruallyMaxDay = gc.getActualMaximum(Calendar.DAY_OF_MONTH);	//获取当月最大天数	//获取当月最大天数
		for(int day =1;day<actruallyMaxDay+1;day++) {
			Date in = DateUtil.randomDate("2020-"+month+"-"+day+" 07:20", "2020-"+month+"-"+day+" 08:50");
			Date out = DateUtil.randomDate("2020-"+month+"-"+day+" 17:00", "2020-"+month+"-"+day+" 21:00");
			
			if(in!=null&&out!=null) {
				clock_in = formatTime.format(in).substring(11);
				clock_out = formatTime.format(out).substring(11);
			}
			isLeave = (int)(Math.random()*30)>24?"1":"2";
			attendance_date = "2020-"+month+"-"+day;
			if(isLeave!=null&&attendance_date!=null) {
//				System.out.println("日期："+attendance_date
//						+"\t上班："+clock_in
//						+"\t下班："+clock_out
//						+"\t是否请假："+isLeave);
				if(dbu.update(sql, attendance_date,isLeave,clock_in,clock_out)>0) {
					//重新封装数据
					Attendance tempA = new Attendance();
					tempA.setAttend_date(attendance_date);//日期
					tempA.setClock_in(clock_in);//上班时间
					tempA.setClock_out(clock_out);//下班时间
					Employee tempEmpId = new Employee();
					tempEmpId.setEmployee_id(Integer.parseInt(empId));
					if(ename!=null)
						tempEmpId.setName(ename);
					tempA.setEmpid(tempEmpId);
					tempA.setIsLeave(Integer.parseInt(isLeave));
					list.add(tempA);
				}
			}
				
		}
		dbu.close();
		return list;
	}
}
