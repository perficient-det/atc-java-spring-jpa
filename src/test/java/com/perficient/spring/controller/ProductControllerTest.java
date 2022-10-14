package com.perficient.spring.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perficient.inventory.InventoryApplication;
import com.perficient.inventory.controller.ErrorHandlingControllerAdvice;
import com.perficient.inventory.domain.ErrorDetail;
import com.perficient.inventory.domain.ErrorResponse;
import com.perficient.inventory.domain.Product;
import com.perficient.inventory.domain.ProductManufacturer;
import com.perficient.inventory.domain.ProductMarket;
import com.perficient.inventory.domain.ProductSearchResponse;


/**
 * JUnit test class for the ManagerController class.
 *
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = InventoryApplication.class)
@AutoConfigureMockMvc
@Transactional
class ProductControllerTest {
	
	@Autowired
	MockMvc mockController;
	
	@Test
	public void testFindAllProducts() throws Exception{
		ProductSearchResponse response = obtainGetResponse("/products", ProductSearchResponse.class);
		assertTrue(response.getProducts().size() > 0);
		for (Product product : response.getProducts()) {
			assertNotNull(product.getId());
		}
	}
	
	@Test
	public void testFindAllProductsWithSearchCriteria() throws Exception{
		ProductSearchResponse response = obtainGetResponse("/products?model=ixel&upc=345&quantity=10&brandName=oo", ProductSearchResponse.class);
		assertTrue(response.getProducts().size() > 0);
		assertEquals("oo", response.getRequest().getBrandName());
		assertEquals("ixel", response.getRequest().getModel());
		assertEquals(10, response.getRequest().getQuantity());
		assertEquals("345", response.getRequest().getUpc());
		for (Product product : response.getProducts()) {
			assertTrue(product.getModel().contains("ixel"));
			assertTrue(product.getUpc().contains("345"));
			assertTrue(product.getQuantity() >= 10);
		}
	}
	
	@Test
	public void testPostProduct() throws Exception{
		List<ProductManufacturer> manufacturers = obtainGetResponseAsList("/manufacturers", ProductManufacturer.class);
		
		ArrayList<ProductMarket> markets = new ArrayList<>();
		markets.add(new ProductMarket("region", "country"));
		Product product = new Product(null, "UPC123", manufacturers.get(0), "model", 20, markets);
		Product savedProduct = obtainPostResponse("/products", product, Product.class);
		assertNotNull(savedProduct.getId()); 
		Product foundProduct = obtainGetResponse("/products/" + savedProduct.getId(), Product.class);
		assertEquals(savedProduct.getId(), foundProduct.getId());
		assertEquals(product.getUpc(), foundProduct.getUpc());
		assertEquals(product.getModel(), foundProduct.getModel());
		assertEquals(product.getQuantity(), foundProduct.getQuantity());
		assertEquals(product.getMarkets().get(0).getCountry(), foundProduct.getMarkets().get(0).getCountry());
		assertEquals(product.getMarkets().get(0).getRegion(), foundProduct.getMarkets().get(0).getRegion());
	}
	
	@Test
	public void testPostProductInvalidRequest() throws Exception{
		
		ArrayList<ProductMarket> markets = new ArrayList<>();
		markets.add(new ProductMarket("", ""));
		Product product = new Product(null, "", null, "", 0, markets);
		ErrorResponse error = obtainPostResponse("/products", product, ErrorResponse.class);
		
		assertEquals(ErrorHandlingControllerAdvice.ERROR_TYPE_VALIDATION, error.getType());
		assertEquals("Validation error(s) encountered", error.getMessage());
		assertTrue(!error.getErrors().isEmpty(), "Validation error should contain a list of errors");
	}
	
	@Test
	public void testFindProductNotFound() throws Exception{
		
		ErrorResponse error = obtainGetResponse("/products/XYZ", ErrorResponse.class);
		assertEquals(ErrorHandlingControllerAdvice.ERROR_TYPE_NOT_FOUND, error.getType());
	}
	
	@Test
	public void testFindProductGeneralException() throws Exception{
		
		ErrorResponse error = obtainGetResponse("/products?quantity=ABC", ErrorResponse.class);
		assertEquals(ErrorHandlingControllerAdvice.ERROR_TYPE_UNKNOWN, error.getType());
	}
	
	protected MockMvc getMockController() {
		return mockController;
	}

	protected String obtainGetResponseString(String url) throws Exception, UnsupportedEncodingException {
		MvcResult result = getMockController().perform(get(url).contentType(MediaType.APPLICATION_JSON)).andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		return contentAsString;
	}

	protected <T> List<T> obtainGetResponseAsList(String url, Class<T> type)
			throws Exception, UnsupportedEncodingException, JsonProcessingException, JsonMappingException {
						ObjectMapper mapper = new ObjectMapper();
						return mapper.readValue(obtainGetResponseString(url), 
								mapper.getTypeFactory().constructCollectionType(List.class, type));
			}

	protected <T> T obtainGetResponse(String url, Class<T> type)
			throws Exception, UnsupportedEncodingException, JsonProcessingException, JsonMappingException {
				return new ObjectMapper().readValue(obtainGetResponseString(url),type);
			}

	protected <T> T obtainPostResponse(String url, Object body, Class<T> type) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		MvcResult result = getMockController().perform(
				post(url).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(body))).andReturn();
		return mapper.readValue(result.getResponse().getContentAsString(), type);
	}

	protected <T> List<T> obtainPostResponseAsList(String url, Object body, Class<T> type) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		MvcResult result = getMockController().perform(
				post(url).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(body))).andReturn();
		return mapper.readValue(result.getResponse().getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, type));
	}	
}