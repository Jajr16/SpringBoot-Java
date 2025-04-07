package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.DTO.IngresoInstalacionDTO;
import com.example.PruebaCRUD.Repositories.IngresoInstalacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.sql.Time;

@Service
public class IngresoInstalacionService {

    @Autowired
    private IngresoInstalacionRepository ingresoInstalacionRepository;

    public List<IngresoInstalacionDTO> getAlumnosInscritosETS(String boleta) {
        return ingresoInstalacionRepository.findAlumnosInscritosETS(boleta);
    }

    @Transactional
    public List<IngresoInstalacionDTO> registrarEntrada(String boleta, String fechaStr, String horaStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            Date fecha = dateFormat.parse(fechaStr);
            Time hora = new Time(timeFormat.parse(horaStr).getTime());

            List<IngresoInstalacionDTO> inscripciones = getAlumnosInscritosETS(boleta);

            if (inscripciones.isEmpty()) {
                throw new RuntimeException("El alumno no tiene ETS inscritos");
            }

            for (IngresoInstalacionDTO inscripcion : inscripciones) {
                ingresoInstalacionRepository.saveAttendance(
                        inscripcion.getBoleta(),
                        Integer.parseInt(inscripcion.getIdETS()),
                        fecha,
                        hora
                );
            }

            return getAlumnosInscritosETS(boleta);
        } catch (ParseException e) {
            throw new RuntimeException("Formato de fecha/hora inválido");
        }
    }
}