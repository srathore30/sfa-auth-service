package sfa.auth.constant;

import lombok.Getter;

@Getter
public enum ApiErrorCodes implements Error {
    INVALID_INPUT(1001, "Invalid request input"),
    NOT_FOUND(1002, "Resource not found"),
    USER_NOT_FOUND(11189, "user not found"),
    INVALID_SEARCH_CRITERIA(1003, "Invalid search criteria"),
    PRODUCT_NOT_FOUND(1004, "Product not found"),
    PRODUCT__PRICE_NOT_FOUND(1005, "Product price not found"),
    INVALID_USER_ROLE(11107, "Invalid role or no role found"),
    INVALID_USERNAME_OR_PASSWORD(11105, "Invalid username or password" ),
    ALREADY_EXISTS(11112,"User Already Registered with Same Mobile No");




    private int errorCode;
    private String errorMessage;

    ApiErrorCodes(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }
    }

