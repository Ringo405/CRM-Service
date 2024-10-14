package com.agilemonkeys.crm.api.security;

import com.agilemonkeys.crm.api.infrastructure.persistance.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SecurityUtilsTest {

    @BeforeEach
    public void setUp() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testGetCurrentLoggedInUserId_Success() {
        // Arrange
        Long expectedUserId = 1L;
        UserEntity userEntity = new UserEntity();
        userEntity.setId(expectedUserId);

        CustomUserDetails userDetails = new CustomUserDetails(userEntity);

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act
        Long actualUserId = SecurityUtils.getCurrentLoggedInUserId();

        // Assert
        assertEquals(expectedUserId, actualUserId);
    }

    @Test
    void testGetCurrentLoggedInUserId_NotAuthenticated() {
        // Arrange
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(securityContext);

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            SecurityUtils.getCurrentLoggedInUserId();
        });
        assertEquals("User is not authenticated", exception.getMessage());
    }

    @Test
    void testGetCurrentLoggedInUserId_PrincipalNotCustomUserDetails() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(new Object());

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            SecurityUtils.getCurrentLoggedInUserId();
        });
        assertEquals("Principal is not an instance of CustomUserDetails", exception.getMessage());
    }
}
