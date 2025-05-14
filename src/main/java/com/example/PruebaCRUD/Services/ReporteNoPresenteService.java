package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.ETS;
import com.example.PruebaCRUD.BD.IngresoSalon;
import com.example.PruebaCRUD.BD.InscripcionETS;
import com.example.PruebaCRUD.BD.PKCompuesta.BoletaETSPK;
import com.example.PruebaCRUD.BD.PKCompuesta.InscripcionETSPK;
import com.example.PruebaCRUD.Repositories.ETSRepositorio;
import com.example.PruebaCRUD.Repositories.IngresoSalonRepositorio;
import com.example.PruebaCRUD.Repositories.InscripcionETSRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;


@Service
public class ReporteNoPresenteService {

    @Autowired
    private InscripcionETSRepositorio inscripcionETSRepository;

    @Autowired
    private ETSRepositorio etsRepositorio;

    @Autowired
    private IngresoSalonRepositorio ingresoSalonRepositorio;

    private final ZoneId mexicoCityZone = ZoneId.of("America/Mexico_City");

    @Scheduled(fixedRate = 5000)
    public void verificarYCrearIngresosRetrasados() {
        List<Object[]> distinctInscripciones = inscripcionETSRepository.findDistinctBoletaIdets();

        for (Object[] inscripcion : distinctInscripciones) {
            String boleta = (String) inscripcion[0];
            Integer idets = (Integer) inscripcion[1];

            System.out.println(boleta);
            System.out.println(idets);

            Optional<ETS> etsOptional = etsRepositorio.findById(idets);

            etsOptional.ifPresent(ets -> {
                LocalDate fechaEts = ets.getFecha().toInstant().atZone(mexicoCityZone).toLocalDate();
                System.out.println("cosa rara " + fechaEts);
                LocalTime horaEts = ets.getHora().toLocalTime();
                LocalDateTime etsDateTime = LocalDateTime.of(fechaEts, horaEts);
                LocalDateTime now = LocalDateTime.now(mexicoCityZone);
                LocalDateTime delayDateTime = etsDateTime.plusHours(2).plusMinutes(1);

                if (now.isAfter(delayDateTime)) {

                    if (!ingresoSalonRepositorio.verificarIngresoFuncion(boleta, idets)) {
                        IngresoSalon nuevoIngreso = new IngresoSalon();
                        BoletaETSPK boletaETSPK = new BoletaETSPK();
                        boletaETSPK.setBoleta(boleta);
                        boletaETSPK.setIdets(idets);
                        nuevoIngreso.setId(boletaETSPK);

                        InscripcionETS inscripcionETS = new InscripcionETS();
                        InscripcionETSPK inscripcionETSPK = new InscripcionETSPK();
                        inscripcionETSPK.setBoleta(boleta);
                        inscripcionETSPK.setIdETS(idets);
                        inscripcionETS.setId(inscripcionETSPK);
                        nuevoIngreso.setInscripcionETS(inscripcionETS);

                        nuevoIngreso.setFecha(null);
                        nuevoIngreso.setHora(null);
                        nuevoIngreso.setDocente(null);
                        nuevoIngreso.setTipo(null);
                        ingresoSalonRepositorio.save(nuevoIngreso);
                        System.out.println("Creando ingreso_salon para boleta: " + boleta + ", idets: " + idets);
                    }
                }
            });
        }
    }
}

