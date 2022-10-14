package com.perficient.inventory.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "product_manufacturer")
public class ProductManufacturerEntity implements Serializable{

	private static final long serialVersionUID = -3003695949477917689L;

	@Id
	@Column(name="id")
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String id;
	
	@Column(name="brand_name")
	private String brandName;
	
	@Column(name="company_name")
	
	private String companyName;

	public ProductManufacturerEntity() {
		super();
	}
	
	public ProductManufacturerEntity(String id, String brandName, String companyName) {
		super();
		setId(id);
		setBrandName(brandName);
		setCompanyName(companyName);
	}
	
	public String getId() {
		return id;
	}
	

	public void setId(String id) {
		this.id = id;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
