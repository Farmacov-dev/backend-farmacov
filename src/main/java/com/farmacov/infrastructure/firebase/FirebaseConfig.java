package com.farmacov.infrastructure.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@ApplicationScoped
public class FirebaseConfig {

    @ConfigProperty(name = "firebase.credentials")
    String firebaseCredentialsPath;

    void onStart(@Observes StartupEvent event) {
        try {
            if (FirebaseApp.getApps().isEmpty()) {

                InputStream serviceAccount;

                if (firebaseCredentialsPath.startsWith("/")) {
                    // Producción Cloud Run: /secrets/firebase.json
                    serviceAccount = new FileInputStream(firebaseCredentialsPath);
                } else {
                    // Local: archivo dentro de src/main/resources
                    serviceAccount = Thread.currentThread()
                            .getContextClassLoader()
                            .getResourceAsStream(firebaseCredentialsPath);
                }

                if (serviceAccount == null) {
                    throw new IOException("No se encontró el archivo de credenciales de Firebase en: " + firebaseCredentialsPath);
                }

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error inicializando Firebase Admin SDK", e);
        }
    }
}