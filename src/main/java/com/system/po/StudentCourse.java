package com.system.po;

//Course扩展类       包括课程信息  和教师表中的教师名称  属性
public class StudentCourse extends Course {
	private String username;//教师姓名属性

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
