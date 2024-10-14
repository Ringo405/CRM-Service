package com.agilemonkeys.crm.api.infrastructure.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void testHandleExpiredJwtException() {
        // Arrange
        ExpiredJwtException expiredJwtException = Mockito.mock(ExpiredJwtException.class);

        // Act
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleExpiredJwtException(expiredJwtException);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(ErrorMessages.JWT_TOKEN_EXPIRED, response.getBody().get(ErrorMessages.ERROR));
    }

    @Test
    void testHandleAuthenticationException() {
        // Arrange
        AuthenticationException authenticationException = Mockito.mock(AuthenticationException.class);

        // Act
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleAuthenticationException(authenticationException);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(ErrorMessages.NOT_AUTHENTICATED, response.getBody().get(ErrorMessages.ERROR));
    }

    @Test
    void testHandleAccessDeniedException() {
        // Arrange
        AccessDeniedException accessDeniedException = Mockito.mock(AccessDeniedException.class);

        // Act
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleAccessDeniedException(accessDeniedException);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(ErrorMessages.INVALID_ROLE, response.getBody().get(ErrorMessages.ERROR));
    }
}
