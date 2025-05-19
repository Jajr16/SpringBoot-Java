package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.periodoETS;
import com.example.PruebaCRUD.DTO.Saes.PeriodosETSProjectionSaes;
import com.example.PruebaCRUD.DTO.TiempoParaETS;
import com.example.PruebaCRUD.Repositories.periodoETSRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

 /**
 * Clase que contendrá la lógica que para realizar las funciones principales de los endpoints
 */
@Service // Anotación que indica que esta clase es un servicio de negocio
public class PeriodoETSServicio {
    HashMap<String, Object> datos = new HashMap<>();

    private final periodoETSRepositorio periodRepo;

    @Autowired // Notación que permite inyectar dependencias
    public PeriodoETSServicio(periodoETSRepositorio periodoService) {
        this.periodRepo = periodoService;
    }

    public List<periodoETS> obtenerPeriodos() {
        return periodRepo.findAll();
    }

     /**
      * Lógica para devolver los días faltantes para el periodo de ETS
      */
    public TiempoParaETS obtenerTiempoParaETS() {
        LocalDate diaActual = LocalDate.now();
        String periodo = crearPeriodo();

        // Se busca la fecha de inicio del periodo mediante el periodo
        List<String> fechaETS = periodRepo.findFechasByPeriodo(periodo);
        System.out.println("Las fechas obtenidas son: " + fechaETS);
        String listaFechas = fechaETS.get(0);

        String[] fechasSeparadas = listaFechas.split(",");

        LocalDate fechaInicioETS;
        LocalDate fechaFinETS;

        try {
            fechaInicioETS = LocalDate.parse(fechasSeparadas[0].trim());
            fechaFinETS = LocalDate.parse(fechasSeparadas[1].trim());

        } catch (java.time.format.DateTimeParseException e) {
            e.printStackTrace(); // O registra el error de otra manera
            return new TiempoParaETS("Error al parsear las fechas: " + e.getMessage());
        }

        String response = "";

        if (diaActual.isAfter(fechaInicioETS) && diaActual.isBefore(fechaFinETS)) {
            response = "Ya estás en el periodo de ETS!!!";
        } else if (diaActual.isAfter(fechaFinETS)) {
            response = "El periodo de ETS ya ha finalizado!!!";
        } else if (diaActual.isBefore(fechaInicioETS)) {
            // Se calcula la diferencia de días
            long diasDeDiferencia = ChronoUnit.DAYS.between(diaActual, fechaInicioETS);
            if (diasDeDiferencia < 0) {
                response = "Aún no está programado el siguiente periodo de ETS.";
            } else {
                response = "Faltan " + diasDeDiferencia + " días para el periodo de ETS.";
            }
        }


        // Se retorna la respuesta por medio de un DTO
        return new TiempoParaETS(response);
    }

     /**
      * Lógica para la alta de un nuevo periodo
      * @param periodoETS variable de periodoETS la cuál contiene todos los datos de la tabla periodoETS
      */
    public ResponseEntity<Object> nuevoPeriodo(periodoETS periodoETS) {
        datos = new HashMap<>();

        if (periodoETS.getPeriodo() == null || periodoETS.getPeriodo().isEmpty()
                || periodoETS.getFecha_Inicio() == null || periodoETS.getFecha_Fin() == null
                || periodoETS.getTipo() == '\0') {
            datos = new HashMap<>();
            datos.put("Error", true);
            datos.put("message", "No puedes dejar ningún campo vacío.");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        // Se verifica que no haya un registro existente con los datos recibidos
        Optional<periodoETS> response = periodRepo.findByPeriodoAndTipo(periodoETS.getPeriodo(), periodoETS.getTipo());

        if (response.isPresent()) { // Si sí existe ya un registro, entra aquí para devolver error y un mensaje.
            datos.put("Error", true);
            datos.put("message", "Ese periodo ya está registrada.");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        // Se guarda el nuevo periodo con los datos recibidos del cliente
        periodRepo.save(periodoETS);
        datos.put("data", periodoETS);
        datos.put("message", "El periodo fue registrado exitosamente.");

        // Se retorna una respuesta con los datos y un mensaje
        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }

    public List<PeriodosETSProjectionSaes> obtenerPeriodosSAES() {
        return periodRepo.findAllBy();
    }

    public static String crearPeriodo() {
        int año = LocalDate.now().getYear();
        int mes = LocalDate.now().getMonthValue();

        //Se inicializa la variable que servirá para armar el periodo en el estamos actualmente
        String periodo = "";

        // Obtiene los últimos dos dígitos del año actual
        String año_abreviado = Integer.toString(año).substring(2);

        // Se arma el periodo
        if (mes >= 8 || mes <= 1) {
            periodo = año_abreviado.concat("/1");
        } else {
            periodo = año_abreviado.concat("/2");
        }

        return periodo;
    }
}
