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
import com.example.PruebaCRUD.Repositories.TokenNotificacionRepositorio;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class FirebaseServicio {
    private static final Logger registro = LoggerFactory.getLogger(FirebaseServicio.class);
    private static final String RUTA_CREDENCIALES_LOCAL = "./src/main/java/com/example/PruebaCRUD/serviceAccountKey.json";
    
    // Cache de la instancia de FirebaseMessaging
    private final FirebaseMessaging mensajesFirebase;
    private final TokenNotificacionRepositorio tokenNotificacionRepositorio;

    @Autowired
    public FirebaseServicio(TokenNotificacionRepositorio tokenNotificacionRepositorio) {
        this.tokenNotificacionRepositorio = tokenNotificacionRepositorio;
        this.mensajesFirebase = inicializarfirebase();
    }

    private FirebaseMessaging inicializarfirebase() {
        try {
            // Usar la configuración específica para Firebase
            SSLConfig.configurarParaFirebase();

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
            registro.error("Error crítico al inicializar Firebase: {}", e.getMessage());
            throw new RuntimeException("No se pudo inicializar Firebase", e);
        }
    }

    private GoogleCredentials obtenerCredenciales() throws IOException {
        // Cache de credenciales para evitar lecturas repetidas
        try {
            return GoogleCredentials
                    .fromStream(new FileInputStream(RUTA_CREDENCIALES_LOCAL))
                    .createScoped("https://www.googleapis.com/auth/firebase.messaging");
        } catch (IOException localFileError) {
            registro.debug("Archivo local no encontrado, usando variables de entorno");
            
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
            Optional<TokenNotificacion> tokenOpt = tokenNotificacionRepositorio.findByUsuarioUsuario(usuario);
            if (tokenOpt.isEmpty() || tokenOpt.get().getToken() == null || tokenOpt.get().getToken().isEmpty()) {
                registro.warn("Token no válido para usuario: {}", usuario);
                return;
            }

            Message msg = construirMensaje(tokenOpt.get().getToken(), titulo, cuerpo, usuario, remitente, mensaje);
            String response = mensajesFirebase.send(msg);
            registro.info("Notificación enviada. ID: {}", response);

        } catch (FirebaseMessagingException e) {
            registro.error("Error al enviar notificación a {}: {}", usuario, e.getMessage());
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