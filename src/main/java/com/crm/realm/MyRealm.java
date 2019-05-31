package com.crm.realm;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crm.service.LoginService;
import com.system.po.Userlogin;

@Component
public class MyRealm extends AuthorizingRealm{

	@Autowired
	private LoginService loginService;//注入用户登录服务
	//授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		//principal获取用户信息
		Object result = principal.getPrimaryPrincipal();//获取用户名
		//System.out.println(result);
		String username = (String) getAvailablePrincipal(principal);//获取用户名
		//根据用户名查询登录用户权限
		Userlogin user = loginService.findLoginUser(username);//从数据库中查询
		Set<String> set = new HashSet<>();
		if((user.getRole()).equals(0)) {//role = 0 是管理员
			set.add("admin");//添加权限
		}
		if((user.getRole()).equals(1)) {//role = 1 是教师
			set.add("teacher");//添加权限
		}
		if((user.getRole()).equals(2)) {//role = 2 是学生
			set.add("student");//添加权限
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(set);
		return info;
	}

	//登入认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken autoken) throws AuthenticationException {
		UsernamePasswordToken token=(UsernamePasswordToken)autoken;
		String username = token.getUsername();//获取登录的用户用户名
		String password = new String(token.getPassword());//获取密码
		Userlogin user=null;
		try {
			user = loginService.findLoginUser(username);//从数据库中查询是否存在
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("登录出了问题:"+e.getMessage());
		}
		//如果user中没有数据  表示不存在
		if(user == null || "".equals(user)) {
			//没有该用户名
            throw new UnknownAccountException();
		}else if(!password.equals(user.getPassword())) {
			//密码错误
            throw new IncorrectCredentialsException();
		}
		//如果数据都通过  则认证成功   将用户名和密码保存  subject.getPrincipal();//获取用户名
		AuthenticationInfo info = new SimpleAuthenticationInfo(username, password,getName());
		return info;
	}

}
