package cn.edu.cqupt.dao;

import cn.edu.cqupt.model.Foreigncurrency;

public interface ForeigncurrencyMapper {
    int deleteByPrimaryKey(String faccountid);

    int insert(Foreigncurrency record);

    int insertSelective(Foreigncurrency record);

    Foreigncurrency selectByPrimaryKey(String faccountid);

    int updateByPrimaryKeySelective(Foreigncurrency record);

    int updateByPrimaryKey(Foreigncurrency record);
}