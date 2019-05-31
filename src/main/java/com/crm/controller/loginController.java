package com.crm.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//登入
@Controller
public class loginController {
	@RequestMapping("/login")
	public String loginAuth(@RequestParam("username") String username
							,@RequestParam("password") String password) {
		
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username,password);
		//进行登录验证
		subject.login(token);
		if(subject.hasRole("admin")) {//用户拥有admin权限 则跳转到
			return "redirect:/admin/showStudent";
		}else if(subject.hasRole("teacher")) {//用户拥有teacher权限 则跳转到
			return "redirect:/teacher/showCourse";
		}else if(subject.hasRole("student")) {//用户拥有student权限 则跳转到
			return "redirect:/student/showCourse";
		}
		//如果都没有权限  重新登录
		//判断是否授权
        /*if (!subject.isAuthenticated()) {
        	UsernamePasswordToken token = new UsernamePasswordToken(username,password);
            token.setRememberMe(true);//开启记住我
            try {
            	subject.login(token);//登入验证  token中的数据会在AuthorizingRealm类的子类中进行授权和认证
            }
            catch (Exception ae) {
                System.out.println("用户："+ae.getMessage());
            }
        }
		return "redirect:/showStudent";*/
		return "login";
	}
}
