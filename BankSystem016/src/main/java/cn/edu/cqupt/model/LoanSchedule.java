package cn.edu.cqupt.model;

import java.util.Date;

/**
 * 贷款进度实体
 * 
 * @author ZhangGuanQun
 *
 */
public class LoanSchedule {
	private String name;
	private String loanType;
	private String creatTime;
	private String status;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name  == null ? null : name.trim();
	}
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public String getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "LoanSchedule [name=" + name + ", loanType=" + loanType + ", creatTime=" + creatTime + ", status="
				+ status + "]";
	}
}
