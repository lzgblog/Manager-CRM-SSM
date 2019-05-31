package com.system.po;

import java.util.List;

public class DataResult{
	
	//总页数
	private int pageCount;
	//当前页
	private Integer currentPage;
	
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

	private Integer total;//数量
	
	private List rows;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "DataResult [pageCount=" + pageCount + ", currentPage=" + currentPage + ", total=" + total + ", rows="
				+ rows + "]";
	}

	
}
