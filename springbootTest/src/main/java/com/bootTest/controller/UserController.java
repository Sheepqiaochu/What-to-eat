package com.bootTest.controller;

import org.springframework.web.bind.annotation.RestController;

import com.bootTest.domain.Dish;
import com.bootTest.domain.DishFeature;
import com.bootTest.domain.Evaluate;
import com.bootTest.domain.Favorite;
import com.bootTest.domain.ResCode;
import com.bootTest.domain.ResJson;
import com.bootTest.domain.User;
import com.bootTest.domain.UserFeature;
import com.bootTest.service.DishService;
import com.bootTest.service.UserService;
import com.bootTest.recommend.update;

import java.util.ArrayList;
import java.util.List;

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
	@Autowired
	private DishService dishService;
	
	//随便写的试了一下无实际意义
	/*@RequestMapping(value="/hello",method=RequestMethod.GET)
	public User findOneUser(@RequestParam(value="uid",required=true) int uid){
		return userService.findUserById(uid);
		//City c=cityService.findCityByName(cityname);
		//return JSONObject.toJSONString(c);
	}*/
	
	//注册
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ResJson postUserInfo(@RequestBody User userinfo){
		User resData=new User();
		//判断是否已存在账号
		String uid=userService.haveAccount(userinfo.getPhoneNumber());
		if(uid!=null)
			resData.setUid(-1);  //已存在
		else{
			//插入数据并返回信息
			userService.Register(userinfo);
			resData=userService.Login(userinfo);
		}
		//return userinfo;
		return new ResJson(ResCode.SUCCESS,resData);
	}
	
	//注册时的用户评价/用户反馈
	@RequestMapping(value="/initEvaluate",method=RequestMethod.POST)
	public ResJson setUserF(@RequestBody Evaluate evaluate){
		UserFeature userfeature =new UserFeature();
		userfeature.setUid(evaluate.getUid());
		
		//运行推荐算法
		//得到用户特征值
		List<UserFeature> users=userService.findAllFeature();
		List<DishFeature> dishes=dishService.findAllDishFeature();
		int dishNum=dishService.findDishCount();
		
		evaluate.updatePoints(dishNum);
		update one = new update(users,dishes);
		one.update_user(evaluate);
		one.resolve(users, dishes);
		//update(evaluate);
		
		//更新数据库数据
		for(int i=0;i<users.size();i++)
			userService.updateUserFeature(users.get(i));
		/*for(int i=0;i<dishes.size();i++)
			dishService.updateDishFeature(dishes.get(i));
		*/
		
		//userService.updateUserFeature(userfeature);
		userfeature=userService.findUserFeature(userfeature.getUid());

		return new ResJson(ResCode.SUCCESS,userfeature);
	}
	
	//登录（登陆成功返回用户信息）
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResJson login(@RequestBody User logininfo){
		User userinfo = userService.Login(logininfo);
		
		return new ResJson(ResCode.SUCCESS,userinfo);
	}
	
	//获得推荐
	@RequestMapping(value="/getRecommend",method=RequestMethod.GET)
	public List<Dish> getRecommend(@RequestParam(value="uid",required=true) int uid){
		UserFeature userfeature=new UserFeature();
		//int recommendId[];
		List<DishFeature> allDishes=dishService.findAllDishFeature();
		userfeature=userService.findUserFeature(uid);
		
		//根据用户id进行推荐
		userfeature.d_sort(allDishes);
		int recommendId[]=userfeature.recommend(0,5);
		//List<Integer> recommendId=userfeature.recommend(0,5);
		
		
		List<Dish> dishes=new ArrayList<>();
		for(int i=0;i<recommendId.length;i++){
			Dish dish=dishService.findDishInfo(recommendId[i]);
			dishes.add(dish);
			if(dishService.isFavorite(uid, dish.getDid())!=null){
				dish.setFavorite(true);
			}
		}
		//return new ResJson(ResCode.SUCCESS,dishes);
		return dishes;
	}
	
	
	//获取用户特征值
	@RequestMapping(value="/getUserFeature",method=RequestMethod.GET)
	public ResJson getUserFeature(@RequestParam(value="uid",required=true) int uid){
		UserFeature feature=new UserFeature();
		feature=userService.findUserFeature(uid);
		//feature.setUid(uid);
		return new ResJson(ResCode.SUCCESS,feature);
	}
	
	//修改用户信息
	@RequestMapping(value="/changeInfo",method=RequestMethod.POST)
	public ResJson changeInfo(@RequestBody User user){
		User resData=new User();
		
		if(user.getName()!=null)
			userService.changeName(user);
		if(user.getPhoneNumber()!=null)
			userService.changePhone(user);
		
		resData=userService.findUserById(user.getUid());
		return new ResJson(ResCode.SUCCESS,resData);
	}
	
	//获得全部菜品信息
	@RequestMapping(value="/getAllDishes",method=RequestMethod.GET)
	public List<Dish> getAllDishes(@RequestParam(value="uid",required=false) Integer uid){
		List<Dish> dishes =new ArrayList<>();
		//dishes=dishService.findAllDish();
			
		if(uid!=null){
			UserFeature userfeature=new UserFeature();
			List<DishFeature> allDishes=dishService.findAllDishFeature();
			userfeature=userService.findUserFeature(uid);
			
			//根据用户id进行推荐
			userfeature.d_sort(allDishes);
			int recommendId[]=userfeature.recommend(0,allDishes.size());
			//List<Integer> recommendId=userfeature.recommend(0,allDishes.size());
			
			for(int i=0;i<recommendId.length;i++){
				Dish dish=dishService.findDishInfo(recommendId[i]);
				dish.setPriority(i+1);
				//dishes.add(dish);
				if(dishService.isFavorite(uid, dish.getDid())!=null){
					dish.setFavorite(true);
				}
				dishes.add(dish);
			}
		}
		else{
			dishes=dishService.findAllDish();
		}
		//return new ResJson(ResCode.SUCCESS,dishes);
		return dishes;
	}

	//获得收藏的菜品
	@RequestMapping(value="/getFavorite",method=RequestMethod.GET)
	public List<Dish> getFavorite(@RequestParam(value="uid",required=true) int uid){
		List<Dish> dishes =new ArrayList<>();
		int favorite[]=dishService.findFavorite(uid);
		
		for(int i=0;i<favorite.length;i++){
			Dish dish=dishService.findDishInfo(favorite[i]);
			dish.setFavorite(true);
			dishes.add(dish);
		}
		return dishes;
	}
	
	//插入收藏
	@RequestMapping(value="/insertFavorite",method=RequestMethod.POST)
	public ResJson insertFavorite(@RequestBody Favorite fav){
		dishService.insertFavorite(fav.getUid(), fav.getDid());
		return new ResJson(ResCode.SUCCESS,null);
	}
	
	//删除收藏
	@RequestMapping(value="/deleteFavorite",method=RequestMethod.POST)
	public ResJson deleteFavorite(@RequestBody Favorite fav){
		dishService.deleteFavorite(fav.getUid(), fav.getDid());
		return new ResJson(ResCode.SUCCESS,null);
	}
	
}
