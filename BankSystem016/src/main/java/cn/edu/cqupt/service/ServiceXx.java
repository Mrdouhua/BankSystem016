package cn.edu.cqupt.service;

import java.util.ArrayList;
import java.util.HashMap;

import cn.edu.cqupt.model.Interesttable;
import cn.edu.cqupt.model.Rmbbusiness;
import cn.edu.cqupt.model.RmbbusinessShow;
import cn.edu.cqupt.model.Userinfo;

/*
 * 熊胜相专用service
 */
public interface ServiceXx {
	
	//通过身份证检查该用户是否存在于userinfo中
	Userinfo is_Have(String idnumber) throws Exception;
	//插入开户数据
	int insert(Rmbbusiness rmbbusiness);
	//查询有效区间为time的各类存款利率
	ArrayList<Interesttable> get_Interest(String time);
	//返回账户信息
	Rmbbusiness get_Rmbbusiness(String raccountid);
	//更新密码
	int updata_message(Rmbbusiness rmbbusiness);
	//用户转账
	boolean transfer(Rmbbusiness rmbbusiness, Rmbbusiness oppRmbbusiness);
	//显示账户信息列表
	ArrayList<RmbbusinessShow> show_Rmbbusiness(int offset,int size);
	//查询所有账户数目
	int selectaccountsnum();
	int updateRmbcard(Rmbbusiness card);
	
}
