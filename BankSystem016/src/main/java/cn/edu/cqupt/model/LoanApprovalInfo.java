package cn.edu.cqupt.model;

import java.math.BigDecimal;

/**
 * 贷款申请者信息实体
 * 
 * @author ZhangGuanQun
 *
 */
public class LoanApprovalInfo {
	private String name;
	private String idNumber;
	private String loanType;
	private BigDecimal capital;
	private String cardID;
	private String status;
	private String proveFile;
	

	public String getProveFile() {
		return proveFile;
	}

	public void setProveFile(String proveFile) {
		this.proveFile = proveFile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber == null ? null : idNumber.trim();
	}
	
	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType == null ? null : loanType.trim();
	}
	public BigDecimal getCapital() {
		return capital;
	}

	public void setCapital(BigDecimal capital) {
		this.capital = capital;
	}

	public String getCardID() {
		return cardID;
	}

	public void setCardID(String cardID) {
		this.cardID = cardID == null ? null : cardID.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}
	
	@Override
	public String toString() {
		return "LoanApprovalInfo [name=" + name + ", idNumber=" + idNumber + ", loanType=" + loanType + ", capital="
				+ capital + ", cardID=" + cardID + ", status=" + status + "]";
	}
}
