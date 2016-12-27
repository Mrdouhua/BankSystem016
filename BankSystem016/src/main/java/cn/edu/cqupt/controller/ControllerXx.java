package cn.edu.cqupt.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.registry.infomodel.User;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xml.internal.security.utils.IgnoreAllErrorHandler;

import cn.edu.cqupt.model.Interesttable;
import cn.edu.cqupt.model.Loginuser;
import cn.edu.cqupt.model.ReturnInfo;
import cn.edu.cqupt.model.Rmbbusiness;
import cn.edu.cqupt.model.RmbbusinessShow;
import cn.edu.cqupt.model.Userinfo;
import cn.edu.cqupt.service.ServiceXx;

/*
 * 熊胜相专用controller
 * 此类用于处理前台请求以及返回结果
 */
@Controller
public class ControllerXx extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(ControllerXx.class);
	
	@Resource
	private ServiceXx serviceXx;

	public ServiceXx getServiceXx() {
		return serviceXx;
	}

	public void setServiceXx(ServiceXx serviceXx) {
		this.serviceXx = serviceXx;
	}
	//跳转到贷款储蓄利率表
	@RequestMapping(value="user/topinserttable")
	public String toPositInserttable(){
		System.out.println("请求查看利率");
		return "storage-rate";
	}
	
	//查看储蓄业务介绍信息
	@RequestMapping("user/toPositintroduce")
	public String toPositintroduce(){
		return "storage-service";
	}
	
	//进入到预约开户界面
	@RequestMapping("user/togetaccount")
	public String set_Rmbbusinessindex(){
		return "open-account";
	}
	
	//预约开户v，已测试，功能正常
	@ResponseBody
	@RequestMapping(value="index/user/register_rmb",method=RequestMethod.POST)
	public ReturnInfo set_Rmbbusiness(@RequestParam(value="name", required=false, defaultValue="null") String name,
									@RequestParam(value="password", required=false, defaultValue="null") String password,
									@RequestParam(value="idnumber",required=false, defaultValue="null") String idnumber,
						HttpServletRequest request) throws Exception{
		System.out.println("name>>>>>>"+name);
		System.out.println("password>>>>>>>>>>"+password);
		System.out.println("idnumber>>>>>>>>"+idnumber);
		
		ReturnInfo info = new ReturnInfo();
		HttpSession session = request.getSession();
		//非法字符串请求判断
		if ("null".equals(name)||"null".equals(password)){
			//提示用户用户名或者密码不能为空
			info.setInfo("用户名或密码不能为空");
			info.setStatus("0");
		}else{
			//非法户主名判断
			Userinfo userinfo = serviceXx.is_Have(idnumber);
			if(userinfo==null){
				info.setInfo("用户不存在");
				info.setStatus("0");
			}else{
				//随机生成卡号(20位)     
				String raccountid = new String();
				int length = 20;
				Random random = new Random(new Date().getTime());
				for (int j = 0; j < length; j++) {
					raccountid = raccountid+random.nextInt(9);
				}
				//数据插入
				Rmbbusiness rmbbusiness = new Rmbbusiness();
				rmbbusiness.setIdnumber(idnumber);
				rmbbusiness.setStatus("0");
				rmbbusiness.setRaccountid(raccountid);
				rmbbusiness.setPassword(password);
				rmbbusiness.setCreattime(new Date());
				rmbbusiness.setCardtype("R");
				rmbbusiness.setCapital(new BigDecimal("0"));
				rmbbusiness.setEdittime(new Date());
				if(serviceXx.insert(rmbbusiness)==0){
					info.setInfo("提交失败，重新提交");
					throw new Exception("插入失败");
				}else{
					info.setInfo("提交成功，等待审核");
					info.setStatus("1");
				}
				session.setAttribute("rmbbusiness", rmbbusiness);
			}
		}
		Loginuser user=(Loginuser) session.getAttribute("user");
		//根据角色类型来进行跳转
		if (user==null)
			info.setUrl("/BankSystem_16/index.do");
		else if (user.getUsertype().equals("U"))
			info.setUrl("/BankSystem_16/index/user.do");
		else info.setUrl("/BankSystem_16/index/clerk.do");
		return info;
	}
	//利率表数据获取
	/*
	 * 已测试，功能正常，未交互
	 */
	//@RequestMapping(value="index/user/interest_show",method=RequestMethod.POST)
	@ResponseBody
	@RequestMapping(value="index/user/interest_show",method=RequestMethod.GET)
	public ArrayList<Interesttable> interest_Show(@RequestParam(value="time",required=true,defaultValue="2015-2016") String time)
	{
		ArrayList<Interesttable> interesttables = serviceXx.get_Interest(time);
		return interesttables;
	}
	
	//跳转到存钱、取钱、转账的界面
	@RequestMapping("clerk/tomoney_changeindex")
	public String tomoney_Changeindex(){
		return "save-draw-transfer";
	}
	//存钱、取钱、转账
	//已测试，交互正常
	@ResponseBody
	@RequestMapping(value="index/clerk/money_change",method=RequestMethod.POST)
	public ReturnInfo money_Change(@RequestParam(value="opera_type",required=true,defaultValue="null") String oprea_type,
									@RequestParam(value="money",required=true,defaultValue="null") String money,
									@RequestParam(value="raccountid",required=true,defaultValue="null") String raccountid,
									@RequestParam(value="opp_raccountid",required=false,defaultValue="null") String opp_raccountid){
		System.out.println("opertype:"+oprea_type+",money:"+money+",fromid:"+raccountid+",toid:"+opp_raccountid);
		ReturnInfo info = new ReturnInfo();
		Rmbbusiness rmbbusiness = serviceXx.get_Rmbbusiness(raccountid);
		System.out.println(rmbbusiness.getPassword());
		BigDecimal capital = rmbbusiness.getCapital();
		//更新最近账户信息变更时间
		rmbbusiness.setEdittime(new Date());
		//如果接收到的是存钱指令则进行存钱操作
		if("save".equals(oprea_type)){
			rmbbusiness.setCapital(capital.add(new BigDecimal(money)));
			if(serviceXx.updata_message(rmbbusiness)==0){
				info.setInfo("存钱失败，重新操作");
				info.setStatus("0");
			}else{
				info.setInfo("存钱成功");
				info.setStatus("1");
			}
		}
		//如果接收到的是取钱指令则进行取钱操作
		if ("withdraw".equals(oprea_type)) {
			if (capital.compareTo(new BigDecimal("money"))==-1){
				//表明账户余额不足，不能进行取钱操作
				info.setInfo("账户余额为:"+capital+",余额不足");
				info.setStatus("0");
			}
			else{
			rmbbusiness.setCapital(capital.subtract(new BigDecimal(money)));
			if(serviceXx.updata_message(rmbbusiness)==0){
				info.setInfo("取钱失败，重新操作");
				info.setStatus("0");
			}else{
				info.setInfo("取钱成功");
				info.setStatus("1");
			}
			}
		}
		//如果接收到的是转账指令则进行转账操作
		if ("transfer".equals(oprea_type)) {
			if (capital.compareTo(new BigDecimal("money"))==-1){
				//账户余额不足
				info.setInfo("账户余额为:"+capital+",余额不足");
				info.setStatus("0");
			}else {
			//收款方信息
			Rmbbusiness oppRmbbusiness = serviceXx.get_Rmbbusiness(opp_raccountid);
			//收款方余额
			BigDecimal oppCapital = oppRmbbusiness.getCapital();
			//更新收款方和发款方信息
			oppRmbbusiness.setEdittime(new Date());
			oppRmbbusiness.setCapital(oppCapital.add(new BigDecimal(money)));
			rmbbusiness.setCapital(capital.subtract(new BigDecimal(money)));
			if(serviceXx.transfer(rmbbusiness,oppRmbbusiness)){
				info.setInfo("转账成功");
				info.setStatus("1");
			}else{
				info.setInfo("转账失败，请重新转账");
				info.setStatus("0");
			}
			}
		}
		return info;
	}
	
	//跳转到修改储蓄卡卡号的页面
	@RequestMapping("user/fixaccountpwd")
	public String fixpasswordindex(){
		return "modify-password";
	}
	
	
	//修改密码
	/**
	 * 逻辑错误,未知错误
	 * @param raccountid
	 * @param old_password
	 * @param new_password
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="index/clerk/password_updata",method=RequestMethod.POST)
	public ReturnInfo password_Updata(@RequestParam(value="raccountid",required=true,defaultValue="null") String raccountid,
										@RequestParam(value="old_password",required=true,defaultValue="null") String old_password,
										@RequestParam(value="new_password",required=true,defaultValue="null") String new_password){
		System.out.println("银行卡号为:"+raccountid+",旧密码为:"+old_password+",新密码为:"+new_password);
		ReturnInfo info = new ReturnInfo();
		if("null".equals(raccountid)||"null".equals(old_password)||"null".equals(new_password)){
			info.setInfo("信息不能为空");
			info.setStatus("0");
		}else{
			//判断账户、密码是否一致
			Rmbbusiness rmbbusiness = serviceXx.get_Rmbbusiness(raccountid);
			if(rmbbusiness!=null&&old_password.equals(rmbbusiness.getPassword())){
				rmbbusiness.setEdittime(new Date());
				rmbbusiness.setPassword(new_password);
				int flag = serviceXx.updata_message(rmbbusiness);
				if(flag==1){
					info.setInfo("密码修改成功");
					info.setStatus("1");
				}else{
					info.setInfo("密码修改失败，请重新提交");
					info.setStatus("0");
				}
			}else{
				info.setInfo("账号或密码有误");
				info.setStatus("0");
			}
		}	
		return info;
	}
	//跳转到用户储蓄卡管理界面
	@RequestMapping("clerk/manageaccount")
	public String manageAccount(){
		return "account-manage";
	}
	
	//编辑卡的状态值
	@ResponseBody
	@RequestMapping(value="clerk/editCardStatus",method=RequestMethod.POST)
	public ReturnInfo changeCardStatus(
			@RequestParam(value="raccountid",required=true,defaultValue="null")String cardid,
			@RequestParam(value="status",required=true,defaultValue="null")String status){
		ReturnInfo info =new ReturnInfo();
		if (cardid.equals("null")||status.equals("null")){
			info.setInfo("操作不合法");
			info.setStatus("0");
		}
		else {
		//根据卡号获取到卡的信息
		Rmbbusiness card=serviceXx.get_Rmbbusiness(cardid);
		if (card==null){
			info.setInfo("未知错误");
			info.setStatus("0");
		}
		else {
			if (status.equals("0"))
				card.setStatus("1");
			else
			card.setStatus(status);
			//修改数据库表
			int res=serviceXx.updateRmbcard(card);
			if (res==1){
				info.setInfo("修改储蓄卡状态成功");
				info.setStatus("1");
			}
		}
		
		}
		return info;
		
	}
	
	//已测试，功能正常,等待前台交互
	@ResponseBody
	@RequestMapping(value="index/clerk/account_Control",method=RequestMethod.POST)
	public HashMap<String, Object> account_show(@RequestParam(value="current",required=false,defaultValue="1") int page){
		//数据总条数
		int counts=serviceXx.selectaccountsnum();
		//总数据条数对默认页面数据条数10求余,得到总的分页数
		double pagesize=10;
		int pagenum=(int) Math.ceil((double)counts/pagesize);
		int size=10;//默认每页显示的数据条数
		if(page>pagenum)
			page=pagenum;
		int offset = size*(page-1);
		ArrayList<RmbbusinessShow> rmbbusinesses =  serviceXx.show_Rmbbusiness(offset,size);
		for (RmbbusinessShow e:rmbbusinesses){
			if (e.getStatus().equals("0")){
				e.setStatus("未激活");
				continue;
			}
			if (e.getStatus().equals("1")){
				e.setStatus("激活");
				continue;
			}
			if (e.getStatus().equals("2")){
				e.setStatus("挂失");
				continue;
			}
			if(e.getStatus().equals("3")){
				e.setStatus("冻结");
				continue;
			}
			if (e.getStatus().equals("4")){
				e.setStatus("注销");
				continue;
			}
		}
		HashMap<String, Object> res=new HashMap<>();
		res.put("pagenum", pagenum);
		res.put("datas", rmbbusinesses);
		return res;
	}
	
	
}