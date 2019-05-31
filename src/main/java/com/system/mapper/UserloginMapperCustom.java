package com.system.mapper;

import com.system.po.Userlogin;
import com.system.po.UserloginCustom;

/**
 *  UserloginMapper扩展类
 */
public interface UserloginMapperCustom {

    UserloginCustom findOneByName(String name) throws Exception;

    //修改其他账户密码
  	public void updatePasswordByName(Userlogin userlogin);
}
