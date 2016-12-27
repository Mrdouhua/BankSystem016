package cn.edu.cqupt.model;

import java.math.BigDecimal;
import java.util.Date;

public class Fexpensecalendar {
    private Integer fecid;

    private String faccountid;

    private BigDecimal capital;

    private Date creattime;

    public Integer getFecid() {
        return fecid;
    }

    public void setFecid(Integer fecid) {
        this.fecid = fecid;
    }

    public String getFaccountid() {
        return faccountid;
    }

    public void setFaccountid(String faccountid) {
        this.faccountid = faccountid == null ? null : faccountid.trim();
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