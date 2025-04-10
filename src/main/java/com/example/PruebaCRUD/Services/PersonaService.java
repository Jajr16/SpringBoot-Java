package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.*;
import com.example.PruebaCRUD.BD.PKCompuesta.CargoDocentePK;
import com.example.PruebaCRUD.DTO.*;
import com.example.PruebaCRUD.DTO.Saes.*;
import com.example.PruebaCRUD.Frames.DivisionFrames;
import com.example.PruebaCRUD.Repositories.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Clase que contendrá la lógica que para realizar las funciones principales de los endpoints
 */
@Service // Anotación que indica que esta clase es un servicio de negocio
public class PersonaService {
    HashMap<String, Object> datos = new HashMap<>();

    private final PersonaRepository personaRepository;
    private final SexoRepository sexoRepository;
    private final UnidadAcademicaRepository unidadAcademicaRepository;
    private final AlumnoRepository alumnoRepository;
    private final DocenteRepository docenteRepository;
    private final PersonalSeguridadRepository personalSeguridadRepository;
    private final TurnoRepository turnoRepository;
    private final CargoPSRepository cargoPSRepository;
    private final UsuarioRepository usuarioRepository;
    private final TipoPersonalRepository tipoPersonalRepository;
    private final PersonalAcademicoRepository personalAcademicoRepository;
    private final CargoRepository cargoRepository;
    private final CargoDocenteRepository cargoDocenteRepository;
    private final ProgramaAcademicoRepository programaAcademicoRepository;

    // Con esta inyección podremos hacer el CRUD de forma directa
    @Autowired // Notación que permite inyectar dependencias
    public PersonaService(PersonaRepository personaRepository, SexoRepository sexoRepository,
                          UnidadAcademicaRepository unidadAcademicaRepository,
                          AlumnoRepository alumnoRepository, DocenteRepository docenteRepository,
                          PersonalSeguridadRepository personalSeguridadRepository, TurnoRepository turnoRepository,
                          CargoPSRepository cargoPSRepository, UsuarioRepository usuarioRepository,
                          TipoPersonalRepository tipoPersonalRepository,
                          PersonalAcademicoRepository personalAcademicoRepository, CargoRepository cargoRepository,
                          CargoDocenteRepository cargoDocenteRepository,
                          ProgramaAcademicoRepository programaAcademicoRepository) {
        this.personaRepository = personaRepository;
        this.sexoRepository = sexoRepository;
        this.unidadAcademicaRepository = unidadAcademicaRepository;
        this.alumnoRepository = alumnoRepository;
        this.docenteRepository = docenteRepository;
        this.personalSeguridadRepository = personalSeguridadRepository;
        this.turnoRepository = turnoRepository;
        this.cargoPSRepository = cargoPSRepository;
        this.usuarioRepository = usuarioRepository;
        this.tipoPersonalRepository = tipoPersonalRepository;
        this.personalAcademicoRepository = personalAcademicoRepository;
        this.cargoRepository = cargoRepository;
        this.cargoDocenteRepository = cargoDocenteRepository;
        this.programaAcademicoRepository = programaAcademicoRepository;
    }

    // =================== ALUMNOS ======================
    // Función para traer a todos los alumnos
    public List<AlumnoDTOSaes> getAlumnos() {
        return alumnoRepository.findAllAsDTO();
    }

    //Función para crear a un nuevo alumno apartir de Excel
    @Transactional
    public ResponseEntity<Object> newVideoAlumno(NewVideoAlumnoDTOSaes newVideoAlumnoDTOSaes) throws IOException {

        HashMap<String, Object> datos = new HashMap<>();

        if (Stream.of(newVideoAlumnoDTOSaes.getBoleta(), newVideoAlumnoDTOSaes.getCredencial())
                .anyMatch(val -> val == null || val.isEmpty())) {
            throw new RuntimeException("Debes de llenar todos los campos.");
        }

        File carpeta = new File(new File("").getAbsolutePath()
                + "/src/main/java/com/example/PruebaCRUD/EntrenamientoIMG/"
                + newVideoAlumnoDTOSaes.getBoleta() + "/");

        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        Optional<UnidadAcademica> uaAlumno = this.unidadAcademicaRepository.findByNombre("ESCOM");

        try (FileInputStream archivo = new FileInputStream("src/main/java/com/example/PruebaCRUD/grupos_ESCOM.xlsx");
             Workbook libro = new XSSFWorkbook(archivo)) {

            DataFormatter formatter = new DataFormatter();

            for (Sheet hoja : libro) {
                for (Row fila : hoja) {
                    String boletaExcel = formatter.formatCellValue(fila.getCell(0));

                    if (boletaExcel.equals(newVideoAlumnoDTOSaes.getBoleta())) {

                        String nombre = fila.getCell(1).toString();
                        String apellido_p = fila.getCell(2).toString();
                        String apellido_m = fila.getCell(3).toString();
                        String correo = fila.getCell(4).toString();
                        String sexo = fila.getCell(5).toString();
                        String carrera = fila.getCell(6).toString();
                        String curp = formatter.formatCellValue(fila.getCell(7));

                        if (this.personaRepository.findPersonaByCURP(curp).isPresent()
                                || this.alumnoRepository.findByBoleta(newVideoAlumnoDTOSaes.getBoleta()).isPresent()) {
                            throw new RuntimeException("Ese Alumno ya ha sido registrado con anterioridad.");
                        }

                        Optional<Sexo> sexoRep = this.sexoRepository.findByNombre(sexo);
                        if (sexoRep.isEmpty()) throw new RuntimeException("Sexo no encontrado.");

                        Persona npersona = new Persona();
                        npersona.setCURP(curp);
                        npersona.setNombre(nombre);
                        npersona.setApellido_P(apellido_p);
                        npersona.setApellido_M(apellido_m);
                        npersona.setSexo(sexoRep.get());
                        npersona.setUnidadAcademica(uaAlumno.get());
                        personaRepository.save(npersona);

                        Optional<ProgramaAcademico> pa = this.programaAcademicoRepository.findBy(1, carrera);
                        if (pa.isEmpty()) throw new RuntimeException("Esa carrera no existe.");

                        Alumno nalumno = new Alumno();
                        nalumno.setBoleta(newVideoAlumnoDTOSaes.getBoleta());
                        nalumno.setCorreoI(correo);
                        nalumno.setImagenCredencial(newVideoAlumnoDTOSaes.getCredencial());
                        nalumno.setCURP(npersona);
                        nalumno.setIdPA(pa.get());
                        alumnoRepository.save(nalumno);

                        datos.put("message", "Alumno registrado con éxito.");
                        return new ResponseEntity<>(datos, HttpStatus.CREATED);
                    }
                }
            }

        }

        throw new RuntimeException("La boleta no se encontró en el Excel.");
    }

    // Función para crear un nuevo alumno
    @Transactional // Notación que indica que si algo falla, hace un rollback a todas las transacciones ya hechas
    public ResponseEntity<Object> newAlumno(NewAlumnoDTOSaes newAlumnoDTOSaes, MultipartFile multipartFile)
            throws IOException {
        datos = new HashMap<>();

        if (Stream.of(newAlumnoDTOSaes.getCurp(), newAlumnoDTOSaes.getBoleta(), newAlumnoDTOSaes.getNombre(),
                newAlumnoDTOSaes.getApellido_p(), newAlumnoDTOSaes.getApellido_m(), newAlumnoDTOSaes.getSexo(),
                newAlumnoDTOSaes.getCorreo(), newAlumnoDTOSaes.getCarrera(),
                newAlumnoDTOSaes.getCredencial()).anyMatch(val -> val == null || val.isEmpty())) {
            datos.put("Error", true);
            datos.put("message", "Debes de llenar todos los campos");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        File carpeta = new File(new File("").getAbsolutePath()
                + "/src/main/java/com/example/PruebaCRUD/EntrenamientoIMG/"
                + newAlumnoDTOSaes.getBoleta() + "/");

        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        File destino = new File(carpeta, Objects.requireNonNull(multipartFile.getOriginalFilename()));

        multipartFile.transferTo(destino);

        DivisionFrames.extractFrames(destino.getPath(), destino.getParent());

        Optional<UnidadAcademica> uaAlumno = this.unidadAcademicaRepository.findById(newAlumnoDTOSaes.getEscuela());

        if (uaAlumno.isEmpty()) {
            datos.put("Error", true);
            datos.put("message", "La escuela proporcionada no existe");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        Optional<Sexo> sexo = this.sexoRepository.findByNombre(newAlumnoDTOSaes.getSexo());

        if (sexo.isEmpty()) {
            datos.put("Error", true);
            datos.put("message", "El sexo proporcionado no es válido");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        Optional<Persona> exists = this.personaRepository.findPersonaByCURP(newAlumnoDTOSaes.getCurp());
        Optional<Alumno> existsA = this.alumnoRepository.findByBoleta(newAlumnoDTOSaes.getBoleta());

        if (exists.isPresent() || existsA.isPresent()) {
            datos.put("Error", true);
            datos.put("message", "Ese alumno ya ha sido registrado con anterioridad.");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        Persona npersona = new Persona();
        npersona.setCURP(newAlumnoDTOSaes.getCurp());
        npersona.setNombre(newAlumnoDTOSaes.getNombre());
        npersona.setApellido_P(newAlumnoDTOSaes.getApellido_p());
        npersona.setApellido_M(newAlumnoDTOSaes.getApellido_m());
        npersona.setSexo(sexo.get());
        npersona.setUnidadAcademica(uaAlumno.get());

        personaRepository.save(npersona);

        Optional<ProgramaAcademico> pa = this.programaAcademicoRepository.findBy(newAlumnoDTOSaes.getEscuela(),
                newAlumnoDTOSaes.getCarrera());

        if (pa.isEmpty()) {
            datos.put("Error", true);
            datos.put("message", "Esa carrera no existe.");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        Alumno nalumno = new Alumno();
        nalumno.setBoleta(newAlumnoDTOSaes.getBoleta());
        nalumno.setCURP(npersona);
        nalumno.setCorreoI(newAlumnoDTOSaes.getCorreo());
        nalumno.setIdPA(pa.get());
        nalumno.setImagenCredencial(newAlumnoDTOSaes.getCredencial());

        alumnoRepository.save(nalumno);

        datos.put("message", "Alumno registrado con éxito.");
        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }

    // =================== DOCENTES =====================
//    Función para traer a todos los docentes
    public List<DocentesDTOSaes> getDocentes() {
        return docenteRepository.findDocentes();
    }

//    Función para traer a todos los docentes
    public List<DocentesDTOToETS> getDocentesToETS() {
        return docenteRepository.findDocentesToSaes();
    }

     /**
      * Función para crear un nuevo Docente
      */
     @Transactional // Notación que indica que si algo falla, hace un rollback a todas las transacciones ya hechas
    public ResponseEntity<Object> newDocente(NewDocentesDTOSaes docente) {
        datos = new HashMap<>();

        // Se comprueba que nada venga vacío, en caso de que sí, se devuelve un error
        if (Stream.of(docente.getCurp(), docente.getRfc(), docente.getNombre(), docente.getApellido_p(),
                docente.getApellido_m(), docente.getSexo(), docente.getCorreo(), docente.getCargo(), docente.getTurno(),
                docente.getUser()).anyMatch(val -> val == null || val.isEmpty())) {
            datos.put("Error", true);
            datos.put("message", "Debes de llenar todos los campos");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        // Se busca la escuela a la que pertenecerá el docente para guardarlo en la bd
        Integer escuela = usuarioRepository.findEscuela(docente.getUser());

        if (escuela == null) {
            datos.put("Error", true);
            datos.put("message", "Hubo un error al asignar la escuela.");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        UnidadAcademica unidadAcademica = unidadAcademicaRepository.getReferenceById(escuela);

        if (unidadAcademica == null) {
            datos.put("Error", true);
            datos.put("message", "Hubo un error al asignar la escuela");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        // Se comprueba que la variable mandada sea correcta
        Optional<Sexo> sexo = sexoRepository.findByNombre(docente.getSexo());

        if (sexo.isEmpty()) {
            datos.put("Error", true);
            datos.put("message", "El sexo proporcionado no es válido: " +
                    docente.getSexo());

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        // Se busca un registro de la persona para ver si no ha sido registrada con anterioridad
        if(personaRepository.findPersonaByCURP(docente.getCurp()).isPresent()) {
            datos.put("Error", true);
            datos.put("message", "Ya existe un registro de esta persona");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        // Se crea a la persona
        Persona persona = new Persona();
        persona.setCURP(docente.getCurp());
        persona.setNombre(docente.getNombre());
        persona.setApellido_P(docente.getApellido_p());
        persona.setApellido_M(docente.getApellido_m());
        persona.setSexo(sexo.get());
        persona.setUnidadAcademica(unidadAcademica);

        personaRepository.save(persona);

        // Se busca el tipo de personal académico que era (en este caso siempre será docente) para guardarlo
        // posteriormente
        Optional<TipoPersonal> tipo = tipoPersonalRepository.findByCargo("Docente");

        // Se guardan los datos en una clase PersonalAcademico
        PersonalAcademico newPersonalAcademico = new PersonalAcademico();
        newPersonalAcademico.setRFC(docente.getRfc());
        newPersonalAcademico.setCURP(persona);
        newPersonalAcademico.setCorreoI(docente.getCorreo());
        newPersonalAcademico.setTipoPA(tipo.get());
        // Se guarda el registro en la BD
        personalAcademicoRepository.save(newPersonalAcademico);

        // Se buscan los datos de los cargos de los docentes para guardarlos posteriormente
        Optional<Cargo> cargo = cargoRepository.findByCargo(docente.getCargo());
        if (cargo.isEmpty()) {
            datos.put("Error", true);
            datos.put("message", "El cargo proporcionado no es válido: " +
                    docente.getSexo());

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        // Se establece la llave primaria que tendrá en la BD
        CargoDocentePK cdpk = new CargoDocentePK();
        cdpk.setIdCargo(cargo.get().getIdCargo());
        cdpk.setRFC(docente.getRfc());

        // Se guarda el cargo que tiene como docente
        CargoDocente cargoDocente = new CargoDocente();
        cargoDocente.setId(cdpk);
        cargoDocente.setRFC(newPersonalAcademico);
        cargoDocente.setIdCargo(cargo.get());

        cargoDocenteRepository.save(cargoDocente);

        // Se devuelve la respuesta de éxito al cliente
        datos.put("message", "Docente registrado con éxito.");
        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }

    // ====================== PERSONAL DE SEGURIDAD ====================
//    Función para traer a todo el personal de seguridad
    public List<PersonalSeguridadDTOSaes> getPS() {
        return personalSeguridadRepository.findPersonalSeguridad();
    }

     /**
      * Función para crear un nuevo Personal de seguridad
      */
     @Transactional // Notación que indica que si algo falla, hace un rollback a todas las transacciones ya hechas
     public ResponseEntity<Object> newPersonalSeguridad(NewPersonalSeguridadDTOSaes personalSeguridad) {
         datos = new HashMap<>();

         if (Stream.of(personalSeguridad.getCurp(), personalSeguridad.getNombre(), personalSeguridad.getApellido_P(),
                 personalSeguridad.getApellido_M(), personalSeguridad.getSexo(), personalSeguridad.getTurno(),
                 personalSeguridad.getCargoPS(), personalSeguridad.getUser()).anyMatch(val -> val == null || val.isEmpty())) {
            datos.put("Error", true);
            datos.put("message", "Debes de llenar todos los campos");

             return new ResponseEntity<>(
                     datos,
                     HttpStatus.CONFLICT
             );
         }

        Integer escuela = usuarioRepository.findEscuela(personalSeguridad.getUser());

         if (escuela == null) {
             datos.put("Error", true);
             datos.put("message", "Hubo un error al asignar la escuela.");

             return new ResponseEntity<>(
                     datos,
                     HttpStatus.CONFLICT
             );
         }

         UnidadAcademica unidadAcademica = unidadAcademicaRepository.getReferenceById(escuela);

         if (unidadAcademica == null) {
             datos.put("Error", true);
             datos.put("message", "Hubo un error al asignar la escuela");

             return new ResponseEntity<>(
                     datos,
                     HttpStatus.CONFLICT
             );
         }

         Optional<Sexo> sexo = sexoRepository.findByNombre(personalSeguridad.getSexo());

         if (sexo.isEmpty()) {
             datos.put("Error", true);
             datos.put("message", "El sexo proporcionado no es válido: " +
                     personalSeguridad.getSexo());

             return new ResponseEntity<>(
                     datos,
                     HttpStatus.CONFLICT
             );
         }

         Persona persona = new Persona();

         if(personaRepository.findPersonaByCURP(personalSeguridad.getCurp()).isPresent()) {
             datos.put("Error", true);
             datos.put("message", "Ya existe un registro de esta persona");

             return new ResponseEntity<>(
                     datos,
                     HttpStatus.CONFLICT
             );
         }
         persona.setCURP(personalSeguridad.getCurp());
         persona.setNombre(personalSeguridad.getNombre());
         persona.setApellido_P(personalSeguridad.getApellido_P());
         persona.setApellido_M(personalSeguridad.getApellido_M());
         persona.setSexo(sexo.get());
         persona.setUnidadAcademica(unidadAcademica);

         personaRepository.save(persona);

         Optional<Turno> turno = turnoRepository.findByNombre(personalSeguridad.getTurno());

         if (turno.isEmpty()) {
             datos.put("Error", true);
             datos.put("message", "El turno proporcionado no existe.");

             return new ResponseEntity<>(
                     datos,
                     HttpStatus.CONFLICT
             );
         }

         Optional<CargoPS> cargo = cargoPSRepository.findByNombre(personalSeguridad.getCargoPS());

         if (cargo.isEmpty()) {
             datos.put("Error", true);
             datos.put("message", "El cargo proporcionado no existe.");

             return new ResponseEntity<>(
                     datos,
                     HttpStatus.CONFLICT
             );
         }

         PersonalSeguridad nuevoPersonalSeguridad = new PersonalSeguridad();
         nuevoPersonalSeguridad.setRfc(personalSeguridad.getRfc());
         nuevoPersonalSeguridad.setCURP(persona);
         nuevoPersonalSeguridad.setTurno(turno.get());
         nuevoPersonalSeguridad.setCargo(cargo.get());

         personalSeguridadRepository.save(nuevoPersonalSeguridad);

         datos.put("message", "Personal de seguridad registrada con éxito.");
         return new ResponseEntity<>(
                 datos,
                 HttpStatus.CREATED
         );
     }

     // =================== PERSONA ===================
    // Esto traerá un listado de todas las personas registradas en la BD
    public List<PersonaDTO> getPersona() {
        return personaRepository.findAllAsDTO();
    }

    /**
    * Función para registrar una nueva persona
    * */
    @Transactional // Notación que indica que si algo falla, hace un rollback a todas las transacciones ya hechas
    public ResponseEntity<Object> newPersona(Persona persona) {
        datos = new HashMap<>();

        if (persona.getCURP() == null || persona.getCURP().isEmpty()) {
            datos.put("Error", true);
            datos.put("message", "El CURP no puede estar vacío.");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        // Se comprueba que no haya un registro existente con los datos recibidos
        Optional<Persona> res = personaRepository.findPersonaByCURP(persona.getCURP());

        if (res.isPresent()) {
            datos.put("Error", true);
            datos.put("message", "Esa persona ya está registrada.");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        // Se comprueba que exista un registro del sexo de la persona con los datos recibidos
        Optional<Sexo> sexOpt = sexoRepository.findByNombre(persona.getSexo().getNombre());
        if (sexOpt.isEmpty()) {
            datos.put("Error", true);
            datos.put("message", "El sexo proporcionado no es válido: " +
                    persona.getSexo().getNombre());

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        persona.setSexo(sexOpt.get());

        // Se comprueba que la Unidad Académica recibida exista
        Optional<UnidadAcademica> uaOP =
                unidadAcademicaRepository.findByNombre(persona.getUnidadAcademica().getNombre());
        if (uaOP.isEmpty()) {
            datos.put("Error", true);
            datos.put("message", "La unidad académica ingresada no existe: " +
                    persona.getUnidadAcademica().getNombre());

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        persona.setUnidadAcademica(uaOP.get());


        personaRepository.save(persona);
        datos.put("data", persona);
        datos.put("message", "La persona fue registrada exitosamente.");

        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }

    /**
    * Función para eliminar una persona
    * */
    public ResponseEntity<Object> deletePersona(String curp) {
        datos = new HashMap<>();

        boolean exists = this.personaRepository.existsById(curp);

        if (!exists) {
            datos.put("Error", true);
            datos.put("message", "Esta persona no está registrada.");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        personaRepository.deleteById(curp);
        datos.put("message", "Persona eliminada.");
        return new ResponseEntity<>(
                datos,
                HttpStatus.ACCEPTED
        );

    }
}
