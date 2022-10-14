package com.perficient.inventory.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

import com.perficient.inventory.entity.ProductEntity;

public interface ProductRepository extends CrudRepository<ProductEntity, String> {

	Optional<ProductEntity> findByUpc(String upc);
	List<ProductEntity> findByUpcContaining(String upc);
	List<ProductEntity> findByUpcContainingOrModelIgnoreCase(String upc, String model);
	
	List<ProductEntity> findAll(Specification<ProductEntity> specification);
	
}
