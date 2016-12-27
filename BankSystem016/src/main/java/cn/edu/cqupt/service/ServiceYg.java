package cn.edu.cqupt.service;

import java.awt.List;
import java.util.ArrayList;

import cn.edu.cqupt.model.Interesttable;
import cn.edu.cqupt.model.Loginuser;

/*
 * 杨刚专用service
 */
public interface ServiceYg {
	public ArrayList<Loginuser> searchbyLoginName(String username);
	public int insert(Loginuser loginuser);
	public int updatepassword(String LoginName,String password,String newpassword);
	public int isHaveUser(String username);
	public int interest_update(ArrayList<Interesttable> interesttables);
	public int updateLoanInterest(ArrayList<Interesttable> interesttables);
	public int updateLoginuser(String loginname,String userid,String status,String usertype);
	//删除角色用户
	int deleteLoginuser(String userid);
	//分页显示用户角色
	public ArrayList<Loginuser> show_Loginuser(int offset,int size);
	public int selectsystemuserCounts();
	public Loginuser selectLoginuserByuserid(String userid);
	public int updateLoginuserByuser(Loginuser user);
}
