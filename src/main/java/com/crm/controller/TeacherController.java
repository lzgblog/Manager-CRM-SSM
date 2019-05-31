package com.crm.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.crm.service.AdminService;
import com.crm.service.TeacherService;
import com.crm.service.UserNamePasswordService;
import com.system.po.Course;
import com.system.po.Score;
import com.system.po.StudentInfo;
import com.system.po.Teacher;
import com.system.po.TeacherCustom;
import com.system.po.Userlogin;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

	@Autowired
	private UserNamePasswordService userNamePasswordService;//注入用户密码服务接口   根据登录用户名查询登录用户密码
	@Autowired
	private TeacherService teacherService;//注入教师服务接口
	@Autowired
	private AdminService adminService;//注入学生服务接口   根据id查询学生信息
	
	//查询该教师所授课程
	@RequestMapping("/showCourse")
	public String showCourse(Model model) {
		//通过Security获取登录的用户名
		Subject subject = SecurityUtils.getSubject();
		String userName = (String)subject.getPrincipal();//获取登录用户的用户名
		//根据用户名userName==课程表中教师的id teacherID  查出该教师所授的课程(由于课程表中的教师id类型为int)
		List<Course> course = teacherService.findAllCoursByName(Integer.parseInt(userName));
		model.addAttribute("course", course);
		return "teacher/showCourse";
	}
	
	//根据课程名称查询课程    查询的课程是该教师所授的课程中的数据
	@RequestMapping("/selectCourse")
	public String selectCourse(@RequestParam String name,Model model){
		//通过Security获取登录的用户名
		Subject subject = SecurityUtils.getSubject();
		String userName = (String)subject.getPrincipal();//获取登录用户名
		//将其转成int类型
		int teacherID = Integer.parseInt(userName);
		//通过name参数模糊查询出所有符合的课程  在遍历获取课程中课程的teacherID 之后跟登录的ID进行比对
		List<Course> course = teacherService.selectCourse(name, teacherID);
		model.addAttribute("course", course);
		return "teacher/showCourse";
	}
	
	//根据课程id查询选该门课程学生的成绩
	@RequestMapping("/gradeCourse")
	public String gradeCourse(Integer id,Model model) {
		List<Score> listScore= teacherService.findGradeCourseById(id);
		model.addAttribute("listScore", listScore);
		return "teacher/showGrade";
	}
	
	//根据课程id和学生id  查询该生信息并回显
	@RequestMapping("/goMarkPage")
	public String goMarkPage(Integer studentid,Integer courseid,Model model) {
		//根据学生id查询学生信息
		StudentInfo info = adminService.findStudentById(studentid);
		//下一步修改成绩时  需要用到课程id和学生id 两个数据  所以都需要将数据保持在页面中
		model.addAttribute("courseid", courseid);//将课程id回显   并在页面隐藏
		model.addAttribute("info", info);//将学生信息回显
		return "teacher/mark";
	}
	
	//修改学生成绩
	@RequestMapping("/updateMark")
	public String updateMark(@RequestParam("courseid") Integer courseid
							,@RequestParam("userid") Integer userid
							,@RequestParam("mark") Integer mark){
		
		teacherService.updateMark(courseid, userid, mark);
		return "redirect:/teacher/showCourse";
	}
	
	//跳转到修改密码页面
	@RequestMapping("/goPasswordRestPage")
	public String goPasswordRestPage(){
		
		return "teacher/passwordRest";
	}
	//修改密码
	@RequestMapping("/passwordRest")
	public String passwordRest(String oldPassword,String password1,Model model){
		//通过Security获取当前登录用户的用户名
		Subject subject = SecurityUtils.getSubject();
		String username = (String)subject.getPrincipal();//获取用户名
		//根据登录用户名查询登录账号信息   获取数据库中的原密码
		List<Userlogin> list = userNamePasswordService.findUserByName(username);
		for (Userlogin userlogin : list) {
			//判断输入的旧密码是否符合数据库中保存地密码
			if(!oldPassword.equals(userlogin.getPassword())) {//密码不一致
				model.addAttribute("message", "旧密码不正确");
				return "teacher/passwordRest";
			}else {
				//如果密码一致   根据用户名进行修改密码
				userNamePasswordService.updateUserPassword(username, password1);
			}
		}
		//重定向到logout进行用户注销
		return "redirect:/logout";
	}
	
	//查看教师个人信息
	@RequestMapping("/goEditTeacherInfo")
	public String goEditTeacherInfo(Model model) {
		//通过Security获取当前登录用户的用户名
		Subject subject = SecurityUtils.getSubject();
		String loginUserName = (String)subject.getPrincipal();
		//登录的用户名(userName)类型为String  而teacher表中的教师id(userID)为int类型 
		int userID = Integer.parseInt(loginUserName);
		//根据教师id查询该教师信息
		TeacherCustom custom = teacherService.findTeacherById(userID);
		model.addAttribute("teacher", custom);
		return "teacher/teacher";
	}
	//修改教师个人信息
	@RequestMapping("/updateTeacherInfo")
	public String editTeacher(Teacher teacher){
		teacherService.editTeacher(teacher);
		return "redirect:/teacher/showCourse";
	}
}
