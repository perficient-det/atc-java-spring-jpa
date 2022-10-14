package com.perficient.inventory.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.perficient.inventory.domain.Product;
import com.perficient.inventory.domain.ProductManufacturer;
import com.perficient.inventory.domain.ProductMarket;
import com.perficient.inventory.domain.ProductSearchRequest;
import com.perficient.inventory.entity.ProductEntity;
import com.perficient.inventory.entity.ProductManufacturerEntity;
import com.perficient.inventory.entity.ProductMarketEntity;
import com.perficient.inventory.error.ErrorCode;
import com.perficient.inventory.error.NotFoundException;
import com.perficient.inventory.persistence.ProductRepository;
import com.perficient.inventory.persistence.ProductSearchSpecification;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	
	public Product getProduct(String productId) {
		Optional<ProductEntity> productEntity = getProductRepository().findById(productId);
		if(productEntity.isPresent()) {
			return buildProduct(productEntity.get());
		} else {
			throw new NotFoundException(ErrorCode.ITEM_NOT_FOUND, "A product was not found with the id of: " + productId);
		}
	}
	
	public List<Product> findProducts(ProductSearchRequest request) {
		Iterable<ProductEntity> productEntities = getProductRepository().findAll(ProductSearchSpecification.buildFindQuery(request));
		return StreamSupport.stream(productEntities.spliterator(),false).map(this::buildProduct).toList();
	}

	public Product createProduct(Product product) {
		List<ProductMarketEntity> markets = new ArrayList<>();
		ProductEntity productEntity = new ProductEntity(product.getUpc(), buildProductManufacture(product.getBrand()), product.getModel(), product.getQuantity(), markets);
		if(!CollectionUtils.isEmpty(product.getMarkets())) {
			for (ProductMarket market : product.getMarkets()) {
				markets.add(new ProductMarketEntity(market.getRegion(),market.getCountry(), productEntity));
			}
		}
		productEntity = getProductRepository().save(productEntity);
		return buildProduct(productEntity);
	}
	
	
	private ProductManufacturerEntity buildProductManufacture(ProductManufacturer brand) {
		return new ProductManufacturerEntity(brand.getId(), null, null); 
	}

	private Product buildProduct(ProductEntity entity) {
		return new Product(entity.getProductId(), entity.getUpc(), buildProductManufacturer(entity.getBrand()), entity.getModel(), entity.getQuantity(),
				entity.getProductMarkets().stream().map(this::buildProductMarket).toList());
	}
	
	private ProductManufacturer buildProductManufacturer(ProductManufacturerEntity brand) {
		return new ProductManufacturer(brand.getId(), brand.getBrandName(), brand.getCompanyName());
	}

	private ProductMarket buildProductMarket(ProductMarketEntity pm) {
		return new ProductMarket(pm.getMarketId(), pm.getRegion(), pm.getCountry());
	}

	private ProductRepository getProductRepository() {
		return productRepository;
	}

}

