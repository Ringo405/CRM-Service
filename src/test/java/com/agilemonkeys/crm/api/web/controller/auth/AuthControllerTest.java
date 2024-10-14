package com.agilemonkeys.crm.api.web.controller.auth;

import com.agilemonkeys.crm.api.application.dto.AuthRequest;
import com.agilemonkeys.crm.api.security.CustomUserDetailsService;
import com.agilemonkeys.crm.api.security.jwt.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginSuccess() {
        AuthRequest authRequest = new AuthRequest("testUser", "testPassword");
        String expectedToken = "token123";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(userDetailsService.loadUserByUsername(authRequest.getUsername())).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails.getUsername())).thenReturn(expectedToken);

        ResponseEntity<String> response = authController.login(authRequest);

        assertEquals(expectedToken, response.getBody());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userDetailsService, times(1)).loadUserByUsername(authRequest.getUsername());
        verify(jwtUtil, times(1)).generateToken(userDetails.getUsername());
    }

    @Test
    void testLoginFailure() {
        AuthRequest authRequest = new AuthRequest("wrongUser", "wrongPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Authentication failed"));

        try {
            authController.login(authRequest);
        } catch (RuntimeException e) {
            assertEquals("Authentication failed", e.getMessage());
        }

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userDetailsService, never()).loadUserByUsername(anyString());
        verify(jwtUtil, never()).generateToken(anyString());
    }
}
