package com.bootTest.dao;

import com.bootTest.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
	User findById(@Param("uid") String uid);
	String login(@Param("logininfo") User logininfo);
	void insertUserInfo(@Param("userinfo") User userinfo);
}
