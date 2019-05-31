package com.crm.service;

import java.util.List;

import com.system.po.Teacher;
import com.system.po.TeacherCustom;
import com.system.po.TeacherExt;

//教师管理
public interface AdminTeacherService {

	/**
	 * 分页查询所有教师
	 * @param currentPage
	 * @param rows
	 * @return
	 */
	public TeacherExt findAllTeacher(Integer currentPage,Integer rows);
	/**
	 * 根据教师id 查询教师信息
	 * @param id
	 * @return
	 */
	public TeacherCustom findTeacherById(Integer id);
	/**
	 * 根据教师姓名查询教师信息
	 * @param name
	 * @return
	 */
	public TeacherExt findTeacherByName(String name);
	/**
	 * 添加教师
	 *  (添加一个教师时应该同时给登录表中添加一条信息  表示该教师的登录名和初始密码)
	 * @param teacher
	 */
	public void addTeacher(Teacher teacher);
	/**
	 * 修改教师信息
	 * @param teacher
	 */
	public void editTeacher(Teacher teacher);
	/**
	 * 根据id删除教师
	 * @param id
	 */
	public void removeTeacher(Integer id);
}
