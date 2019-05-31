package com.crm.service;

import com.system.po.Userlogin;

public interface LoginService {

	/**
	 * 根据用户名查询登录的用户信息
	 * @param username
	 * @return
	 */
	public Userlogin findLoginUser(String username);
}
