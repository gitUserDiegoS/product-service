package com.ecommerce.productservice.infrastructure.adapter.securityauth.config;


import com.ecommerce.productservice.domain.model.passwordencoder.gateway.PasswordEncoderRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Slf4j
@Getter
@Setter
@Component
public class BCryptPasswordEncoderAdapter implements PasswordEncoderRepository {

    private final BCryptPasswordEncoder delegate;

    public BCryptPasswordEncoderAdapter() {
        this.delegate = new BCryptPasswordEncoder();
    }

    @Override
    public Boolean matches(String rawPassword, String encodedPassword) {
        return delegate.matches(rawPassword, encodedPassword);

    }


}
