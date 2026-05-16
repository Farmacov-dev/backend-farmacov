package com.farmacov.domain.auth;

public interface IdentityProvider {
    // Verifica el token y devuelve el firebase_uuid
    // lanza una excepcion si el usuario no esta validado
    String verifyToken(String token);
}