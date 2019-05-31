package com.system.po;

import java.util.List;

public class TeacherExt {
	//总页数
	private int pageCount;
	//当前页
	private Integer currentPage;
	
	private List<TeacherCustom> list;
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
	public List<TeacherCustom> getList() {
		return list;
	}
	public void setList(List<TeacherCustom> list) {
		this.list = list;
	}
	
		
}
