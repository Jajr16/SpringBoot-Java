package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.IngresoInstalacion;
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

    @Transactional
    public List<IngresoInstalacionDTO> registrarEntrada(String boleta, String fechaStr, String horaStr, Integer idETS) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            Date fecha = dateFormat.parse(fechaStr);
            Time hora = new Time(timeFormat.parse(horaStr).getTime());

            // 1. Verificar si ya existe registro para hoy
            List<IngresoInstalacion> registrosHoy = ingresoInstalacionRepository
                    .findByBoletaAndFechaAndIdETS(boleta, fecha, idETS);

            if (!registrosHoy.isEmpty()) {
                throw new RuntimeException("La asistencia ya fue registrada hoy para este ETS");
            }

            // 2. Verificar inscripción
            List<IngresoInstalacionDTO> inscripcion = ingresoInstalacionRepository
                    .findAlumnosInscritosETS(boleta, idETS);

            if (inscripcion.isEmpty()) {
                throw new RuntimeException("El alumno no está inscrito en este ETS");
            }

            // 3. Registrar asistencia
            ingresoInstalacionRepository.saveAttendance(
                    boleta,
                    idETS,
                    fecha,
                    hora
            );

            return inscripcion;
        } catch (ParseException e) {
            throw new RuntimeException("Formato de fecha/hora inválido");
        }
    }
}