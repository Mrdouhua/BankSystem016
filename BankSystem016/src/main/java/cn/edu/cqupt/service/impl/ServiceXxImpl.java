package cn.edu.cqupt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.edu.cqupt.dao.InteresttableMapper;
import cn.edu.cqupt.dao.RmbbusinessMapper;
import cn.edu.cqupt.dao.UserinfoMapper;
import cn.edu.cqupt.model.Interesttable;
import cn.edu.cqupt.model.Rmbbusiness;
import cn.edu.cqupt.model.RmbbusinessShow;
import cn.edu.cqupt.model.Userinfo;
import cn.edu.cqupt.service.ServiceXx;

@Service("serviceXx")
public class ServiceXxImpl implements ServiceXx {
	
	//加载相应been映射实例
	@Resource
	private UserinfoMapper userinfoMapper;
	@Resource
	private RmbbusinessMapper rmbbusinessMapper;
	@Resource
	private InteresttableMapper interesttableMapper;
	
	//通过身份证检查该用户是否存在于userinfo中,存在的话获取该userinfo实例
	public Userinfo is_Have(String idnumber) throws Exception{
		Userinfo userinfo=null;
		userinfo = userinfoMapper.selectByPrimaryKey(idnumber);
		if(userinfo==null){
			throw new Exception("用户不存在");
		}
		return userinfo;
	}
	//插入开户数据
	public int insert(Rmbbusiness rmbbusiness){
		return rmbbusinessMapper.insertSelective(rmbbusiness);
	}
	//返回利率
	public ArrayList<Interesttable> get_Interest(String time) {
		ArrayList<Interesttable> interesttables = new ArrayList<Interesttable>();
		//加入整存整取类型利率DA
		String cycle = "D";
		String depositType = "DA";
		for (int i = 1; i <= 6; i++) {
			interesttables.add(interesttableMapper.select(depositType,cycle+i,time));
		}
		//加入零存整取、整存零取、存本取息类型利率DB
		String depositType1 = "DB";
		for (int i = 1; i <= 3; i++) {
			interesttables.add(interesttableMapper.select(depositType1,cycle+i,time));
		}
		//加入定活两便类型利率DC
		String depositType2 = "DC";
		interesttables.add(interesttableMapper.select(depositType2,"nu",time));
		return interesttables;
	}
	//返回账户信息
	public Rmbbusiness get_Rmbbusiness(String raccountid){
		Rmbbusiness rmbbusiness = rmbbusinessMapper.selectByPrimaryKey(raccountid);
		if(rmbbusiness==null){
			return null;
		}else{
			return rmbbusiness;
		}
	}
	//更新账户信息
	public int updata_message(Rmbbusiness rmbbusiness){
		System.out.println("进入到实现层了。。。。。");
		System.out.println(rmbbusiness.getPassword());
		System.out.println(rmbbusiness.getRaccountid());
		System.out.println(rmbbusiness.getStatus());
		System.out.println(rmbbusiness.getCapital());
		return rmbbusinessMapper.updateByPrimaryKeySelective(rmbbusiness);
	}
	//用户转账
	public boolean transfer(Rmbbusiness rmbbusiness, Rmbbusiness oppRmbbusiness){
		boolean flag = false;
		try {
			rmbbusinessMapper.updateByPrimaryKeySelective(rmbbusiness);
			rmbbusinessMapper.updateByPrimaryKey(oppRmbbusiness);
			flag = true;
		} catch (Exception e) {
			throw new RuntimeException("失误操作失败");
		}
		return flag;
	}
	//显示账户信息
	public ArrayList<RmbbusinessShow> show_Rmbbusiness(int offset,int size){
		ArrayList<RmbbusinessShow> rmbbusinesses = rmbbusinessMapper.showRmbbusinessByLimit(offset,offset+size);
		return rmbbusinesses;
	}
	@Override
	public int selectaccountsnum() {
		return rmbbusinessMapper.selectaccountsnum();
	}
	@Override
	public int updateRmbcard(Rmbbusiness card) {
		
		return rmbbusinessMapper.updateByPrimaryKeySelective(card);
	}
}
