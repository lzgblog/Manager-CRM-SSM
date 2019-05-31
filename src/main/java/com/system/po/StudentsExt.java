package com.system.po;

import java.util.List;

public class StudentsExt {
	//总页数
	private int pageCount;
	//当前页
	private Integer currentPage;
	
	private List<StudentInfo> list;//Student扩展类  包括院系名称
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
	public List<StudentInfo> getList() {
		return list;
	}
	public void setList(List<StudentInfo> list) {
		this.list = list;
	}
	
		
}
