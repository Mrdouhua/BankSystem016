package cn.edu.cqupt.core;

import java.util.List;

public class PageParam<T> {
	
	private int currPage = 1;  //当前页
	
	private int totalPage = 1; //总页
	
	private int rowCount = 1;  //总记录数
	
	private int pageSize = 10;  //页大小
	
	private List<T> data;  //数据

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		if(rowCount <= 0) {
			rowCount = 1;
		}
		int totalPage = rowCount / pageSize;
		if(rowCount % pageSize > 0) {
			totalPage ++;
		}
		setTotalPage(totalPage);
		this.rowCount = rowCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	
}