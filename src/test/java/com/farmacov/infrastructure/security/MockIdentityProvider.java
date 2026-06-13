package com.farmacov.infrastructure.security;

import com.farmacov.domain.auth.IdentityProvider;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;

@Alternative
@Priority(1)
@ApplicationScoped
public class MockIdentityProvider implements IdentityProvider {

    @Override
    public String verifyToken(String token) {
        return "test-firebase-uid";
    }

    @Override
    public String crearUsuario(String email, String password) {
        return "mock-firebase-uid-nuevo";
    }
}
