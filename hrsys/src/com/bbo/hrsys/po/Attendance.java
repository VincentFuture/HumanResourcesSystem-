package com.bbo.hrsys.po;

public class Attendance {
	private int attend_id;//考勤记录id
	private String attend_date;//打卡日期
	private int isLeave;//是否请假  1:是 2:否
	private String clock_in; //上班打卡时间
	private String clock_out; // 下班打卡时间
	private Employee empid;//雇员id
	
	public Employee getEmpid() {
		return empid;
	}
	public void setEmpid(Employee empid) {
		this.empid = empid;
	}
	public int getAttend_id() {
		return attend_id;
	}
	public void setAttend_id(int attend_id) {
		this.attend_id = attend_id;
	}
	public String getAttend_date() {
		return attend_date;
	}
	public void setAttend_date(String attend_date) {
		this.attend_date = attend_date;
	}
	public int getIsLeave() {
		return isLeave;
	}
	public void setIsLeave(int isLeave) {
		this.isLeave = isLeave;
	}
	public String getClock_in() {
		return clock_in;
	}
	public void setClock_in(String clock_in) {
		this.clock_in = clock_in;
	}
	public String getClock_out() {
		return clock_out;
	}
	public void setClock_out(String clock_out) {
		this.clock_out = clock_out;
	}
	
	
}
