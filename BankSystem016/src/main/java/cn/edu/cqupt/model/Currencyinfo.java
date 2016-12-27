package cn.edu.cqupt.model;

import java.util.Date;

public class Currencyinfo {
    private String ciid;

    private String currencygroup;

    private Double buyin;

    private Double sellout;

    private Double maxrate;

    private Double middlerate;

    private Double minrate;

    private Date creattime;

    public String getCiid() {
        return ciid;
    }

    public void setCiid(String ciid) {
        this.ciid = ciid == null ? null : ciid.trim();
    }

    public String getCurrencygroup() {
        return currencygroup;
    }

    public void setCurrencygroup(String currencygroup) {
        this.currencygroup = currencygroup == null ? null : currencygroup.trim();
    }

    public Double getBuyin() {
        return buyin;
    }

    public void setBuyin(Double buyin) {
        this.buyin = buyin;
    }

    public Double getSellout() {
        return sellout;
    }

    public void setSellout(Double sellout) {
        this.sellout = sellout;
    }

    public Double getMaxrate() {
        return maxrate;
    }

    public void setMaxrate(Double maxrate) {
        this.maxrate = maxrate;
    }

    public Double getMiddlerate() {
        return middlerate;
    }

    public void setMiddlerate(Double middlerate) {
        this.middlerate = middlerate;
    }

    public Double getMinrate() {
        return minrate;
    }

    public void setMinrate(Double minrate) {
        this.minrate = minrate;
    }

    public Date getCreattime() {
        return creattime;
    }

    public void setCreattime(Date creattime) {
        this.creattime = creattime;
    }
}