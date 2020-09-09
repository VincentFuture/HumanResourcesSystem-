package com.bbo.hrsys.po;

//雇员薪水实体
public class EmployeeSalary {
	private int esId;//记录id
	private double sStandard;//雇员薪水标准
	private Employee emp;//雇员信息
	
	public Employee getEmp() {
		return emp;
	}
	public int getEsId() {
		return esId;
	}
	public void setEsId(int esId) {
		this.esId = esId;
	}
	public double getsStandard() {
		return sStandard;
	}
	public void setsStandard(double sStandard) {
		this.sStandard = sStandard;
	}
	public void setEmp(Employee emp) {
		this.emp = emp;
	}
	
	
}
