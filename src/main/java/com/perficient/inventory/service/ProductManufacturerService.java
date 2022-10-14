package com.perficient.inventory.service;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perficient.inventory.domain.ProductManufacturer;
import com.perficient.inventory.entity.ProductManufacturerEntity;
import com.perficient.inventory.persistence.ProductManufacturerRepository;

@Service
public class ProductManufacturerService {
	
	@Autowired
	private ProductManufacturerRepository repository;
	
	public List<ProductManufacturer> getProducts() {
		Iterable<ProductManufacturerEntity> manufacturers = getRepository().findAll();
		return StreamSupport.stream(manufacturers.spliterator(),false).map(this::buildProductManufacturer).toList();
	}

	public ProductManufacturer createProduct(ProductManufacturer manufacturer) {
		ProductManufacturerEntity entity = new ProductManufacturerEntity(manufacturer.getId(), manufacturer.getBrandName(), manufacturer.getCompanyName());
		return buildProductManufacturer(getRepository().save(entity));
	}
	
	
	private ProductManufacturer buildProductManufacturer(ProductManufacturerEntity manufacturer) {
		return new ProductManufacturer(manufacturer.getId(), manufacturer.getBrandName(), manufacturer.getCompanyName());
	}
	
	private ProductManufacturerRepository getRepository() {
		return repository;
	}

}

