package cn.edu.cqupt.controller;

import java.security.interfaces.RSAKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.cqupt.core.UUIDFactory;
import cn.edu.cqupt.model.Interesttable;
import cn.edu.cqupt.model.Loginuser;
import cn.edu.cqupt.model.ReturnInfo;
import cn.edu.cqupt.service.ServiceGq;
import cn.edu.cqupt.service.ServiceXx;
import cn.edu.cqupt.service.ServiceYg;

/*
 * 杨刚专用controller
 * 此类用于处理前台请求以及返回结果
 */
@Controller
public class ControllerYg extends BaseController{
	
	private static Logger logger = LoggerFactory.getLogger(ControllerYg.class);
	
	@Resource
	private ServiceYg serviceYg;
	@Resource
	private ServiceXx serviceXx;
	@Resource
	private ServiceGq serviceGq;

	public ServiceXx getServiceXx() {
		return serviceXx;
	}

	public void setServiceXx(ServiceXx serviceXx) {
		this.serviceXx = serviceXx;
	}
	public ServiceYg getServiceYg() {
		return serviceYg;
	}

	public void setServiceYg(ServiceYg serviceYg) {
		this.serviceYg = serviceYg;
	}
	
	
	@RequestMapping("/admin/roleManager")
	public String gotoRoleManager() {
		logger.info("进入SE银行角色管理页面");
		return "role-manage";
	}

	
	//显示用户角色
	//账号管理
	//已测试，待交互
	@ResponseBody
	@RequestMapping(value="admin/Loginuser_show",method=RequestMethod.GET)
	public HashMap<String, Object> Loginuser_show(@RequestParam(value="current",required=true,defaultValue="1") int page){
		int pagenum=0;
		int size=10;
		//先查询数据总条数
		int counts=serviceYg.selectsystemuserCounts();
		pagenum=(int) Math.ceil((double)counts/size);
		if (page>pagenum)
			page=pagenum;
		int offset = size*(page-1);
		ArrayList<Loginuser> Loginusers = serviceYg.show_Loginuser(offset, size);
		HashMap<String, Object>res=new HashMap<>();
		res.put("datas", Loginusers);
		res.put("pagenum", pagenum);
		return res;
	}
	
	//已测试，待交互
	//根据角色账户查询
	@ResponseBody
	@RequestMapping(value="admin/searchbyLoginName",method=RequestMethod.GET)
	public HashMap<String, Object> searchbyLoginName(@RequestParam(value = "loginName", required = false, defaultValue = "null") String loginName){
		ArrayList<Loginuser>loginusers=serviceYg.searchbyLoginName(loginName);
 		HashMap<String, Object>res =new HashMap<>();
 		res.put("datas", loginusers);
 		res.put("pagenum", 1);
 		return res;
	}
	
	//已测试，待交互
	//删除角色信息
	@ResponseBody
	@RequestMapping(value="admin/deleteLoginuser",method=RequestMethod.GET)
	public ReturnInfo  deleteLoginuser(@RequestParam(value = "userid", required = false, defaultValue = "null") String userid){
		ReturnInfo info=new ReturnInfo();
		info.setStatus("0");
		if (userid.equals("null")){
		info.setInfo("未知错误，请稍后再试");
		}
		else {
		info.setInfo("服务器异常！请稍后再试");
		int res=serviceYg.deleteLoginuser(userid);
		//此处调用角色显示方法
		if (res==1){
			info.setInfo("删除角色成功");
			info.setStatus("1");
		}
		}
		return info;
	}
	
	//跳转到修改用户信息界面
	@RequestMapping("admin/fixinfopage")
	public String tofixSystemUserinfopage(){
		return "fixinfopage";
	}
	
	//获取修改对象当前的信息
	//已测试，未交互
	@RequestMapping("admin/fixinfo/getuserinfo")
	public Loginuser getuserinfo(
			@RequestParam(value = "userid", required = false, defaultValue = "null") String userid){
		Loginuser user=serviceYg.selectLoginuserByuserid(userid);
		return user;
		
		
	}
	
	//修改角色信息
	//已测试，未交互
	@ResponseBody
	@RequestMapping(value="admin/updateLoginuser",method=RequestMethod.POST)//这里GET要注意可能要修改
	public ReturnInfo updateLoginuser(
			@RequestParam(value = "loginName", required = false, defaultValue = "null") String loginName,
			@RequestParam(value = "userid", required = false, defaultValue = "null") String userid,
			@RequestParam(value = "usertype", required = false, defaultValue = "null") String usertype){
		    //serviceYg.updateLoginuser(loginName, userid, status, usertype);
		    ReturnInfo info=new ReturnInfo();
		    //首先根据userid查询该用户的信息
		    Loginuser user=serviceYg.selectLoginuserByuserid(userid);
		    if (user==null){
		    	info.setInfo("用户信息错误");
		    	info.setStatus("0");
		    }
		    else{
		    	if(!loginName.equals("null"))
		    	user.setLoginname(loginName);
		    	user.setEdittime(new Date());
		    	if (!usertype.equals("null"))
		    	user.setUsertype(usertype);
		    	int res=serviceYg.updateLoginuserByuser(user);
		    	if (res==1){
		    		info.setInfo("修改角色信息成功");
		    		info.setStatus("1");
		    	}
		    	else{
		    		info.setInfo("修改角色信息失败，服务器未知错误，请稍后再试");
		    		info.setStatus("0");
		    	}
		    }
		    //不管怎样，都要进行页面跳转
		    info.setUrl("/BankSystem_16/admin/roleManager.do");
		    //此处调用角色显示方法
		    return info;
	}
	
	
	@RequestMapping("/admin/addRole")
	public String gotoAddRoleManager() {
		logger.info("进入SE银行添加角色页面");
		return "add-role";
	}
	
	//添加新角色
	//已测试，未交互
	@ResponseBody
	@RequestMapping(value="admin/insert",method=RequestMethod.POST)
	public ReturnInfo insert(
			@RequestParam(value = "loginName", required = false, defaultValue = "null") String loginName,
			@RequestParam(value = "password", required = false, defaultValue = "null") String password,
			@RequestParam(value = "userType", required = false, defaultValue = "null") String userType){
		ReturnInfo info=new ReturnInfo();
		if(serviceYg.isHaveUser(loginName)==1){
			info.setInfo("该用户名已经存在");
			info.setStatus("0");
		}else if (loginName.equals("null")||password.equals("null")||userType.equals("null")){
			info.setInfo("用户信息填写不完善");
			info.setStatus("0");
		}
		else{
			Loginuser  loginuser=new Loginuser();
			Date date=new Date();
			loginuser.setUserid(UUIDFactory.generateUUID());
			loginuser.setLoginname(loginName);
			loginuser.setPassword(password);
			loginuser.setStatus("1");
			loginuser.setCreattime(date);
			loginuser.setEdittime(date);
			loginuser.setUsertype(userType);
			int res=serviceYg.insert(loginuser);
			if (res==1){
			info.setInfo("添加成功！！");
			info.setStatus("1");
			info.setUrl("/BankSystem_16/admin/roleManager.do");
			}
			else {
				info.setInfo("添加系统账号失败，服务器未知错误，请稍后再试");
				info.setStatus("0");
			}
			
		}
		return info;
	}
	
	@RequestMapping("/admin/modifyPsw")
	public String gotoModifyPsw() {
		logger.info("进入SE银行修改密码页面");
		return "manage-modify-password";
	}
	
	//修改密码
	//已测试，未交互
	@ResponseBody
	@RequestMapping(value="admin/updatepassword",method=RequestMethod.POST)//这里GET要注意可能要修改
	public ReturnInfo update(
			@RequestParam(value = "loginName", required = false, defaultValue = "null") String loginName,
			@RequestParam(value = "password", required = false, defaultValue = "null") String password,
			@RequestParam(value = "newpassword", required = false, defaultValue = "null") String newpassword){
		    ReturnInfo info=new ReturnInfo();
		    if(serviceYg.updatepassword(loginName, password, newpassword)==1){
		    	System.out.println("密码修改成功");
		    	info.setInfo("密码修改成功");
		    	info.setStatus("1");
		    	info.setUrl("/BankSystem_16/admin/roleManager.do");
		    }else{
		    	System.out.println("用户名或者密码错误");
		    	info.setInfo("用户名或密码错误");
		    	info.setStatus("0");
		    	info.setUrl("/BankSystem_16/admin/roleManager.do");
		    }
		    return info;
	}
	
	@RequestMapping("/admin/storageRateManager")
	public String gotoStorageRateManager() {
		logger.info("进入SE银行储蓄利率管理页面");
		return "manage-storage-rate";
	}
	
	
	
	//储蓄利率表展示
	@ResponseBody
	@RequestMapping(value="admin/interest_show",method=RequestMethod.GET)
	public ArrayList<Interesttable> interest_Show(@RequestParam(value="time",required=true,defaultValue="2015-2016") String time)
	{
		ArrayList<Interesttable> interesttables = serviceXx.get_Interest(time);
		return interesttables;
	}
	
	
	//储蓄利率修改
	//逻辑错误,弃用
	@ResponseBody
	@RequestMapping(value="admin/interest_update",method=RequestMethod.GET)
	public ArrayList<Interesttable> interest_update(@RequestParam(value="time",required=true,defaultValue="2015-2016") String time)
	{	
		ArrayList<Interesttable> interesttables=null;//注意修改此处
		interesttables = serviceXx.get_Interest("2015-2016");
		serviceYg.interest_update(interesttables);
		interesttables = serviceXx.get_Interest("2015-2016");
		return interesttables;
	}
	
	@RequestMapping("/admin/loanRateManager")
	public String gotoLoanRateManager() {
		logger.info("进入SE银行贷款利率管理页面");
		return "manage-loan-rate";
	}
	
	
	//已测试，待交互
	//贷款利率表显示
	@ResponseBody
	@RequestMapping(value = "admin/loanInterestTableQuery", method = RequestMethod.GET)
	public List<Interesttable> loanInterestTableQuery(
			@RequestParam(value = "time", required = false, defaultValue = "2015-2016") String time,
			HttpServletRequest request) {
		List<Interesttable> result = null;
		if("null".equals(time)) {
			logger.warn("贷款利率表查看模块：时间传输有误！");
		} else {
			logger.info("贷款利率表查看！");
			result = serviceGq.getLoanInterestInf(time);
		}
		return result;
	}
	
	
	
	//贷款利率修改
	//逻辑错误
	@ResponseBody
	@RequestMapping(value="admin/LoanInterestupdate",method=RequestMethod.GET)
	public ArrayList<Interesttable> LoanInterestupdate(
			@RequestParam(value="time",required=true,defaultValue="2015-2016") String time)
	{	
		ArrayList<Interesttable> interesttables=null;//注意修改此处
		interesttables = (ArrayList<Interesttable>) serviceGq.getLoanInterestInf("2015-2016");
		serviceYg.updateLoanInterest(interesttables);
		interesttables = (ArrayList<Interesttable>) serviceGq.getLoanInterestInf("2015-2016");
		return interesttables;
	}
}
