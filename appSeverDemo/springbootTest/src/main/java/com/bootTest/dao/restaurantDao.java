package com.bootTest.dao;

import java.util.List;
import com.bootTest.domain.Restaurant;

public interface restaurantDao {
	List<Restaurant> findAll();
}
