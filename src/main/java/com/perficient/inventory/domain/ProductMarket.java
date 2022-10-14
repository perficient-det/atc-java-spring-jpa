package com.perficient.inventory.domain;

public class ProductMarket {
	private String id;
	private String region;
	private String country;

	public ProductMarket(String id, String region, String country) {
		this.id = id;
		this.region = region;
		this.country = country;
	}
	
	public ProductMarket(String region, String country) {
		this.region = region;
		this.country = country;
	}
	public ProductMarket() {
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
}
