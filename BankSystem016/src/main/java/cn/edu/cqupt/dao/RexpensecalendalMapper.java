package cn.edu.cqupt.dao;

import cn.edu.cqupt.model.Rexpensecalendal;

public interface RexpensecalendalMapper {
    int deleteByPrimaryKey(Integer recid);

    int insert(Rexpensecalendal record);

    int insertSelective(Rexpensecalendal record);

    Rexpensecalendal selectByPrimaryKey(Integer recid);

    int updateByPrimaryKeySelective(Rexpensecalendal record);

    int updateByPrimaryKey(Rexpensecalendal record);
}