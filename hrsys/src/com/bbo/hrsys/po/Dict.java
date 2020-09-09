package com.bbo.hrsys.po;

public class Dict {
	private int dct_id ;//字典记录id
	private String title ;//条目标题(内容)
	private int type_id;//条目id;
	private String type_name;
	
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public int getDct_id() {
		return dct_id;
	}
	public void setDct_id(int dct_id) {
		this.dct_id = dct_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getType_id() {
		return type_id;
	}
	public void setType_id(int type_id) {
		this.type_id = type_id;
	}
	
}
