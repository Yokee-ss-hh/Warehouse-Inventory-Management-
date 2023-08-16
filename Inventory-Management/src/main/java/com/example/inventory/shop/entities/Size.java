package com.example.inventory.shop.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "size_tbl")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Size {
	
	@Id
	@GeneratedValue
	@Column(name = "size_id")
	private Integer id;
	
	@Column(name = "size_type")
	private String size;
	
	@ManyToOne
	@JoinColumn(name = "product_fk")
	@JsonIgnoreProperties(value = "sizes", allowSetters = true)
	private Product product;

	public Size(Integer id, String size, Product product) {
		super();
		this.id = id;
		this.size = size;
		this.product = product;
	}
	
	public Size() {}
	
	@Override
	public String toString() {
		return "Size [id=" + id + ", size=" + size + ", product=" + product + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
