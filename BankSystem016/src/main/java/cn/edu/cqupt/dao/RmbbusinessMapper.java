package cn.edu.cqupt.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import cn.edu.cqupt.model.Rmbbusiness;
import cn.edu.cqupt.model.RmbbusinessShow;

public interface RmbbusinessMapper {
    int deleteByPrimaryKey(String raccountid);

    int insert(Rmbbusiness record);

    int insertSelective(Rmbbusiness record);

    Rmbbusiness selectByPrimaryKey(String raccountid);

    int updateByPrimaryKeySelective(Rmbbusiness record);

    int updateByPrimaryKey(Rmbbusiness record);

    ArrayList<RmbbusinessShow> showRmbbusinessByLimit(@Param("offset") int offset ,@Param("size") int size);

	int selectaccountsnum();
}