package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.Scraping.SSLConfig;
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
    private static final String LOCAL_CREDENTIALS_PATH = "./src/main/java/com/example/PruebaCRUD/serviceAccountKey.json";
    
    // Cache de la instancia de FirebaseMessaging
    private final FirebaseMessaging firebaseMessaging;
    private final TokenNotificacionRepository tokenNotificacionRepository;

    @Autowired
    public FirebaseService(TokenNotificacionRepository tokenNotificacionRepository) {
        this.tokenNotificacionRepository = tokenNotificacionRepository;
        this.firebaseMessaging = initializeFirebase();
    }

    private FirebaseMessaging initializeFirebase() {
        try {
            // Usar la configuración específica para Firebase
            SSLConfig.configureForFirebase();

            System.setProperty("com.google.api.client.googleapis.auth.oauth2.GoogleCredentials.USE_SYSTEM_TRUSTED_STORE", "true");

            if (!FirebaseApp.getApps().isEmpty()) {
                return FirebaseMessaging.getInstance();
            }

            GoogleCredentials credentials = obtenerCredenciales();
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();

            FirebaseApp.initializeApp(options);
            return FirebaseMessaging.getInstance();
        } catch (IOException e) {
            logger.error("Error crítico al inicializar Firebase: {}", e.getMessage());
            throw new RuntimeException("No se pudo inicializar Firebase", e);
        }
    }

    private GoogleCredentials obtenerCredenciales() throws IOException {
        // Cache de credenciales para evitar lecturas repetidas
        try {
            return GoogleCredentials
                    .fromStream(new FileInputStream(LOCAL_CREDENTIALS_PATH))
                    .createScoped("https://www.googleapis.com/auth/firebase.messaging");
        } catch (IOException localFileError) {
            logger.debug("Archivo local no encontrado, usando variables de entorno");
            
            String credentials = System.getenv("FIREBASE_CREDENTIALS");
            if (credentials == null || credentials.isEmpty()) {
                throw new IOException("No se encontraron credenciales de Firebase");
            }

            return GoogleCredentials
                    .fromStream(new ByteArrayInputStream(credentials.getBytes()))
                    .createScoped("https://www.googleapis.com/auth/firebase.messaging");
        }
    }

    @Async
    public void enviarNoti(String usuario, String titulo, String cuerpo, String remitente, String mensaje) {
        try {
            Optional<TokenNotificacion> tokenOpt = tokenNotificacionRepository.findByUsuarioUsuario(usuario);
            if (tokenOpt.isEmpty() || tokenOpt.get().getToken() == null || tokenOpt.get().getToken().isEmpty()) {
                logger.warn("Token no válido para usuario: {}", usuario);
                return;
            }

            Message msg = construirMensaje(tokenOpt.get().getToken(), titulo, cuerpo, usuario, remitente, mensaje);
            String response = firebaseMessaging.send(msg);
            logger.info("Notificación enviada. ID: {}", response);

        } catch (FirebaseMessagingException e) {
            logger.error("Error al enviar notificación a {}: {}", usuario, e.getMessage());
        }
    }

    private Message construirMensaje(String token, String titulo, String cuerpo, 
                                   String destinatario, String remitente, String mensaje) {
        Map<String, String> data = new HashMap<>(4); // Tamaño inicial óptimo
        data.put("message", mensaje);
        data.put("sender", remitente);
        data.put("destinatario", destinatario);

        return Message.builder()
                .setToken(token)
                .putAllData(data)
                .setNotification(Notification.builder()
                        .setTitle(titulo)
                        .setBody(cuerpo)
                        .build())
                .setAndroidConfig(AndroidConfig.builder()
                        .setPriority(AndroidConfig.Priority.HIGH)
                        .setNotification(AndroidNotification.builder()
                                .setTitle(titulo)
                                .setBody(cuerpo)
                                .setSound("default")
                                .setDefaultVibrateTimings(true)
                                .build())
                        .build())
                .build();
    }
}