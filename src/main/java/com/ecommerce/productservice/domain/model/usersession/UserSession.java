package com.ecommerce.productservice.domain.model.usersession;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserSession {

    private final Long id;

    private final String email;

    private final String role;
}
