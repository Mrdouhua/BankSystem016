package cn.edu.cqupt.dao;

import cn.edu.cqupt.model.Creditcard;

public interface CreditcardMapper {
    int deleteByPrimaryKey(String caccountid);

    int insert(Creditcard record);

    int insertSelective(Creditcard record);

    Creditcard selectByPrimaryKey(String caccountid);

    int updateByPrimaryKeySelective(Creditcard record);

    int updateByPrimaryKey(Creditcard record);
}