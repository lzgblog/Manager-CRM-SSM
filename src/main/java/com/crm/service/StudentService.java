package com.crm.service;


import java.util.List;

import com.system.po.Course;
import com.system.po.CourseAndScore;
import com.system.po.Selectedcourse;
import com.system.po.Student;
import com.system.po.StudentCoursePage;
import com.system.po.StudentInfo;

//学生服务接口
public interface StudentService {

	/**
	 * 根据当前页  和每页显示的条数  对数据库进行分页查询课程
	 * @param currentPage
	 * @param rows
	 * @return
	 */
	public StudentCoursePage findAllCourse(Integer currentPage, Integer rows);
	/**
	 * 根据课程名称查找课程信息   包括教师姓名
	 * @param name
	 * @return
	 */
	public StudentCoursePage selectCourseByName(String name);
	
	/**
	 * 根据课程id和学生id确认是否选过这门课程
	 * @param courseID
	 * @param studentID
	 */
	public List<Selectedcourse> checkSelectedCourse(Integer courseID,Integer studentID);
	
	/**
	 * 根据课程id 、 学生id添加一门选课
	 * @param id
	 * @param studentID
	 */
	public void addSelectedCourse(Integer courseID,Integer studentID);
	/**
	 * 根据用户id  查询该用户已选的课程   数据库中没有成绩的课程的信息
	 * @return
	 */
	public List<Course> selectedCourseByStId(Integer studentID);
	/**
	 * 根据课程id和登录用户id来进行退课   确定退的是该登录用户的课程
	 * 删除已选课程 
	 * @param courseID
	 * @param studentID
	 */
	public void outCourse(Integer courseID,Integer studentID);
	/**
	 * 根据用户id查找所有修完的课程
	 * @param studentID
	 * @return
	 */
	public List<CourseAndScore> overCourse(Integer studentID);
	/**
	 * 根据学生id查询学生信息
	 * @param studentID
	 * @return
	 */
	public StudentInfo findStudentInfoById(Integer studentID);
	/**
	 * 修改学生个人信息
	 * @param student
	 */
	public void updateStudentInfo(Student student);
}
