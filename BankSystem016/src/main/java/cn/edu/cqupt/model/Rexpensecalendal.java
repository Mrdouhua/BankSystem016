package cn.edu.cqupt.model;

import java.math.BigDecimal;
import java.util.Date;

public class Rexpensecalendal {
    private Integer recid;

    private String raccountid;

    private BigDecimal capital;

    private Date creattime;

    public Integer getRecid() {
        return recid;
    }

    public void setRecid(Integer recid) {
        this.recid = recid;
    }

    public String getRaccountid() {
        return raccountid;
    }

    public void setRaccountid(String raccountid) {
        this.raccountid = raccountid == null ? null : raccountid.trim();
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