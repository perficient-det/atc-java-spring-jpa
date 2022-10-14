package com.perficient.inventory.domain;

import java.util.Collections;
import java.util.List;

public class Product {

	private String id;
	private String upc;
	private ProductManufacturer brand;
	private String model;
	private int quantity;
	private List<ProductMarket> markets;

	public Product() {
		this.markets = Collections.emptyList();
	}
	
	public Product(String id, String upc, ProductManufacturer brand, String model, int quantity) {
		this.id = id;
		this.upc = upc;
		this.quantity = quantity;
		this.model = model;
		this.brand = brand;
		this.markets = Collections.emptyList();
	}

	public Product(String id, String upc, ProductManufacturer brand, String model, int quantity, List<ProductMarket> markets) {
		this.id = id;
		this.upc = upc;
		this.quantity = quantity;
		this.model = model;
		this.brand = brand;
		this.markets = markets;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the upc
	 */
	public String getUpc() {
		return upc;
	}

	/**
	 * @return the brand
	 */
	public ProductManufacturer getBrand() {
		return brand;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @return the markets
	 */
	public List<ProductMarket> getMarkets() {
		return markets;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}

	public void setBrand(ProductManufacturer brand) {
		this.brand = brand;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setMarkets(List<ProductMarket> markets) {
		this.markets = markets;
	}

}
