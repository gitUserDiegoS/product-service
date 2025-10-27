package com.ecommerce.productservice.domain.model.tokenprovider.gateway;


import com.ecommerce.productservice.domain.model.usersession.UserSession;

/**
 * Defines a contract to validate jwt tokens
 */
public interface TokenProviderRepository {

    UserSession validateToken(String token);
}
