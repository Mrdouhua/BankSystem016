package cn.edu.cqupt.dao;

import cn.edu.cqupt.model.LoanSchedule;

public interface LoanScheduleMapper {
	/**
	 * 根据登录身份获取贷款信息进度(包含字段:loginName,loanType,createTime,status)
	 * @param loginName
	 * @return
	 */
	LoanSchedule getLoanScheduleByName(String loginName);
}
