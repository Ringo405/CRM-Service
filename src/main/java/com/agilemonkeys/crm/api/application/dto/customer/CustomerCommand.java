package com.agilemonkeys.crm.api.application.dto.customer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCommand {
    private Long id;
    private String name;
    private String surname;
    private String photoUrl;
}
