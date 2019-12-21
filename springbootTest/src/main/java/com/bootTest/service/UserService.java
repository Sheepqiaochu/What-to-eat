package com.bootTest.service;

import java.util.List;

import com.bootTest.domain.User;
import com.bootTest.domain.UserFeature;

public interface UserService {
	User findUserById(int uid);
	User Login(User logininfo);
	void Register(User userinfo);
	String haveAccount(String phone);
	UserFeature findUserFeature(int uid);
	void changeName(User user);
	void changePhone(User user);
	void updateUserFeature(UserFeature userfeature);
	List<UserFeature> findAllFeature();
}
