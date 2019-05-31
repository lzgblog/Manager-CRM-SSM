package com.system.mapper;

import com.system.po.Course;
import com.system.po.CourseCustom;
import com.system.po.Page;

import java.util.List;


public interface CourseMapperCustom {

    //分页查询课程信息
	public List<CourseCustom> findByPaging(Page page);
    
    //查询课程总数量
  	public int selectCourseCount();
  	
  	//根据课程id 查询课程信息以及学院名称
  	public CourseCustom selectCourseById(Integer id);
  	
  	//根据课程名称查询课程信息
  	public List<CourseCustom> findCourseByName(String name);
  	
  	//分页查询课程信息  返回类型Course
  	public List<Course> findCourseByPage(Page page);
}
