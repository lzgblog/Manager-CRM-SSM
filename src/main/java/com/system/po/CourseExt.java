package com.system.po;

import java.util.List;

public class CourseExt {
	//总页数
	private int pageCount;
	//当前页
	private Integer currentPage;
	
	private List<CourseCustom> list;
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
	public List<CourseCustom> getList() {
		return list;
	}
	public void setList(List<CourseCustom> list) {
		this.list = list;
	}
	
		
}
