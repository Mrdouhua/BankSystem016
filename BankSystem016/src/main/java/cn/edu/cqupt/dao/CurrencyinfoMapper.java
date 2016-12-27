package cn.edu.cqupt.dao;

import cn.edu.cqupt.model.Currencyinfo;

public interface CurrencyinfoMapper {
    int deleteByPrimaryKey(String ciid);

    int insert(Currencyinfo record);

    int insertSelective(Currencyinfo record);

    Currencyinfo selectByPrimaryKey(String ciid);

    int updateByPrimaryKeySelective(Currencyinfo record);

    int updateByPrimaryKey(Currencyinfo record);
}