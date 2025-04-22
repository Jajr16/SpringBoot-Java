package com.example.PruebaCRUD.Services;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.*;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import java.io.IOException;
import java.io.FileInputStream;

import com.example.PruebaCRUD.BD.TokenNotificacion;
import com.example.PruebaCRUD.Repositories.TokenNotificacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@Service
public class FirebaseService {

    private static final Logger logger = LoggerFactory.getLogger(FirebaseService.class);
    private final TokenNotificacionRepository tokenNotificacionRepository;

    @Autowired
    public FirebaseService(TokenNotificacionRepository tokenNotificacionRepository) {
        this.tokenNotificacionRepository = tokenNotificacionRepository;
    }

    static {
        try {
            FileInputStream serviceAccount = new FileInputStream("./src/main/java/com/example/PruebaCRUD/serviceAccountKey.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            logger.error("Error al inicializar Firebase", e);
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

    private void enviarMensajeFCM(String titulo, String cuerpo, String token, String destinatario, String remitente, String mensaje) throws FirebaseMessagingException {
        Map<String, String> data = new HashMap<>();
        data.put("message", mensaje);
        data.put("sender", remitente);
        data.put("destinatario", destinatario);

        System.out.println("Payload de la notificación FCM: {}" + data);

        Message msg = Message.builder()
                .putData("message", mensaje)
                .putData("sender", remitente)
                .putData("destinatario", destinatario)
                .setToken(token)
                .setNotification(Notification.builder()
                        .setTitle(titulo)
                        .setBody(cuerpo)
                        .build())
                .setAndroidConfig(AndroidConfig.builder()
                        .setNotification(AndroidNotification.builder()
                                .setTitle(titulo)
                                .setBody(cuerpo)
                                .build())
                        .build())
                .build();

        String response = FirebaseMessaging.getInstance().send(msg);
        logger.info("Mensaje enviado correctamente a FCM. ID del mensaje: {}", response);
    }
}
