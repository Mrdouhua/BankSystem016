package cn.edu.cqupt.model;

import java.math.BigDecimal;
import java.util.Date;

public class Creditcard {
    private String caccountid;

    private String password;

    private String creditlevel;

    private BigDecimal capital;

    private String status;

    private Date creattime;

    private String cardtype;

    private String city;

    private String province;

    private String moneytype;

    private String idnumber;

    public String getCaccountid() {
        return caccountid;
    }

    public void setCaccountid(String caccountid) {
        this.caccountid = caccountid == null ? null : caccountid.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getCreditlevel() {
        return creditlevel;
    }

    public void setCreditlevel(String creditlevel) {
        this.creditlevel = creditlevel == null ? null : creditlevel.trim();
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreattime() {
        return creattime;
    }

    public void setCreattime(Date creattime) {
        this.creattime = creattime;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype == null ? null : cardtype.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getMoneytype() {
        return moneytype;
    }

    public void setMoneytype(String moneytype) {
        this.moneytype = moneytype == null ? null : moneytype.trim();
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber == null ? null : idnumber.trim();
    }
}