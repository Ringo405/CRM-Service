package com.agilemonkeys.crm.api.application.dto.user;

import com.agilemonkeys.crm.api.domain.valueobject.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserCommand {
    private Long id;
    private String password;
    private String username;
    private Role role;
    //private LocalDateTime createdAt;
    //private LocalDateTime updatedAt;
    //private String createdBy; lo tiene que coger del user que este logeado
    //private String updatedBy; lo tiene que coger del user que este logeado
}
