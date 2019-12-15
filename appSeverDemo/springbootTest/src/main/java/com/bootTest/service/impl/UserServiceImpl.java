package com.bootTest.service.impl;

import com.bootTest.dao.UserDao;
import com.bootTest.domain.User;
import com.bootTest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao userDao;
	
	public User findUserById(String uid){
		return userDao.findById(uid);
	}
	
	public String Login(User logininfo){
		return userDao.login(logininfo);
	}
	
	public void Register(User userinfo){
		userDao.insertUserInfo(userinfo);
	}
}
