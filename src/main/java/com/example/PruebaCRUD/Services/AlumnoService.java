package com.example.PruebaCRUD.Services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.PruebaCRUD.BD.*;
import com.example.PruebaCRUD.DTO.*;
import com.example.PruebaCRUD.DTO.Saes.AlumnoDTOSaes;
import com.example.PruebaCRUD.DTO.Saes.ListInsAlumnProjectionSaes;
import com.example.PruebaCRUD.DTO.Saes.NewAlumnoDTOSaes;
import com.example.PruebaCRUD.DTO.Saes.NewVideoAlumnoDTOSaes;
import com.example.PruebaCRUD.Exceptions.AlumnoExistenteException;
import com.example.PruebaCRUD.Exceptions.EntidadNoEncontradaException;
import com.example.PruebaCRUD.Frames.DivisionFrames;
import com.example.PruebaCRUD.Repositories.*;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
import java.nio.file.Files;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

@Service
public class AlumnoService {

    private final AlumnoRepository alumnoRepository;
    private final InscripcionETSRepository inscripcionETSRepository;
    private final periodoETSRepository periodRepo;
    private final UnidadAcademicaRepository unidadAcademicaRepository;
    private final ProgramaAcademicoRepository programaAcademicoRepository;
    private final PersonaService personaService;

    HashMap<String, Object> datos = new HashMap<>();
    private final Cloudinary cloudinary;

    @Autowired
    public AlumnoService(AlumnoRepository alumnoRepository, InscripcionETSRepository inscripcionETSRepository,
                         periodoETSRepository periodRepo, UnidadAcademicaRepository unidadAcademicaRepository,
                         ProgramaAcademicoRepository programaAcademicoRepository, Cloudinary cloudinary,
                         PersonaService personaService) {
        this.alumnoRepository = alumnoRepository;
        this.inscripcionETSRepository = inscripcionETSRepository;
        this.periodRepo = periodRepo;
        this.unidadAcademicaRepository = unidadAcademicaRepository;
        this.programaAcademicoRepository = programaAcademicoRepository;
        this.cloudinary = cloudinary;
        this.personaService = personaService;
    }

    public List<AlumnoDTO>findAlumnosInscritosETS() {

        String periodo = PeriodoETSService.crearPeriodo();

        List<String> fechasPeriodos = periodRepo.findFechasByPeriodo(periodo);

        if (fechasPeriodos == null) { // Si la fecha obtenida es null
            // Usamos ArrayList como implementación de List
            return new ArrayList<>();  // Retornamos la lista que contiene el error
        }

        String fechasString = fechasPeriodos.get(0);

        String[] fechasSeparadas = fechasString.split(",");

        System.out.println("LAS FECHAS DEL PERIODO " + periodo + " SON " + fechasPeriodos);

        // Fecha actual
        Date fechaActual = Date.valueOf(LocalDate.now());

        Date fechaInicio = Date.valueOf(LocalDate.parse(fechasSeparadas[0]));
        Date fechaFin = Date.valueOf(LocalDate.parse(fechasSeparadas[1]));

        List<AlumnoDTO> results = inscripcionETSRepository.findAlumnosInscritosETS(fechaActual, fechaInicio, fechaFin,
                periodo);

        System.out.println("LOS ALUMNOS QUE PRESENTAN ETS HOY SON: " + results);
        return results;
    }

    public List<ListInsAlumnProjectionSaes> getAlumnos(String usuario) {
        return this.alumnoRepository.findAlumnosSaes(usuario);
    }

    public ComparacionDTO compararDatos(String boleta, DatosWebDTO datosWeb) {
        System.out.println("\n=== INICIO DE COMPARACIÓN ===");

        // 1. Obtener datos del sistema
        List<CredencialDTO> datosSistemaList = alumnoRepository.findbyBoleta(boleta);
        if (datosSistemaList.isEmpty()) {
            System.out.println("ERROR: Boleta no encontrada en el sistema");
            return new ComparacionDTO(false, List.of("Boleta no encontrada en el sistema"));
        }

        CredencialDTO datosSistema = datosSistemaList.get(0);
        System.out.println("Datos del sistema: " + datosSistema.toString());
        System.out.println("Datos del html: " + datosWeb.toString());

        // 2. Comparar campos
        List<String> errores = new ArrayList<>();

        // Boleta
        if (!datosSistema.getBoleta().equals(datosWeb.getBoleta())) {
            System.out.printf("ERROR: Boleta | Sistema: %s vs Frontend: %s%n",
                    datosSistema.getBoleta(), datosWeb.getBoleta());
            errores.add("Boleta no coincide");
        }

        // CURP (comparación sensible a mayúsculas)
        if (!datosSistema.getCurp().equals(datosWeb.getCurp())) {  // Cambiado a getCurp()
            System.out.printf("ERROR: CURP | Sistema: %s vs Frontend: %s%n",
                    datosSistema.getCurp(), datosWeb.getCurp());  // Cambiado a getCurp()
            errores.add("CURP no coincide");
        }

        // Nombre completo (normalizado a mayúsculas)
        String nombreCompletoSistema = (datosSistema.getNombre() + " " +
                datosSistema.getApellidoP() + " " +
                datosSistema.getApellidoM()).toUpperCase().trim();
        String nombreFrontend = datosWeb.getNombre().toUpperCase().trim();

        if (!nombreCompletoSistema.equals(nombreFrontend)) {
            System.out.printf("ERROR: Nombre | Sistema: %s vs Frontend: %s%n",
                    nombreCompletoSistema, nombreFrontend);
            errores.add("Nombre no coincide");
        }

        // Carrera (comparación case insensitive)
        if (!datosSistema.getCarrera().equalsIgnoreCase(datosWeb.getCarrera())) {
            System.out.printf("ERROR: Carrera | Sistema: %s vs Frontend: %s%n",
                    datosSistema.getCarrera(), datosWeb.getCarrera());
            errores.add("Carrera no coincide");
        }

        // Unidad Académica vs Escuela
        if (!datosSistema.getUnidadAcademica().equalsIgnoreCase(datosWeb.getEscuela())) {
            System.out.printf("ERROR: Unidad Académica | Sistema: %s vs Frontend (Escuela): %s%n",
                    datosSistema.getUnidadAcademica(), datosWeb.getEscuela());
            errores.add("Unidad académica no coincide");
        }

        System.out.println("=== RESULTADO: " + (errores.isEmpty() ? "COINCIDEN" : "HAY ERRORES") + " ===");
        return new ComparacionDTO(errores.isEmpty(), errores);
    }

    public List<CredencialDTO> findCredencialPorBoleta(String boleta) {
        return alumnoRepository.findbyBoleta(boleta);
    }

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

        Optional<UnidadAcademica> uaAlumno = this.unidadAcademicaRepository.findByNombre("ESCOM");

        try (FileInputStream archivo = new FileInputStream("./src/main/java/com/example/PruebaCRUD/grupos_ESCOM.xlsx");
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

                        if (this.alumnoRepository.findByBoleta(newVideoAlumnoDTOSaes.getBoleta()).isPresent()) {
                            throw new AlumnoExistenteException("Ese Alumno ya ha sido registrado con anterioridad.");
                        }

                        Persona persona;
                        persona = PersonaService.Persona(newVideoAlumnoDTOSaes.getCurp(), nombre, apellido_p, apellido_m,
                                sexo, uaAlumno.get());

                        ResponseEntity<Object> responsePersona = personaService.newPersona(persona);
                        if (responsePersona.getStatusCode() != HttpStatus.CREATED) {
                            return responsePersona;
                        }

                        Optional<ProgramaAcademico> pa = this.programaAcademicoRepository.findBy(1, carrera);
                        if (pa.isEmpty()) throw new EntidadNoEncontradaException("Esa carrera no existe: " + carrera);

                        Alumno nalumno = new Alumno();
                        nalumno.setBoleta(newVideoAlumnoDTOSaes.getBoleta());
                        nalumno.setCorreoI(correo);
                        nalumno.setImagenCredencial(newVideoAlumnoDTOSaes.getCredencial());
                        nalumno.setCURP(persona);
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
    @Transactional
    public ResponseEntity<Object> newAlumno(NewAlumnoDTOSaes newAlumnoDTOSaes, MultipartFile video,
                                            MultipartFile credencial) throws IOException, ExecutionException,
            InterruptedException {

        System.out.println("===== CREANDO ALUMNO =====");
        System.out.println("===== LOS DATOS SON: " + newAlumnoDTOSaes + " =====");

        datos = new HashMap<>();

        if (Stream.of(newAlumnoDTOSaes.getCurp(), newAlumnoDTOSaes.getBoleta(), newAlumnoDTOSaes.getNombre(),
                newAlumnoDTOSaes.getApellido_p(), newAlumnoDTOSaes.getApellido_m(), newAlumnoDTOSaes.getSexo(),
                newAlumnoDTOSaes.getCorreo(), newAlumnoDTOSaes.getCarrera()).anyMatch(val -> val == null || val.isEmpty())
                || video == null || video.isEmpty() || credencial == null || credencial.isEmpty()) {
            datos.put("Error", true);
            datos.put("message", "Debes de llenar todos los campos y subir una imagen.");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }

        System.out.println("===== GUARDANDO VIDEO TEMPORALMENTE =====");
        // Guardar el video temporalmente
        File tempVideoFile = File.createTempFile("video_temp", ".mp4");
        video.transferTo(tempVideoFile);

        System.out.println("===== GUARDANDO CARPETA DE FRAMES TEMPORALMENTE =====");
        // Crear carpeta temporal para los frames
        File framesDir = Files.createTempDirectory("frames_" + newAlumnoDTOSaes.getBoleta()).toFile();
        DivisionFrames.extractFrames(tempVideoFile.getAbsolutePath(), framesDir.getAbsolutePath());

        System.out.println("===== EMPEZANDO TAREAS ASÍNCRONAS =====");
        // Usar CompletableFuture para tareas asíncronas
        CompletableFuture<List<String>> framesUploadFuture = CompletableFuture.supplyAsync(() -> {
            List<String> frameUrls = new ArrayList<>();
            File[] frames = framesDir.listFiles((dir, name) -> name.endsWith(".png"));
            if (frames != null) {
                for (File frame : frames) {
                    try {
                        Map uploadResult = cloudinary.uploader().upload(frame, ObjectUtils.asMap(
                                "folder", "frames/" + newAlumnoDTOSaes.getBoleta()
                        ));
                        frameUrls.add(uploadResult.get("secure_url").toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return frameUrls;
        });

        // Subir la credencial en paralelo
        CompletableFuture<String> credencialUploadFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Map uploadResult = cloudinary.uploader().upload(
                        credencial.getBytes(),
                        ObjectUtils.asMap(
                                "folder", "fotosCredencial",
                                "public_id", newAlumnoDTOSaes.getBoleta()
                        )
                );
                return uploadResult.get("secure_url").toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });

        // Esperar a que ambas tareas asíncronas terminen
        CompletableFuture.allOf(framesUploadFuture, credencialUploadFuture).join();

        // Obtener resultados de las tareas asíncronas
        String credencialUrl = credencialUploadFuture.get();

        System.out.println("===== EMPEZANDO CON EL GUARDADO DEL ALUMNO =====");
        // Validación de existencia de datos
        Optional<UnidadAcademica> uaAlumno = this.unidadAcademicaRepository.findById(newAlumnoDTOSaes.getEscuela());
        if (uaAlumno.isEmpty()) {
            datos.put("Error", true);
            datos.put("message", "La escuela proporcionada no existe");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }

        Persona persona;
        persona = PersonaService.Persona(newAlumnoDTOSaes.getCurp(), newAlumnoDTOSaes.getNombre(), newAlumnoDTOSaes.getApellido_p(),
                newAlumnoDTOSaes.getApellido_m(), newAlumnoDTOSaes.getSexo(), uaAlumno.get());

        ResponseEntity<Object> responsePersona = personaService.newPersona(persona);
        if (responsePersona.getStatusCode() != HttpStatus.CREATED) {
            return responsePersona;
        }

        Optional<Alumno> existsA = this.alumnoRepository.findByBoleta(newAlumnoDTOSaes.getBoleta());
        if (existsA.isPresent()) {
            datos.put("Error", true);
            datos.put("message", "Ese alumno ya ha sido registrado con anterioridad.");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }

        // Crear el alumno
        Optional<ProgramaAcademico> pa = this.programaAcademicoRepository.findBy(newAlumnoDTOSaes.getEscuela(),
                newAlumnoDTOSaes.getCarrera());
        if (pa.isEmpty()) {
            datos.put("Error", true);
            datos.put("message", "Esa carrera no existe.");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }

        Alumno nalumno = new Alumno();
        nalumno.setBoleta(newAlumnoDTOSaes.getBoleta());
        nalumno.setCURP(persona);
        nalumno.setCorreoI(newAlumnoDTOSaes.getCorreo());
        nalumno.setIdPA(pa.get());
        nalumno.setImagenCredencial(credencialUrl);

        alumnoRepository.save(nalumno);

        // Devolver respuesta
        datos.put("message", "Alumno registrado con éxito.");
        datos.put("image_path", credencialUrl);
        return new ResponseEntity<>(datos, HttpStatus.CREATED);
    }

    // Método para obtener los datos de un estudiante específico por boleta
    public EstudianteEspecificoDTO obtenerEstudiantePorBoleta(String boleta) {
        List<Object[]> results = alumnoRepository.callAlumnoEspecificoData(boleta);

        // Verificar si hay resultados
        if (!results.isEmpty()) {
            Object[] result = results.get(0);

            // Obtener los valores de las columnas que devuelve la consulta nativa
            String nombre = (String) result[4];
            String apellidoP = (String) result[2];
            String apellidoM = (String) result[3];
            String boletaResultado = (String) result[0];
            String curp = (String) result[1];
            String unidadAcademica = (String) result[5];

            // Crear y retornar el DTO
            return new EstudianteEspecificoDTO(nombre, apellidoP, apellidoM, boletaResultado, curp, unidadAcademica);
        }

        return null;
    }

    public String obtenerUrlPorBoleta(String boleta) {
        String rutaImagen = alumnoRepository.findRutaImagenByBoleta(boleta);

        if (rutaImagen == null || rutaImagen.isEmpty()) {
            throw new RuntimeException("Ruta de imagen no encontrada para la boleta: " + boleta);
        }

        return rutaImagen;
    }

    public List<ReporteSqlDTO> obtenerDatosReporte(Integer idets, String boleta) {
        List<Object[]> resultados = alumnoRepository.obtenerDatosReporte(idets, boleta);
        List<ReporteSqlDTO> reportes = new ArrayList<>();

        for (Object[] resultado : resultados) {
            ReporteSqlDTO reporte = new ReporteSqlDTO();
            reporte.setCurp((String) resultado[0]);
            reporte.setNombre((String) resultado[1]);
            reporte.setApellidoP((String) resultado[2]);
            reporte.setApellidoM((String) resultado[3]);
            reporte.setEscuela((String) resultado[4]);
            reporte.setCarrera((String) resultado[5]);
            reporte.setPeriodo((String) resultado[6]);
            reporte.setTipo((String) resultado[7]);
            reporte.setTurno((String) resultado[8]);
            reporte.setMateria((String) resultado[9]);
            reporte.setFechaIngreso((Date) resultado[10]);
            reporte.setHoraIngreso((Time) resultado[11]);
            reporte.setNombreDocente((String) resultado[12]);
            reporte.setTipoEstado((String) resultado[13]);
            reporte.setPresicion(((Number) resultado[14]).floatValue());
            reporte.setMotivo((String) resultado[15]);
            reportes.add(reporte);
        }

        return reportes;
    }

    public String obtenerImagenAlumno(Integer idets, String boleta) {
        return alumnoRepository.obtenerImagenAlumno(idets, boleta);
    }
}
