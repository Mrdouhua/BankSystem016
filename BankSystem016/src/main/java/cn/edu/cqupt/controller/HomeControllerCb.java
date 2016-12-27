package cn.edu.cqupt.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.lang.model.element.Element;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Null;
import javax.xml.registry.infomodel.User;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.jdbc.Util;
import com.sun.org.apache.bcel.internal.generic.ReturnaddressType;

import cn.edu.cqupt.core.UUIDFactory;
import cn.edu.cqupt.model.FormInterests;
import cn.edu.cqupt.model.Interesttable;
import cn.edu.cqupt.model.Loan;
import cn.edu.cqupt.model.LoginUserInfo;
import cn.edu.cqupt.model.Loginuser;
import cn.edu.cqupt.model.ReturnInfo;
import cn.edu.cqupt.model.Userinfo;
import cn.edu.cqupt.service.HomeService;
import sun.misc.BASE64Decoder;

@Controller
public class HomeControllerCb extends BaseController{
	
	private static Logger logger = LoggerFactory.getLogger(HomeControllerCb.class);
	@Value("/upload_files")
	private String upload;
	@Resource
	private HomeService homeService;
	
	public HomeService getHomeService() {
		return homeService;
	}

	public void setHomeService(HomeService homeService) {
		this.homeService = homeService;
	}

	
	@RequestMapping("index")
	public String index(){
		return "login";
	}
	
	//获取session中维护的登录人信息，发送给前台展示
	@ResponseBody
	@RequestMapping("index/getuserinfo")
	public Userinfo showUserinfo(HttpServletRequest request){
		HttpSession session=request.getSession();
		return (Userinfo) session.getAttribute("userinfo");
		
	}
	
	@RequestMapping("index/clerk")
	public String clerk(HttpServletRequest request){
		HttpSession session=request.getSession();
		if (session.getAttribute("user")==null)
			return "redirect:/BankSystem_16/index.do";
		logger.info("进入到员工主页");
		return "employee_main";
	}
	
	@RequestMapping("index/user")
	public String userindex(HttpServletRequest request){
		HttpSession session=request.getSession();
		if (session.getAttribute("user")==null)
			return "redirect:/BankSystem_16/index.do";
		logger.info("进入到用户主页");
		return "user_main";
	}
	
	@RequestMapping("index/admin")
	public String adminindex(HttpServletRequest request){
		HttpSession session=request.getSession();
		if (session.getAttribute("user")==null)
			return "redirect:/BankSystem_16/index.do";
		logger.info("进入到管理员页面");
		return "manage_main";
	}
	
	@ResponseBody
	@RequestMapping(value="index/login",method=RequestMethod.POST)
	public ReturnInfo login(@RequestParam(value = "username", required = false, defaultValue = "null") String username,
			@RequestParam(value = "password", required = false, defaultValue = "null") String password,
			HttpServletRequest request){
		System.out.println("username>>>>>>"+username);
		System.out.println("password>>>>>>>>>>"+password);
	ReturnInfo info=new ReturnInfo();
	int change=0;
	if ("null".equals(username)||"null".equals(password)){
		//提示用户用户名或者密码不能为空
		info.setInfo("用户名或密码不能为空");
		info.setStatus("0");
	}
	else {
		HashMap<String, Object> loginuser = null;
		try {
			loginuser=homeService.selectLoginuser(username,password);
			Loginuser user=null;
			Userinfo uinfo=null;
			if(loginuser!=null){
				user=(Loginuser) loginuser.get("loginuser");
				uinfo=(Userinfo) loginuser.get("userinfo");
				
			}
			if (user.getUsertype().equals("A")){
				info.setUrl("/BankSystem_16/index/admin.do");
			}
			if (user.getUsertype().equals("U")){
				info.setUrl("/BankSystem_16/index/user.do");
			}
			if (user.getUsertype().equals("D")){
				info.setUrl("/BankSystem_16/index/clerk.do");
			}
			change=1;
			info.setInfo("登陆成功！！");
			info.setStatus("1");
			HttpSession session=request.getSession();
			session.setAttribute("user", user);
			session.setAttribute("userinfo", uinfo);
			session.setAttribute("change", change);
		} catch (Exception e) {
			info.setInfo(e.getMessage().toString());
			info.setStatus("0");
		}
	}
		return info;
	}
	
	@RequestMapping("loginout")
	public String loginout(HttpServletRequest request){
		
		HttpSession session=request.getSession();
		session.removeAttribute("user");
		session.removeAttribute("userinfo");
		session.removeAttribute("change");
		
		return "redirect:/index.do";
		
	}
	/**
	 * 测试使用页面
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getuserinfo")
	public LoginUserInfo getuserinfo(){
		LoginUserInfo info=null;
		info=homeService.selectuserinfo("2014211514");
		return info;
	}
	
	/**
	 * 进入到注册主页面
	 * @return
	 */
	@RequestMapping("index/registerpage")
	public String toregister(){
		return "register";
	}
	
	/**
	 * 逻辑错误
	 * @param loginname
	 * @param password
	 * @param idnumber
	 * @param name
	 * @param address
	 * @param phone
	 * @param email
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="index/register",method=RequestMethod.POST)
	public ReturnInfo register(
			@RequestParam(value="loginname",required=false,defaultValue="null")String loginname,
			@RequestParam(value="password",required=false,defaultValue="null")String password,
			@RequestParam(value="idnumber",required=false,defaultValue="null")String idnumber,
		    @RequestParam(value="name",required=false,defaultValue="null")String name,
		    @RequestParam(value="address",required=false,defaultValue="null")String address,
		    @RequestParam(value="phone",required=false,defaultValue="null")String phone,
		    @RequestParam(value="email",required=false,defaultValue="null")String email,
		    @RequestParam(value="sex",required=false,defaultValue="1")String sex,
		    @RequestParam(value="file",required=false) MultipartFile file,
		    HttpServletRequest request){
		System.out.println(sex);
		// @RequestParam(value="file",required=false) MultipartFile file,
		System.out.println("用户请求注册。。。。。。。。。。。。");
		String message="发生未知错误";
		String status="0";
		int res=0;
		//检测信息的合法性
		System.out.println("loginname:"+loginname+",password:"+password+",name:"+name);
		System.out.println("idnumber:"+idnumber);
		System.out.println("address:"+address+",phone:"+phone+",email:"+email);
		if(loginname.equals("null")||password.equals("null")||name.equals("null")){
			message="用户信息不完善，不予注册";
		}else if(idnumber.equals("null")||!(idnumber.length()==18||idnumber.length()==15)){
			message="用户身份虚假，不予注册";
		}else{
			//用户填写的关键信息正确，给予注册
			Loginuser user=new Loginuser();
			Userinfo userinfo=new Userinfo();
			Date crtime=new Date();
			user.setCreattime(crtime);
			user.setEdittime(crtime);
			user.setLoginname(loginname);
			user.setPassword(password);
			user.setStatus("1");
			user.setUsertype("U");
			String userid=UUIDFactory.generateUUID();
			user.setUserid(userid);
			userinfo.setAddress(address);
			userinfo.setCreattime(crtime);
			userinfo.setEdittime(crtime);
			userinfo.setEmail(email);
			userinfo.setIdnumber(idnumber);
			userinfo.setName(name);
			userinfo.setPhone(phone);
			userinfo.setSex(sex);
			userinfo.setUserid(userid);
			//保存用户的头像，如果用户上传了的
			//String upload="D://file_uploads";
			if(file!=null){
				ServletContext sc = request.getSession().getServletContext();
				String dir = sc.getRealPath(upload);
				//String dir=upload;
				System.out.println("存放文件的路径为:"+dir);
				String basepath=dir+"/memberphotos";
					String originalFileName = file.getOriginalFilename();
					String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
					String filename=(String)UUIDFactory.generateUUID();
					String FileName=filename+ext;
					File targetFile=new File(basepath,FileName);
					if (!targetFile.exists()){
						targetFile.mkdirs();
					}
					try {
						file.transferTo(targetFile);
					} catch (Exception e) {
						logger.error("公司联系人头像上传异常", e);
					}
					userinfo.setPhoto(upload+"/memberphotos/"+FileName);
			}
			
			//进行用户信息保存
			res=homeService.saveuser(user,userinfo);
			System.out.println("res="+res);
			if (res==1){
			    status="1";
			    message="注册成功";
			}
			else
				message="保存用户数据异常";
			
		}
	    ReturnInfo reinfo=new ReturnInfo();
		reinfo.setInfo(message);
		reinfo.setStatus(status);
		reinfo.setUrl("/BankSystem_16/index.do");
		return reinfo;
	}
	
	//数据操作员修改贷款利率表
	@ResponseBody
	@RequestMapping("/admin/editloaninterest")
	public ReturnInfo editloaninterest(
			@RequestParam(value="time",defaultValue="2015-2016")String time,
			@RequestParam(value="v1",defaultValue="null")String v1,
			@RequestParam(value="v2",defaultValue="null")String v2,
			@RequestParam(value="v3",defaultValue="null")String v3,
			@RequestParam(value="v4",defaultValue="null")String v4,
			@RequestParam(value="v5",defaultValue="null")String v5,
			@RequestParam(value="v6",defaultValue="null")String v6,
			@RequestParam(value="v7",defaultValue="null")String v7,
			@RequestParam(value="v8",defaultValue="null")String v8,
			@RequestParam(value="v9",defaultValue="null")String v9,
			@RequestParam(value="v10",defaultValue="null")String v10){
		ReturnInfo  info=new ReturnInfo();
		if (v1.equals("null")||v2.equals("null")||v3.equals("null")||v4.equals("null")||v5.equals("null")||v6.equals("null")||
				v7.equals("null")||v8.equals("null")||v9.equals("null")||v10.equals("null"))
		{
			info.setInfo("利率不能为空");
			info.setStatus("0");
		}
		else{
			
			Interesttable i1=new Interesttable();
			i1.setTime(time);
			i1.setDeposittype("DA");
			i1.setCycle("D1");
			i1.setInterestvalue(Double.valueOf(v1));
			Interesttable i2=new Interesttable();
			i2.setTime(time);
			i2.setDeposittype("DA");
			i2.setCycle("D2");
			i2.setInterestvalue(Double.valueOf(v2));
			Interesttable i3=new Interesttable();
			i3.setTime(time);
			i3.setDeposittype("DA");
			i3.setCycle("D3");
			i3.setInterestvalue(Double.valueOf(v3));
			Interesttable i4=new Interesttable();
			i4.setTime(time);
			i4.setDeposittype("DA");
			i4.setCycle("D4");
			i4.setInterestvalue(Double.valueOf(v4));
			Interesttable i5=new Interesttable();
			i1.setTime(time);
			i1.setDeposittype("DA");
			i1.setCycle("D5");
			i1.setInterestvalue(Double.valueOf(v5));
			Interesttable i6=new Interesttable();
			i6.setTime(time);
			i6.setDeposittype("DA");
			i6.setCycle("D6");
			i6.setInterestvalue(Double.valueOf(v6));
			Interesttable i7=new Interesttable();
			i7.setTime(time);
			i7.setDeposittype("DB");
			i7.setCycle("D3");
			i7.setInterestvalue(Double.valueOf(v7));
			Interesttable i8=new Interesttable();
			i8.setTime(time);
			i8.setDeposittype("DB");
			i8.setCycle("D5");
			i8.setInterestvalue(Double.valueOf(v8));
			Interesttable i9=new Interesttable();
			i9.setTime(time);
			i9.setDeposittype("DB");
			i9.setCycle("D6");
			i9.setInterestvalue(Double.valueOf(v9));
			Interesttable i10=new Interesttable();
			i10.setTime(time);
			i10.setDeposittype("DC");
			i10.setCycle("nu");
			i10.setInterestvalue(Double.valueOf(v10));
		List<Interesttable> interests=new ArrayList<>();
		interests.add(i1);
		interests.add(i2);
		interests.add(i3);
		interests.add(i4);
		interests.add(i5);
		interests.add(i6);
		interests.add(i7);
		interests.add(i8);
		interests.add(i9);
		interests.add(i10);
		
		int res=homeService.updateInterests(interests);
		if (res==1){
			info.setInfo("修改利率成功");
			info.setStatus("1");
		}
		else {
			info.setInfo("网络异常，请稍后再试!");
			info.setStatus("0");
		}
		}
		return info;
	}
	
	@ResponseBody
	@RequestMapping(value="/admin/editloan")
	public ReturnInfo editLoanInterests(
			@RequestParam(value="time",defaultValue="2015-2016")String time,
			@RequestParam(value="v1",defaultValue="3.22")String v1,
			@RequestParam(value="v2",defaultValue="3.24")String v2,
			@RequestParam(value="v3",defaultValue="3.55")String v3){
	
		ReturnInfo info=new ReturnInfo();
		if (v1.equals("null")||v2.equals("null")||v3.equals("null")){
			info.setStatus("0");
			info.setInfo("利率不能为空");
		}
		else{
		Interesttable i1=new Interesttable();
		i1.setTime(time);
		i1.setDeposittype("LA");
		i1.setCycle("L1");
		i1.setInterestvalue(Double.valueOf(v1));
		Interesttable i2=new Interesttable();
		i2.setTime(time);
		i2.setDeposittype("LB");
		i2.setCycle("L2");
		i2.setInterestvalue(Double.valueOf(v2));
		Interesttable i3=new Interesttable();
		i3.setTime(time);
		i3.setDeposittype("LB");
		i3.setCycle("L3");
		i3.setInterestvalue(Double.valueOf(v3));
		List<Interesttable> interests=new ArrayList<>();
		interests.add(i1);
		interests.add(i2);
		interests.add(i3);
		int res=homeService.updateLoanInterests(interests);
		if (res==1){
			info.setInfo("修改贷款利率成功");
			info.setStatus("1");
		}
		else {
			info.setInfo("网络异常，请稍后再试！");
			info.setStatus("0");
		}
		}
		return info;
		
	}
}
