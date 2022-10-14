package com.perficient.inventory.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perficient.inventory.domain.Product;
import com.perficient.inventory.domain.ProductManufacturer;
import com.perficient.inventory.domain.ProductSearchRequest;
import com.perficient.inventory.domain.ProductSearchResponse;
import com.perficient.inventory.service.ProductManufacturerService;
import com.perficient.inventory.service.ProductService;
import com.perficient.inventory.validation.Validator;

@RestController
public class ProductController {

	private static final Logger LOGGER = Logger.getLogger(ProductController.class.getName());

	@Autowired
	private ProductService service;
	
	@Autowired
	private ProductManufacturerService manufactureService;
	
	@Autowired
	private Validator validator;
	
	@GetMapping("/products/{product_id}")
	public Product findProduct(@PathVariable("product_id") String productId) {
		Product product =  getService().getProduct(productId);
		LOGGER.log(Level.INFO, "Returning test product response with Id: {} and upc: {}", new Object[] {product.getId(), product.getUpc()});
		return product;
	}

//	@GetMapping("/products")
//	public List<Product> findProducts() {
//		List<Product> products = getService().getProducts();
//		LOGGER.info("Returning {} products", products.size());
//		return products;
//	}
	
	@GetMapping("/products")
	public ResponseEntity<ProductSearchResponse> findProducts(
			@RequestParam(name = "upc", required = false) String upc,
			@RequestParam(name = "brandName", required = false) String brandName,
			@RequestParam(name = "model", required = false) String model,
			@RequestParam(name = "quantity", required = false) Integer quantity) {
		
		ProductSearchRequest request = new ProductSearchRequest(upc, brandName, model, quantity);
		List<Product> products = getService().findProducts(request);
		return new ResponseEntity<>(new ProductSearchResponse(request, products), HttpStatus.OK);
	}
	
	@GetMapping("/manufacturers")
	public List<ProductManufacturer> findManufacturers() {
		return getManufactureService().getProducts();
	}
	
	@PostMapping("/products")
	public Product createProduct(@RequestBody Product product) {
		LOGGER.log(Level.INFO,"Creating product with id: {}", product.getId());
		getValidator().validate(product);
		return getService().createProduct(product);
	}

	private ProductService getService() {
		return service;
	}

	private ProductManufacturerService getManufactureService() {
		return manufactureService;
	}

	private Validator getValidator() {
		return validator;
	}
}


