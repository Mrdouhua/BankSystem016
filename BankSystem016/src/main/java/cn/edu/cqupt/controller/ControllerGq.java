package cn.edu.cqupt.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.edu.cqupt.model.Interesttable;
import cn.edu.cqupt.model.Loan;
import cn.edu.cqupt.model.LoanApprovalInfo;
import cn.edu.cqupt.model.LoanSchedule;
import cn.edu.cqupt.model.Loginuser;
import cn.edu.cqupt.model.ReturnInfo;
import cn.edu.cqupt.model.Userinfo;
import cn.edu.cqupt.service.ServiceGq;

/**
 * 张冠群专用controller 此类用于处理前台请求以及返回结果
 *
 * @author ZhangGuanQun
 *
 */
@Controller
public class ControllerGq extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(ControllerGq.class);

	@Value("/upload_files")
	private String upload;

	@Resource
	private ServiceGq serviceGq;

	public ServiceGq getServiceGq() {
		return serviceGq;
	}

	public void setServiceGq(ServiceGq serviceGq) {
		this.serviceGq = serviceGq;
	}

	/* --------------- 员工端 贷款部分 --------------- */

	/*
	 * SE银行贷款审批发放业务：
	 * 
	 * 1.查询用户的贷款信息：接收前端query请求(表单页面的姓名和身份证号)，调用Service逻辑处理；
	 * 
	 * 2.查询到的贷款信息，状态有三种： 
	 *     (1)审核通过；
	 *     (2)审核未通过；
	 *     (3)未审核：此时的状态值为一个超链接，链接到贷款证明材料的显示页面；
	 * 
	 * 3.删除贷款信息：接收前端delete请求(对应信息主键)，调用Service逻辑处理；
	 * 
	 * 4.对未审核信息处理： 
	 *     (1)进入审核页面时，自动显示用户贷款信息； 
	 *     (2)提供下载贷款人证明材料功能； 
	 *     (3)提交贷款审批结果；
	 * 
	 * 5.贷款产品信息介绍录入(目前做成静态网页部分)；
	 * 
	 * 6.贷款归还业务说明录入(目前做成静态网页部分)；
	 * 
	 */

	/**
	 * 跳转到SE银行贷款审批信息页面(loan_approval.html)
	 * 
	 * @return 审批信息页面名称
	 */
	@RequestMapping("loanApprovalQuery")
	public String loanApprovalQuery() {
		logger.info("进入SE银行贷款审批信息页面");
		return "loan-approval";
	}
	
	/*
	public HashMap<String, Object> getpagedatas(@RequestParam(value="current",required=false,defaultValue="1")int currentpage){
		HashMap<String, Object>res=new HashMap<>();
		//SELECT NAME ,l.IDnumber,capital,cardID ,STATUS FROM userinfo,loan l WHERE userinfo.IDnumber=l.IDnumber
		//查询数据的总条数
		int counts=serviceGq.selectLoanNumbers();
		
		return res;
	}
	*/
	

	/**
	 * 贷款审批信息页面中的查询模块
	 * 
	 * 本方法会在三种场景下调用： (1)刚进入页面时，执行所有数据搜索(全空,查询所有数据,分页显示第一页);
	 * (2)用户输入搜索条件后,且指定页码和分页数进行搜索; (3)用户点击删除后自动刷新页面(刷新页面时,需要保留搜索条件参数,前端将搜索条件传入);
	 * 
	 * @param userName
	 *            查询条件： 姓名
	 * @param idNumber
	 *            查询条件： 身份证号
	 * @param numPerPage
	 *            每页显示数据的条数
	 * @param pageNo
	 *            显示第几页的内容
	 * @param request
	 *            HttpServlet请求
	 * @return 查询结果
	 */
	//已测试，未交互
	@ResponseBody
	@RequestMapping(value = "loanApprovalQuery", method = RequestMethod.POST)
	public HashMap<String, Object> loanInfQuery(
			@RequestParam(value = "name", required = false, defaultValue = "null") String userName,
			@RequestParam(value = "idnumber", required = false, defaultValue = "null") String idNumber,
			@RequestParam(value = "current", required = false, defaultValue = "1") String pageNo,
			HttpServletRequest request) {
		String numPerPage="10";
		int pagenum=1;//那些查询的值只有一页数据
		/**
		 * 根据前端传过来的username和IDNumber来进行数据查找，查找结果以对象的形式发回（前端以JSON形式接收）
		 */
		logger.info("UserName>>>>>>>>" + userName);
		logger.info("IDNumber>>>>>>>>" + idNumber);

		List<LoanApprovalInfo> result = null;

		if ("null".equals(numPerPage) || "null".equals(pageNo)) {
			logger.error("信息查询模块：分页信息接收错误！");
		} else {
			if ("null".equals(userName) && "null".equals(idNumber)) {
				//已测试，功能正常
				/* 全空：调用查询全部数据的方法(刚进入页面或者空查询,注意分页号) */
				//查询数据总条数
				int counts=serviceGq.selectLoanNumbers();
				int pages=(int) Math.ceil((double)counts/(10.00));
				pagenum=pages;
				int page=Integer.parseInt(pageNo);
				if (page>pages)
					page=pages;
				result = serviceGq.getLoanResultByNull(numPerPage, pageNo);
			} else if ("null".equals(userName) && !("null".equals(idNumber))) {
				/* userName空,idNumber不空：调用根据idNumber查询的方法 */
				result = serviceGq.getLoanResultByid(idNumber, numPerPage, pageNo);
			} else if ("null".equals(idNumber) && !("null".equals(userName))) {
				/* idNumber空,userName不空：调用根据userName查询的方法 */
				result = serviceGq.getLoanResultByName(userName, numPerPage, pageNo);
			} else {
				/* 全不空，调用根据userName和idNumber查询的方法 */
				result = serviceGq.getLoanResultByNameAndid(userName, idNumber, numPerPage, pageNo);
			}
		}
		HashMap<String, Object>res=new HashMap<>();
		res.put("datas", result);
		res.put("pagenum", pagenum);
		return res;
	}

	/**
	 * 贷款审批信息页面中的删除模块(单条数据删除)
	 * 
	 * 注意：本方法不会真正从数据库中删除信息，而是将对应信息的状态设置为无效(Status=4)
	 * 
	 * @param idNumber
	 *            主键：身份证号，唯一
	 * @param request
	 *            HttpServlet请求
	 * @return 状态值、提示用户的信息、操作成功之后跳转到的页面的URL
	 */
	@ResponseBody
	@RequestMapping(value = "loanApprovalDelete", method = RequestMethod.POST)
	public ReturnInfo loanInfDelete(
			@RequestParam(value = "IDNumber", required = false, defaultValue = "null") String idNumber,
			HttpServletRequest request) {
		int status = 0;
		ReturnInfo info = new ReturnInfo();
		if ("null".equals(idNumber)) {
			info.setInfo("删除失败：获取数据有误！");
			info.setStatus("0");
		} else {
			status = serviceGq.deleteLoanInf(idNumber);
		}
		if (status == 1) {
			info.setInfo("删除成功！");
			info.setStatus("1");
		}
		return info;
	}

	/**
	 * 跳转到SE银行贷款审核页面(audit.html)
	 * 
	 * @return 贷款审核页面名称
	 */
	@RequestMapping("loanApprovalindex")
	public String loanApproval() {
		logger.info("进入SE银行贷款审核页面");
		return "audit";
	}

	/**
	 * 贷款审核页面信息自动显示模块
	 * 
	 * @param idNumber
	 *            主键：身份证号
	 * @param request
	 *            HttpServlet请求
	 * @return 贷款人信息
	 */
	//已测试，未交互
	@ResponseBody
	@RequestMapping(value = "loanApproval", method = RequestMethod.GET)
	public List<LoanApprovalInfo> loanInfQuery(
			@RequestParam(value = "idnumber", required = false, defaultValue = "null") String idNumber,
			HttpServletRequest request) {
		List<LoanApprovalInfo> result = null;
		if ("null".equals(idNumber)) {
			logger.error("审核信息显示模块：idNumber传输错误！");
		} else {
			logger.info("审核信息显示！");
			result = serviceGq.getLoanResultByid(idNumber);
		}
		return result;
	}

	/**
	 * 贷款审核页面信息下载模块
	 * 
	 * @param cardID
	 *            主键：银行卡号
	 * @param request
	 *            HttpServlet请求
	 * @return 下载状态信息
	 */
	//逻辑错误，待测试
	@ResponseBody
	@RequestMapping(value = "loanApprovalDownload", method = RequestMethod.POST)
	public ReturnInfo downloadLoanerInf(
			@RequestParam(value = "cardID", required = false, defaultValue = "null") String cardID,
			HttpServletRequest request) {
		ReturnInfo info = new ReturnInfo();
		if ("null".equals(cardID)) {
			logger.error("下载模块：cardID传输错误！");
			info.setInfo("cardID传输错误！");
			info.setStatus("0");
		} else {
			/* 获取贷款证明材料下载地址 */
			String downloadUrl = serviceGq.getDownloadUrlByCardID(cardID);
			info.setInfo("获取下载地址成功！");
			info.setUrl(downloadUrl);
			info.setStatus("1");
		}
		return info;
	}

	/**
	 * 后台贷款审核信息提交
	 * 
	 * @param isPass
	 *            是否通过，两种值："True(Status=1) / "False"(Status=2)
	 * @param approvalComments
	 *            贷款审核意见
	 * @param request
	 *            HttpServlet请求
	 * @return 提交状态信息
	 */
	//已测试，待交互
	@ResponseBody
	@RequestMapping(value = "loanApprovalSubmit", method = RequestMethod.POST)
	public ReturnInfo submitApprovalResult(
			@RequestParam(value="idnumber",required=true,defaultValue="null")String idnumber,
			@RequestParam(value = "status", required = true, defaultValue = "null") String isPass,
			@RequestParam(value = "approvalComments", required = true, defaultValue = "null") String approvalComments,
			HttpServletRequest request) {
		//首先根据用户身份证号码查询贷款记录，此处假设每个用户只贷一次款
		Loan loan=serviceGq.selectLoanByIdnumber(idnumber);
		ReturnInfo info = new ReturnInfo();
		if ((!"1".equals(isPass) && !"0".equals(isPass)) || "null".equals(isPass)||loan==null) {
			logger.error("审核提交模块：接收通过信息有误!");
			info.setInfo("接收通过信息有误!");
			info.setStatus("0");
		} else {
			if ("null".equals(approvalComments)) {
				logger.error("审核提交模块：审核意见信息为空！");
				info.setInfo("审核意见信息为空！");
				info.setStatus("0");
			} else {
				//审核不通过
				if (isPass.equals("0"))
				loan.setStatus("4");
				else 
					loan.setStatus(isPass);
				loan.setComment(approvalComments);
				int res=serviceGq.updateLoan(loan);
				if (res==1){
					logger.info("审核提交成功！");
					info.setInfo("审核提交成功！");
					info.setStatus("1");
					info.setUrl("/BankSystem_16/loanApprovalQuery.do");
				}
				else {
					logger.info("审核提交，数据库操作异常");
					info.setInfo("服务器异常，稍后再试！");
					info.setStatus("0");
					info.setUrl("/BankSystem_16/loanApprovalQuery.do");
				}

			}
		}
		return info;
	}

	/* --------------- 用户端 贷款部分 --------------- */

	/**
	 * SE银行贷款审批发放业务：
	 * 
	 * 1.用户贷款申请：进入用户贷款申请页面，下拉列表所有值由前端负责完成，
	 * 用户输入贷款申请信息，并接收用户上传的文件，发回后端调用Service处理；
	 * 
	 * 2.贷款进度查询：根据主键查询用户贷款进度；
	 * 
	 * 3.用户查看贷款利率：进入贷款利率查看页面，下拉列表所有值由前端负责完成，并设置一个默认值， 返回一条贷款利率信息；
	 * 
	 */
	
	//跳转到贷款归还介绍页面
	@RequestMapping("user/loanbackintroduce")
	public String loanbackintroduce(){
		return "loaning-back";
	}
	
	//跳转到贷款产品介绍页面
	@RequestMapping("user/loanproductintroduce")
	public String loanproductintroduce(){
		
		return "loaning-product";
	}

	/**
	 * 跳转到SE银行用户贷款申请页面(loaning-my.html)
	 * 
	 * @return 贷款审核页面名称
	 */
	@RequestMapping("loanApply")
	public String loanApply() {
		logger.info("进入SE银行用户贷款申请页面");
		return "loaning-my";
	}

	/**
	 * 用户贷款信息提交
	 * 
	 * @param idNumber
	 *            身份证号
	 * @param loanType
	 *            贷款类型
	 * @param capital
	 *            贷款金额
	 * @param cardID
	 *            银行卡号
	 * @param uploadInf
	 *            上传文件
	 * @param request
	 *            HttpServlet请求
	 * @return 提交状态信息
	 */
	//待交互
	@ResponseBody
	@RequestMapping(value = "loanApplySubmit", method = RequestMethod.POST)
	public ReturnInfo submitApplyInf(
			@RequestParam(value = "idNumber", required = false, defaultValue = "null") String idNumber,
			@RequestParam(value = "loanType", required = false, defaultValue = "null") String loanType,
			@RequestParam(value = "capital", required = false, defaultValue = "null") String capital,
			@RequestParam(value = "cardID", required = false, defaultValue = "null") String cardID,
			@RequestParam(value = "file", required = false) MultipartFile file,
			 HttpServletRequest request) {
		System.out.println("idNumber:"+idNumber);
		System.out.println("loanType:"+loanType);
		//@RequestParam(value = "file", required = false) MultipartFile file,
		ReturnInfo info = new ReturnInfo();
		if ("null".equals(idNumber) || "null".equals(loanType) || "null".equals(capital) || "null".equals(cardID)||file==null) {
			logger.error("提交信息模块：提交信息不全！");
			info.setInfo("提交信息不全！");
			info.setStatus("0");
		} else {
			/* 允许用户不上传文件 */ 
			String serverUploadFilePath = "null";
				serverUploadFilePath = serviceGq.getUploadFilePath(file, upload, request);
				System.out.println("controller中的文件路径"+serverUploadFilePath);
				
			
			int status = serviceGq.setLoanApplyInf(idNumber, loanType, capital, cardID, serverUploadFilePath);
			if (status == 1) {
				logger.info("提交信息模块：提交信息成功！");
				info.setInfo("提交信息成功！");
				info.setStatus("1");
			} else {
				logger.warn("提交信息模块：插入数据失败！");
				info.setInfo("插入数据失败！");
				info.setStatus("0");
			}
		}
		return info;
	}
	/**
	 * 跳转到SE银行用户贷款进度查询页面(loaning-process.html)
	 * 
	 * @return 贷款审核页面名称
	 */
	@RequestMapping("loanApplySchedule")
	public String loanApplySchedule() {
		logger.info("进入SE银行用户贷款进度查询页面");
		return "loaning-process";
	}

	/**
	 * 用户贷款进度查询模块
	 * 
	 * @param request
	 *            HttpServlet请求
	 * @return 查询结果
	 */
	//已测试，未交互
	@ResponseBody
	@RequestMapping(value = "loanScheduleQuery")
	public LoanSchedule loanApplySchedule(HttpServletRequest request) {
		LoanSchedule result = new LoanSchedule();
		HttpSession session = request.getSession();
		/*
		Loginuser loginUser = (Loginuser) session.getAttribute("loginuser");
		String status = loginUser.getStatus();
		String loginName = loginUser.getLoginname();
		if ("null".equals(loginName)) {
			logger.warn("贷款进度查询模块：接收登录名称有误!");
		}
		if (status == "0") {
			logger.warn("贷款进度查询模块：登录状态有误！");
		} else {
			logger.info("登录状态正常！");
			result = serviceGq.getLoanSchedule(loginName);
		}
		*/
		Loginuser user=(Loginuser) session.getAttribute("user");
		Userinfo userinfo=(Userinfo) session.getAttribute("userinfo");
		if (user!=null&&userinfo!=null){
		Loan loan=serviceGq.selectLoanByIdnumber(userinfo.getIdnumber());
		if (loan!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String loantime=sdf.format(loan.getCreattime());
			if (loan.getLoantype().equals("LA"))
				loan.setLoantype("短期贷款");
			else loan.setLoantype("中长期贷款");
			if (loan.getStatus().equals("0")){
				loan.setStatus("审核中");
			}
			else{ 
				if (loan.getStatus().equals("1"))
				loan.setStatus("审核通过");
				else loan.setStatus("拒绝贷款");
			}
			result.setCreatTime(loantime);
			result.setLoanType(loan.getLoantype());
			result.setName(userinfo.getName());
			result.setStatus(loan.getStatus());
		}
		}
		else{
			logger.info("用户信息不完善，不能获取贷款进度");
		}
		
		return result;
	}

	/**
	 * 跳转到SE银行用户贷款利率表查看页面(loaning-rate.html)
	 * 
	 * @return 贷款审核页面名称
	 */
	@RequestMapping("loanInterestTable")
	public String loanInterestTable() {
		logger.info("进入SE银行用户贷款利率表查看页面");
		return "loaning-rate";
	}

	/**
	 * 用户贷款利率表信息查询模块
	 * 
	 * @param time
	 *            查询时间
	 * @param request
	 *            HttpServlet请求
	 * @return 查询结果
	 */
	//已测试，获取获取正常，未交互
	@ResponseBody
	@RequestMapping(value = "loanInterestTableQuery", method = RequestMethod.GET)
	public List<Interesttable> loanInterestTableQuery(
			@RequestParam(value = "time", required = false, defaultValue = "2015-2016") String time,
			HttpServletRequest request) {
		List<Interesttable> result = null;
		if ("null".equals(time)) {
			logger.warn("贷款利率表查看模块：时间传输有误！");
		} else {
			logger.info("贷款利率表查看！");
			result = serviceGq.getLoanInterestInf(time);
		}
		return result;
	}
}
