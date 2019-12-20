package com.bootTest.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bootTest.domain.Dish;
import com.bootTest.domain.DishFeature;

public interface DishDao {
	DishFeature findFeatureById(@Param("did") int did);//查询菜品特征值
	void updateDishFeature(@Param("dishfeature") DishFeature dishfeature);  //更新菜品特征值
	//DishFeature[] selectAllDishFeature();
	Dish findById(@Param("did") int did);   //根据id查询菜品信息
	List<Dish> findAll();                   //查询所有菜品信息
	int findDishCount();                    //查询菜品数量
	List<DishFeature> findAllDishFeature(); //获得所有菜品特征值
	int[] getFavorite(@Param("uid") int uid); //获得用户收藏
	String isFavorite(@Param("uid") int uid,@Param("did") int did); //是否被收藏
	void insertFavorite(@Param("uid") int uid,@Param("did") int did);//插入收藏
	void deleteFavorite(@Param("uid") int uid,@Param("did") int did);//删除收藏
}
