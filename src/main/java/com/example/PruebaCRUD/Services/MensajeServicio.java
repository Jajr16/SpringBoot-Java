package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.Chat;
import com.example.PruebaCRUD.BD.Mensaje;
import com.example.PruebaCRUD.BD.PKCompuesta.MensajePK;
import com.example.PruebaCRUD.BD.Usuario;
import com.example.PruebaCRUD.DTO.ChatsDTO;
import com.example.PruebaCRUD.DTO.ListadoUsuariosDTO;
import com.example.PruebaCRUD.DTO.MensajeDTO;
import com.example.PruebaCRUD.Repositories.ChatRepositorio;
import com.example.PruebaCRUD.Repositories.MensajeRepositorio;
import com.example.PruebaCRUD.Repositories.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Clase que contendrá la lógica que para realizar las funciones principales de los endpoints
 */
@Service // Anotación que indica que esta clase es un servicio de negocio
public class MensajeServicio {
    private final MensajeRepositorio mensajeRepositorio;
    private final ChatRepositorio chatRepositorio;
    private final FirebaseService firebaseService;
    private final UsuarioRepositorio usuarioRepository;

    @Autowired
    public MensajeServicio(MensajeRepositorio mensajeRepositorio,
                           FirebaseService firebaseService, ChatRepositorio chatRepositorio, UsuarioRepositorio usuarioRepository) {
        this.mensajeRepositorio = mensajeRepositorio;
        this.firebaseService = firebaseService;
        this.chatRepositorio = chatRepositorio;
        this.usuarioRepository = usuarioRepository;
    }

    public void enviarMensaje(Usuario remitente, Usuario destinatario, String contenido) {
        System.out.println("Antes de guardar el mensaje en la base de datos");
        Optional<Chat> chatExistente = chatRepositorio.findByRemitente_UsuarioAndDestinatario_Usuario(
                remitente.getUsuario(), destinatario.getUsuario()
        );

        if (chatExistente.isEmpty()) {
            chatExistente = chatRepositorio.findByDestinatario_UsuarioAndRemitente_Usuario(
                    remitente.getUsuario(), destinatario.getUsuario()
            );
        }

        Chat chat;
        if (chatExistente.isEmpty()) {
            chat = new Chat(remitente, destinatario);
            chatRepositorio.save(chat);
        } else {
            chat = chatExistente.get();
        }

        MensajePK mensajePK = new MensajePK(chat, LocalDateTime.now());
        Mensaje mensaje = new Mensaje(mensajePK, remitente, contenido);
        mensajeRepositorio.save(mensaje);

        System.out.println("Mensaje guardado correctamente en la base de datos");

        // Enviar notificación al destinatario
        firebaseService.enviarNoti(destinatario.getUsuario(), "Nuevo mensaje", contenido, remitente.getUsuario(), contenido);
    }

    public List<ListadoUsuariosDTO> obtenerUsuarios(String usuario) {
        System.out.println("EL RESULTADO DE LA CONSULTA FUE " + this.usuarioRepository.findUsers(usuario));
        return this.usuarioRepository.findUsers(usuario);
    }

    public ResponseEntity<Object> obtenerChat(String user) {
        try {
            System.out.println("Buscando chats para usuario: " + user);

            List<ChatsDTO> chatsRemitente = chatRepositorio.findChatsRemitente(user);
            System.out.println("Chats como remitente: " + chatsRemitente.size());

            List<ChatsDTO> chats = new ArrayList<>(chatsRemitente);

            List<ChatsDTO> chatsDestinatario = chatRepositorio.findChatsDestinatario(user);
            System.out.println("Chats como destinatario: " + chatsDestinatario.size());

            chats.addAll(chatsDestinatario);

            if (chats.isEmpty()) {
                return ResponseEntity.ok()
                        .body(Map.of("mensaje", "Aún no has hablado con nadie."));
            }

            return ResponseEntity.ok()
                    .body(Map.of("chats", chats));

        } catch (Exception e) {
            System.err.println("Error al obtener chats: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }


    public ResponseEntity<List<MensajeDTO>> obtenerHistorialMensajes(String remitente, String destinatario) {
        Optional<Chat> chat = chatRepositorio.findChatByUsers(remitente, destinatario);

        if (chat.isPresent()) {
            List<MensajeDTO> mensajes = mensajeRepositorio.findById_Chat_IdOrderById_FechahoraAsc(chat.get().getId())
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
