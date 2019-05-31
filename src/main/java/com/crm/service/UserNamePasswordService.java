package com.crm.service;

import java.util.List;

import com.system.po.Userlogin;

//用户名密码
public interface UserNamePasswordService {

	/**
	 * 重置其他账户密码
	 * @param username
	 * @param password
	 */
	public void updateUserPassword(String username,String password);
	/**
	 * 根据登录用户名查询登录用户信息
	 * @param name
	 * @return
	 */
	public List<Userlogin> findUserByName(String name);
}
