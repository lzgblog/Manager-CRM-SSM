package com.system.mapper;

import com.system.po.Page;
import com.system.po.TeacherCustom;

import java.util.List;


public interface TeacherMapperCustom {

    //分页查询教师信息
    public List<TeacherCustom> findAllTeacherByPaging(Page page);
    //查询教师总数
    public int selectTeacherCount();
    //根据id 查询教师
    public TeacherCustom findTeacherById(Integer id);
}
