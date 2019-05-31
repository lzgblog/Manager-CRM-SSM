package com.crm.service;

import java.util.List;

import com.system.po.College;
import com.system.po.DataResult;
import com.system.po.Student;
import com.system.po.StudentInfo;
import com.system.po.StudentsExt;

//学生管理服务
public interface AdminService {

	/**
	 * 使用PageHepler分页查询显示
	 * @param currentPage
	 * @param rows
	 * @return
	 */
	public DataResult findAllStudentByPageHelper(Integer currentPage, Integer rows);
	/**
	 * 管理员查询所有学生信息  返回StudentCustom类型
	 * @param page
	 * @param rows
	 * @return
	 */
	public StudentsExt findAllStudents(Integer currentPage,Integer rows);
	/**
	 * 根据学生姓名查询学生信息
	 * @param name
	 * @return
	 */
	public StudentsExt findStudentByName(String name);
	/**
	 * 查询所有院系的名称
	 * @return
	 */
	public List<College> findCollegeInfo();
	/**
	 * 添加学生信息
	 * (添加一个学生时应该同时给登录表中添加一条信息  表示该生的登录名和初始密码)
	 * @param student
	 */
	public void addStudent(Student student);
	/**
	 * 根据学生id 查询学生信息
	 * @param userid
	 * @return
	 */
	public StudentInfo findStudentById(Integer userid);
	/**
	 * 修改学生信息
	 * @param student
	 */
	public void editStudent(Student student);
	/**
	 * 根据学生id删除学生信息
	 * @param id
	 */
	public void removeStudent(Integer id);
}
