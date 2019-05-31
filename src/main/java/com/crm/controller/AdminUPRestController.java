package com.crm.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.crm.service.UserNamePasswordService;
import com.system.po.Userlogin;

@Controller
@RequestMapping("/admin")
public class AdminUPRestController {
	@Autowired
	private UserNamePasswordService userNamePasswordService;
	//跳转到重置账号密码页面
	@RequestMapping("/userPasswordRest")
	public String userPasswordRest() {
		
		return "admin/userPasswordRest";
	}
	//重置其他账号密码
	@RequestMapping("/otherPasswordRest")
	public String otherPasswordRest(@RequestParam("username") String username
									,@RequestParam("password") String password
									,Model model){
		
		userNamePasswordService.updateUserPassword(username, password);
		model.addAttribute("success", "成功！！！");
		return "admin/userPasswordRest";
	}
	//跳转到重置密码页面
	@RequestMapping("/goPasswordRest")
	public String goPasswordRest() {
		return "admin/passwordRest";
	}
	
	//修改密码
	@RequestMapping("/passwordRest")
	public String passwordRest(@RequestParam("oldPassword") String oldPassword
								,@RequestParam("password1") String password1) {
		
		//从shiro中获取当前登录的用户用户名
		Subject subject = SecurityUtils.getSubject();
		String username = (String)subject.getPrincipal();//获取用户名
		//根据用户名查询该用户信息
		List<Userlogin> list = userNamePasswordService.findUserByName(username);
		
		for (Userlogin user : list) {
			if(!oldPassword.equals(user.getPassword())) {
				throw new RuntimeException("旧密码不正确");
			}else {
				userNamePasswordService.updateUserPassword(username, password1);
			}
		}
		//重定向到logout 才会生效
		return "redirect:/logout";
	}
}
