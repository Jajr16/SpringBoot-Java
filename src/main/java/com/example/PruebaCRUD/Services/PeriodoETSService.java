package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.periodoETS;
import com.example.PruebaCRUD.DTO.Saes.PeriodosETSProjectionSaes;
import com.example.PruebaCRUD.DTO.TimeToETSDTO;
import com.example.PruebaCRUD.Repositories.periodoETSRepository;
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
public class PeriodoETSService {
    HashMap<String, Object> datos = new HashMap<>();

    private final periodoETSRepository periodRepo;

    @Autowired // Notación que permite inyectar dependencias
    public PeriodoETSService(periodoETSRepository periodoService) {
        this.periodRepo = periodoService;
    }

    public List<periodoETS> getPeriodos() {
        return periodRepo.findAll();
    }

     /**
      * Lógica para devolver los días faltantes para el periodo de ETS
      */
    public TimeToETSDTO getTimeToETS() {
        // Se obtiene el año y mes actual
        Integer año = LocalDate.now().getYear();
        Integer mes = LocalDate.now().getMonthValue();

        LocalDate today = LocalDate.now();

        //Se inicializa la variable que servirá para armar el periodo en el estamos actualmente
        String periodo = "";

        // Obtiene los últimos dos dígitos del año actual
        String año_abreviado = año.toString().substring(2);

        // Se arma el periodo
        if (mes >= 8 || mes <= 1) {
            periodo = año_abreviado.concat("/1");
        } else {
            periodo = año_abreviado.concat("/2");
        }

        // Se busca la fecha de inicio del periodo mediante el periodo
        String fechaETS = periodRepo.findFechaByPeriodo(periodo);
        LocalDate fechaETSLD = LocalDate.parse(fechaETS);

        // Se calcula la diferencia de días
        long diasDeDiferencia = ChronoUnit.DAYS.between(today, fechaETSLD);

        // Se crea la respuesta que se le entregará al cliente
        String response = "Faltan " + diasDeDiferencia + " días para el periodo de ETS.";

        // Se retorna la respuesta por medio de un DTO
        return new TimeToETSDTO(response);
    }

     /**
      * Lógica para la alta de un nuevo periodo
      * @param periodoETS variable de periodoETS la cuál contiene todos los datos de la tabla periodoETS
      */
    public ResponseEntity<Object> newPeriodo(periodoETS periodoETS) {
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

    public List<PeriodosETSProjectionSaes> obtenerPeriodos() {
        return periodRepo.findAllBy();
    }
}
