package com.agilemonkeys.crm.api.application.dto.user;

import com.agilemonkeys.crm.api.domain.valueobject.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCommand {
    private Long id;
    private String password;
    private String username;
    private Role role;
}
