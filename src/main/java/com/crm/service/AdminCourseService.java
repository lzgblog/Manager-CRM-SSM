package com.crm.service;

import java.util.List;

import com.system.po.College;
import com.system.po.Course;
import com.system.po.CourseCustom;
import com.system.po.CourseExt;
import com.system.po.Teacher;

//课程管理服务
public interface AdminCourseService {

	/**
	 * 查询所有课程  并分页
	 * @param page
	 * @param rows
	 * @return
	 */
	public CourseExt findAllCourse(Integer currentPage,Integer rows);
	/**
	 * 查询所有老师
	 * @return
	 */
	public List<Teacher> findAllTeacher();
	/**
	 * 查询所有院系
	 * @return
	 */
	public List<College> findCollegeInfo();
	/**
	 * 添加课程
	 * @param course
	 */
	public void addCourse(Course course);
	/**
	 * 根据课程id查询课程信息
	 * @param courseid
	 * @return
	 */
	public CourseCustom findCourseById(Integer courseid);
	/**
	 * 修改课程信息
	 * @param course
	 */
	public void editCourse(Course course);
	/**
	 * 删除课程
	 * @param id
	 */
	public void removeCourse(Integer id);
	/**
	 * 根据课程名称模糊查询数据
	 * @param name
	 * @return
	 */
	public CourseExt findCourseByName(String name);
}
