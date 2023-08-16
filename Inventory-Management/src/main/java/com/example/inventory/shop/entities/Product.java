package com.example.inventory.shop.entities;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_tbl")
public class Product {
	
	@Id
	@GeneratedValue
	@Column(name = "product_id")
	private Integer id;
	
	@Column(name = "product_name")
	private String name;
	
	@Column(name = "product_category")
	private String category;
	
	@Column(name = "product_available_quantity")
	private Integer quantity;
	
	@Column(name = "product_price")
	private Float price;
	
	@Column(name = "product_company_name")
	private String company;
	
	@JsonIgnoreProperties(value = "product", allowSetters = true)
	@OneToMany(cascade = CascadeType.ALL)
	private List<Size> sizes;

	public Product(Integer id, String name, String category, Integer quantity, Float price, String company,
			List<Size> sizes) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.quantity = quantity;
		this.price = price;
		this.company = company;
		this.sizes = sizes;
	}
	
	public Product() {}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", category=" + category + ", quantity=" + quantity + ", price="
				+ price + ", company=" + company + ", sizes=" + sizes + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public List<Size> getSizes() {
		return sizes;
	}

	public void setSizes(List<Size> sizes) {
		this.sizes = sizes;
	}
}
