package com.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.service.UserNamePasswordService;
import com.system.mapper.UserloginMapper;
import com.system.mapper.UserloginMapperCustom;
import com.system.po.Userlogin;
import com.system.po.UserloginExample;
import com.system.po.UserloginExample.Criteria;

@Service
public class UserNamePasswordServiceImpl implements UserNamePasswordService {

	@Autowired
	public UserloginMapper userloginMapper;//注入登录用户映射接口
	@Autowired
	public UserloginMapperCustom userloginMapperCustom;//该映射接口中有根据用户名修改密码的方法
	@Override
	public void updateUserPassword(String username, String password) {
		Userlogin userlogin = new Userlogin();
		userlogin.setPassword(password);
		userlogin.setUsername(username);
		userloginMapperCustom.updatePasswordByName(userlogin);
	}
	@Override
	public List<Userlogin> findUserByName(String name) {
		UserloginExample example = new UserloginExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(name);
		List<Userlogin> list = userloginMapper.selectByExample(example);
		return list;
	}

	
}
