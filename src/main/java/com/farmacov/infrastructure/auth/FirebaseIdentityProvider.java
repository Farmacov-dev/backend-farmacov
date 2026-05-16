package com.farmacov.infrastructure.auth;

import com.farmacov.domain.auth.IdentityProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotAuthorizedException;

@ApplicationScoped
public class FirebaseIdentityProvider implements IdentityProvider {

    @Override
    public String verifyToken(String token) {
        try {
            // firebase verifica el JWT y ademas valida que el usuario
            FirebaseToken decoded = FirebaseAuth.getInstance().verifyIdToken(token);
            return decoded.getUid();
        } catch (Exception e) {
            throw new NotAuthorizedException("Token inválido o usuario inhabilitado");
        }
    }
}