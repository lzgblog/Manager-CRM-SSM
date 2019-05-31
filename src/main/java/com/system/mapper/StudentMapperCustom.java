package com.system.mapper;

import com.system.po.StudentCustom;

import java.util.List;


public interface StudentMapperCustom {

    

    //查询学生信息，和其选课信息
    StudentCustom findStudentAndSelectCourseListById(Integer id) throws Exception;

}
