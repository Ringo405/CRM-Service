package com.agilemonkeys.crm.api.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public class SecurityUtils {

    private SecurityUtils() {
    }

    public static Long getCurrentLoggedInUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                CustomUserDetails userDetails = (CustomUserDetails) principal;
                return userDetails.getId();
            } else {
                throw new IllegalStateException("Principal is not an instance of CustomUserDetails");
            }
        }
        throw new IllegalStateException("User is not authenticated");
    }
}
