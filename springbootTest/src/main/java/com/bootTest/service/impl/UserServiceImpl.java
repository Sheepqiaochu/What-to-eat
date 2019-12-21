package com.bootTest.service.impl;

import com.bootTest.dao.UserDao;
import com.bootTest.domain.User;
import com.bootTest.domain.UserFeature;
import com.bootTest.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao userDao;
	
	public User findUserById(int uid){
		return userDao.findById(uid);
	}
	
	public User Login(User logininfo){
		return userDao.login(logininfo);
	}
	
	public void Register(User userinfo){
		userDao.insertUserInfo(userinfo);
	}
	
	public String haveAccount(String phone){
		return userDao.findByPhone(phone);
	}
	
	public UserFeature findUserFeature(int uid){
		UserFeature userFeature=new UserFeature();
		userFeature = userDao.findFeatureById(uid);
		userFeature.setUid(uid);
		return userFeature;
	}
	
	public void changeName(User user){
		userDao.updateUserName(user);
	}
	
	public void changePhone(User user){
		userDao.updateUserPhone(user);
	}

	public void updateUserFeature(UserFeature userfeature) {
		userDao.updateUserFeature(userfeature);
	}

	@Override
	public List<UserFeature> findAllFeature() {
		// TODO Auto-generated method stub
		return userDao.findAllFeature();
	}
	
	
}
