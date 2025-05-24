package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.periodoETS;
import com.example.PruebaCRUD.DTO.PeriodoFechas;
import com.example.PruebaCRUD.DTO.Saes.PeriodosETSProjectionSaes;
import com.example.PruebaCRUD.DTO.TiempoParaETS;
import com.example.PruebaCRUD.Repositories.periodoETSRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
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
         String periodo = crearPeriodo(); // por ejemplo: "25/2"

         List<Object[]> fechas = periodRepo.findFechasByPeriodo(periodo);

         if (fechas.isEmpty()) {
             return new TiempoParaETS("Aún no está programado el periodo de ETS.");
         }

         // Convertimos a objetos de fecha y ordenamos por fechaInicio
         List<PeriodoFechas> listaPeriodos = fechas.stream().map(f -> {
             LocalDate inicio = LocalDate.parse(f[0].toString());
             LocalDate fin = LocalDate.parse(f[1].toString());
             return new PeriodoFechas(inicio, fin);
         }).sorted(Comparator.comparing(PeriodoFechas::getFechaInicio)).toList();

         for (PeriodoFechas periodoETS : listaPeriodos) {
             if (!diaActual.isBefore(periodoETS.getFechaInicio()) && !diaActual.isAfter(periodoETS.getFechaFin())) {
                 return new TiempoParaETS("¡Ya estás en el periodo de ETS!");
             } else if (diaActual.isBefore(periodoETS.getFechaInicio())) {
                 long diasDeDiferencia = ChronoUnit.DAYS.between(diaActual, periodoETS.getFechaInicio());
                 if (diasDeDiferencia == 0) {
                     return new TiempoParaETS("¡Hoy comienzan los ETS!");
                 } else {
                     return new TiempoParaETS("Falta(n) " + diasDeDiferencia + " día(s) para el próximo periodo de ETS.");
                 }
             }
         }

         // Si ya pasaron todos los periodos registrados
         return new TiempoParaETS("Los periodos de ETS ya han finalizado. El siguiente periodo escolar aún no ha sido programado.");
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
