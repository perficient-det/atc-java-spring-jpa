package com.perficient.inventory.domain;

import java.util.List;

public class ProductSearchResponse {

	private ProductSearchRequest request;
	private List<Product> products;
	
	
	public ProductSearchResponse() {
		super();
	}
	
	public ProductSearchResponse(ProductSearchRequest request, List<Product> products) {
		super();
		setRequest(request);
		setProducts(products);
	}
	
	public ProductSearchRequest getRequest() {
		return request;
	}
	public void setRequest(ProductSearchRequest request) {
		this.request = request;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
}
