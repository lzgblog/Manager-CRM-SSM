package com.crm.service;

import java.util.List;


import com.system.po.Course;
import com.system.po.Score;
import com.system.po.Teacher;
import com.system.po.TeacherCustom;

public interface TeacherService {

	/**
	 * 根据教师id  即登录的用户名userName  查询该教师所有的授课
	 * @return
	 */
	public List<Course> findAllCoursByName(Integer teacherID);
	/**
	 * 根据课程名称查询课程信息
	 * @param name
	 * @return
	 */
	public List<Course> selectCourse(String name,int teacherID);
	/**
	 * 根据课程ID查询 课程成绩  以及选该课程的学生
	 * @param courseid
	 * @return
	 */
	public List<Score> findGradeCourseById(Integer courseid);
	/**
	 * 根据学生id和课程id 确定一个  修改学生成绩
	 * @param courseid
	 * @param userid
	 */
	public void updateMark(Integer courseid,Integer userid,Integer mark);
	/**
	 * 根据教师id 查询教师信息  包括该教师所在的学院名称
	 * @param id
	 * @return
	 */
	public TeacherCustom findTeacherById(Integer id);
	/**
	 * 修改教师个人信息
	 * @param teacher
	 */
	public void editTeacher(Teacher teacher);
}
