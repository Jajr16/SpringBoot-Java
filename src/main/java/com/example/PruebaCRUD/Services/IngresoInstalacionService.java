package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.IngresoInstalacion;
import com.example.PruebaCRUD.DTO.IngresoInstalacionDTO;
import com.example.PruebaCRUD.Repositories.IngresoInstalacionRepository;
import com.example.PruebaCRUD.Repositories.InscripcionETSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.sql.Time;

@Service
public class IngresoInstalacionService {

    @Autowired
    private IngresoInstalacionRepository ingresoInstalacionRepository;

    @Autowired
    private InscripcionETSRepository inscripcionETSRepository; // Nuevo repositorio necesario

    @Transactional
    public List<IngresoInstalacionDTO> registrarEntrada(String boleta, String fechaStr, String horaStr, Integer idETS) {
        try {
            // Validación de parámetros
            if (boleta == null || boleta.isEmpty() || idETS == null) {
                throw new IllegalArgumentException("Boleta e ID ETS son requeridos");
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            Date fecha = dateFormat.parse(fechaStr);
            Time hora = new Time(timeFormat.parse(horaStr).getTime());

            // 1. Verificar si el ETS existe
            Integer etsExistente = ingresoInstalacionRepository.verificarExistenciaETS(idETS);
            if (etsExistente == null) {
                throw new IllegalStateException("El ETS con ID " + idETS + " no existe");
            }

            // 2. Verificar si ya existe registro para hoy
            List<IngresoInstalacion> registrosHoy = ingresoInstalacionRepository
                    .findByBoletaAndFechaAndIdETS(boleta, fecha, idETS);

            if (!registrosHoy.isEmpty()) {
                throw new IllegalStateException("La asistencia ya fue registrada hoy para este ETS");
            }

            // 3. Verificar inscripción (versión mejorada)
            List<IngresoInstalacionDTO> inscripcion = ingresoInstalacionRepository
                    .findAlumnosInscritosETS(boleta, idETS);

            if (inscripcion.isEmpty()) {
                throw new IllegalStateException("El alumno con boleta " + boleta +
                        " no está inscrito en el ETS con ID " + idETS);
            }

            // 4. Validar fecha del ETS (opcional, si tienes esta información)
            // Puedes agregar una consulta para verificar que la fecha del ETS coincida

            // 5. Registrar asistencia
            ingresoInstalacionRepository.saveAttendance(
                    boleta,
                    idETS,  // Asegúrate que coincida con el nombre del parámetro en @Query nativo
                    fecha,
                    hora
            );

            return inscripcion;
        } catch (ParseException e) {
            throw new IllegalArgumentException("Formato de fecha/hora inválido. Use yyyy-MM-dd para fecha y HH:mm:ss para hora");
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar asistencia: " + e.getMessage(), e);
        }
    }

    // Método adicional para verificación completa
    public boolean verificarInscripcionCompleta(String boleta, Integer idETS) {
        return !ingresoInstalacionRepository.findAlumnosInscritosETS(boleta, idETS).isEmpty();
    }
}