package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.*;
import com.example.PruebaCRUD.BD.PKCompuesta.AplicaPK;
import com.example.PruebaCRUD.BD.PKCompuesta.SalonETSPK;
import com.example.PruebaCRUD.CryptAES.AES;
import com.example.PruebaCRUD.DTO.DetalleETSDTO;
import com.example.PruebaCRUD.DTO.ETSDTO;
import com.example.PruebaCRUD.DTO.Saes.ETSDTOSaes;
import com.example.PruebaCRUD.DTO.Saes.NuevoETSDTOSaes;
import com.example.PruebaCRUD.DTO.SalonesDTO;
import com.example.PruebaCRUD.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

 /**
 * Clase que contendrá la lógica que para realizar las funciones principales de los endpoints
 */
@Service // Anotación que indica que esta clase es un servicio de negocio
public class ETSServicio {
    HashMap<String, Object> datos = new HashMap<>();

    private final SalonRepositorio salonRepositorio;
    private final ETSRepositorio etsRepositorio;
    private final SalonETSRepositorio salonETSRepositorio;
    private final periodoETSRepositorio periodoETSRepositorio;
    private final TurnoRepositorio turnoRepositorio;
    private final UnidadAprendizajeRepositorio unidadAprendizajeRepositorio;
    private final AplicaRepositorio aplicaRepositorio;
    private final PersonalAcademicoRepositorio personalAcademicoRepositorio;
    private final PersonaRepositorio personaRepositorio;

    @Autowired // Notación que permite inyectar dependencias
    public ETSServicio(SalonRepositorio salonRepositorio, ETSRepositorio etsRepositorio,
                       SalonETSRepositorio salonETSRepositorio, periodoETSRepositorio
                                   periodoETSRepositorio, TurnoRepositorio turnoRepositorio, UnidadAprendizajeRepositorio
                                   unidadAprendizajeRepositorio, AplicaRepositorio aplicaRepositorio, PersonalAcademicoRepositorio
                               personalAcademicoRepositorio, PersonaRepositorio personaRepositorio) {
        this.salonRepositorio = salonRepositorio;
        this.etsRepositorio = etsRepositorio;
        this.salonETSRepositorio = salonETSRepositorio;
        this.periodoETSRepositorio = periodoETSRepositorio;
        this.turnoRepositorio = turnoRepositorio;
        this.unidadAprendizajeRepositorio = unidadAprendizajeRepositorio;
        this.aplicaRepositorio = aplicaRepositorio;
        this.personalAcademicoRepositorio = personalAcademicoRepositorio;
        this.personaRepositorio = personaRepositorio;
    }

    @Transactional // Notación que indica que si algo falla, hace un rollback a todas las transacciones ya hechas
    public ResponseEntity<Object> nuevoETS(NuevoETSDTOSaes ets) throws Exception {
        datos = new HashMap<>(); // Variable que contendrá los datos a devolver al cliente

        if (ets.getCupo() == null || ets.getFecha() == null || ets.getHora() == null || ets.getDuracion() == null
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

        // Busca registros de acuerdo a cada columna indicada en findBy
        Optional<periodoETS> pets = periodoETSRepositorio.findById(ets.getIdPeriodo());
        Optional<Turno> turno = turnoRepositorio.findByNombre(ets.getTurno());
        Optional<UnidadAprendizaje> uapren = unidadAprendizajeRepositorio.findById(ets.getIdUA());

        // Formato de fecha para operar con la BD
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaETS = LocalDate.parse(ets.getFecha(), formato);
        Date fecha = java.sql.Date.valueOf(fechaETS);

        LocalTime hora = LocalTime.parse(ets.getHora(), DateTimeFormatter.ofPattern("HH:mm"));
        Time horaSQL = Time.valueOf(hora);

        // Se crea una nueva instancia de ETS con los datos recibidos
        ETS nets = new ETS(pets.get(), turno.get(), fecha, horaSQL, ets.getCupo(), uapren.get(), ets.getDuracion());

        // Se crea un nuevo registro de ETS
        ETS nuevoETS = etsRepositorio.save(nets);

        if (ets.getDocenteCURP() != null) { // Si el docente asignado al ETS no es nulo entra aquí
            // Busca un registro que coincida con la persona que el cliente mandó (tanto que exista como que sea docente)
            Optional<Persona> persona = personaRepositorio.findPersonaByCURP(AES.Desencriptar(ets.getDocenteCURP()));
            Optional<PersonalAcademico> docente = personalAcademicoRepositorio.findByCURP(persona.get());

            if (docente.isEmpty()) {
                datos.put("Error", true);
                datos.put("message", "El ETS o el docente no existen.");
                return new ResponseEntity<>(datos, HttpStatus.NOT_FOUND);
            }

            /**
             * Para llevar a cabo un regsitro de una tabla con llaves foráneas y llave primaria compuesta
             * debes de llenar todas las variables que contiene la clase principal
             * Aplica, estos incluyen:
             *      - AplicaPK (Instancia de otra clase)
             *      - PersonalAcademico (Instancia de otra clase)
             *      - ETS (Instancia de otra clase)
             */
            AplicaPK napk = new AplicaPK();
            napk.setDocenteRFC(docente.get().getRFC()); // Se asigna el RFC
            napk.setIdETS(nuevoETS.getIdETS()); // Se asigna el ETS

            /**
             * Se hace la instancia de Aplica (tabla princiapl), como se mencionó, se deben de asignar todas
             * las variables de dicha clase, las cuales son instancias de otras clases.
             */
            Aplica nuevoCoordinador = new Aplica();
            nuevoCoordinador.setId(napk);
            nuevoCoordinador.setDocenteRFC(docente.get());
            nuevoCoordinador.setIdETS(nuevoETS);
            nuevoCoordinador.setTitular(ets.isTitular());

            // Se guarda el registro anterior
            aplicaRepositorio.save(nuevoCoordinador);

        }

        if (ets.getSalon() != null) {// Si el salon asignado al ETS no es nulo entra aquí
            // Busca un registro que coincida con el salón que el cliente mandó
            Optional<Salon> salon = salonRepositorio.findByNumSalon(ets.getSalon());

            if (salon.isEmpty()) {
                datos.put("Error", true);
                datos.put("message", "El salon no existe.");
                return new ResponseEntity<>(datos, HttpStatus.NOT_FOUND);
            }

            /**
             * Para llevar a cabo un regsitro de una tabla con llaves foráneas y llave primaria compuesta
             * debes de llenar todas las variables que contiene la clase princiapl
             * SalonETS, estos incluyen:
             *      - SalonETSPK (Instancia de otra clase)
             *      - Salon (Instancia de otra clase)
             *      - ETS (Instancia de otra clase)
             */
            SalonETSPK asignarspk = new SalonETSPK();
            asignarspk.setIdETS(nuevoETS.getIdETS());
            asignarspk.setNumSalon(salon.get().getNumSalon());

            /**
             * Se hace la instancia de SalonETS (tabla princiapl), como se mencionó, se deben de asignar todas
             * las variables de dicha clase, las cuales son instancias de otras clases.
             */
            SalonETS nuevoSalonETS = new SalonETS();
            nuevoSalonETS.setId(asignarspk);
            nuevoSalonETS.setNumSalon(salon.get());
            nuevoSalonETS.setIdETS(nuevoETS);

            // Se guarda el registro anterior
            salonETSRepositorio.save(nuevoSalonETS);
        }

        // Se regresa un mensaje al cliente
        datos.put("message", "El ETS fue registrado exitosamente.");

        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }

     public List<ETSDTOSaes> detallesAdminETS() {
         return etsRepositorio.findETS();
     }

     public DetalleETSDTO detallesETS(Integer ets){
         // Obtiene un ETS por ID
         Optional<ETSDTO> resultado = etsRepositorio.findById_ETS(ets);

         if (resultado.isEmpty()) {
             throw new RuntimeException("No se encontraron ETS");
         }

         // Guarda todos los datos con respecto al DTO de ETS
         ETSDTO detallesETS =  new ETSDTO(
                 resultado.get().getIdETS(),
                 resultado.get().getUnidadAprendizaje(),
                 resultado.get().getTipoETS(),
                 resultado.get().getIdPeriodo(),
                 resultado.get().getTurno(),
                 resultado.get().getFecha(),
                 resultado.get().getCupo(),
                 resultado.get().getDuracion(),
                 resultado.get().getHora()
         );

         // Busca salones asignados al ETS
         List<SalonesDTO> Salon = salonETSRepositorio.findByIdETSSETS(ets);

         if(Salon.isEmpty()) {
             // Devuelve los detalles del ETS
             return new DetalleETSDTO(detallesETS);
         }

         // Devuelve los detalles del ETS junto con sus salones asignados
         return new DetalleETSDTO(
                 detallesETS,
                 Salon
         );
     }

     public List<?> obtenerSalonesParaETS() {
         return this.salonRepositorio.findAllBy();
     }

}
