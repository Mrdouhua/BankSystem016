package cn.edu.cqupt.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import cn.edu.cqupt.model.Interesttable;
import cn.edu.cqupt.model.Loan;
import cn.edu.cqupt.model.LoanApprovalInfo;
import cn.edu.cqupt.model.LoanSchedule;

/**
 * 张冠群专用service 业务逻辑接口
 * 
 * @author ZhangGuanQun
 *
 */
public interface ServiceGq {
	/**
	 * 根据姓名查询贷款信息(包含字段：name(userinfo表),IDNumber,capital,cardID,status)
	 * 
	 * @param userName
	 *            姓名
	 * @param numPerPage
	 *            每页显示数据的条数
	 * @param pageNo
	 *            显示第几页的内容
	 * @return 查询结果
	 */
	List<LoanApprovalInfo> getLoanResultByName(String userName, String numPerPage, String pageNo);

	/**
	 * 根据身份证号查询贷款信息(包含字段：name(userinfo表),IDNumber,capital,cardID,status,
	 * loanType)
	 * 
	 * @param idNumber
	 *            身份证号
	 * @param numPerPage
	 *            每页显示数据的条数
	 * @param pageNo
	 *            显示第几页的内容
	 * @return 查询结果
	 */
	List<LoanApprovalInfo> getLoanResultByid(String idNumber, String numPerPage, String pageNo);

	/**
	 * 根据身份证号查询贷款信息(重载方法,用于贷款审核页自动显示信息模块)
	 * (包含字段：name(userinfo表),IDNumber,capital,cardID,status,loanType)
	 * 
	 * @param idNumber
	 *            身份证号
	 * @return 查询结果
	 */
	List<LoanApprovalInfo> getLoanResultByid(String idNumber);

	/**
	 * 根据姓名和身份证号查询贷款信息(包含字段：name(userinfo表),IDNumber,capital,cardID,status)
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
	List<LoanApprovalInfo> getLoanResultByNameAndid(String userName, String idNumber, String numPerPage, String pageNo);

	/**
	 * 所有搜索条件为空时，根据分页查询全部数据(包含字段：name(userinfo表),IDNumber,capital,cardID,status)
	 * 
	 * @param numPerPage
	 *            每页显示数据的条数
	 * @param pageNo
	 *            显示第几页的内容
	 * @return 查询结果
	 */
	List<LoanApprovalInfo> getLoanResultByNull(String numPerPage, String pageNo);

	/**
	 * 删除贷款信息(IDNumber做主键,Status=4)
	 * 
	 * @param idNumber
	 *            身份证号
	 * @return 结果
	 */
	int deleteLoanInf(String idNumber);

	/**
	 * 获取贷款证明材料下载地址(cardID做主键，获取proveFile字段)
	 * 
	 * @param cardID
	 *            主键：银行卡号
	 * @return 下载URL
	 */
	String getDownloadUrlByCardID(String cardID);

	/**
	 * 获取贷款信息进度(包含字段:loginName,loanType,createTime,status)
	 * 
	 * @param loginName
	 *            登录身份
	 * @return 查询结果
	 */
	LoanSchedule getLoanSchedule(String loginName);

	/**
	 * 获取贷款利率表信息(包含字段：interestValue)
	 * 
	 * @param time
	 *            查询时间
	 * @return 结果集合
	 */
	List<Interesttable> getLoanInterestInf(String time);

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
	int setLoanApplyInf(String idNumber, String loanType, String capital, String cardID, String uploadFilePath);

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
	String getUploadFilePath(MultipartFile file, String upload, HttpServletRequest request);

	Loan selectLoanByIdnumber(String idnumber);

	int selectLoanNumbers();

	int updateLoan(Loan loan);
}
