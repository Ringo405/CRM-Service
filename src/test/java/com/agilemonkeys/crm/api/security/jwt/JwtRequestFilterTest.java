package com.agilemonkeys.crm.api.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.mockito.Mockito.*;

class JwtRequestFilterTest {

    private JwtRequestFilter jwtRequestFilter;
    private UserDetailsService userDetailsService;
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        userDetailsService = mock(UserDetailsService.class);
        jwtUtil = mock(JwtUtil.class);
        jwtRequestFilter = new JwtRequestFilter(userDetailsService, jwtUtil);
    }

    @Test
    void testDoFilterInternal_NoToken() throws Exception {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn(null);

        // Act
        jwtRequestFilter.doFilterInternal(request, response, chain);

        // Assert
        verify(chain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_ValidToken() throws Exception {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        String token = "Bearer valid.jwt.token";
        String username = "validUser";

        when(request.getHeader("Authorization")).thenReturn(token);
        when(jwtUtil.extractUsername(token)).thenReturn(username);
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtUtil.validateToken(token, userDetails)).thenReturn(true);

        // Act
        jwtRequestFilter.doFilterInternal(request, response, chain);

        // Assert
        verify(chain).doFilter(request, response);
    }
}
