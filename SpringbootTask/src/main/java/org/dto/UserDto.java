package org.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;


    @NotBlank
    private String username;


    @NotBlank
    private String fullName;


    @Size(min = 6, message = "password must be at least 6 chars")
    private String password; // only for create/update API; service will hash


    private Set<String> roles;


    private List<AddressDto> addresses;
}
}
