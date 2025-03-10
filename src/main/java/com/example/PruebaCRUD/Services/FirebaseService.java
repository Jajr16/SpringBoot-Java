package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.TokenNotificacion;
import com.example.PruebaCRUD.Repositories.TokenNotificacionRepository;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.json.JSONObject;


import java.util.Optional;

/**
 * Clase que contendrá la lógica que para realizar las funciones principales de los endpoints
 */
@Service // Anotación que indica que esta clase es un servicio de negocio
public class FirebaseService {
    private static final String FCM_URL = "https://fcm.googleapis.com/fcm/send";
    private static final String FCM_SERVER_KEY = "BOqaGj_nit6KOtdZ5WQ1R-6_94UqHNwsJZX2CIXnymdMpMr3uyYI8ZcXY9q-EacGq9EojM4uX5jQZYGuLCo60pY";

    private TokenNotificacionRepository tokenNotificacionRepository;

    @Autowired
    public FirebaseService(TokenNotificacionRepository tokenNotificacionRepository) {
        this.tokenNotificacionRepository = tokenNotificacionRepository;
    }

    public void enviarNoti(String usuario, String titulo, String cuerpo) {
        Optional<TokenNotificacion> tokenOpt = tokenNotificacionRepository.findByUsuarioUsuario(usuario);

        if (tokenOpt.isPresent()) {
            String token = tokenOpt.get().getToken();
            JSONObject message = new JSONObject();
            try {
                message.put("to", token);
                message.put("notification", new JSONObject()
                        .put("title", titulo)
                        .put("body", cuerpo));

                // Aquí debes hacer la petición HTTP a Firebase con el mensaje
                enviarMensajeFCM(message);
            } catch (Exception  e) {
                e.printStackTrace();
            }
        }
    }

    private void enviarMensajeFCM(JSONObject message) throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(FCM_URL);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Authorization", "key=" + FCM_SERVER_KEY);

            StringEntity entity = new StringEntity(message.toString());
            httpPost.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                int statusCode = response.getStatusLine().getStatusCode(); // Usar getStatusCode() en HttpClient 4
                if (statusCode != 200) {
                    throw new RuntimeException("Error al enviar notificación: " +
                            response.getStatusLine().getReasonPhrase()); // Usar getReasonPhrase() en HttpClient 4
                }
            }
        }
    }
}
