package com.increff.pos.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "brand", "category" }, name = "brandCategoryCombo") })
public class BrandDetailPojo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int  brand_id;
	@Column(nullable = false)
	private String brand;
	@Column(nullable = false)
	private String category;

	public int getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(int id) {
		this.brand_id = brand_id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String ctgry) {
		this.category = ctgry;
	}

}
