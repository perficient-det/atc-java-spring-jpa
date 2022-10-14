package com.perficient.inventory.domain;

public class ProductSearchRequest {
	private String upc;
	private String brandName;
	private String model;
	private Integer quantity;
	
	
	public ProductSearchRequest() {
		super();
	}
	
	public ProductSearchRequest(String upc, String brandName, String model, Integer quantity) {
		super();
		setUpc(upc);
		setBrandName(brandName);
		setModel(model);
		setQuantity(quantity);
	}
	
	public String getUpc() {
		return upc;
	}
	public void setUpc(String upc) {
		this.upc = upc;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	
}
