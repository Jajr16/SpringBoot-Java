package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.Aplica;
import com.example.PruebaCRUD.BD.PKCompuesta.AplicaPK;
import com.example.PruebaCRUD.BD.Reemplazo;
import com.example.PruebaCRUD.DTO.SolicitudReemplazoDTO;
import com.example.PruebaCRUD.Repositories.AplicaRepository;
import com.example.PruebaCRUD.Repositories.ReemplazoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Reemplazo crearSolicitudReemplazo(SolicitudReemplazoDTO solicitudDTO) {
        // 1. Verificar si existe la relación Aplica primero
        AplicaPK aplicaPK = new AplicaPK();
        aplicaPK.setIdETS(solicitudDTO.getIdETS());
        aplicaPK.setDocenteRFC(solicitudDTO.getDocenteRFC());

        Aplica aplica = aplicaRepository.findById(aplicaPK)
                .orElseThrow(() -> new RuntimeException("No existe la relación Aplica especificada"));

        // 2. Crear la entidad Reemplazo
        Reemplazo reemplazo = new Reemplazo();
        reemplazo.setId(aplicaPK);
        reemplazo.setReemplazoPK(aplica);
        reemplazo.setMotivo(solicitudDTO.getMotivo());
        reemplazo.setEstatus(0);

        // 3. Guardar el reemplazo
        return reemplazoRepository.save(reemplazo);
    }
}