package com.crm.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.crm.service.AdminCourseService;
import com.crm.service.AdminService;
import com.crm.service.AdminTeacherService;
import com.system.po.College;
import com.system.po.Course;
import com.system.po.CourseCustom;
import com.system.po.CourseExt;
import com.system.po.DataResult;
import com.system.po.Student;
import com.system.po.StudentInfo;
import com.system.po.StudentsExt;
import com.system.po.Teacher;
import com.system.po.TeacherCustom;
import com.system.po.TeacherExt;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminTeacherService adminTeacherService;//注入管理教师服务

	@Autowired
	private AdminService adminService;//注入管理学生服务
	@Autowired
	private AdminCourseService adminCourseService;//注入管理课程服务
	
	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 学生管理  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	//查询所有学生信息
	/*@RequestMapping("/showStudent")
	public String showStudent(Integer page,Model model) {
		if(page == null || "".equals(page)) {
			page=1;//从第一页开始
		}
		Integer rows=5;//一页显示5条数据
		DataResult ext = adminService.findAllStudentByPageHelper(page, rows);
		model.addAttribute("ext", ext);
		return "admin/showStudent";
	}*/
	
	@RequestMapping("/showStudent")
	public String showStudent(Integer page,Model model) {
		if(page == null || "".equals(page)) {
			page=1;//从第一页开始
		}
		Integer rows=5;//一页显示5条数据
		StudentsExt ext =adminService.findAllStudents(page, rows);
		model.addAttribute("ext", ext);
		return "admin/showStudent";
	}
	
	//搜索学生信息
	@RequestMapping("/selectStudent")
	public String selectStudent(@RequestParam("findByName") String findByName,Model model) {
		StudentsExt ext = adminService.findStudentByName(findByName);
		model.addAttribute("ext", ext);
		return "admin/showStudent";
	}
	//查询数据库中所有的院系名称  回显到addStudent页面中
	@RequestMapping("/goAddStudentPage")
	public String goAddStudent(Model model) {
		List<College> list = adminService.findCollegeInfo();
		model.addAttribute("list",list);
		return "admin/addStudent";
	}
	
	//添加学生信息 (添加一个学生时应该同时给登录表中添加一条信息  表示该生的登录名和初始密码)
	@RequestMapping("/addStudent")
	public String addStudent(Student student,Model model) {

		adminService.addStudent(student);
		return "redirect:/admin/showStudent";
	}
	//根据id查询信息   并将该生数据回显到页面中
	@RequestMapping("/goEditStudentPage")
	public String goEditStudent(Integer id,Model model) {
		//查询学生信息
		StudentInfo student = adminService.findStudentById(id);
		//查询学院信息
		List<College> list = adminService.findCollegeInfo();
		model.addAttribute("list",list);
		model.addAttribute("student", student);
		return "admin/editStudent";
	}
	//修改学生信息
	@RequestMapping("/editStudent")
	public String editStudent(Student student) {
		adminService.editStudent(student);
		return "redirect:/admin/showStudent";
	}
	//删除学生
	@RequestMapping("/removeStudent")
	public String removeStudent(Integer id) {
		adminService.removeStudent(id);
		return "redirect:/admin/showStudent";
	}
	
	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 课程管理  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	//查询所有课程信息
	@RequestMapping("/showCourse")
	public String showCourse(Integer page,Model model) {
		if(page == null || "".equals(page)) {
			page=1;//从第一页开始
		}
		Integer rows=5;//一页显示5条数据
		CourseExt ext = adminCourseService.findAllCourse(page, rows);
		model.addAttribute("ext", ext);	
		return "admin/showCourse";
	}
	//查询所有老师和学院 并将数据回显到添加页面
	@RequestMapping("/goAddCoursePage")
	public String goAddCoursePage(Model model) {
		//查询所有老师
		List<Teacher> teacher = adminCourseService.findAllTeacher();
		//查询所有学院
		List<College> college = adminCourseService.findCollegeInfo();
		model.addAttribute("teacher", teacher);
		model.addAttribute("college", college);
		
		return "admin/addCourse";
	}
	//添加课程 
	@RequestMapping("/addCourse")
	public String addCourse(Course course) {
		adminCourseService.addCourse(course);
		return "redirect:/admin/showCourse";
	}
	//根据课程id查询课程信息 并回显到修改页面
	@RequestMapping("/goEditCoursePage")
	public String goEditCoursePage(Integer id,Model model) {
		//查询课程信息
		CourseCustom courseCustom = adminCourseService.findCourseById(id);
		//查询所有老师
		List<Teacher> teacher = adminCourseService.findAllTeacher();
		//查询所有学院
		List<College> college = adminCourseService.findCollegeInfo();
		model.addAttribute("course", courseCustom);
		model.addAttribute("teacher", teacher);
		model.addAttribute("college", college);
		return "admin/editCourse";
	}
	//修改课程信息
	@RequestMapping("/editCourse")
	public String editCourse(Course course) {
		adminCourseService.editCourse(course);
		return "redirect:/admin/showCourse";
	}
	//删除课程
	@RequestMapping("/removeCourse")
	public String removeCourse(Integer id) {
		adminCourseService.removeCourse(id);
		return "redirect:/admin/showCourse";
	}
	//根据课程名称  模糊查询课程信息
	@RequestMapping("/selectCourse")
	public String selectCourse(@RequestParam("findByName") String findByName,Model model) {
		CourseExt ext = adminCourseService.findCourseByName(findByName);
		model.addAttribute("ext", ext);
		return "admin/showCourse";
	}
	
	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 教师管理  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	//分页查询教师信息
	@RequestMapping("/showTeacher")
	public String showTeacher(Integer page,Model model) {
		if(page == null || "".equals(page)) {
			page=1;//从第一页开始
		}
		Integer rows=5;//一页显示5条数据
		TeacherExt ext = adminTeacherService.findAllTeacher(page, rows);
		model.addAttribute("ext", ext);
		return "admin/showTeacher";
	}
	//根据教师姓名 模糊查询教师信息
	@RequestMapping("/findTeacherByName")
	public String findTeacherByName(@RequestParam String name,Model model) {
		TeacherExt ext = adminTeacherService.findTeacherByName(name);
		model.addAttribute("ext", ext);
		return "admin/showTeacher";
	}
	//查询所有学院名称 并回显到添加教师信息页面
	@RequestMapping("/goAddTeacherPage")
	public String goAddTeacherPage(Model model) {
		//查询所有学院
		List<College> college = adminCourseService.findCollegeInfo();
		model.addAttribute("college", college);
		return "admin/addTeacher";
	}
	//添加教师   (添加一个教师时应该同时给登录表中添加一条信息  表示该教师的登录名和初始密码)
	@RequestMapping("/addTeacher")
	public String addTeacher(Teacher teacher){
		adminTeacherService.addTeacher(teacher);
		return "redirect:/admin/showTeacher";
	}
	
	//根据教师id查询教师信息
	@RequestMapping("/selectTeacherById")
	public String selectTeacherById(Integer id,Model model) {
		//查询教师  包括学院名称
		TeacherCustom teacher = adminTeacherService.findTeacherById(id);
		//查询所有学院
		List<College> college = adminCourseService.findCollegeInfo();
		model.addAttribute("college", college);
		model.addAttribute("teacher", teacher);
		return "admin/editTeacher";
	}
	//修改教师信息
	@RequestMapping("/editTeacher")
	public String editTeacher(Teacher teacher) {
		adminTeacherService.editTeacher(teacher);
		return "redirect:/admin/showTeacher";
	}
	//删除教师
	@RequestMapping("/removeTeacher")
	public String removeTeacher(Integer id) {
		adminTeacherService.removeTeacher(id);
		return "redirect:/admin/showTeacher";
	}
}
