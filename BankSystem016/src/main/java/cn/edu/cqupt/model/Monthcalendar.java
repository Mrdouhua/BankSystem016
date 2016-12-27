package cn.edu.cqupt.model;

import java.math.BigDecimal;
import java.util.Date;

public class Monthcalendar {
    private Integer mid;

    private String caccountid;

    private String expensetype;

    private String gno;

    private BigDecimal capital;

    private Date creattime;

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getCaccountid() {
        return caccountid;
    }

    public void setCaccountid(String caccountid) {
        this.caccountid = caccountid == null ? null : caccountid.trim();
    }

    public String getExpensetype() {
        return expensetype;
    }

    public void setExpensetype(String expensetype) {
        this.expensetype = expensetype == null ? null : expensetype.trim();
    }

    public String getGno() {
        return gno;
    }

    public void setGno(String gno) {
        this.gno = gno == null ? null : gno.trim();
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public Date getCreattime() {
        return creattime;
    }

    public void setCreattime(Date creattime) {
        this.creattime = creattime;
    }
}