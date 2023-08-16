package com.example.inventory.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.inventory.shop.entities.Product;
import com.example.inventory.shop.repositories.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	public Product createProduct(Product product) {
		product.getSizes().forEach(x -> x.setProduct(product));
		return productRepository.save(product);
	}
	
	public List<Product> createProducts(List<Product> products){
		for(Product p: products) {
			p.getSizes().forEach(x -> x.setProduct(p));
		}
		return (List<Product>) productRepository.saveAll(products);
	}
	
	public List<Product> fetchAllProducts(){
		return (List<Product>) productRepository.findAll();
	}
	
	public List<Product> fetchAllProductsOfCompany(String name) {
		return productRepository.findByCompany(name);
	}
	
	public List<Product> fetchAllProductsOfCategory(String category){
		return productRepository.findByCategory(category);
	}
	
	public List<Product> fetchAllProductsOfCategoryAndPrice(String category, Float price){
		return productRepository.findByCategoryAndPriceLessThanEqual(category, price);
	}
}
