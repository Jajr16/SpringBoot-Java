package com.example.PruebaCRUD.Services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.PruebaCRUD.BD.*;
import com.example.PruebaCRUD.DTO.*;
import com.example.PruebaCRUD.DTO.Saes.AlumnoDTOSaes;
import com.example.PruebaCRUD.DTO.Saes.ListaAlumnosInscritosProjectionSaes;
import com.example.PruebaCRUD.DTO.Saes.NuevoAlumnoDTOSaes;
import com.example.PruebaCRUD.DTO.Saes.NuevoVideoAlumnoDTOSaes;
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
public class AlumnoServicio {

    private final AlumnoRepositorio alumnoRepositorio;
    private final InscripcionETSRepositorio inscripcionETSRepositorio;
    private final periodoETSRepositorio periodoETSRepositorio;
    private final UnidadAcademicaRepositorio unidadAcademicaRepositorio;
    private final ProgramaAcademicoRepositorio programaAcademicoRepositorio;
    private final PersonaServicio personaServicio;

    HashMap<String, Object> datos = new HashMap<>();
    private final Cloudinary cloudinary;

    @Autowired
    public AlumnoServicio(AlumnoRepositorio alumnoRepository, InscripcionETSRepositorio inscripcionETSRepository,
                          periodoETSRepositorio periodRepo, UnidadAcademicaRepositorio unidadAcademicaRepository,
                          ProgramaAcademicoRepositorio programaAcademicoRepositorio, Cloudinary cloudinary,
                          PersonaServicio personaServicio) {
        this.alumnoRepositorio = alumnoRepository;
        this.inscripcionETSRepositorio = inscripcionETSRepository;
        this.periodoETSRepositorio = periodRepo;
        this.unidadAcademicaRepositorio = unidadAcademicaRepository;
        this.programaAcademicoRepositorio = programaAcademicoRepositorio;
        this.cloudinary = cloudinary;
        this.personaServicio = personaServicio;
    }

    public List<AlumnoDTO> encontrarAlumnosInscritosETS() {
        String periodo = PeriodoETSServicio.crearPeriodo();
        List<Object[]> fechasPeriodos = periodoETSRepositorio.findFechasByPeriodo(periodo);

        if (fechasPeriodos == null || fechasPeriodos.isEmpty()) {
            return new ArrayList<>();
        }

        LocalDate hoy = LocalDate.now();

        // Buscar si hoy está dentro de alguno de los periodos
        for (Object[] fechas : fechasPeriodos) {
            LocalDate inicio = LocalDate.parse(fechas[0].toString());
            LocalDate fin = LocalDate.parse(fechas[1].toString());

            if ((hoy.isEqual(inicio) || hoy.isAfter(inicio)) && (hoy.isBefore(fin) || hoy.isEqual(fin))) {
                Date fechaActual = Date.valueOf(hoy);
                Date fechaInicio = Date.valueOf(inicio);
                Date fechaFin = Date.valueOf(fin);
                System.out.println("LAS FECHAS SON: " + fechaActual);
                System.out.println("LAS FECHAS SON: " + fechaInicio);
                System.out.println("LAS FECHAS SON: " + fechaFin);
                System.out.println("EL PERIODO ES: " + periodo);

                List<AlumnoDTO> resultados = inscripcionETSRepositorio.findAlumnosInscritosETS(
                        fechaActual, fechaInicio, fechaFin, periodo);

                System.out.println("LOS ALUMNOS QUE PRESENTAN ETS HOY SON: " + resultados);
                return resultados;
            }
        }

        // Si no estamos en ningún periodo activo
        System.out.println("HOY NO HAY ETS PROGRAMADOS.");
        return new ArrayList<>();
    }


    public List<ListaAlumnosInscritosProjectionSaes> obtenerAlumnos(String usuario) {
        return this.alumnoRepositorio.findAlumnosSaes(usuario);
    }

    public ComparacionDTO compararDatos(String boleta, DatosWebDTO datosWeb) {
        System.out.println("\n=== INICIO DE COMPARACIÓN ===");

        // 1. Obtener datos del sistema
        List<CredencialDTO> ListaDatosSistema = alumnoRepositorio.findbyBoleta(boleta);
        if (ListaDatosSistema.isEmpty()) {
            System.out.println("ERROR: Boleta no encontrada en el sistema");
            return new ComparacionDTO(false, List.of("Boleta no encontrada en el sistema"));
        }

        CredencialDTO datosSistema = ListaDatosSistema.get(0);
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

    public List<CredencialDTO> encontrarCredencialPorBoleta(String boleta) {
        return alumnoRepositorio.findbyBoleta(boleta);
    }

    public List<AlumnoDTOSaes> obtenerAlumnos() {
        return alumnoRepositorio.findAllAsDTO();
    }

    //Función para crear a un nuevo alumno apartir de Excel
    @Transactional
    public ResponseEntity<Object> nuevoVideoAlumno(NuevoVideoAlumnoDTOSaes nuevoVideoAlumnoDTOSaes) throws IOException {

        HashMap<String, Object> datos = new HashMap<>();

        if (Stream.of(nuevoVideoAlumnoDTOSaes.getBoleta(), nuevoVideoAlumnoDTOSaes.getCredencial())
                .anyMatch(val -> val == null || val.isEmpty())) {
            throw new RuntimeException("Debes de llenar todos los campos.");
        }

        Optional<UnidadAcademica> uaAlumno = this.unidadAcademicaRepositorio.findByNombre("ESCOM");

        try (FileInputStream archivo = new FileInputStream("./src/main/java/com/example/PruebaCRUD/grupos_ESCOM.xlsx");
             Workbook libro = new XSSFWorkbook(archivo)) {

            DataFormatter formato = new DataFormatter();

            for (Sheet hoja : libro) {
                for (Row fila : hoja) {
                    String boletaExcel = formato.formatCellValue(fila.getCell(0));

                    if (boletaExcel.equals(nuevoVideoAlumnoDTOSaes.getBoleta())) {

                        String nombre = fila.getCell(1).toString();
                        String apellido_p = fila.getCell(2).toString();
                        String apellido_m = fila.getCell(3).toString();
                        String correo = fila.getCell(4).toString();
                        String sexo = fila.getCell(5).toString();
                        String carrera = fila.getCell(6).toString();

                        if (this.alumnoRepositorio.findByBoleta(nuevoVideoAlumnoDTOSaes.getBoleta()).isPresent()) {
                            throw new AlumnoExistenteException("Ese Alumno ya ha sido registrado con anterioridad.");
                        }

                        Persona persona;
                        persona = PersonaServicio.Persona(nuevoVideoAlumnoDTOSaes.getCurp(), nombre, apellido_p, apellido_m,
                                sexo, uaAlumno.get());

                        ResponseEntity<Object> respuestaPersona = personaServicio.nuevaPersona(persona);
                        if (respuestaPersona.getStatusCode() != HttpStatus.CREATED) {
                            return respuestaPersona;
                        }

                        Optional<ProgramaAcademico> pa = this.programaAcademicoRepositorio.findBy(1, carrera);
                        if (pa.isEmpty()) throw new EntidadNoEncontradaException("Esa carrera no existe: " + carrera);

                        Alumno nalumno = new Alumno();
                        nalumno.setBoleta(nuevoVideoAlumnoDTOSaes.getBoleta());
                        nalumno.setCorreoI(correo);
                        nalumno.setImagenCredencial(nuevoVideoAlumnoDTOSaes.getCredencial());
                        nalumno.setCURP(persona);
                        nalumno.setIdPA(pa.get());
                        alumnoRepositorio.save(nalumno);

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
    public ResponseEntity<Object> nuevoAlumno(NuevoAlumnoDTOSaes nuevoAlumnoDTOSaes, MultipartFile video,
                                              MultipartFile credencial) throws IOException, ExecutionException,
            InterruptedException {

        System.out.println("===== CREANDO ALUMNO =====");
        System.out.println("===== LOS DATOS SON: " + nuevoAlumnoDTOSaes + " =====");

        datos = new HashMap<>();

        if (Stream.of(nuevoAlumnoDTOSaes.getCurp(), nuevoAlumnoDTOSaes.getBoleta(), nuevoAlumnoDTOSaes.getNombre(),
                nuevoAlumnoDTOSaes.getApellido_p(), nuevoAlumnoDTOSaes.getApellido_m(), nuevoAlumnoDTOSaes.getSexo(),
                nuevoAlumnoDTOSaes.getCorreo(), nuevoAlumnoDTOSaes.getCarrera()).anyMatch(val -> val == null || val.isEmpty())
                || video == null || video.isEmpty() || credencial == null || credencial.isEmpty()) {
            datos.put("Error", true);
            datos.put("message", "Debes de llenar todos los campos y subir una imagen.");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }

        System.out.println("===== GUARDANDO VIDEO TEMPORALMENTE =====");
        // Guardar el video temporalmente
        File videoTemporal = File.createTempFile("video_temp", ".mp4");
        video.transferTo(videoTemporal);

        System.out.println("===== GUARDANDO CARPETA DE FRAMES TEMPORALMENTE =====");
        // Crear carpeta temporal para los frames
        File direccionFotogramas = Files.createTempDirectory("frames_" + nuevoAlumnoDTOSaes.getBoleta()).toFile();
        DivisionFrames.extractFrames(videoTemporal.getAbsolutePath(), direccionFotogramas.getAbsolutePath());

        System.out.println("===== EMPEZANDO TAREAS ASÍNCRONAS =====");
        // Usar CompletableFuture para tareas asíncronas
        CompletableFuture<List<String>> fotogramasSubidos = CompletableFuture.supplyAsync(() -> {
            List<String> urlFotogramas = new ArrayList<>();
            File[] frames = direccionFotogramas.listFiles((dir, name) -> name.endsWith(".png"));
            if (frames != null) {
                for (File frame : frames) {
                    try {
                        Map resultado = cloudinary.uploader().upload(frame, ObjectUtils.asMap(
                                "folder", "frames/" + nuevoAlumnoDTOSaes.getBoleta()
                        ));
                        urlFotogramas.add(resultado.get("secure_url").toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return urlFotogramas;
        });

        // Subir la credencial en paralelo
        CompletableFuture<String> cargaCredencial = CompletableFuture.supplyAsync(() -> {
            try {
                Map resultadoCarga = cloudinary.uploader().upload(
                        credencial.getBytes(),
                        ObjectUtils.asMap(
                                "folder", "fotosCredencial",
                                "public_id", nuevoAlumnoDTOSaes.getBoleta()
                        )
                );
                return resultadoCarga.get("secure_url").toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });

        // Esperar a que ambas tareas asíncronas terminen
        CompletableFuture.allOf(fotogramasSubidos, cargaCredencial).join();

        // Obtener resultados de las tareas asíncronas
        String credencialUrl = cargaCredencial.get();

        System.out.println("===== EMPEZANDO CON EL GUARDADO DEL ALUMNO =====");
        // Validación de existencia de datos
        Optional<UnidadAcademica> uaAlumno = this.unidadAcademicaRepositorio.findById(nuevoAlumnoDTOSaes.getEscuela());
        if (uaAlumno.isEmpty()) {
            datos.put("Error", true);
            datos.put("message", "La escuela proporcionada no existe");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }

        Persona persona;
        persona = PersonaServicio.Persona(nuevoAlumnoDTOSaes.getCurp(), nuevoAlumnoDTOSaes.getNombre(), nuevoAlumnoDTOSaes.getApellido_p(),
                nuevoAlumnoDTOSaes.getApellido_m(), nuevoAlumnoDTOSaes.getSexo(), uaAlumno.get());

        ResponseEntity<Object> responsePersona = personaServicio.nuevaPersona(persona);
        if (responsePersona.getStatusCode() != HttpStatus.CREATED) {
            return responsePersona;
        }

        Optional<Alumno> existsA = this.alumnoRepositorio.findByBoleta(nuevoAlumnoDTOSaes.getBoleta());
        if (existsA.isPresent()) {
            datos.put("Error", true);
            datos.put("message", "Ese alumno ya ha sido registrado con anterioridad.");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }

        // Crear el alumno
        Optional<ProgramaAcademico> pa = this.programaAcademicoRepositorio.findBy(nuevoAlumnoDTOSaes.getEscuela(),
                nuevoAlumnoDTOSaes.getCarrera());
        if (pa.isEmpty()) {
            datos.put("Error", true);
            datos.put("message", "Esa carrera no existe.");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }

        Alumno nalumno = new Alumno();
        nalumno.setBoleta(nuevoAlumnoDTOSaes.getBoleta());
        nalumno.setCURP(persona);
        nalumno.setCorreoI(nuevoAlumnoDTOSaes.getCorreo());
        nalumno.setIdPA(pa.get());
        nalumno.setImagenCredencial(credencialUrl);

        alumnoRepositorio.save(nalumno);

        // Devolver respuesta
        datos.put("message", "Alumno registrado con éxito.");
        datos.put("image_path", credencialUrl);
        return new ResponseEntity<>(datos, HttpStatus.CREATED);
    }

    // Método para obtener los datos de un estudiante específico por boleta
    public EstudianteEspecificoDTO obtenerEstudiantePorBoleta(String boleta) {
        List<Object[]> results = alumnoRepositorio.callAlumnoEspecificoData(boleta);

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
        String rutaImagen = alumnoRepositorio.findRutaImagenByBoleta(boleta);

        if (rutaImagen == null || rutaImagen.isEmpty()) {
            throw new RuntimeException("Ruta de imagen no encontrada para la boleta: " + boleta);
        }

        return rutaImagen;
    }

    public List<ReporteSqlDTO> obtenerDatosReporte(Integer idets, String boleta) {
        List<Object[]> resultados = alumnoRepositorio.obtenerDatosReporte(idets, boleta);
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
        return alumnoRepositorio.obtenerImagenAlumno(idets, boleta);
    }
}
