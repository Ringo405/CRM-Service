package com.agilemonkeys.crm.api.infrastructure.exception;

public class ErrorMessages {
    public static final String USER_NOT_FOUND = "User not found";
    public static final String CUSTOMER_NOT_FOUND = "Customer not found";
    public static final String INVALID_ROLE = "Invalid role provided";
    public static final String ERROR = "Error";
    public static final String JWT_TOKEN_EXPIRED = "The JWT token has expired. Please authenticate again.";
    public static final String NOT_AUTHENTICATED = "You are not authenticated. Please provide a valid token.";

    private ErrorMessages() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
