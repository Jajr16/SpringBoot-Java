package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.*;
import com.example.PruebaCRUD.BD.PKCompuesta.AplicaPK;
import com.example.PruebaCRUD.BD.PKCompuesta.SalonETSPK;
import com.example.PruebaCRUD.CryptAES.AES;
import com.example.PruebaCRUD.DTO.Saes.NewETSDTOSaes;
import com.example.PruebaCRUD.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@Service
public class ETSService {
    HashMap<String, Object> datos = new HashMap<>();

    private final SalonRepository salonRepository;
    private final ETSRepository etsRepository;
    private final SalonETSRepository salonETSRepository;
    private final periodoETSRepository periodoETSRepository;
    private final TurnoRepository turnoRepository;
    private final UnidadAprendizajeRepository unidadAprendizajeRepository;
    private final AplicaRepository aplicaRepository;
    private final DocenteRepository docenteRepository;
    private final PersonaRepository personaRepository;

    @Autowired
    public ETSService(SalonRepository salonRepository, ETSRepository etsRepository,
                      SalonETSRepository salonETSRepository, com.example.PruebaCRUD.Repositories.periodoETSRepository
                                  periodoETSRepository, TurnoRepository turnoRepository, UnidadAprendizajeRepository
                                  unidadAprendizajeRepository, AplicaRepository aplicaRepository, DocenteRepository
                                  docenteRepository, PersonaRepository personaRepository) {
        this.salonRepository = salonRepository;
        this.etsRepository = etsRepository;
        this.salonETSRepository = salonETSRepository;
        this.periodoETSRepository = periodoETSRepository;
        this.turnoRepository = turnoRepository;
        this.unidadAprendizajeRepository = unidadAprendizajeRepository;
        this.aplicaRepository = aplicaRepository;
        this.docenteRepository = docenteRepository;
        this.personaRepository = personaRepository;
    }

    @Transactional
    public ResponseEntity<Object> newETS(NewETSDTOSaes ets) throws Exception {
        datos = new HashMap<>();

        if (ets.getCupo() == null || ets.getFecha() == null || ets.getDuracion() == null
                || ets.getIdPeriodo() == null
                || ets.getTurno() == null || ets.getTurno().isEmpty()
                || ets.getIdUA() == null || ets.getIdUA().isEmpty()) {
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

        ETS newETS = etsRepository.save(nets);

        if (ets.getDocenteCURP() != null) {
            Optional<Persona> persona = personaRepository.findPersonaByCURP(AES.Desencriptar(ets.getDocenteCURP()));
            Optional<PersonalAcademico> docente = docenteRepository.findByCURP(persona.get());

            if (docente.isEmpty()) {
                datos.put("Error", true);
                datos.put("message", "El ETS o el docente no existen.");
                return new ResponseEntity<>(datos, HttpStatus.NOT_FOUND);
            }

            AplicaPK napk = new AplicaPK();
            napk.setDocenteRFC(docente.get().getRFC());
            napk.setIdETS(newETS.getIdETS());

            Aplica newaplica = new Aplica();
            newaplica.setId(napk);
            newaplica.setDocenteRFC(docente.get());
            newaplica.setIdETS(newETS);
            newaplica.setTitular(ets.isTitular());

            aplicaRepository.save(newaplica);

        }

        if (ets.getSalon() != null) {
            Optional<Salon> salon = salonRepository.findByNumSalon(ets.getSalon());

            if (salon.isEmpty()) {
                datos.put("Error", true);
                datos.put("message", "El salon no existe.");
                return new ResponseEntity<>(datos, HttpStatus.NOT_FOUND);
            }

            SalonETSPK setspk = new SalonETSPK();
            setspk.setIdETS(newETS.getIdETS());
            setspk.setNumSalon(salon.get().getNumSalon());

            SalonETS newSalonETS = new SalonETS();
            newSalonETS.setId(setspk);
            newSalonETS.setNumSalon(salon.get());
            newSalonETS.setIdETS(newETS);

            salonETSRepository.save(newSalonETS);
        }

        datos.put("message", "El ETS fue registrado exitosamente.");

        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }


}
