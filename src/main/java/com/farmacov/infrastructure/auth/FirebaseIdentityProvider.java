package com.farmacov.infrastructure.auth;

import com.farmacov.domain.auth.IdentityProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotAuthorizedException;

@ApplicationScoped
public class FirebaseIdentityProvider implements IdentityProvider {

    @Override
    public String verifyToken(String token) {
        try {
            FirebaseToken decoded = FirebaseAuth.getInstance().verifyIdToken(token);
            return decoded.getUid();
        } catch (Exception e) {
            throw new NotAuthorizedException("Token invalido o usuario inhabilitado");
        }
    }


    @Override
    public String crearUsuario(String email, String password) {
        try {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setPassword(password);

            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
            return userRecord.getUid();
        } catch (Exception e) {
            if (e.getMessage().contains("EMAIL_EXISTS")) {
                throw new jakarta.ws.rs.ClientErrorException(
                        "el correo ya esta registrado", 409
                );
            }
            throw new RuntimeException("error creando usuario en firebase: " + e.getMessage() );
        }

    }
}