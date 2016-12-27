package cn.edu.cqupt.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.edu.cqupt.model.Loginuser;


public interface LoginuserMapper {
    int deleteByPrimaryKey(String userid);

    int insert(Loginuser record);

    int insertSelective(Loginuser record);

    Loginuser selectByPrimaryKey(String userid);

    int updateByPrimaryKeySelective(Loginuser record);

    int updateByPrimaryKey(Loginuser record);

	Loginuser isHaveUser(String username);

	int selectsystemuserCounts();

	ArrayList<Loginuser> showLoginuserByLimit(HashMap<String, Integer> param);

	ArrayList<Loginuser> getLoginuserByname(String username);

	int deleteByuserid(String userid);

}