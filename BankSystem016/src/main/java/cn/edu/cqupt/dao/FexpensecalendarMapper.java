package cn.edu.cqupt.dao;

import cn.edu.cqupt.model.Fexpensecalendar;

public interface FexpensecalendarMapper {
    int deleteByPrimaryKey(Integer fecid);

    int insert(Fexpensecalendar record);

    int insertSelective(Fexpensecalendar record);

    Fexpensecalendar selectByPrimaryKey(Integer fecid);

    int updateByPrimaryKeySelective(Fexpensecalendar record);

    int updateByPrimaryKey(Fexpensecalendar record);
}