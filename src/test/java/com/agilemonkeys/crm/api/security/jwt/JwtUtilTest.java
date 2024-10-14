package com.agilemonkeys.crm.api.security.jwt;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private final String testUsername = "testUser";

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
    }

    @Test
    void testGenerateToken() {
        // Act
        String token = jwtUtil.generateToken(testUsername);

        // Assert
        assertEquals(testUsername, jwtUtil.extractUsername(token));
    }

    @Test
    void testExtractClaims() {
        // Act
        String token = jwtUtil.generateToken(testUsername);
        Claims claims = jwtUtil.extractClaims(token);

        // Assert
        assertEquals(testUsername, claims.getSubject());
        assertFalse(claims.getExpiration().before(new Date()));
    }

    @Test
    void testExtractUsername() {
        // Act
        String token = jwtUtil.generateToken(testUsername);
        String extractedUsername = jwtUtil.extractUsername(token);

        // Assert
        assertEquals(testUsername, extractedUsername);
    }

    @Test
    void testIsTokenExpired() {
        // Act
        String token = jwtUtil.generateToken(testUsername);
        boolean isExpired = jwtUtil.isTokenExpired(token);

        // Assert
        assertFalse(isExpired);
    }

    @Test
    void testValidateToken_ValidToken() {
        // Arrange
        String token = jwtUtil.generateToken(testUsername);
        UserDetails userDetails = User.withUsername(testUsername).password("dummy").roles("USER").build();

        // Act
        boolean isValid = jwtUtil.validateToken(token, userDetails);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void testValidateToken_InvalidToken() {
        // Arrange
        String token = jwtUtil.generateToken(testUsername);
        UserDetails userDetails = User.withUsername("anotherUser").password("dummy").roles("USER").build();

        // Act
        boolean isValid = jwtUtil.validateToken(token, userDetails);

        // Assert
        assertFalse(isValid);
    }
}
