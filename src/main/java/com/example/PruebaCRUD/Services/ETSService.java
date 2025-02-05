package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.ETS;
import com.example.PruebaCRUD.BD.Turno;
import com.example.PruebaCRUD.BD.UnidadAprendizaje;
import com.example.PruebaCRUD.BD.periodoETS;
import com.example.PruebaCRUD.DTO.NewETSDTOSaes;
import com.example.PruebaCRUD.Repositories.ETSRepository;
import com.example.PruebaCRUD.Repositories.TurnoRepository;
import com.example.PruebaCRUD.Repositories.UnidadAprendizajeRepository;
import com.example.PruebaCRUD.Repositories.periodoETSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@Service
public class ETSService {
    HashMap<String, Object> datos = new HashMap<>();

    private final ETSRepository etsRepository;
    private final periodoETSRepository periodoETSRepository;
    private final TurnoRepository turnoRepository;
    private final UnidadAprendizajeRepository unidadAprendizajeRepository;

    @Autowired
    public ETSService(ETSRepository etsRepository, com.example.PruebaCRUD.Repositories.periodoETSRepository periodoETSRepository, TurnoRepository turnoRepository, UnidadAprendizajeRepository unidadAprendizajeRepository) {
        this.etsRepository = etsRepository;
        this.periodoETSRepository = periodoETSRepository;
        this.turnoRepository = turnoRepository;
        this.unidadAprendizajeRepository = unidadAprendizajeRepository;
    }

    public ResponseEntity<Object> newETS(NewETSDTOSaes ets) {
        datos = new HashMap<>();

        System.out.println("EL ETS NUEVO ES " + ets);

        if (ets.getCupo() == null || ets.getFecha() == null || ets.getDuracion() == null
                || ets.getIdPeriodo() == null
                || ets.getTurno() == null || ets.getTurno().isEmpty()
                || ets.getIdUA() == null || ets.getIdUA().isEmpty()){
            datos = new HashMap<>();
            datos.put("Error", true);
            datos.put("message", "No puedes dejar ningún campo vacío.");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        Optional<periodoETS> pets = periodoETSRepository.findById(ets.getIdPeriodo());
        Optional<Turno> turno = turnoRepository.findByNombre(ets.getTurno());
        Optional<UnidadAprendizaje> uapren = unidadAprendizajeRepository.findById(ets.getIdUA());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaDate = LocalDate.parse(ets.getFecha(), formatter);
        Date fecha = java.sql.Date.valueOf(fechaDate);

        ETS nets = new ETS(pets.get(), turno.get(), fecha, ets.getCupo(), uapren.get(), ets.getDuracion());

        etsRepository.save(nets);
        datos.put("data", ets);
        datos.put("message", "El ETS fue registrado exitosamente.");

        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }


}
