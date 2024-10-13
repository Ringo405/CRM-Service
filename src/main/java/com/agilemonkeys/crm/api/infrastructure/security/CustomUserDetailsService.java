package com.agilemonkeys.crm.api.infrastructure.security;

import com.agilemonkeys.crm.api.infrastructure.model.UserEntity;
import com.agilemonkeys.crm.api.infrastructure.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import static com.agilemonkeys.crm.api.infrastructure.exception.ErrorMessages.USER_NOT_FOUND;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));

        return new CustomUserDetails(userEntity);
    }
}