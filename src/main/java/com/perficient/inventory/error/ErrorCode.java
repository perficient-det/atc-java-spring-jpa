package com.perficient.inventory.error;

/**
 * Error codes that are used in the construction of an exception that are
 * specific for Custom Package Service.
 */
public enum ErrorCode {
    
	UNKNOWN_ERROR(9999, "Unknown / Unexpected Error Caught"),
	ITEM_NOT_FOUND(9990, "The object could not be located by the provided id."),
	COULD_NOT_DELETE(9985, "The object could not be deleted by provided id."),
	INVALID_PRODUCT_ID(1005, "Invalid product id.  Value must not be greater than 38 characters."),
	INVALID_PRODUCT_UPC(1010, "Invalid product upc.  The value is required and must not be greater than 64 characters."),
	INVALID_PRODUCT_MODEL(1015, "Invalid product model.  The value is required and must not be greater than 255 characters."),
	INVALID_PRODUCT_MARKET_ID(2000, "Invalid Market Id.  Value must not be greater than 38 characters."),
	INVALID_PRODUCT_MARKET_COUNTRY(2010, "Invalid Market Country.  The value is required and must not be greater than 255 characters."),
	INVALID_PRODUCT_MARKET_REGION(2020, "Invalid Market Region.  The value is required and must not be greater than 255 characters."),
	INVALID_PRODUCT_MANUFACTURER(3000, "Invalid Manufacturer Id.  Value must not be greater than 38 characters.");
	
	private final int errorType;
    private final String description;

    private ErrorCode(int errorCode, String description){
        this.errorType=errorCode;
        this.description=description;
    }

    public int getErrorType(){
        return errorType;

    }	
    public String getDescription(){
    	return description;
    }	
}