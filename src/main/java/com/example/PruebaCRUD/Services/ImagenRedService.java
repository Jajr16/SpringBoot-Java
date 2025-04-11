package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.*;
import com.example.PruebaCRUD.BD.PKCompuesta.BoletaETSPK;
import com.example.PruebaCRUD.DTO.PythonResponseDTO;
import com.example.PruebaCRUD.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;


@Service
public class ImagenRedService {
    private static final String IMAGES_DIR = "./src/main/java/com/example/PruebaCRUD/IMGRED/";

    @Autowired
    private ResultadoRNRepository resultadoRNRepository;

    @Autowired
    private IngresoSalonRepository ingresoSalonRepository;

    @Autowired
    private AplicaRepository2 aplicaRepository2;

    @Autowired
    private PersonalAcademicoRepository personalAcademicoRepository;

    @Autowired
    private TipoEstadoRepository tipoEstadoRepository;

    @Autowired
    private MotivoRechazoRepository motivoRechazoRepository;

    public String guardarImagenYActualizarBD(MultipartFile file, String boleta, int idets, String razon, String tipo, String hora, String precision) {
        guardarImagenYActualizarBDCompleto(file, boleta, idets, razon, tipo, hora, precision);
        return "Creacion reporte";
    }

    private void guardarImagenYActualizarBDCompleto(MultipartFile file, String boleta, int idets, String razon, String tipo, String hora, String precision) {
        try {

            BoletaETSPK id = new BoletaETSPK(boleta, idets);

            String docenteRfcResultado = aplicaRepository2.callObtenerDocenteRfc(idets);

            if (docenteRfcResultado == null) {
                throw new DocenteNoEncontradoException("No se encontró docente RFC para idets " + idets);
            }

            int estadoNum = 0;
            switch (tipo) {
                case "Aceptado: Verificado por el profesor.":
                    estadoNum = 6;
                    break;
                case "Aceptado: Verificado con el código QR de la credencial.":
                    estadoNum = 5;
                    break;
                case "Aceptado: Verificado con el reconocimiento facial.":
                    estadoNum = 4;
                    break;
                case "Rechazado: Verificado por el profesor.":
                    estadoNum = 3;
                    break;
                case "Rechazado: Verificado con el código QR de la credencial.":
                    estadoNum = 2;
                    break;
                case "Rechazado: Verificado con el reconocimiento facial.":
                    estadoNum = 1;
                    break;
                default:

                    break;
            }

            // Crear o actualizar IngresoSalon
            IngresoSalon ingresoSalon = ingresoSalonRepository.findById(id).orElse(null);
            if (ingresoSalon == null) {
                ingresoSalon = new IngresoSalon();
                ingresoSalon.setId(id);

                LocalDate localDate = LocalDate.now();
                Date utilDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); // Convertir a java.sql.Date
                ingresoSalon.setFecha(sqlDate);

                // Convertir String hora a Time
                LocalTime localTime = LocalTime.parse(hora);
                Time time = Time.valueOf(localTime);
                ingresoSalon.setHora(time);

                // Obtener PersonalAcademico
                PersonalAcademico personalAcademico = personalAcademicoRepository.findById(docenteRfcResultado).orElse(null);
                ingresoSalon.setDocente(personalAcademico);

                // Obtener TipoEstado
                TipoEstado tipoEstado = tipoEstadoRepository.findById(estadoNum).orElse(null);
                ingresoSalon.setTipo(tipoEstado);
            } else {
                // Actualizar IngresoSalon existente
                LocalTime localTime = LocalTime.parse(hora);
                Time time = Time.valueOf(localTime);
                ingresoSalon.setHora(time);

                // Obtener TipoEstado
                TipoEstado tipoEstado = tipoEstadoRepository.findById(estadoNum).orElse(null);
                ingresoSalon.setTipo(tipoEstado);
            }

            // Guardar IngresoSalon
            ingresoSalonRepository.save(ingresoSalon);

            //Crear directorio si no existe
            Path dirPath = Paths.get(IMAGES_DIR, "ETS_" + idets);
            Files.createDirectories(dirPath);

            //Guardar imagen en el directorio (si no es nula)
            String filePathStr = null;
            if (file != null) {
                String fileName = boleta + "_ETS_" + idets + ".jpg";
                Path filePath = dirPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                filePathStr = filePath.toString();
            }

            //Actualizar o insertar en la base de datos

            ingresoSalon = ingresoSalonRepository.findById(id).orElse(null);

            if (ingresoSalon == null) {
                throw new IngresoSalonNoEncontradoException("No se encontró IngresoSalon para boleta " + boleta + " y idets " + idets);
            }

            // No crear ResultadoRN si file o precision son nulos
            if (file != null && precision != null && !precision.isEmpty()) {
                ResultadoRN resultadoRN = resultadoRNRepository.findById(id).orElse(null);

                if (resultadoRN == null) {
                    // Manejar la precisión nula o vacía
                    Float precisionFloat = Float.parseFloat(precision);
                    resultadoRN = new ResultadoRN(id, filePathStr, precisionFloat, ingresoSalon);
                } else {
                    resultadoRN.setImagenAlumno(filePathStr);
                    resultadoRN.setPrecision(Float.parseFloat(precision));
                }

                resultadoRNRepository.save(resultadoRN); // Guardar o actualizar ResultadoRN
            }

            if (razon != null){
                MotivoRechazo motivoRechazo = new MotivoRechazo(id, razon);
                motivoRechazo.setIngresoSalon(ingresoSalon); // Establecer ingresoSalon
                motivoRechazoRepository.save(motivoRechazo);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class DocenteNoEncontradoException extends RuntimeException {
        public DocenteNoEncontradoException(String message) {
            super(message);
        }
    }

    public static class IngresoSalonNoEncontradoException extends RuntimeException {
        public IngresoSalonNoEncontradoException(String message) {
            super(message);
        }
    }
}
