package cn.edu.cqupt.dao;

import java.util.HashMap;

import cn.edu.cqupt.model.Loan;

public interface LoanMapper {
    int deleteByPrimaryKey(Integer loanid);

    int insert(Loan record);

    int insertSelective(Loan record);

    Loan selectByPrimaryKey(Integer loanid);

    int updateByPrimaryKeySelective(Loan record);

    int updateByPrimaryKey(Loan record);
    /**
	 * 插入用户贷款提交信息
	 * 
	 * @param insertMap
	 *            用户贷款提交信息下集合
	 * @return 结果状态
	 */
	int insertUserLoanApply(HashMap<String, Object> insertMap);

	Loan selectLoanByIdnumber(String idnumber);

	int selectLoanNumbers();
}