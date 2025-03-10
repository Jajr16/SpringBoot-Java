package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.Mensaje;
import com.example.PruebaCRUD.BD.PKCompuesta.MensajePK;
import com.example.PruebaCRUD.BD.Usuario;
import com.example.PruebaCRUD.DTO.ListadoUsuariosDTO;
import com.example.PruebaCRUD.Repositories.MensajeRepository;
import com.example.PruebaCRUD.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Clase que contendrá la lógica que para realizar las funciones principales de los endpoints
 */
@Service // Anotación que indica que esta clase es un servicio de negocio
public class MensajeService {
    private MensajeRepository mensajeRepository;
    private UsuarioRepository usuarioRepository;
    private FirebaseService firebaseService;

    @Autowired
    public MensajeService(MensajeRepository mensajeRepository, UsuarioRepository usuarioRepository,
                          FirebaseService firebaseService) {
        this.mensajeRepository = mensajeRepository;
        this.usuarioRepository = usuarioRepository;
        this.firebaseService = firebaseService;
    }

    public void enviarMensaje(Usuario remitente, Usuario destinatario, String contenido) {
        MensajePK mensajePK = new MensajePK(remitente, destinatario, LocalDate.now());
        Mensaje mensaje = new Mensaje(mensajePK, contenido);
        mensajeRepository.save(mensaje);

        // Enviar notificación al destinatario
        firebaseService.enviarNoti(destinatario.getUsuario(), "Nuevo mensaje", contenido);
    }

    public List<ListadoUsuariosDTO> getUsers() {
        return this.mensajeRepository.findUsers();
    }

}
