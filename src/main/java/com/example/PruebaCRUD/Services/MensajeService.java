package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.Chat;
import com.example.PruebaCRUD.BD.Mensaje;
import com.example.PruebaCRUD.BD.PKCompuesta.MensajePK;
import com.example.PruebaCRUD.BD.Usuario;
import com.example.PruebaCRUD.DTO.ChatsDTO;
import com.example.PruebaCRUD.DTO.ListadoUsuariosDTO;
import com.example.PruebaCRUD.DTO.MensajeDTO;
import com.example.PruebaCRUD.Repositories.ChatRepository;
import com.example.PruebaCRUD.Repositories.MensajeRepository;
import com.example.PruebaCRUD.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Clase que contendrá la lógica que para realizar las funciones principales de los endpoints
 */
@Service // Anotación que indica que esta clase es un servicio de negocio
public class MensajeService {
    private final MensajeRepository mensajeRepository;
    private final ChatRepository chatRepository;
    private final UsuarioRepository usuarioRepository;
    private final FirebaseService firebaseService;

    @Autowired
    public MensajeService(MensajeRepository mensajeRepository, UsuarioRepository usuarioRepository,
                          FirebaseService firebaseService, ChatRepository chatRepository) {
        this.mensajeRepository = mensajeRepository;
        this.usuarioRepository = usuarioRepository;
        this.firebaseService = firebaseService;
        this.chatRepository = chatRepository;
    }

    public void enviarMensaje(Usuario remitente, Usuario destinatario, String contenido) {
        System.out.println("Antes de guardar el mensaje en la base de datos");
        Optional<Chat> chatExistente = chatRepository.findByRemitente_UsuarioAndDestinatario_Usuario(
                remitente.getUsuario(), destinatario.getUsuario()
        );

        if (chatExistente.isEmpty()) {
            chatExistente = chatRepository.findByDestinatario_UsuarioAndRemitente_Usuario(
                    remitente.getUsuario(), destinatario.getUsuario()
            );
        }

        Chat chat;
        if (chatExistente.isEmpty()) {
            chat = new Chat(remitente, destinatario);
            chatRepository.save(chat);
        } else {
            chat = chatExistente.get();
        }

        MensajePK mensajePK = new MensajePK(chat, LocalDateTime.now());
        Mensaje mensaje = new Mensaje(mensajePK, remitente, contenido);
        mensajeRepository.save(mensaje);

        System.out.println("Mensaje guardado correctamente en la base de datos");

        // Enviar notificación al destinatario
        firebaseService.enviarNoti(destinatario.getUsuario(), "Nuevo mensaje", contenido, remitente.getUsuario(), contenido);
    }

    public List<ListadoUsuariosDTO> getUsers() {
        return this.mensajeRepository.findUsers();
    }

    public ResponseEntity<Object> getChat(String user) {
        List<ChatsDTO> chats = chatRepository.findChatsRemitente(user);

        if (chats.isEmpty()) {
            chats = chatRepository.findChatsDestinatario(user);

            if (chats.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "Aún no has hablado con nadie.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("chats", chats);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<List<MensajeDTO>> getHistorialMensajes(String remitente, String destinatario) {
        Optional<Chat> chat = chatRepository.findChatByUsers(remitente, destinatario);

        if (chat.isPresent()) {
            List<MensajeDTO> mensajes = mensajeRepository.findById_Chat_IdOrderById_FechahoraAsc(chat.get().getId())
                    .stream()
                    .map(mensaje -> new MensajeDTO(
                            mensaje.getRemitente().getUsuario(),
                            mensaje.getId().getFechahora(),
                            mensaje.getMensaje()))
                    .toList();
            System.out.println("LO QUE DEVUELVE ES " + mensajes);

            return ResponseEntity.ok(mensajes);
        }

        return ResponseEntity.ok(Collections.emptyList());
    }

}
