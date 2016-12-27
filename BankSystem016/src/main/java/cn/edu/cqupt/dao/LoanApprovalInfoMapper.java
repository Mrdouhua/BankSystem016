package cn.edu.cqupt.dao;

import java.util.HashMap;
import java.util.List;

import cn.edu.cqupt.model.LoanApprovalInfo;

/**
 * 贷款信息查询 数据库映射接口
 * 
 * @author ZhangGuanQun
 *
 */
public interface LoanApprovalInfoMapper {
	/**
	 * 根据姓名查询贷款信息
	 * 
	 * (包含字段：name(userinfo表),IDNumber,capital,cardID,status)
	 * 
	 * @param hm
	 *            (userName 姓名, numPerPage 每页显示数据的条数, pageNo 显示第几页的内容)
	 * @return 查询结果
	 */
	List<LoanApprovalInfo> getLoanInfByUserName(HashMap<String, String> hm);

	/**
	 * 根据身份证号查询贷款信息
	 * 
	 * (包含字段：name(userinfo表),IDNumber,capital,cardID,status)
	 * 
	 * @param hm
	 *            (idNumber 身份证号, numPerPage 每页显示数据的条数, pageNo 显示第几页的内容)
	 * @return 查询结果
	 */
	List<LoanApprovalInfo> getLoanInfByidNumber(HashMap<String, String> hm);

	/**
	 * 根据姓名和身份证号查询贷款信息
	 * 
	 * (包含字段：name(userinfo表),IDNumber,capital,cardID,status)
	 * 
	 * @param hm
	 *            (userName 姓名, idNumber 身份证号, numPerPage 每页显示数据的条数, pageNo
	 *            显示第几页的内容)
	 * @return 查询结果
	 */
	List<LoanApprovalInfo> getLoanInfByNameAndid(HashMap<String, String> hm);

	/**
	 * 所有搜索条件为空时，根据分页查询全部数据
	 * 
	 * (包含字段：name(userinfo表),IDNumber,capital,cardID,status)
	 * 
	 * @param hm
	 *            (numPerPage 每页显示数据的条数, pageNo 显示第几页的内容)
	 * @return 查询结果
	 */
	List<LoanApprovalInfo> getLoanInfByNull(HashMap<String, Integer> hm);

	/**
	 * 根据身份证号查询贷款信息
	 * 
	 * (用于审核页面的显示,包含字段：name(userinfo表),loanType,capital,cardID)
	 * 
	 * @param idNumber
	 *            身份证号
	 * @return 查询结果
	 */
	List<LoanApprovalInfo> getLoanInfByidNumberSingle(String idNumber);

	/**
	 * 根据身份证号删除贷款信息
	 * 
	 * (IDNumber做主键,Status=4)
	 * 
	 * @param idNumber
	 *            身份证号
	 * @return 结果
	 */
	int deleteApprovalInfByidNumber(String idNumber);

	/**
	 * 获取贷款证明材料下载地址
	 * 
	 * (cardID做主键，获取proveFile字段)
	 * 
	 * @param cardID
	 *            主键：银行卡号
	 * @return 下载URL
	 */
	String getDownloadUrlByCardID(String cardID);
}
