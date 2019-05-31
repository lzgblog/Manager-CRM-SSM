package com.crm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.service.StudentService;
import com.system.mapper.AllStudentsMapper;
import com.system.mapper.CourseMapper;
import com.system.mapper.CourseMapperCustom;
import com.system.mapper.SelectedMapper;
import com.system.mapper.SelectedcourseMapper;
import com.system.mapper.StudentMapper;
import com.system.mapper.TeacherMapper;
import com.system.po.Course;
import com.system.po.CourseAndScore;
import com.system.po.CourseExample;
import com.system.po.Page;
import com.system.po.Selectedcourse;
import com.system.po.SelectedcourseExample;
import com.system.po.SelectedcourseExample.Criteria;
import com.system.po.Student;
import com.system.po.StudentCourse;
import com.system.po.StudentCoursePage;
import com.system.po.StudentInfo;
import com.system.po.Teacher;
@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentMapper studentMapper;//注入学生映射接口   修改学生信息
	@Autowired
	private AllStudentsMapper allStudentsMapper;//注入student扩展映射接口  查询学生信息 包括该生所在的学院名称
	@Autowired
	private SelectedMapper selectedMapper;//自定义的课程选择接口
	@Autowired
	private CourseMapper CourseMapper;//课程接口
	@Autowired
	private CourseMapperCustom courseMapperCustom;//注入课程扩展映射接口  可以查询课程数量
	@Autowired
	private TeacherMapper teacherMapper;//教师映射接口  可以获取教师姓名
	@Autowired
	private SelectedcourseMapper selectedcourseMapper;//注入课程映射接口
	
	@Override
	public StudentCoursePage findAllCourse(Integer currentPage, Integer rows) {
		Page page=new Page();//设置分页
		//设置开始位置
		Integer start=(currentPage-1)*rows;//从第几个开始
		page.setstart(start);
		page.setRows(rows);
		//根据分页信息   查询出Course表中所有的数据
		List<Course> listCourse = courseMapperCustom.findCourseByPage(page);
		
		List<StudentCourse> list=new ArrayList<>();
		for (Course course : listCourse) {//循环遍历课程信息   并放到新的容器中
			//创建课程扩展类 该类中包含课程所有信息和教师姓名 
			StudentCourse studentCourse = new StudentCourse();
			//将遍历的课程course属性复制到课程扩展类StudentCourse中  使用spring的BeanUtils
			BeanUtils.copyProperties(course, studentCourse);
			//根据course表中的教师id查询教师姓名  并放入StudentCourse类中
			Teacher teacher = teacherMapper.selectByPrimaryKey(course.getTeacherid());
			studentCourse.setUsername(teacher.getUsername());//放入教师姓名
			list.add(studentCourse);
		}
		//查询所有课程的数量
		int count = courseMapperCustom.selectCourseCount();
		//计算总页数
		int pageCount=(int)Math.ceil(count*1.0/rows);
		
		StudentCoursePage scp = new StudentCoursePage();
		scp.setListCourse(list);
		scp.setCurrentPage(currentPage);//当前页
		scp.setPageCount(pageCount);//总页数
		return scp;
	}

	
	@Override
	public StudentCoursePage selectCourseByName(String name) {
		//根据课程名称  模糊查询课程信息
		CourseExample example = new CourseExample();
		com.system.po.CourseExample.Criteria criteria = example.createCriteria();
		//添加模糊查找条件
		criteria.andCoursenameLike("%"+name+"%");
		List<Course> list = CourseMapper.selectByExample(example);//查出所有符合条件的数据集合
		List<StudentCourse> list2 = new ArrayList<>();
		for(Course course : list) {//遍历course数据
			//创建一个存放课程信息和教师名称的容器
			StudentCourse course2 = new StudentCourse();
			//使用spring的BeanUtils将课程属性copy到课程扩展类中
			BeanUtils.copyProperties(course, course2);
			//根据teacherID到教师表中查找 教师
			Teacher teacher = teacherMapper.selectByPrimaryKey(course.getTeacherid());
			//将教师名称存放到实体类中
			course2.setUsername(teacher.getUsername());//教师名称
			//放入集合
			list2.add(course2);
		}
		StudentCoursePage ext = new StudentCoursePage();
		ext.setListCourse(list2);
		ext.setCurrentPage(1);
		ext.setPageCount(1);
		return ext;
	}

	
	@Override
	public void addSelectedCourse(Integer courseID, Integer studentID) {
		Selectedcourse selectedcourse = new Selectedcourse();
		selectedcourse.setCourseid(courseID);
		selectedcourse.setStudentid(studentID);
		//学生添加一门课程
		selectedcourseMapper.insert(selectedcourse);
		
	}


	@Override
	public List<Selectedcourse> checkSelectedCourse(Integer courseID, Integer studentID) {
		Selectedcourse selectedcourse = new Selectedcourse();
		selectedcourse.setCourseid(courseID);
		selectedcourse.setStudentid(studentID);
		List<Selectedcourse> list = selectedMapper.checkSelectedcourse(selectedcourse);
		return list;
	}


	@Override
	public List<Course> selectedCourseByStId(Integer studentID) {
		//根据用户id查找所有选过的课程
		SelectedcourseExample example = new SelectedcourseExample();
		Criteria criteria = example.createCriteria();
		criteria.andStudentidEqualTo(studentID);//查询所有符合条件的数据
		List<Selectedcourse> list = selectedcourseMapper.selectByExample(example);
		List<Course> list2=new ArrayList<>();
		for (Selectedcourse course : list) {
			//判断分数是否为null  null则是已选    如果不是null则是已修完
			if(course.getMark()==null) {//已选
				//根据课程id获取课程信息
				Course course2 = CourseMapper.selectByPrimaryKey(course.getCourseid());
				list2.add(course2);
			}
			
		}
		
		return list2;
	}


	//根据课程id和登录用户id来进行退课   确定退的是该登录用户的课程
	@Override
	public void outCourse(Integer courseID,Integer studentID) {
		SelectedcourseExample example = new SelectedcourseExample();
		Criteria criteria = example.createCriteria();
		criteria.andCourseidEqualTo(courseID);
		criteria.andStudentidEqualTo(studentID);

		//根据课程id和登录用户id
		selectedcourseMapper.deleteByExample(example);
	}


	@Override
	public List<CourseAndScore> overCourse(Integer studentID) {
		//根据用户id查找所有修完的课程
		SelectedcourseExample example = new SelectedcourseExample();
		Criteria criteria = example.createCriteria();
		criteria.andStudentidEqualTo(studentID);//查询所有符合条件的数据
		List<Selectedcourse> list = selectedcourseMapper.selectByExample(example);
		List<CourseAndScore> list2 = new ArrayList<>();
		for (Selectedcourse course : list) {//遍历出所有selectedcourse表中符合条件的数据
			if(course.getMark()!=null) {//分数不为null是  表示已经修完该课程
				//创建一个容器   存放course信息和分数
				CourseAndScore courseAndScore = new CourseAndScore();
				courseAndScore.setMark(course.getMark());//放入分数
				//根据课程id获取课程信息
				Course course2 = CourseMapper.selectByPrimaryKey(course.getCourseid());
				//使用spring的BeanUtils复制属性    其中CourseAndScore为Course的子类  才能使用
				BeanUtils.copyProperties(course2, courseAndScore);//放入Course所有属性
				list2.add(courseAndScore);
			}
		}
		//返回
		return list2;
	}


	@Override
	public StudentInfo findStudentInfoById(Integer studentID) {
		//根据id查询学生信息 包括该生所在的学院名称 
		StudentInfo info = allStudentsMapper.selectStudentById(studentID);
		//查出一条信息   返回
		return info;
	}


	@Override
	public void updateStudentInfo(Student student) {
		studentMapper.updateByPrimaryKey(student);
	}


	

	
}
