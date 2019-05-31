package com.crm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.service.AdminTeacherService;
import com.system.mapper.CollegeMapper;
import com.system.mapper.TeacherMapper;
import com.system.mapper.TeacherMapperCustom;
import com.system.mapper.UserloginMapper;
import com.system.po.College;
import com.system.po.Page;
import com.system.po.Teacher;
import com.system.po.TeacherCustom;
import com.system.po.TeacherExample;
import com.system.po.TeacherExample.Criteria;
import com.system.po.TeacherExt;
import com.system.po.Userlogin;
@Service
public class AdminTeacherServiceImpl implements AdminTeacherService {
	@Autowired
	private UserloginMapper userloginMapper;//注入登录表接口   添加教师时给登录表添加一个教师登录的账号
	@Autowired
	private CollegeMapper collegeMapper;//注入学院映射接口
	@Autowired
	private TeacherMapper teacherMapper;//注入教师映射接口
	@Autowired
	public TeacherMapperCustom teacherMapperCustom;//注入教师扩展接口映射
	@Override
	public TeacherExt findAllTeacher(Integer currentPage, Integer rows) {
		Page p = new Page();
		Integer start=(currentPage-1)*rows;//从第几个开始
		p.setstart(start);
		p.setRows(rows);//一页显示多少
		//分页查询  使用limit
		List<TeacherCustom> list = teacherMapperCustom.findAllTeacherByPaging(p);
		//查询学生总数
		int count = teacherMapperCustom.selectTeacherCount();
		//计算总页数
		int PageCount=(int)Math.ceil(count*1.0/rows);
		TeacherExt ext = new TeacherExt();
		ext.setCurrentPage(currentPage);
		ext.setPageCount(PageCount);
		ext.setList(list);
		return ext;
	}
	@Override
	public TeacherCustom findTeacherById(Integer id) {
		TeacherCustom teacher = teacherMapperCustom.findTeacherById(id);
		return teacher;
	}
	@Override
	public TeacherExt findTeacherByName(String name) {
		//查询教师信息
		TeacherExample example = new TeacherExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameLike("%"+name+"%");
		List<Teacher> list = teacherMapper.selectByExample(example);
		//根据查询出的教师信息中的学院id  查询学院名称
		ArrayList<TeacherCustom> list2 = new ArrayList<>();
		if(list !=null || list.size()>0) {
			for (Teacher teacher : list) {
				TeacherCustom custom = new TeacherCustom();
				//类拷贝   将遍历出的Teacher信息 拷贝到TeacherCustom类中  （Teacher为TeacherCustom的父类）
                org.springframework.beans.BeanUtils.copyProperties(teacher, custom);
                //获取课程名
				College college = collegeMapper.selectByPrimaryKey(teacher.getCollegeid());
				custom.setcollegeName(college.getCollegename());
				//将custom 放入list中
				list2.add(custom);
			}
		}
		TeacherExt ext = new TeacherExt();
		ext.setCurrentPage(1);
		ext.setPageCount(1);
		ext.setList(list2);
		return ext;
	}
	@Override
	public void addTeacher(Teacher teacher) {
		//给教师表添加一条教师信息
		teacherMapper.insert(teacher);
		
		
		//(添加一个教师时应该同时给登录表中添加一条信息  表示该教师的登录名和初始密码)
		//添加教师时给登录表添加一个教师登录的账号   教师id为登录名  设置初始密码为123  设置权限为1（1表示教师）
		Userlogin userlogin = new Userlogin();
		//给Userlogin对象设值   登录表中的登录名为教师id  两个表中的字段类型不同需要转换
		userlogin.setUsername(teacher.getUserid()+"");
		//设置初始密码
		userlogin.setPassword("123");
		//设置权限
		userlogin.setRole(1);
		//添加一个登录用户信息
		userloginMapper.insert(userlogin);
	}
	@Override
	public void editTeacher(Teacher teacher) {
		teacherMapper.updateByPrimaryKey(teacher);
	}
	@Override
	public void removeTeacher(Integer id) {
		teacherMapper.deleteByPrimaryKey(id);
	}
	
	
}
