package cn.edu.cqupt.dao;

import java.util.HashMap;

import cn.edu.cqupt.model.Userinfo;

public interface UserinfoMapper {
    int deleteByPrimaryKey(String idnumber);

    int insert(Userinfo record);

    int insertSelective(Userinfo record);

    Userinfo selectByPrimaryKey(String idnumber);

    int updateByPrimaryKeySelective(Userinfo record);

    int updateByPrimaryKey(Userinfo record);

	Userinfo selectUserinfoByuserid(String userid);

	void insertUserinfo(HashMap<String, Object> param);
}