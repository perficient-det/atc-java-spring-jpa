package com.perficient.inventory.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "product_market")
public class ProductMarketEntity implements Serializable {
	private static final long serialVersionUID = -509520891300511298L;

	@Id
	@Column(name="id")
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String marketId;

	@Column(name="region")
	private String region;
	
	@Column(name="country")
	private String country;
	
	@ManyToOne
	private ProductEntity product;
	
	public ProductMarketEntity() {
		super();
	}
	
	public ProductMarketEntity(String region, String country, ProductEntity product) {
		setRegion(region);
		setCountry(country);
		setProduct(product);
	}
	
	public String getMarketId() {
		return marketId;
	}
	public void setMarketId(String marketId) {
		this.marketId = marketId;
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

	public ProductEntity getProduct() {
		return product;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
	}

}