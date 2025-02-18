package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.DTO.DetailETSDTO;
import com.example.PruebaCRUD.DTO.ETSDTO;
import com.example.PruebaCRUD.DTO.Saes.ETSDTOSaes;
import com.example.PruebaCRUD.DTO.SalonesDTO;
import com.example.PruebaCRUD.Repositories.ETSRepository;
import com.example.PruebaCRUD.Repositories.SalonETSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

 /**
 * Clase que contendrá la lógica que para realizar las funciones principales de los endpoints
 */
@Service // Anotación que indica que esta clase es un servicio de negocio
public class ETSDetailsService {
    private final SalonETSRepository salonetsRepository;
    private final ETSRepository etsRepository;

    @Autowired // Notación que permite inyectar dependencias, en este caso, SalonETSRepository y ETSRepository
    public ETSDetailsService(SalonETSRepository salonetsRepository, ETSRepository etsRepository) {
        this.salonetsRepository = salonetsRepository;
        this.etsRepository = etsRepository;
    }

    public List<ETSDTOSaes> detailAdminETS() {
        return etsRepository.findETS();
    }

    public DetailETSDTO detallesETS(Integer ets){
        // Obtiene un ETS por ID
        Optional<ETSDTO> result = etsRepository.findById_ETS(ets);

        if (result.isEmpty()) {
            throw new RuntimeException("No se encontraron ETS");
        }

        // Guarda todos los datos con respecto al DTO de ETS
        ETSDTO detailETS =  new ETSDTO(
                result.get().getIdETS(),
                result.get().getUnidadAprendizaje(),
                result.get().getTipoETS(),
                result.get().getIdPeriodo(),
                result.get().getTurno(),
                result.get().getFecha(),
                result.get().getCupo(),
                result.get().getDuracion()
        );

        // Busca salones asignados al ETS
        List<SalonesDTO> Salon = salonetsRepository.findByIdETSSETS(ets);

        if(Salon.isEmpty()) { // Si no encuentra ningún salón entra aquí
            // Devuelve los detalles del ETS
            return new DetailETSDTO(detailETS);
        }

        // Devuelve los detalles del ETS junto con sus salones asignados
        return new DetailETSDTO(
                detailETS,
                Salon
        );
    }
}
