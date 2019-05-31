package com.crm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.service.TeacherService;
import com.system.mapper.CourseMapper;
import com.system.mapper.SelectedcourseMapper;
import com.system.mapper.StudentMapper;
import com.system.mapper.TeacherMapper;
import com.system.mapper.TeacherMapperCustom;
import com.system.po.Course;
import com.system.po.CourseExample;
import com.system.po.CourseExample.Criteria;
import com.system.po.Score;
import com.system.po.Selectedcourse;
import com.system.po.SelectedcourseExample;
import com.system.po.Student;
import com.system.po.Teacher;
import com.system.po.TeacherCustom;
@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TeacherMapper teacherMapper;//注入教师扩展接口映射  修改教师信息
	@Autowired
	public TeacherMapperCustom teacherMapperCustom;//注入教师扩展接口映射  获取教师信息及其所在学院名称
	@Autowired
	private CourseMapper CourseMapper;//注入课程映射接口
	@Autowired
	private SelectedcourseMapper selectedcourseMapper;//注入课程成绩
	@Autowired
	private StudentMapper studentMapper;//学生表映射接口
	
	@Override
	public List<Course> findAllCoursByName(Integer teacherID) {
		CourseExample example = new CourseExample();
		Criteria criteria = example.createCriteria();
		criteria.andTeacheridEqualTo(teacherID);//根据教师id查询该教师所授课程
		List<Course> list = CourseMapper.selectByExample(example);
		return list;
	}
	
	@Override
	public List<Course> selectCourse(String name,int teacherID) {
		CourseExample example = new CourseExample();
		Criteria criteria = example.createCriteria();
		criteria.andCoursenameLike("%"+name+"%");//根据课程名称将库中所有符合条件都查询出来
		List<Course> list = CourseMapper.selectByExample(example);
		// 再遍历课程集合  获得课程的teacherID 之后跟登录的teacherID进行比对
		List<Course> list2 = new ArrayList<>();//创建新的容器 存放符合条件的Course数据
		for (Course course : list) {
			if((course.getTeacherid()).equals(teacherID)) {
				list2.add(course);
			}
		}
		return list2;
	}
	
	@Override
	public List<Score> findGradeCourseById(Integer courseid) {
		//由于该表没有唯一键  所有需要创建条件进行查询
		SelectedcourseExample example = new SelectedcourseExample();
		com.system.po.SelectedcourseExample.Criteria criteria = example.createCriteria();
		criteria.andCourseidEqualTo(courseid);//通过课程id查询
		List<Selectedcourse> list = selectedcourseMapper.selectByExample(example);
		List<Score> listScore=new ArrayList<>();//创建一个容器   存放学生信息
		for (Selectedcourse sel : list) {
			Score score = new Score();//student扩展类  包括学生分数/课程id属性
			score.setMark(sel.getMark());//将分数放到score实体类中
			score.setCourseid(sel.getCourseid());//将课程id放到score实体类中
			//遍历获取学生id  并到学生表中查询学生信息
			Student student = studentMapper.selectByPrimaryKey(sel.getStudentid());
			//将student拷贝到score实体类中
			BeanUtils.copyProperties(student, score);
			//将获取到的学生对象放到集合容器中
			listScore.add(score);
		}
		//将所有数据  学生信息、分数、课程Id放到一个实体类中
		
		return listScore;
	}

	@Override
	public void updateMark(Integer courseid, Integer userid,Integer mark) {
		Selectedcourse selectedcourse = new Selectedcourse();
		selectedcourse.setStudentid(userid);
		selectedcourse.setCourseid(courseid);
		selectedcourse.setMark(mark);
		SelectedcourseExample example = new SelectedcourseExample();
		com.system.po.SelectedcourseExample.Criteria criteria = example.createCriteria();
		criteria.andCourseidEqualTo(courseid);//课程id
		criteria.andStudentidEqualTo(userid);//学生id
		selectedcourseMapper.updateByExample(selectedcourse, example);
	}

	@Override
	public TeacherCustom findTeacherById(Integer id) {
		//注入教师扩展接口映射  获取教师信息及其所在学院名称
		TeacherCustom teacher = teacherMapperCustom.findTeacherById(id);
		return teacher;
	}

	@Override
	public void editTeacher(Teacher teacher) {
		teacherMapper.updateByPrimaryKey(teacher);
	}

}
