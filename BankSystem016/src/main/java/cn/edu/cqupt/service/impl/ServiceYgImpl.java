package cn.edu.cqupt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.edu.cqupt.dao.InteresttableMapper;
import cn.edu.cqupt.dao.LoginuserMapper;
import cn.edu.cqupt.model.Interesttable;
import cn.edu.cqupt.model.Loginuser;
import cn.edu.cqupt.model.RmbbusinessShow;
import cn.edu.cqupt.service.ServiceYg;

@Service("serviceYg")
public class ServiceYgImpl implements ServiceYg {
	
	@Resource
	private LoginuserMapper loginuserMapper ;
	
	@Resource
	private InteresttableMapper interesttableMapper;
	
	public LoginuserMapper getLoginuserMapper() {
		return loginuserMapper;
	}
	public void setLoginuserMapper(LoginuserMapper loginuserMapper) {
		this.loginuserMapper = loginuserMapper;
	}
	
	
	public ArrayList<Loginuser> searchbyLoginName(String username){
		ArrayList<Loginuser> loginusers=loginuserMapper.getLoginuserByname(username);
		for (Loginuser e:loginusers){
			if (e.getStatus().equals("1")){
				e.setStatus("正常");
				continue;
			}
			if (e.getStatus().equals("0")){
				e.setStatus("账号冻结");
				continue;
			}		
		}
		for (Loginuser e:loginusers){
			String type=e.getUsertype();
			if (type.equals("A")){
				e.setUsertype("超级管理员");
				continue;
			}
			if (type.equals("B")){
				e.setUsertype("行长");
				continue;
			}
			if (type.equals("C")){
				e.setUsertype("数据操作员");
				continue;
			}
			if (type.equals("D")){
				e.setUsertype("出纳");
				continue;
			}
			if (type.equals("E")){
				e.setUsertype("贷款发放员");
				continue;
			}
			if (type.equals("F")){
				e.setUsertype("贷款审批员");
				continue;
			}
			if (type.equals("G")){
				e.setUsertype("信用卡审批员");
				continue;
			}
			if (type.equals("H")){
				e.setUsertype("信用卡客服工作人员");
				continue;
			}
		}
		return loginusers;
	}
	
	//修改角色信息
	 public int updateLoginuser(String loginname,String userid,String status,String usertype){
			if(loginuserMapper.selectByPrimaryKey(userid)!=null){
				Loginuser loginuser=loginuserMapper.selectByPrimaryKey(userid);
				loginuser.setLoginname(loginname);
				loginuser.setStatus(status);
				loginuser.setUsertype(usertype);
				if(loginuserMapper.updateByPrimaryKey(loginuser)==1){
					return 1;
					}else{
						return 0;
					}
			}else{
				return 0;
			}
		}
	//删除角色用户
	 public int deleteLoginuser(String userid){
		 return loginuserMapper.deleteByuserid(userid);
		
	 }
	 
	 
	//显示账户信息
		public ArrayList<Loginuser> show_Loginuser(int offset,int size){
			HashMap< String, Integer> param=new HashMap<>();
			param.put("start", offset);
			param.put("end", offset+size);
			ArrayList<Loginuser> Loginusers =loginuserMapper.showLoginuserByLimit(param) ;
			for (Loginuser e:Loginusers){
				if (e.getStatus().equals("1")){
					e.setStatus("正常");
					continue;
				}
				if (e.getStatus().equals("0")){
					e.setStatus("账号冻结");
					continue;
				}		
			}
			
			return  Loginusers;
		}
	
	
	//插入新角色用户
	public int insert(Loginuser loginuser) {
		return loginuserMapper.insert(loginuser);
	}
	
	
	//修改密码
	 public int updatepassword(String LoginName,String password,String newpassword){
		if(loginuserMapper.isHaveUser(LoginName)!=null){
			Loginuser loginuser=loginuserMapper.isHaveUser(LoginName);
			if(loginuser.getPassword().equals(password)){
				loginuser.setPassword(newpassword);
				loginuserMapper.updateByPrimaryKeySelective(loginuser);
				return 1;
				}else{
					return 0;
				}
		}else{
			return 0;
		}
	}
	 
	 
	 //查看用户名是否存在
	public int isHaveUser(String LoginName) {
		// TODO Auto-generated method stub
		 if(loginuserMapper.isHaveUser(LoginName)!=null){
			 System.out.println("该用户名已存在");
			 return 1;
		 }else{
			 System.out.println("该用户名可用");
			 return 0;
		 }
	}
	 
	 //储蓄利率修改
	 public int interest_update(ArrayList<Interesttable> interesttables){
			int i=0;
			Interesttable ita[]=new Interesttable[10];
			//取出Interesttable对象依次放在ita中
			Iterator<Interesttable>iter=interesttables.iterator();
			while(iter.hasNext()){
				ita[i]=iter.next();
				ita[i].setInterestvalue(4.06);
				interesttableMapper.updatedDepositInterest(ita[i]);
				i++;
			}
		 System.out.println("完成储蓄利率修改");
		 return 0;
	 }
	 
	 //贷款利率修改
	 public int updateLoanInterest(ArrayList<Interesttable> interesttables){
			int i=0;
			Interesttable ita[]=new Interesttable[3];
			//取出Interesttable对象依次放在ita中
			Iterator<Interesttable>iter=interesttables.iterator();
			while(iter.hasNext()){
				ita[i]=iter.next();
				ita[i].setInterestvalue(3.55);
				interesttableMapper.updatedLoanInterest(ita[i]);
				i++;
			}

		 System.out.println("完成贷款利率修改");
		 return 0;
	 }
	@Override
	public int selectsystemuserCounts() {
		return loginuserMapper.selectsystemuserCounts();
	}
	@Override
	public Loginuser selectLoginuserByuserid(String userid) {
		return loginuserMapper.selectByPrimaryKey(userid);
	}
	@Override
	public int updateLoginuserByuser(Loginuser user) {
		return loginuserMapper.updateByPrimaryKeySelective(user);
	}
}
