package com.perficient.inventory.configuration;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.perficient.inventory.domain.Product;
import com.perficient.inventory.domain.ProductManufacturer;
import com.perficient.inventory.domain.ProductMarket;
import com.perficient.inventory.service.ProductManufacturerService;
import com.perficient.inventory.service.ProductService;

@Configuration
public class InitializeData {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductManufacturerService manufacturerService;
	
	@PostConstruct
	private void init() {
		ProductManufacturer apple = getManufacturerService().createProduct(new ProductManufacturer(null, "Apple", "Apple inc."));
		ProductManufacturer google = getManufacturerService().createProduct(new ProductManufacturer(null, "Google", "Alphabet inc."));
		
		getProductService().createProduct(new Product(null, "1234567890", apple, "iPhone 14 Plus", 50));
		getProductService().createProduct(new Product(null, "1234567891", apple, "iPhone 14 Plus Max", 40));
		getProductService().createProduct(new Product(null, "2234567892", google, "Pixel 6", 22));
		getProductService().createProduct(new Product(null, "2234567893", google, "Pixel 6", 12, usMarkets()));
	}
	
	private List<ProductMarket> usMarkets() {
		List<ProductMarket> markets = new ArrayList<>();
		markets.add(new ProductMarket("Alabama", "US"));
		markets.add(new ProductMarket("Illinois", "US"));
		markets.add(new ProductMarket("Minnesota", "US"));
		markets.add(new ProductMarket("New Mexico", "US"));
		
		return markets;
	}

	private ProductService getProductService() {
		return productService;
	}

	private ProductManufacturerService getManufacturerService() {
		return manufacturerService;
	}

}
