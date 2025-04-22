package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.Aplica;
import com.example.PruebaCRUD.BD.PKCompuesta.AplicaPK;
import com.example.PruebaCRUD.BD.Reemplazo;
import com.example.PruebaCRUD.DTO.SolicitudReemplazoDTO;
import com.example.PruebaCRUD.DTO.VerificacionSolicitudResponseDTO;
import com.example.PruebaCRUD.Repositories.AplicaRepository;
import com.example.PruebaCRUD.Repositories.ReemplazoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReemplazoService {

    private final ReemplazoRepository reemplazoRepository;
    private final AplicaRepository aplicaRepository;

    @Autowired
    public ReemplazoService(ReemplazoRepository reemplazoRepository,
                            AplicaRepository aplicaRepository) {
        this.reemplazoRepository = reemplazoRepository;
        this.aplicaRepository = aplicaRepository;
    }

    public List<SolicitudReemplazoDTO> obtenerTodasSolicitudes() {
        return reemplazoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public SolicitudReemplazoDTO crearSolicitudReemplazo(SolicitudReemplazoDTO solicitudDTO) {
        // 1. Verificar relación Aplica
        AplicaPK aplicaPK = new AplicaPK();
        aplicaPK.setIdETS(solicitudDTO.getIdETS());
        aplicaPK.setDocenteRFC(solicitudDTO.getDocenteRFC());

        Aplica aplica = aplicaRepository.findById(aplicaPK)
                .orElseThrow(() -> new RuntimeException("No existe la relación Aplica especificada"));

        // 2. Verificar solicitud pendiente
        Optional<Reemplazo> existingReemplazo = reemplazoRepository.findById(aplicaPK);
        if (existingReemplazo.isPresent() && existingReemplazo.get().getEstatus() == 0) {
            throw new RuntimeException("Ya existe una solicitud pendiente para este ETS. No puedes enviar otra hasta que se procese esta.");
        }

        // 3. Crear entidad Reemplazo
        Reemplazo reemplazo = new Reemplazo();
        reemplazo.setId(aplicaPK);
        reemplazo.setReemplazoPK(aplica);
        reemplazo.setMotivo(solicitudDTO.getMotivo());
        reemplazo.setEstatus(0); // 0 = PENDIENTE

        // 4. Guardar
        Reemplazo savedReemplazo = reemplazoRepository.save(reemplazo);

        // 5. Devolver DTO
        return new SolicitudReemplazoDTO(
                savedReemplazo.getId().getIdETS(),
                savedReemplazo.getId().getDocenteRFC(),
                savedReemplazo.getMotivo(),
                "PENDIENTE"
        );
    }

    @Transactional(readOnly = true)
    public List<SolicitudReemplazoDTO> obtenerSolicitudesPendientes() {
        List<Reemplazo> reemplazos = reemplazoRepository.findByEstatus(0); // 0 = PENDIENTE
        return reemplazos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private SolicitudReemplazoDTO convertToDTO(Reemplazo reemplazo) {
        String estado = reemplazo.getEstatus() == 0 ? "PENDIENTE" :
                reemplazo.getEstatus() == 1 ? "APROBADO" : "RECHAZADO";

        return new SolicitudReemplazoDTO(
                reemplazo.getId().getIdETS(),
                reemplazo.getId().getDocenteRFC(),
                reemplazo.getMotivo(),
                estado
        );
    }

    @Transactional(readOnly = true)
    public VerificacionSolicitudResponseDTO verificarSolicitudPendiente(Integer etsId, String docenteRFC) {
        AplicaPK aplicaPK = new AplicaPK();
        aplicaPK.setIdETS(etsId);
        aplicaPK.setDocenteRFC(docenteRFC);

        Optional<Reemplazo> reemplazo = reemplazoRepository.findById(aplicaPK);

        if (reemplazo.isPresent() && reemplazo.get().getEstatus() == 0) {
            Reemplazo r = reemplazo.get();
            return new VerificacionSolicitudResponseDTO(
                    true,
                    new SolicitudReemplazoDTO(
                            r.getId().getIdETS(),
                            r.getId().getDocenteRFC(),
                            r.getMotivo(),
                            "PENDIENTE"
                    )
            );
        }

        return new VerificacionSolicitudResponseDTO(false, null);
    }

    @Transactional
    public void aprobarReemplazo(Integer idETS, String docenteRFC, String docenteReemplazo) {
        AplicaPK aplicaPK = new AplicaPK(idETS, docenteRFC);

        Reemplazo reemplazo = reemplazoRepository.findById(aplicaPK)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        // Concatenar el reemplazo al motivo existente
        String nuevoMotivo = reemplazo.getMotivo() + " | Reemplazo: " + docenteReemplazo;
        reemplazo.setMotivo(nuevoMotivo);

        reemplazo.setEstatus(1); // 1 = APROBADO
        reemplazoRepository.save(reemplazo);
    }

    @Transactional
    public void rechazarReemplazo(Integer idETS, String docenteRFC, String motivoRechazo) {
        // 1. Verificar que la solicitud existe y está pendiente
        AplicaPK aplicaPK = new AplicaPK(idETS, docenteRFC);

        Reemplazo reemplazo = reemplazoRepository.findById(aplicaPK)
                .orElseThrow(() -> new RuntimeException("No existe la solicitud de reemplazo especificada"));

        if (reemplazo.getEstatus() != 0) {
            throw new RuntimeException("La solicitud no está en estado PENDIENTE");
        }

        String motivoActualizado = reemplazo.getMotivo();
        if (motivoRechazo != null && !motivoRechazo.isEmpty()) {
            motivoActualizado += " | Rechazado porque: " + motivoRechazo;
        } else {
            motivoActualizado += " | Rechazado sin motivo especificado";
        }
        reemplazo.setMotivo(motivoActualizado);

        // 3. Cambiar estado a RECHAZADO (2)
        reemplazo.setEstatus(2); // 2 = RECHAZADO
        reemplazoRepository.save(reemplazo);
    }
}