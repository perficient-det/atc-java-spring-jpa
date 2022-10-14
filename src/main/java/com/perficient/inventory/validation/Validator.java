package com.perficient.inventory.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.perficient.inventory.domain.Product;
import com.perficient.inventory.domain.ProductManufacturer;
import com.perficient.inventory.domain.ProductMarket;
import com.perficient.inventory.error.ErrorCode;
import com.perficient.inventory.error.ValidationException;


@Component
public class Validator {
	
	private static final String ERRORS_LOGGING_LABEL = " Errors: ";

	private static final String VALIDATION_ERROR_LOG_LABEL = "Validation Error Found for: ";

	private static final Logger LOGGER = Logger.getLogger(Validator.class.getName());
	
	public void validate(Product product) {
		List<ErrorCode> codes = new ArrayList<>();
		validateNullableField(product.getId(), 38, ErrorCode.INVALID_PRODUCT_ID, codes);
		validateRequiredField(product.getUpc(), 64, ErrorCode.INVALID_PRODUCT_UPC, codes);
		validateRequiredField(product.getModel(), 255, ErrorCode.INVALID_PRODUCT_MODEL, codes);
		validate(product.getBrand(), codes);
		validate(product.getMarkets(), codes);
		
		if (!codes.isEmpty()) {
			LOGGER.info(() -> VALIDATION_ERROR_LOG_LABEL + product + ERRORS_LOGGING_LABEL + codes);
			throw new ValidationException("Validation Error for Candidate: " + product, codes);
		}
	}

	private void validate(ProductManufacturer manufacturer, List<ErrorCode> codes) {
		if (manufacturer == null) {
			codes.add(ErrorCode.INVALID_PRODUCT_MANUFACTURER);
			return; 
		}
		validateRequiredField(manufacturer.getId(), 38, ErrorCode.INVALID_PRODUCT_MANUFACTURER, codes);
	}
	
	private void validate(List<ProductMarket> markets, List<ErrorCode> codes) {
		if (CollectionUtils.isEmpty(markets)) { return; }  //GUARD CLAUSE
		for (ProductMarket market : markets) {
			validate(market, codes);
		}
		
	}

	private void validate(ProductMarket market, List<ErrorCode> codes) {
		validateNullableField(market.getId(), 38, ErrorCode.INVALID_PRODUCT_MARKET_ID, codes);
		validateRequiredField(market.getCountry(), 255, ErrorCode.INVALID_PRODUCT_MARKET_COUNTRY, codes);
		validateRequiredField(market.getRegion(), 255, ErrorCode.INVALID_PRODUCT_MARKET_REGION, codes);
	}


	private void validateNullableField(String field, int length, ErrorCode erroCode, List<ErrorCode> codes) {
		if (!StringUtils.hasText(field)) { return; } //GUARD CLAUSE
		
		if (field.length() > length) {
			codes.add(erroCode);
		}
		
	}
	
	private void validateRequiredField(String field, int length, ErrorCode erroCode, List<ErrorCode> codes) {
		
		if (!StringUtils.hasText(field) || field.length() > length) {
			codes.add(erroCode);
		}
		
	}
	
}
