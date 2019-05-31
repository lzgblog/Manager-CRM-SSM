package com.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.service.AdminCourseService;
import com.system.mapper.CollegeMapper;
import com.system.mapper.CourseMapper;
import com.system.mapper.CourseMapperCustom;
import com.system.mapper.TeacherMapper;
import com.system.po.College;
import com.system.po.CollegeExample;
import com.system.po.Course;
import com.system.po.CourseCustom;
import com.system.po.CourseExt;
import com.system.po.Page;
import com.system.po.Teacher;
import com.system.po.TeacherExample;

@Service
public class AdminCourseServiceImpl implements AdminCourseService {

	@Autowired
	private CourseMapper courseMapper;//注入课程映射接口
	@Autowired
	private CollegeMapper collegeMapper;//注入学院映射接口  获取学生学院名称
	@Autowired
	private TeacherMapper teacherMapper;//注入老师映射接口
	@Autowired
	private CourseMapperCustom courseMapperCustom;//注入课程扩展映射接口
	@Override
	public CourseExt findAllCourse(Integer currentPage, Integer rows) {
		Page p = new Page();
		Integer start=(currentPage-1)*rows;//从第几个开始
		p.setstart(start);
		p.setRows(rows);//一页显示多少
		//分页查询  使用limit
		List<CourseCustom> list = courseMapperCustom.findByPaging(p);
		//查询课程总数
		int count = courseMapperCustom.selectCourseCount();
		//计算总页数
		int PageCount=(int)Math.ceil(count*1.0/rows);
		CourseExt ext = new CourseExt();
		ext.setCurrentPage(currentPage);
		ext.setPageCount(PageCount);
		ext.setList(list);
		return ext;
	}
	
	@Override
	public List<Teacher> findAllTeacher() {
		TeacherExample example = new TeacherExample();
		List<Teacher> list = teacherMapper.selectByExample(example);
		return list;
	}
	@Override
	public List<College> findCollegeInfo() {
		CollegeExample example = new CollegeExample();
		List<College> list = collegeMapper.selectByExample(example);
	
		return list;
	}

	@Override
	public void addCourse(Course course) {
		courseMapper.insert(course);
	}

	@Override
	public CourseCustom findCourseById(Integer courseid) {
		CourseCustom courseCustom = courseMapperCustom.selectCourseById(courseid);
		return courseCustom;
	}

	@Override
	public void editCourse(Course course) {
		courseMapper.updateByPrimaryKey(course);
	}

	@Override
	public void removeCourse(Integer id) {
		courseMapper.deleteByPrimaryKey(id);
	}

	@Override
	public CourseExt findCourseByName(String name) {
		List<CourseCustom> list = courseMapperCustom.findCourseByName(name);
		CourseExt ext = new CourseExt();
		ext.setCurrentPage(1);
		ext.setPageCount(1);
		ext.setList(list);
		return ext;
	}

}
