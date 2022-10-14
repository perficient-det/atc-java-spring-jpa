package com.perficient.inventory.persistence;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.perficient.inventory.domain.ProductSearchRequest;
import com.perficient.inventory.entity.ProductEntity;
import com.perficient.inventory.entity.ProductManufacturerEntity;
/**
 * 
 * Specification class to build dynamic SQL query with joins.
 */
public class ProductSearchSpecification {

	private static final String MANUFACTURER_FIELD_NAME = "manufacturer";
	private static final String UPC_FIELD = "upc";
	private static final String BRAND_NAME_FIELD = "brandName";
	private static final String MODEL_FIELD = "model";
	private static final String QUANTITY_FIELD = "quantity";
	
	private ProductSearchSpecification() {
		super();
	}

	public static Specification<ProductEntity> buildFindQuery(ProductSearchRequest request) {
		return new Specification<ProductEntity>() {

			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<ProductEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

				final Collection<Predicate> predicates = new ArrayList<>();
				addUpc(request, root, builder, predicates);
				addBrandName(request, root, builder, predicates);
				addModel(request, root, builder, predicates);
				addQuantity(request, root, builder, predicates);
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}

			private void addQuantity(ProductSearchRequest request, Root<ProductEntity> root,
					CriteriaBuilder builder, final Collection<Predicate> predicates) {
				if (request.getQuantity() != null) {
					final Predicate predicate = builder.greaterThanOrEqualTo(
							root.get(QUANTITY_FIELD),
							request.getQuantity());
					predicates.add(predicate);
				}
			}

			private void addModel(ProductSearchRequest request, Root<ProductEntity> root,
					CriteriaBuilder builder, final Collection<Predicate> predicates) {
				if (StringUtils.hasText(request.getModel())) {
					final Predicate predicate = builder.like(
				            builder.lower(root.get(MODEL_FIELD)), 
				            "%" + request.getModel().toLowerCase() + "%");
					predicates.add(predicate);
				}
			}

			private void addBrandName(ProductSearchRequest request, Root<ProductEntity> root,
					CriteriaBuilder builder, final Collection<Predicate> predicates) {
				if (StringUtils.hasText(request.getBrandName())) {
					Join<ProductEntity, ProductManufacturerEntity> join = root.join(MANUFACTURER_FIELD_NAME);
					final Predicate predicate = builder.like(
							builder.lower(join.<String> get(BRAND_NAME_FIELD)),
							"%" + request.getBrandName().toLowerCase() + "%");
					predicates.add(predicate);
				}
			}

			private void addUpc(ProductSearchRequest request, Root<ProductEntity> root,
					CriteriaBuilder builder, final Collection<Predicate> predicates) {
				if (StringUtils.hasText(request.getUpc())) {
					final Predicate predicate = builder.like(
							builder.lower(root.get(UPC_FIELD)), 
							"%" + request.getUpc().toLowerCase() + "%");
					predicates.add(predicate);
				}
			}
		};
	}
	
}
