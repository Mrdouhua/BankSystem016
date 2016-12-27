package cn.edu.cqupt.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.fabric.xmlrpc.base.Data;

import cn.edu.cqupt.core.UUIDFactory;
import cn.edu.cqupt.dao.InteresttableMapper;
import cn.edu.cqupt.dao.LoanApprovalInfoMapper;
import cn.edu.cqupt.dao.LoanMapper;
import cn.edu.cqupt.dao.LoanScheduleMapper;
import cn.edu.cqupt.model.Interesttable;
import cn.edu.cqupt.model.Loan;
import cn.edu.cqupt.model.LoanApprovalInfo;
import cn.edu.cqupt.model.LoanSchedule;
import cn.edu.cqupt.service.ServiceGq;

@Service("serviceGq")
public class ServiceGqImpl implements ServiceGq {

	@Resource
	private LoanApprovalInfoMapper loanApprovalInfoMapper;

	public LoanApprovalInfoMapper getLoanApprovalInfoMapper() {
		return loanApprovalInfoMapper;
	}

	public void setLoanApprovalInfoMapper(LoanApprovalInfoMapper loanApprovalInfoMapper) {
		this.loanApprovalInfoMapper = loanApprovalInfoMapper;
	}

	@Resource
	private LoanScheduleMapper loanScheduleMapper;

	public LoanScheduleMapper getLoanScheduleMapper() {
		return loanScheduleMapper;
	}

	public void setLoanScheduleMapper(LoanScheduleMapper loanScheduleMapper) {
		this.loanScheduleMapper = loanScheduleMapper;
	}

	@Resource
	private InteresttableMapper interesttableMapper;

	public InteresttableMapper getInteresttableMapper() {
		return interesttableMapper;
	}

	public void setInteresttableMapper(InteresttableMapper interesttableMapper) {
		this.interesttableMapper = interesttableMapper;
	}

	@Resource
	private LoanMapper loanMapper;

	public LoanMapper getLoanMapper() {
		return loanMapper;
	}

	public void setLoanMappers(LoanMapper loanMapper) {
		this.loanMapper = loanMapper;
	}

	/**
	 * 根据姓名查询贷款信息
	 * 
	 * (包含字段：name(userinfo表),IDNumber,capital,cardID,status)
	 * 
	 * @param userName
	 *            姓名
	 * @param numPerPage
	 *            每页显示数据的条数
	 * @param pageNo
	 *            显示第几页的内容
	 * @return 查询结果
	 */
	@Override
	public List<LoanApprovalInfo> getLoanResultByName(String userName, String numPerPage, String pageNo) {
		// TODO Auto-generated method stub
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("userName", userName.trim());
		List<LoanApprovalInfo> result = null;
		result = loanApprovalInfoMapper.getLoanInfByUserName(hm);
		for (LoanApprovalInfo e:result){
			if (e.getStatus().equals("0")){
				e.setStatus("待审核");
				continue;
			}
			if (e.getStatus().equals("1")){
				e.setStatus("审核通过");
				continue;
			}
		}
		return result;
	}

	/**
	 * 根据身份证号查询贷款信息
	 * 
	 * (包含字段：name(userinfo表),IDNumber,capital,cardID,status)
	 * 
	 * @param idNumber
	 *            身份证号
	 * @param numPerPage
	 *            每页显示数据的条数
	 * @param pageNo
	 *            显示第几页的内容
	 * @return 查询结果
	 */
	@Override
	public List<LoanApprovalInfo> getLoanResultByid(String idNumber, String numPerPage, String pageNo) {
		// TODO Auto-generated method stub
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("idNumber", idNumber.trim());
		List<LoanApprovalInfo> result = null;
		result = loanApprovalInfoMapper.getLoanInfByidNumber(hm);
		for (LoanApprovalInfo e:result){
			if (e.getStatus().equals("0")){
				e.setStatus("待审核");
				continue;
			}
			if (e.getStatus().equals("1")){
				e.setStatus("审核通过");
				continue;
			}
		}
		return result;
	}

	/**
	 * 根据姓名和身份证号查询贷款信息
	 * 
	 * (包含字段：name(userinfo表),IDNumber,capital,cardID,status)
	 * 
	 * @param userName
	 *            姓名
	 * @param idNumber
	 *            身份证号
	 * @param numPerPage
	 *            每页显示数据的条数
	 * @param pageNo
	 *            显示第几页的内容
	 * @return 查询结果
	 */
	@Override
	public List<LoanApprovalInfo> getLoanResultByNameAndid(String userName, String idNumber, String numPerPage,
			String pageNo) {
		// TODO Auto-generated method stub
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("userName", userName.trim());
		hm.put("idNumber", idNumber.trim());

		List<LoanApprovalInfo> result = null;
		result = loanApprovalInfoMapper.getLoanInfByNameAndid(hm);
		for (LoanApprovalInfo e:result){
			if (e.getStatus().equals("0")){
				e.setStatus("未审核");
				continue;
			}
			if (e.getStatus().equals("1")){
				e.setStatus("审核通过");
				continue;
			}
		}
		return result;
	}

	/**
	 * 删除贷款信息
	 * 
	 * (IDNumber做主键,Status=4)
	 * 
	 * @param idNumber
	 *            身份证号
	 * @return 结果状态
	 */
	@Override
	public int deleteLoanInf(String idNumber) {
		// TODO Auto-generated method stub
		int status = loanApprovalInfoMapper.deleteApprovalInfByidNumber(idNumber.trim());
		return status;
	}

	/**
	 * 所有搜索条件为空时，根据分页查询全部数据
	 * 
	 * (包含字段：name(userinfo表),IDNumber,capital,cardID,status)
	 * 
	 * @param numPerPage
	 *            每页显示数据的条数
	 * @param pageNo
	 *            显示第几页的内容
	 * @return 查询结果
	 */
	@Override
	public List<LoanApprovalInfo> getLoanResultByNull(String numPerPage, String pageNo) {
		// TODO Auto-generated method stub
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		int current=Integer.parseInt(pageNo);
		int size=Integer.parseInt(numPerPage);
		current=(current-1)*size;
		hm.put("numPerPage", current);
		hm.put("pageNo", current+size);
		List<LoanApprovalInfo> result = null;
		result = loanApprovalInfoMapper.getLoanInfByNull(hm);
		for (LoanApprovalInfo e:result){
			if (e.getStatus().equals("0")){
				e.setStatus("未审核");
				break;
			}
			if (e.getStatus().equals("1")){
				e.setStatus("审核通过");
				break;
			}
		}
		return result;
	}

	/**
	 * 根据身份证号查询贷款信息(重载方法,用于贷款审核页自动显示信息模块)
	 * 
	 * (包含字段：name(userinfo表),loanType,capital,cardID)
	 * 
	 * @param idNumber
	 *            身份证号
	 * @return 查询结果
	 */
	@Override
	public List<LoanApprovalInfo> getLoanResultByid(String idNumber) {
		List<LoanApprovalInfo> result = null;
		System.out.println(idNumber);
		result = loanApprovalInfoMapper.getLoanInfByidNumberSingle(idNumber.trim());
		for (LoanApprovalInfo e:result){
			if (e.getStatus().equals("0")){
				e.setStatus("未审核");
				break;
			}
			if (e.getStatus().equals("1")){
				e.setStatus("审核通过");
				break;
			}
		}
		return result;
	}

	/**
	 * 获取贷款证明材料下载地址
	 * 
	 * (cardID做主键，获取proveFile字段)
	 * 
	 * @param cardID
	 *            主键：银行卡号
	 * @return 下载URL
	 */
	@Override
	public String getDownloadUrlByCardID(String cardID) {
		// TODO Auto-generated method stub
		String downloadUrl = null;
		downloadUrl = loanApprovalInfoMapper.getDownloadUrlByCardID(cardID);
		return downloadUrl;
	}

	/**
	 * 获取贷款信息进度(包含字段:loginName,loanType,createTime,status)
	 * 
	 * @param loginName
	 *            登录身份
	 * @return 查询结果
	 */
	@Override
	public LoanSchedule getLoanSchedule(String loginName) {
		// TODO Auto-generated method stub
		LoanSchedule result = loanScheduleMapper.getLoanScheduleByName(loginName.trim());
		if (result != null) {
			HashMap<String, String> statusMap = new HashMap<String, String>();
			statusMap.put("0", "待审核");
			statusMap.put("1", "审核通过");
			statusMap.put("4", "拒绝贷款");
			String status = result.getStatus();
			if (statusMap.containsKey(status)) {
				result.setStatus(statusMap.get(status));
			}
		}
		return result;
	}

	/**
	 * 获取贷款利率表信息(包含字段：interestValue)
	 * 
	 * @param time
	 *            查询时间
	 * @return 结果集合
	 */
	@Override
	public List<Interesttable> getLoanInterestInf(String time) {
		// TODO Auto-generated method stub
		List<Interesttable> result = interesttableMapper.getLoanInterestByTime(time);
		return result;
	}

	/**
	 * 将用户提交的贷款信息插入数据库
	 * 
	 * @param idNumber
	 *            身份证号
	 * @param loanType
	 *            贷款类型
	 * @param capital
	 *            贷款金额
	 * @param cardID
	 *            银行卡号
	 * @param uploadFilePath
	 *            上传文件的服务器路径
	 * @return 结果状态
	 */
	@Override
	public int setLoanApplyInf(String idNumber, String loanType, String capital, String cardID, String uploadFilePath) {
		// TODO Auto-generated method stub
		BigDecimal capitalNumber = new BigDecimal(capital.trim());
		HashMap<String, Object> insertMap = new HashMap<String, Object>();
		Date time=new Date();
		insertMap.put("idNumber", idNumber.trim());
		insertMap.put("loanType", loanType.trim());
		insertMap.put("capital", capitalNumber);
		insertMap.put("cardID", cardID.trim());
		insertMap.put("uploadFilePath", uploadFilePath.trim());
		insertMap.put("creatTime", time);
		insertMap.put("editTime", time);
		int status = loanMapper.insertUserLoanApply(insertMap);
		return status;
	}

	/**
	 * 获取上传文件的服务器路径
	 * 
	 * @param file
	 *            上传文件
	 * @param upload
	 *            客户端文件上传路径
	 * @param request
	 *            HttpServlet请求
	 * @return 上传文件的服务器路径
	 */
	@Override
	public String getUploadFilePath(MultipartFile file, String upload, HttpServletRequest request) {
		// TODO Auto-generated method stub
		ServletContext sc = request.getSession().getServletContext();
		String dir = sc.getRealPath(upload);
		/* 获取上传文件的基本信息 */
		String basepath = dir + "/proveFile";
		String originalFileName = file.getOriginalFilename();
		String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
		String filename = (String) UUIDFactory.generateUUID();
		String FileName = filename + ext;
		File targetFile = new File(basepath, FileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		/* 文件上传 */
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			System.out.println("文件上传部分：上传错误！" + e);
		}
		/* 文件路径上传到数据库中 */
		String serverUploadFilePath = upload + "/proveFile/" + FileName;
		System.out.println("filepath:"+serverUploadFilePath);
		return serverUploadFilePath;
	}

	@Override
	public Loan selectLoanByIdnumber(String idnumber) {
		return loanMapper.selectLoanByIdnumber(idnumber);
	}

	@Override
	public int selectLoanNumbers() {
		return loanMapper.selectLoanNumbers();
	}

	@Override
	public int updateLoan(Loan loan) {
		return loanMapper.updateByPrimaryKeySelective(loan);
	}
}
