package com.crm.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.crm.service.StudentService;
import com.crm.service.UserNamePasswordService;
import com.system.po.Course;
import com.system.po.CourseAndScore;
import com.system.po.Selectedcourse;
import com.system.po.Student;
import com.system.po.StudentCoursePage;
import com.system.po.StudentInfo;
import com.system.po.Userlogin;

@Controller
@RequestMapping("/student")
public class StudentController {
	@Autowired
	private UserNamePasswordService userNamePasswordService;//登录服务接口
	@Autowired
	private StudentService studentService;// 学生服务接口

	// 查询所有课程 并分页显示
	@RequestMapping("/showCourse")
	public String showCourse(Integer page, Model model) {

		if (page == null || "".equals(page)) {
			page = 1;// 从第一页开始
		}
		Integer rows = 5;// 一页显示5条数据
		StudentCoursePage ext = studentService.findAllCourse(page, rows);

		model.addAttribute("ext", ext);
		return "student/showCourse";
	}
	
	//根据课程名称模糊查询课程信息
	@RequestMapping("/selectCourse")
	public String selectCourse(@RequestParam String name,Model model){
		StudentCoursePage ext = studentService.selectCourseByName(name);
		model.addAttribute("ext", ext);
		return "student/showCourse";
	}
	

	// 学生选课 即给该学生添加一个课程
	@RequestMapping("/stuSelectedCourse")
	public String stuSelectedCourse(Integer id, Model model) {// 页面传达课程id过来
		// Security获取当前登录用户名
		Subject subject = SecurityUtils.getSubject();
		String userName = (String) subject.getPrincipal();
		// 将userName转成int类型 选课表中的学生id为int类型
		int studentID = Integer.parseInt(userName);
		// 根据课程id和学生id确认是否选过这门课程
		List<Selectedcourse> list = studentService.checkSelectedCourse(id, studentID);

		// 判断是否存在 如果存在则表示选过该门课程
		if (list.size() > 0) {// 存在
			// 返回原来页面 并进行提示
			model.addAttribute("message", "已选课程，不能再选");
			return "student/showCourse";
		}
		// 如果不存在 给该生添加一门课程
		// 将学生id、课程id添加到选课表中
		studentService.addSelectedCourse(id, studentID);

		model.addAttribute("message", "选课成功");
		// 将数据回显到已选课程页面
		return "student/showCourse";
	}

	// 查看已选的课程 （查没有成绩的即可）
	@RequestMapping("/selectedCourse")
	public String selectedCourse(Model model) {
		// 获取登录用户名
		Subject subject = SecurityUtils.getSubject();
		String userName = (String) subject.getPrincipal();
		// 根据用户名查询该用户已选课程信息
		List<Course> list = studentService.selectedCourseByStId(Integer.parseInt(userName));
		model.addAttribute("list", list);
		return "student/selectCourse";
	}

	// 退课 根据课程id和登录用户id来进行退课 唯一确定一条数据
	@RequestMapping("/outCourse")
	public String outCourse(Integer id) {// 课程id
		// 获取登录用户id
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		int studentID = Integer.parseInt(username);

		// 根据课程id和登录用户id来进行退课 确定退的是该登录用户的课程
		studentService.outCourse(id, studentID);
		// 重定向到 查询已选课程的路径
		return "redirect:/student/selectedCourse";
	}

	// 查看已修完的课程 （查有成绩的即可）
	@RequestMapping("/overCourse")
	public String overCourse(Model model) {
		// 获取当前用户名
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();

		// 查询当前用户已修完的课程信息
		List<CourseAndScore> list = studentService.overCourse(Integer.parseInt(username));
		model.addAttribute("list", list);
		return "student/overCourse";
	}

	//跳转到修改密码页面
	@RequestMapping("/goPasswordRestPage")
	public String goPasswordRestPage(){
		
		return "student/passwordRest";
	}
	
	// 修改密码
	@RequestMapping("/passwordRest")
	public String passwordRest(String oldPassword,String password1,Model model) {
		// 通过Security获取当前登录用户的用户名
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();// 获取用户名
		// 根据登录用户名查询登录账号信息 获取数据库中的原密码
		List<Userlogin> list = userNamePasswordService.findUserByName(username);
		for (Userlogin userlogin : list) {
			// 判断输入的旧密码是否符合数据库中保存地密码
			if (!oldPassword.equals(userlogin.getPassword())) {// 密码不一致
				model.addAttribute("message", "旧密码不正确");
				return "student/passwordRest";
			} else {
				// 如果密码一致 根据用户名进行修改密码
				userNamePasswordService.updateUserPassword(username, password1);
			}
		}
		// 重定向到logout进行用户注销
		return "redirect:/logout";
	}
	
	//查询学生个人信息
	@RequestMapping("/goStudentInfo")
	public String goStudentInfo(Model model){
		//获取学生id   即登录用户的用户名
		Subject subject = SecurityUtils.getSubject();
		String username = (String)subject.getPrincipal();
		StudentInfo student = studentService.findStudentInfoById(Integer.parseInt(username));
		model.addAttribute("student", student);
		
		return "student/student";
	}
	
	//修改学生个人信息
	@RequestMapping("/editStudent")
	public String editStudent(Student student){
		studentService.updateStudentInfo(student);
		return "redirect:/student/showCourse";
	}
}
