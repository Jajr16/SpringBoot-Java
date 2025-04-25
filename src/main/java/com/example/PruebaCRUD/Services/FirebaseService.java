package com.example.PruebaCRUD.Services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.PruebaCRUD.BD.TokenNotificacion;
import com.example.PruebaCRUD.Repositories.TokenNotificacionRepository;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class FirebaseService {
    private static final Logger logger = LoggerFactory.getLogger(FirebaseService.class);
    private final TokenNotificacionRepository tokenNotificacionRepository;
    private final FirebaseApp firebaseApp;
    
    // Ruta local para desarrollo
    private static final String LOCAL_CREDENTIALS_PATH = "./src/main/java/com/example/PruebaCRUD/serviceAccountKey.json";

    @Autowired
    public FirebaseService(TokenNotificacionRepository tokenNotificacionRepository) {
        this.tokenNotificacionRepository = tokenNotificacionRepository;
        this.firebaseApp = initializeFirebaseApp();
    }

    private FirebaseApp initializeFirebaseApp() {
        try {
            if (!FirebaseApp.getApps().isEmpty()) {
                return FirebaseApp.getInstance();
            }

            // Configurar el truststore para certificados
            System.setProperty("com.google.api.client.googleapis.auth.oauth2.GoogleCredentials.USE_SYSTEM_TRUSTED_STORE", "true");

            // Primero intenta con el archivo local
            try {
                FileInputStream serviceAccount = new FileInputStream(LOCAL_CREDENTIALS_PATH);
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount)
                                .createScoped("https://www.googleapis.com/auth/firebase.messaging"))
                        .build();
                logger.info("Firebase inicializado con archivo local de credenciales");
                return FirebaseApp.initializeApp(options);
            } catch (IOException localFileError) {
                logger.info("No se encontró archivo local de credenciales, intentando con variables de entorno");
                
                // Si no encuentra el archivo local, intenta con las variables de entorno
                String credentials = System.getenv("FIREBASE_CREDENTIALS");
                String projectId = System.getenv("FIREBASE_PROJECT_ID");

                if (credentials != null && !credentials.isEmpty()) {
                    GoogleCredentials googleCredentials = GoogleCredentials.fromStream(
                            new ByteArrayInputStream(credentials.getBytes())
                    ).createScoped("https://www.googleapis.com/auth/firebase.messaging");

                    FirebaseOptions options = FirebaseOptions.builder()
                            .setCredentials(googleCredentials)
                            .setProjectId(projectId)
                            .build();

                    logger.info("Firebase inicializado con variables de entorno");
                    return FirebaseApp.initializeApp(options);
                } else {
                    throw new RuntimeException("No se encontraron credenciales de Firebase (ni local ni en variables de entorno)");
                }
            }
        } catch (IOException e) {
            logger.error("Error al inicializar Firebase: {}", e.getMessage(), e);
            throw new RuntimeException("No se pudo inicializar Firebase", e);
        }
    }

    private void enviarMensajeFCM(String titulo, String cuerpo, String token, String destinatario,
                                  String remitente, String mensaje) throws FirebaseMessagingException {
        try {
            Map<String, String> data = new HashMap<>();
            data.put("message", mensaje);
            data.put("sender", remitente);
            data.put("destinatario", destinatario);

            Message msg = Message.builder()
                    .putAllData(data)
                    .setToken(token)
                    .setNotification(Notification.builder()
                            .setTitle(titulo)
                            .setBody(cuerpo)
                            .build())
                    .setAndroidConfig(AndroidConfig.builder()
                            .setPriority(AndroidConfig.Priority.HIGH) // Añadido para mejorar la entrega
                            .setNotification(AndroidNotification.builder()
                                    .setTitle(titulo)
                                    .setBody(cuerpo)
                                    .setSound("default") // Asegura que haya sonido de notificación
                                    .setDefaultSound(true)
                                    .setDefaultVibrateTimings(true)
                                    .build())
                            .build())
                    .build();

            String response = FirebaseMessaging.getInstance().send(msg);
            logger.info("Mensaje FCM enviado correctamente. ID: {}", response);
        } catch (FirebaseMessagingException e) {
            logger.error("Error al enviar mensaje FCM: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Async
    public void enviarNoti(String usuario, String titulo, String cuerpo, String remitente, String mensaje) {
        try {
            Optional<TokenNotificacion> tokenOpt = tokenNotificacionRepository.findByUsuarioUsuario(usuario);
            if (tokenOpt.isPresent()) {
                String token = tokenOpt.get().getToken();
                if (token != null && !token.isEmpty()) {
                    enviarMensajeFCM(titulo, cuerpo, token, usuario, remitente, mensaje);
                } else {
                    logger.warn("Token de notificación nulo o vacío para el usuario: {}", usuario);
                }
            } else {
                logger.warn("No se encontró token de notificación para el usuario: {}", usuario);
            }
        } catch (Exception e) {
            logger.error("Error al enviar notificación a usuario {}: {}", usuario, e.getMessage(), e);
        }
    }
}