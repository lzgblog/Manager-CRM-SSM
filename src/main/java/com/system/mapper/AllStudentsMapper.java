package com.system.mapper;

import java.util.List;

import com.system.po.Page;
import com.system.po.StudentInfo;
import com.system.po.Userlogin;

//所有学生
public interface AllStudentsMapper {

	//查询所有学生信息   StudentCustom该属性类可以封装学生个人信息和所在学院
	public List<StudentInfo> selectAllStudents(Page page);
	//查询学生总数量
	public int selectStudentsCount();
	//根据学生id 查询学生信息
	public StudentInfo selectStudentById(Integer userid);
	
	
}
