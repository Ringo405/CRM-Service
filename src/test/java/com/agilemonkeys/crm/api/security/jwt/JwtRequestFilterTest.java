package com.agilemonkeys.crm.api.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.PrintWriter;

import static org.mockito.ArgumentMatchers.any;
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
    void testDoFilterInternal_ExpiredToken() throws Exception {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        String token = "Bearer expired.jwt.token";

        when(request.getHeader("Authorization")).thenReturn(token);
        when(jwtUtil.extractUsername(any())).thenThrow(new ExpiredJwtException(null, null, "Token expirado"));

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        // Act
        jwtRequestFilter.doFilterInternal(request, response, chain);

        // Assert
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response).setContentType("application/json");
        verify(writer).write("{ \"error\": \"El token JWT ha expirado\" }");
        verify(chain, never()).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_MalformedToken() throws Exception {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        String token = "Bearer malformed.jwt.token";

        when(request.getHeader("Authorization")).thenReturn(token);
        when(jwtUtil.extractUsername(any())).thenThrow(new MalformedJwtException("Token malformado"));

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        // Act
        jwtRequestFilter.doFilterInternal(request, response, chain);

        // Assert
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(response).setContentType("application/json");
        verify(writer).write("{ \"error\": \"El token JWT es inválido\" }");
        verify(chain, never()).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_UnsupportedToken() throws Exception {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        String token = "Bearer unsupported.jwt.token";

        when(request.getHeader("Authorization")).thenReturn(token);
        when(jwtUtil.extractUsername(any())).thenThrow(new UnsupportedJwtException("Token no soportado"));

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        // Act
        jwtRequestFilter.doFilterInternal(request, response, chain);

        // Assert
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(response).setContentType("application/json");
        verify(writer).write("{ \"error\": \"El token JWT no es soportado\" }");
        verify(chain, never()).doFilter(request, response);
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
}
