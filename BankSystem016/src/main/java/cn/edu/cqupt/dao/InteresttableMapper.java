package cn.edu.cqupt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.edu.cqupt.model.Interesttable;

public interface InteresttableMapper {
    int insert(Interesttable record);

    int insertSelective(Interesttable record);

    /**
	 * 通过时间获取贷款利率表信息(包含字段：interestValue)
	 * @param time 查询时间
	 * @return 结果集合
	 */
	List<Interesttable> getLoanInterestByTime(String time);
    
	//修改存储利率
	int updatedDepositInterest(Interesttable record);
	
	//修改贷款利率
	int updatedLoanInterest(Interesttable record);
	
	Interesttable select(@Param("depositType") String depositType, 
			@Param("cycle") String cycle, @Param("time") String time);
	
    
}