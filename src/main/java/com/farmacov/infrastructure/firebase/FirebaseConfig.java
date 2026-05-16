package com.farmacov.infrastructure.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import java.io.IOException;
import java.io.InputStream;

@ApplicationScoped
public class FirebaseConfig {

    void onStart(@Observes StartupEvent event) {
        try {
            // Busca el archivo dentro del classpath (src/main/resources)
            // funciona igual en desarrollo y en prod
            InputStream serviceAccount = Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("farmacovauth-firebase-adminsdk-fbsvc-7b4f17c372.json");

            if (serviceAccount == null) {
                throw new IOException("No se encontró el archivo de credenciales de Firebase");
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            // Evitamos inicializar dos veces en hot reload
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error inicializando Firebase Admin SDK", e);
        }
    }
}