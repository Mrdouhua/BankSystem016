package cn.edu.cqupt.dao;

import cn.edu.cqupt.model.Monthcalendar;

public interface MonthcalendarMapper {
    int deleteByPrimaryKey(Integer mid);

    int insert(Monthcalendar record);

    int insertSelective(Monthcalendar record);

    Monthcalendar selectByPrimaryKey(Integer mid);

    int updateByPrimaryKeySelective(Monthcalendar record);

    int updateByPrimaryKey(Monthcalendar record);
}