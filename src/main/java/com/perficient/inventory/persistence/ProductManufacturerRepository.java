package com.perficient.inventory.persistence;

import org.springframework.data.repository.CrudRepository;

import com.perficient.inventory.entity.ProductManufacturerEntity;

public interface ProductManufacturerRepository extends CrudRepository<ProductManufacturerEntity, String> {

}
