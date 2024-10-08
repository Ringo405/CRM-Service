package com.agilemonkeys.crm.api.application.dto.user.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UsersQueryResponse {
    List<UserQueryResponse> users;

}
