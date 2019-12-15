package com.bootTest.service;

import com.bootTest.domain.User;

public interface UserService {
	User findUserById(String uid);
	String Login(User logininfo);
	void Register(User userinfo);
}
