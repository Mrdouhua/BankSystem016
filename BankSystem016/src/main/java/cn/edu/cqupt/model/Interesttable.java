package cn.edu.cqupt.model;

public class Interesttable {
	private Integer interestid;

    private String deposittype;

    private String cycle;

    private String time;

    private Double interestvalue;

    public Integer getInterestid() {
        return interestid;
    }

    public void setInterestid(Integer interestid) {
        this.interestid = interestid;
    }

    public String getDeposittype() {
        return deposittype;
    }

    public void setDeposittype(String deposittype) {
        this.deposittype = deposittype == null ? null : deposittype.trim();
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle == null ? null : cycle.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public Double getInterestvalue() {
        return interestvalue;
    }

    public void setInterestvalue(Double interestvalue) {
        this.interestvalue = interestvalue;
    }
    
    @Override
	public String toString() {
		return "Interesttable [interestid=" + interestid + ", deposittype=" + deposittype + ", cycle=" + cycle
				+ ", time=" + time + ", interestvalue=" + interestvalue + "]";
	}
}