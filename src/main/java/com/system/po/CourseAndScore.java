package com.system.po;

//Course的扩展类 包括课程信息和分数等属性
public class CourseAndScore extends Course{
	private Integer mark;//分数

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}
	
}
