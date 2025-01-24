package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.ETS;
import com.example.PruebaCRUD.DTO.DetailETSDTO;
import com.example.PruebaCRUD.DTO.ETSDTO;
import com.example.PruebaCRUD.DTO.SalonesDTO;
import com.example.PruebaCRUD.Repositories.ETSRepository;
import com.example.PruebaCRUD.Repositories.SalonETSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ETSDetailsService {
    private final SalonETSRepository salonetsRepository;
    private final ETSRepository etsRepository;

    @Autowired
    public ETSDetailsService(SalonETSRepository salonetsRepository, ETSRepository etsRepository) {
        this.salonetsRepository = salonetsRepository;
        this.etsRepository = etsRepository;
    }

    public DetailETSDTO detallesETS(Integer ets){
        System.out.println("Consultando ETS con id: " + ets);
        Optional<ETSDTO> result = etsRepository.findById_ETS(ets);

        if (result.isEmpty()) {
            throw new RuntimeException("No se encontraron ETS");
        }

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

        List<SalonesDTO> Salon = salonetsRepository.findByIdETSSETS(ets);

        if(Salon.isEmpty()) {
            return new DetailETSDTO(detailETS);
        }

        return new DetailETSDTO(
                detailETS,
                Salon
        );
    }
}
