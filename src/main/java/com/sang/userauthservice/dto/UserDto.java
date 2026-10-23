package com.sang.userauthservice.dto;

import com.sang.userauthservice.model.Role;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {

    private Long id;
    private String email;
    private Role role;
}
