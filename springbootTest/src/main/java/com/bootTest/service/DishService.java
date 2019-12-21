package com.bootTest.service;

import java.util.List;

import com.bootTest.domain.Dish;
import com.bootTest.domain.DishFeature;

public interface DishService {
	DishFeature findDishFeature(int did);
	void updateDishFeature(DishFeature dishfeature);
	Dish findDishInfo(int did);
	List<Dish> findAllDish();
	int findDishCount();
	List<DishFeature> findAllDishFeature();
	int[] findFavorite(int uid);
	String isFavorite(int uid,int did);
	void insertFavorite(int uid,int did);
	void deleteFavorite(int uid,int did);
}
