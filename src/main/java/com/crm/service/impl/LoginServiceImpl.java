package com.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.service.LoginService;
import com.system.mapper.UserloginMapper;
import com.system.po.Userlogin;
import com.system.po.UserloginExample;
import com.system.po.UserloginExample.Criteria;
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserloginMapper UserloginMapper;//获取登录接口映射
	@Override
	public Userlogin findLoginUser(String username) {
		UserloginExample example = new UserloginExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		//根据name进行查询
		List<Userlogin> list = UserloginMapper.selectByExample(example);
		//返回第一条数据   用户名是唯一的
		return list.get(0);
	}

}
