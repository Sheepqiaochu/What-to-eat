package com.bootTest.dao;

import com.bootTest.domain.User;
import com.bootTest.domain.UserFeature;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserDao {
	User findById(@Param("uid") int uid); 
	User login(@Param("logininfo") User logininfo); //登录
	void insertUserInfo(@Param("userinfo") User userinfo); //注册
	String findByPhone(@Param("phone") String phone);  //是否已有帐号
	UserFeature findFeatureById(@Param("uid") int uid);//查询用户特征值
	void updateUserName(@Param("user") User user);     //修改昵称
	void updateUserPhone(@Param("user") User user);    //修改昵称
	void updateUserFeature(@Param("userfeature") UserFeature userfeature);  //更新用户特征值
	List<UserFeature> findAllFeature();                //获取所有用户特征
}
