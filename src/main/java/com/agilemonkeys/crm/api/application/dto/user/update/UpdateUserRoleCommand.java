package com.agilemonkeys.crm.api.application.dto.user.update;

import com.agilemonkeys.crm.api.domain.valueobject.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UpdateUserRoleCommand {
    private Long id;
    private Role role;
}
