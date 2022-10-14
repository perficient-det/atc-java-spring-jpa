package com.perficient.inventory.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class ProductManufacturer {

	private String id;
	
	@JsonProperty(access = Access.READ_ONLY)
	private String brandName;
	
	@JsonProperty(access = Access.READ_ONLY)
	private String companyName;


	public ProductManufacturer() {
		super();
	}

	public ProductManufacturer(String id, String brandName, String companyName) {
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
