package com.bootTest.controller;

import org.springframework.web.bind.annotation.RestController;

import com.bootTest.domain.ResCode;
import com.bootTest.domain.ResJson;
import com.bootTest.domain.User;
import com.bootTest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//随便写的试了一下无实际意义
	@RequestMapping(value="/hello",method=RequestMethod.GET)
	public User findOneUser(@RequestParam(value="uid",required=true) String uid){
		return userService.findUserById(uid);
		//City c=cityService.findCityByName(cityname);
		//return JSONObject.toJSONString(c);
	}
	
	//注册(暂无已有帐号时的处理）
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ResJson postUserInfo(@RequestBody User userinfo){
		userService.Register(userinfo);
		//return userinfo;
		return new ResJson(ResCode.SUCCESS,userinfo);
	}
	
	//注册时的用户评价（没想好用什么格式接收数据，需接收uid,did,评分）
	@RequestMapping(value="/initFeature",method=RequestMethod.POST)
	public void setUserF(){
		
		
	}
	
	//登录（登陆成功返回用户id）
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResJson login(@RequestBody User logininfo){
		String uid = userService.Login(logininfo);
		
		return new ResJson(ResCode.SUCCESS,uid);
	}
	
	//获得推荐
	@RequestMapping(value="/getRecommend",method=RequestMethod.GET)
	public ResJson getRecommend(@RequestParam(value="uid",required=true) String uid){
		JSONArray recDishes=new JSONArray();
		
		//根据用户id进行推荐，在recDishes中加入三个Dish后返回
		//另外可修改返回数据格式，一同把dish对应的restaurant返回（或相关详细信息）
		
		return new ResJson(ResCode.SUCCESS,recDishes);
	}
	
	/*@RequestMapping(value="/userinfo",method=RequestMethod.POST)
	public String postUserInfo(@RequestBody User userinfo){
		JSONObject res=new JSONObject();
		//JSONArray res=new JSONArray();
		res.put("msg", "ok");
	    res.put("method", "json");
	    res.put("data", userinfo);
		
	    return res.toJSONString();
	}*/

}
