package com.sise.bao.service;

import com.sise.bao.VO.UserVO;
import com.sise.bao.dao.UserDao;
import com.sise.bao.entity.User;
import com.sise.bao.utils.Page;

public class UserServiceManager {
	
	private UserDao userDao = new UserDao();
	
	
	public Page<User> login(UserVO vo,Page page) {
		return userDao.findPage(page, "from User where loginCode=? and password=?", vo.getName(),vo.getPassword());
	}
	
	public void register(User user) {
		userDao.save(user);
	}
	
}
