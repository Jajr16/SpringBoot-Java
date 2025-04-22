package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.*;
import com.example.PruebaCRUD.BD.PKCompuesta.AplicaPK;
import com.example.PruebaCRUD.BD.PKCompuesta.SalonETSPK;
import com.example.PruebaCRUD.CryptAES.AES;
import com.example.PruebaCRUD.DTO.DetailETSDTO;
import com.example.PruebaCRUD.DTO.ETSDTO;
import com.example.PruebaCRUD.DTO.Saes.ETSDTOSaes;
import com.example.PruebaCRUD.DTO.Saes.NewETSDTOSaes;
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
public class ETSService {
    HashMap<String, Object> datos = new HashMap<>();

    private final SalonRepository salonRepository;
    private final ETSRepository etsRepository;
    private final SalonETSRepository salonETSRepository;
    private final periodoETSRepository periodoETSRepository;
    private final TurnoRepository turnoRepository;
    private final UnidadAprendizajeRepository unidadAprendizajeRepository;
    private final AplicaRepository aplicaRepository;
    private final PersonalAcademicoRepository personalAcademicoRepository;
    private final PersonaRepository personaRepository;

    @Autowired // Notación que permite inyectar dependencias
    public ETSService(SalonRepository salonRepository, ETSRepository etsRepository,
                      SalonETSRepository salonETSRepository, com.example.PruebaCRUD.Repositories.periodoETSRepository
                                  periodoETSRepository, TurnoRepository turnoRepository, UnidadAprendizajeRepository
                                  unidadAprendizajeRepository, AplicaRepository aplicaRepository, PersonalAcademicoRepository
                                  personalAcademicoRepository, PersonaRepository personaRepository) {
        this.salonRepository = salonRepository;
        this.etsRepository = etsRepository;
        this.salonETSRepository = salonETSRepository;
        this.periodoETSRepository = periodoETSRepository;
        this.turnoRepository = turnoRepository;
        this.unidadAprendizajeRepository = unidadAprendizajeRepository;
        this.aplicaRepository = aplicaRepository;
        this.personalAcademicoRepository = personalAcademicoRepository;
        this.personaRepository = personaRepository;
    }

    @Transactional // Notación que indica que si algo falla, hace un rollback a todas las transacciones ya hechas
    public ResponseEntity<Object> newETS(NewETSDTOSaes ets) throws Exception {
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
        Optional<periodoETS> pets = periodoETSRepository.findById(ets.getIdPeriodo());
        Optional<Turno> turno = turnoRepository.findByNombre(ets.getTurno());
        Optional<UnidadAprendizaje> uapren = unidadAprendizajeRepository.findById(ets.getIdUA());

        // Formato de fecha para operar con la BD
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaDate = LocalDate.parse(ets.getFecha(), formatter);
        Date fecha = java.sql.Date.valueOf(fechaDate);

        LocalTime hora = LocalTime.parse(ets.getHora(), DateTimeFormatter.ofPattern("HH:mm"));
        Time horaSQL = Time.valueOf(hora);

        // Se crea una nueva instancia de ETS con los datos recibidos
        ETS nets = new ETS(pets.get(), turno.get(), fecha, horaSQL, ets.getCupo(), uapren.get(), ets.getDuracion());

        // Se crea un nuevo registro de ETS
        ETS newETS = etsRepository.save(nets);

        if (ets.getDocenteCURP() != null) { // Si el docente asignado al ETS no es nulo entra aquí
            // Busca un registro que coincida con la persona que el cliente mandó (tanto que exista como que sea docente)
            Optional<Persona> persona = personaRepository.findPersonaByCURP(AES.Desencriptar(ets.getDocenteCURP()));
            Optional<PersonalAcademico> docente = personalAcademicoRepository.findByCURP(persona.get());

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
            napk.setIdETS(newETS.getIdETS()); // Se asigna el ETS

            /**
             * Se hace la instancia de Aplica (tabla princiapl), como se mencionó, se deben de asignar todas
             * las variables de dicha clase, las cuales son instancias de otras clases.
             */
            Aplica newaplica = new Aplica();
            newaplica.setId(napk);
            newaplica.setDocenteRFC(docente.get());
            newaplica.setIdETS(newETS);
            newaplica.setTitular(ets.isTitular());

            // Se guarda el registro anterior
            aplicaRepository.save(newaplica);

        }

        if (ets.getSalon() != null) {// Si el salon asignado al ETS no es nulo entra aquí
            // Busca un registro que coincida con el salón que el cliente mandó
            Optional<Salon> salon = salonRepository.findByNumSalon(ets.getSalon());

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
            SalonETSPK setspk = new SalonETSPK();
            setspk.setIdETS(newETS.getIdETS());
            setspk.setNumSalon(salon.get().getNumSalon());

            /**
             * Se hace la instancia de SalonETS (tabla princiapl), como se mencionó, se deben de asignar todas
             * las variables de dicha clase, las cuales son instancias de otras clases.
             */
            SalonETS newSalonETS = new SalonETS();
            newSalonETS.setId(setspk);
            newSalonETS.setNumSalon(salon.get());
            newSalonETS.setIdETS(newETS);

            // Se guarda el registro anterior
            salonETSRepository.save(newSalonETS);
        }

        // Se regresa un mensaje al cliente
        datos.put("message", "El ETS fue registrado exitosamente.");

        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }

     public List<ETSDTOSaes> detailAdminETS() {
         return etsRepository.findETS();
     }

     public DetailETSDTO detallesETS(Integer ets){
         // Obtiene un ETS por ID
         Optional<ETSDTO> result = etsRepository.findById_ETS(ets);

         if (result.isEmpty()) {
             throw new RuntimeException("No se encontraron ETS");
         }

         // Guarda todos los datos con respecto al DTO de ETS
         ETSDTO detailETS =  new ETSDTO(
                 result.get().getIdETS(),
                 result.get().getUnidadAprendizaje(),
                 result.get().getTipoETS(),
                 result.get().getIdPeriodo(),
                 result.get().getTurno(),
                 result.get().getFecha(),
                 result.get().getCupo(),
                 result.get().getDuracion(),
                 result.get().getHora()
         );

         // Busca salones asignados al ETS
         List<SalonesDTO> Salon = salonETSRepository.findByIdETSSETS(ets);

         if(Salon.isEmpty()) {
             // Devuelve los detalles del ETS
             return new DetailETSDTO(detailETS);
         }

         // Devuelve los detalles del ETS junto con sus salones asignados
         return new DetailETSDTO(
                 detailETS,
                 Salon
         );
     }

     public List<?> getSalonesToETS() {
         return this.salonRepository.findAllBy();
     }

}
