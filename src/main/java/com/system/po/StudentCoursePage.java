package com.system.po;

import java.util.List;

//学生课程信息  分页信息
public class StudentCoursePage {

	private List<StudentCourse> listCourse;//课程扩展类  包括课程信息和教师姓名
	
	//总页数
	private int pageCount;
	//当前页
	private Integer currentPage;
	public List<StudentCourse> getListCourse() {
		return listCourse;
	}
	public void setListCourse(List<StudentCourse> listCourse) {
		this.listCourse = listCourse;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	
	
}
