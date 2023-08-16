package com.example.inventory.shop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventory.shop.entities.Product;
import com.example.inventory.shop.services.ProductService;

@RestController
@RequestMapping(path = "/shop/api")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping(path = "/create-new-product")
	public ResponseEntity<Product> postProduct(RequestEntity<Product> product){
		return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(product.getBody()));
	}
	
	@PostMapping(path = "/create-new-products")
	public ResponseEntity<List<Product>> postProducts(RequestEntity<List<Product>> products){
		return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProducts(products.getBody()));
	}
	
	@GetMapping(path = "/get-products-by-company")
	public ResponseEntity<List<Product>> getAllProductsWithCompanyName(@RequestParam(name = "name") String companyName){
		return ResponseEntity.status(HttpStatus.OK).body(productService.fetchAllProductsOfCompany(companyName));
	}
	
	@GetMapping(path = "/get-products-of-category")
	public ResponseEntity<List<Product>> getAllProductWithCategory(@RequestParam(name = "category") String categoryName){
		return ResponseEntity.status(HttpStatus.OK).body(productService.fetchAllProductsOfCategory(categoryName));
	}
	
	@GetMapping(path = "/get-products-of-category-and-price")
	public ResponseEntity<List<Product>> getAllProductsOfCategoryWithPrice(@RequestParam(name = "category") String categoryName, @RequestParam(name = "price") Float productPrice){
		return ResponseEntity.status(HttpStatus.OK).body(productService.fetchAllProductsOfCategoryAndPrice(categoryName, productPrice));
	}
	
	@GetMapping(path = "/get-all-products")
	public ResponseEntity<List<Product>> getAllProducts(){
		return ResponseEntity.status(HttpStatus.OK).body(productService.fetchAllProducts());
	}
}
