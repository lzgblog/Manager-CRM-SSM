package com.system.po;
//学生的扩展类  包括该学生的分数 课程id
public class Score extends Student{

	private Integer mark;//分数
	private Integer courseid;//课程id
	public Integer getMark() {
		return mark;
	}
	public void setMark(Integer mark) {
		this.mark = mark;
	}
	public Integer getCourseid() {
		return courseid;
	}
	public void setCourseid(Integer courseid) {
		this.courseid = courseid;
	}
	
}
