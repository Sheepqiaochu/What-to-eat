package com.bootTest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bootTest.dao.DishDao;
import com.bootTest.domain.Dish;
import com.bootTest.domain.DishFeature;
import com.bootTest.service.DishService;

@Service
public class DishServiceImpl implements DishService{
	@Autowired
	private DishDao dishDao;
	
	public DishFeature findDishFeature(int did){
		DishFeature  dishFeature=new DishFeature();
		dishFeature=dishDao.findFeatureById(did);
		dishFeature.setDid(did);
		return dishFeature;
	}
	
	public void updateDishFeature(DishFeature dishfeature){
		dishDao.updateDishFeature(dishfeature);
	}

	@Override
	public Dish findDishInfo(int did) {
		return dishDao.findById(did);
	}

	@Override
	public List<Dish> findAllDish() {
		// TODO Auto-generated method stub
		return dishDao.findAll();
	}

	@Override
	public int findDishCount() {
		// TODO Auto-generated method stub
		return dishDao.findDishCount();
	}

	@Override
	public List<DishFeature> findAllDishFeature() {
		// TODO Auto-generated method stub
		return dishDao.findAllDishFeature();
	}

	@Override
	public int[] findFavorite(int uid) {
		// TODO Auto-generated method stub
		return dishDao.getFavorite(uid);
	}

	@Override
	public String isFavorite(int uid, int did) {
		// TODO Auto-generated method stub
		return dishDao.isFavorite(uid, did);
	}

	@Override
	public void insertFavorite(int uid, int did) {
		// TODO Auto-generated method stub
		dishDao.insertFavorite(uid, did);
	}

	@Override
	public void deleteFavorite(int uid, int did) {
		// TODO Auto-generated method stub
		dishDao.deleteFavorite(uid, did);
	}
}
