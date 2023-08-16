package com.example.inventory.shop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.inventory.shop.entities.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
	
	public List<Product> findByCompany(String companyName);
	
	public List<Product> findByCategory(String categoryName);
	
	public List<Product> findByCategoryAndPriceLessThanEqual(String categoryName, Float price);
}
