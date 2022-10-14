package com.perficient.inventory.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "product")
public class ProductEntity implements Serializable {
	private static final long serialVersionUID = 5613217441338186603L;

	@Id
	@Column(name="id")
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String productId;

	@Column(name="upc", unique=true)
	private String upc;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="manufacturer_id")
	private ProductManufacturerEntity manufacturer;

	@Column(name="model")
	private String model;
	
	@Column(name="quantity")
	private Integer quantity;
	
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true, mappedBy = "product")
	private List<ProductMarketEntity> productMarkets;
	
	public ProductEntity() {}
	
	public ProductEntity(String upc, ProductManufacturerEntity brand, String model, int quantity, List<ProductMarketEntity> productMarkets) {
		this.upc = upc;
		this.manufacturer = brand;
		this.model = model;
		this.quantity = quantity;
		this.productMarkets = productMarkets;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getUpc() {
		return upc;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}

	public ProductManufacturerEntity getBrand() {
		return manufacturer;
	}

	public void setBrand(ProductManufacturerEntity brand) {
		this.manufacturer = brand;
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

	public List<ProductMarketEntity> getProductMarkets() {
		return productMarkets;
	}

	public void setProductMarket(List<ProductMarketEntity> productMarkets) {
		this.productMarkets = productMarkets;
	}
}