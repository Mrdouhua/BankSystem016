package cn.edu.cqupt.model;

import java.util.Date;

public class Loginuser {
    private String userid;

    private String loginname;

    private String password;

    private String status;

    private Date creattime;

    private Date edittime;

    private String usertype;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname == null ? null : loginname.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
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

    public Date getEdittime() {
        return edittime;
    }

    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype == null ? null : usertype.trim();
    }
    
    @Override
    public String toString() {
    	return "LoginUser:[userid:"+userid+",loginname:"+loginname+
    			",password:"+password+",status:"+status+",creattime:"+
    			creattime+",edittime:"+edittime+",usertype:"+usertype+"]";
    }
}