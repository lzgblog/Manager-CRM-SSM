package com.crm.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.service.AdminService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.system.mapper.AllStudentsMapper;
import com.system.mapper.CollegeMapper;
import com.system.mapper.StudentMapper;
import com.system.mapper.UserloginMapper;
import com.system.po.College;
import com.system.po.CollegeExample;
import com.system.po.DataResult;
import com.system.po.Page;
import com.system.po.Student;
import com.system.po.StudentExample;
import com.system.po.StudentExample.Criteria;
import com.system.po.StudentInfo;
import com.system.po.StudentsExt;
import com.system.po.Userlogin;
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private UserloginMapper userloginMapper;//注入登入表接口   添加一个学生或教师时需要给该表添加一条数据
	@Autowired
	private CollegeMapper collegeMapper;//注入学院映射接口  获取学生学院名称
	@Autowired
	private StudentMapper studentMapper;//注入学生映射接口  获取学生信息
	@Autowired
	private AllStudentsMapper allStudentsMapper;
	
	@Override
	public DataResult findAllStudentByPageHelper(Integer currentPage, Integer rows) {
		PageHelper.startPage(currentPage, rows);
		StudentExample example = new StudentExample();
		List<Student> list = studentMapper.selectByExample(example);
		PageInfo<Student> info = new PageInfo<>(list);
		DataResult result = new DataResult();
		result.setRows(info.getList());
		result.setTotal((int)info.getTotal());
		result.setCurrentPage(currentPage);
		return result;
	}
	
	
	@Override
	public StudentsExt findAllStudents(Integer currentPage, Integer rows) {
		Page p = new Page();
		Integer start=(currentPage-1)*rows;//从第几个开始
		p.setstart(start);
		p.setRows(rows);//一页显示多少
		//分页查询  使用limit
		List<StudentInfo> list = allStudentsMapper.selectAllStudents(p);
		//查询学生总数
		int count = allStudentsMapper.selectStudentsCount();
		//计算总页数
		int PageCount=(int)Math.ceil(count*1.0/rows);
		StudentsExt ext = new StudentsExt();
		ext.setCurrentPage(currentPage);
		ext.setPageCount(PageCount);
		ext.setList(list);
		return ext;
	}
	@Override
	public StudentsExt findStudentByName(String name) {
		//根据姓名在学生表student中查询信息
		StudentExample example = new StudentExample();
		Criteria criteria = example.createCriteria();//创建查询条件
		criteria.andUsernameLike("%"+name+"%");//查询条件  相当于  select * from student where name like %name%
		List<Student> list = studentMapper.selectByExample(example);
		List<StudentInfo> lists=new ArrayList<>();
		//根据学生表中的课程id获取 College表中的课程名称
		if(list !=null || list.size()>0) {
			for(Student stu:list) {//如果存在同名的情况  需要遍历出来一个一个的查询
				College college = collegeMapper.selectByPrimaryKey(stu.getCollegeid());
				//由于这是两个不同的实体类  需要封装进同一个实体类中
				StudentInfo info=new StudentInfo();
				info.setCollegeid(college.getCollegeid());
				info.setCollegeName(college.getCollegename());
				info.setUserid(stu.getUserid());
				info.setUsername(stu.getUsername());
				info.setSex(stu.getSex());
				info.setBirthyear(stu.getBirthyear());
				info.setGrade(stu.getGrade());
				lists.add(info);
			}
			
		}
		//处理条件查询后分页问题  略
		StudentsExt ext = new StudentsExt();
		ext.setCurrentPage(1);
		ext.setPageCount(1);
		ext.setList(lists);
		return ext;
	}
	@Override
	public List<College> findCollegeInfo() {
		CollegeExample example = new CollegeExample();
		List<College> list = collegeMapper.selectByExample(example);
		return list;
	}
	@Override
	public void addStudent(Student student) {
		//给学生表添加一个学生信息
		studentMapper.insert(student);
		
		
		//(添加一个学生时应该同时给登录表中添加一条信息  表示该生的登录名和初始密码)
		//给学生添加一个登录账号账号为userID的  设置初始密码123  设置登录权限2（2表示学生）
		Userlogin userlogin = new Userlogin();
		//给Userlogin对象设值   登录表中的登录名为学生id  两个表中的字段类型不同需要转换
		userlogin.setUsername(student.getUserid()+"");
		//添加初始密码
		userlogin.setPassword("123");
		//添加权限
		userlogin.setRole(2);
		//添加一条数据
		userloginMapper.insert(userlogin);
		
	}
	@Override
	public StudentInfo findStudentById(Integer userid) {
		StudentInfo info = allStudentsMapper.selectStudentById(userid);
		return info;
	}
	@Override
	public void editStudent(Student student) {
		studentMapper.updateByPrimaryKey(student);
	}
	@Override
	public void removeStudent(Integer id) {
		studentMapper.deleteByPrimaryKey(id);
		
	}
	

}
