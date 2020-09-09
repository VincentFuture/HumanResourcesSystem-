package com.bbo.hrsys.po;

public class Employee {
    private int employee_id; //雇员id
    private String name; //雇员姓名
    private  int age; //雇员年龄
    private Dict gender;//性别  
    private Dict city;	//城市
    private String avator; //头像
    private User user_id;  //用户id
    private Department dept_id; //部门id


    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    public Department getDept_id() {
        return dept_id;
    }

    public void setDept_id(Department dept_id) {
        this.dept_id = dept_id;
    }

	public Dict getGender() {
		return gender;
	}

	public void setGender(Dict gender) {
		this.gender = gender;
	}

	public Dict getCity() {
		return city;
	}

	public void setCity(Dict city) {
		this.city = city;
	}


    
}
