package com.perficient.spring.validation;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.perficient.inventory.domain.Product;
import com.perficient.inventory.domain.ProductManufacturer;
import com.perficient.inventory.domain.ProductMarket;
import com.perficient.inventory.error.ErrorCode;
import com.perficient.inventory.error.ValidationException;
import com.perficient.inventory.validation.Validator;

public class ValidatorTest {
	
	@Test
	public void testValidProduct() {
		ArrayList<ProductMarket> markets = new ArrayList<>();
		markets.add(new ProductMarket("region", "country"));
		Product product = new Product(null, "123", new ProductManufacturer("123", null, null), "model", 0, markets);
		new Validator().validate(product);
	}
	
	@Test
	public void testInvalidProductEmpty() {
		ArrayList<ProductMarket> markets = new ArrayList<>();
		markets.add(new ProductMarket("", ""));
		Product product = new Product(null, "", new ProductManufacturer("", null, null), "", 0, markets);
		try {
			new Validator().validate(product);		
			Assertions.fail("Exception was expected");
		} catch (ValidationException exception) {
			Assertions.assertEquals(5, exception.getErrorCodes().size());
			Assertions.assertTrue(exception.getErrorCodes().contains(ErrorCode.INVALID_PRODUCT_MANUFACTURER));
			Assertions.assertTrue(exception.getErrorCodes().contains(ErrorCode.INVALID_PRODUCT_MARKET_COUNTRY));
			Assertions.assertTrue(exception.getErrorCodes().contains(ErrorCode.INVALID_PRODUCT_MARKET_REGION));
			Assertions.assertTrue(exception.getErrorCodes().contains(ErrorCode.INVALID_PRODUCT_MODEL));
			Assertions.assertTrue(exception.getErrorCodes().contains(ErrorCode.INVALID_PRODUCT_UPC));
		}
	}

	@Test
	public void testInvalidProductLongFields() {
		ArrayList<ProductMarket> markets = new ArrayList<>();
		markets.add(new ProductMarket("", ""));
		Product product = new Product("123456789012345678901234567890123456789", 
				"123456789012345678901234567890123456789123456789012345678901234567890123456789", 
				new ProductManufacturer("", null, null), "", 0, markets);
		try {
			new Validator().validate(product);		
			Assertions.fail("Exception was expected");
		} catch (ValidationException exception) {
			Assertions.assertEquals(6, exception.getErrorCodes().size());
			Assertions.assertTrue(exception.getErrorCodes().contains(ErrorCode.INVALID_PRODUCT_ID));
			Assertions.assertTrue(exception.getErrorCodes().contains(ErrorCode.INVALID_PRODUCT_MANUFACTURER));
			Assertions.assertTrue(exception.getErrorCodes().contains(ErrorCode.INVALID_PRODUCT_MARKET_COUNTRY));
			Assertions.assertTrue(exception.getErrorCodes().contains(ErrorCode.INVALID_PRODUCT_MARKET_REGION));
			Assertions.assertTrue(exception.getErrorCodes().contains(ErrorCode.INVALID_PRODUCT_MODEL));
			Assertions.assertTrue(exception.getErrorCodes().contains(ErrorCode.INVALID_PRODUCT_UPC));
		}
	}
	
	@Test
	public void testInvalidProductNull() {
		Product product = new Product(null, null, null, null, 0);
		try {
			new Validator().validate(product);		
			Assertions.fail("Exception was expected");
		} catch (ValidationException exception) {
			Assertions.assertEquals(3, exception.getErrorCodes().size());
			Assertions.assertTrue(exception.getErrorCodes().contains(ErrorCode.INVALID_PRODUCT_MANUFACTURER));
			Assertions.assertTrue(exception.getErrorCodes().contains(ErrorCode.INVALID_PRODUCT_UPC));
			Assertions.assertTrue(exception.getErrorCodes().contains(ErrorCode.INVALID_PRODUCT_MODEL));
		}
		
	}
	
}
