package com.example.PruebaCRUD.Services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.PruebaCRUD.BD.*;
import com.example.PruebaCRUD.BD.PKCompuesta.BoletaETSPK;
import com.example.PruebaCRUD.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


@Service
public class ImagenRedServicio {
    private static final String IMAGES_DIR = "./src/main/java/com/example/PruebaCRUD/IMGRED/";
    private static final String CLOUDINARY_FOLDER = "IMGRED";

    @Autowired
    private ResultadoRNRepositorio resultadoRNRepositorio;

    @Autowired
    private IngresoSalonRepositorio ingresoSalonRepositorio;

    @Autowired
    private AplicaRepositorio aplicaRepositorio;

    @Autowired
    private PersonalAcademicoRepositorio personalAcademicoRepositorio;

    @Autowired
    private TipoEstadoRepositorio tipoEstadoRepositorio;

    @Autowired
    private MotivoRechazoRepositorio motivoRechazoRepositorio;

    @Autowired
    private Cloudinary cloudinary;

    public String guardarImagenYActualizarBD(MultipartFile file, String boleta, int idets, String razon, String tipo, String hora, String precision) {
        guardarImagenYActualizarBDCompleto(file, boleta, idets, razon, tipo, hora, precision);
        return "Creacion reporte";
    }

    private void guardarImagenYActualizarBDCompleto(MultipartFile file, String boleta, int idets, String razon, String tipo, String hora, String precision) {
        try {
            BoletaETSPK id = new BoletaETSPK(boleta, idets);

            String docenteRfcResultado = aplicaRepositorio.callObtenerDocenteRfc(idets);

            if (docenteRfcResultado == null) {
                throw new DocenteNoEncontradoException("No se encontró docente RFC para idets " + idets);
            }

            int estadoNum = obtenerEstadoNumero(tipo);

            IngresoSalon ingresoSalon = ingresoSalonRepositorio.findById(id).orElse(null);
            if (ingresoSalon == null) {
                ingresoSalon = new IngresoSalon();
                ingresoSalon.setId(id);
                LocalDate localDate = LocalDate.now();
                Date utilDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                ingresoSalon.setFecha(sqlDate);
                LocalTime localTime = LocalTime.parse(hora);
                Time time = Time.valueOf(localTime);
                ingresoSalon.setHora(time);
                PersonalAcademico personalAcademico = personalAcademicoRepositorio.findById(docenteRfcResultado).orElse(null);
                ingresoSalon.setDocente(personalAcademico);
                TipoEstado tipoEstado = tipoEstadoRepositorio.findById(estadoNum).orElse(null);
                ingresoSalon.setTipo(tipoEstado);
            } else {
                LocalTime localTime = LocalTime.parse(hora);
                Time time = Time.valueOf(localTime);
                ingresoSalon.setHora(time);
                TipoEstado tipoEstado = tipoEstadoRepositorio.findById(estadoNum).orElse(null);
                ingresoSalon.setTipo(tipoEstado);
            }
            ingresoSalonRepositorio.save(ingresoSalon);

            final IngresoSalon finalIngresoSalon = ingresoSalon;

            CompletableFuture<String> uploadFuture = CompletableFuture.supplyAsync(() -> {
                if (file == null || file.isEmpty()) {
                    System.out.println("Error: El archivo está nulo o vacío.");
                    return null;
                }
                try {
                    System.out.println("Intentando cargar imagen a Cloudinary para boleta: " + boleta + ", idets: " + idets);
                    Map uploadResult = cloudinary.uploader().upload(
                            file.getBytes(),
                            ObjectUtils.asMap(
                                    "folder", CLOUDINARY_FOLDER + "/ETS_" + idets,
                                    "public_id", boleta + "_ETS_" + idets
                            )
                    );
                    System.out.println("Resultado de la carga: " + uploadResult);
                    return uploadResult.get("secure_url").toString();
                } catch (IOException e) {
                    System.err.println("Error de IO al cargar a Cloudinary: " + e.getMessage());
                    e.printStackTrace();
                    return null;
                } catch (Exception e) {
                    System.err.println("Error general al cargar a Cloudinary: " + e.getMessage());
                    e.printStackTrace();
                    return null;
                }
            });


            String imageUrl = null;
            try {
                imageUrl = uploadFuture.get();
                System.out.println("URL de la imagen cargada (síncrono): " + imageUrl);
            } catch (InterruptedException | java.util.concurrent.ExecutionException e) {
                System.err.println("Error al esperar la carga de Cloudinary: " + e.getMessage());
                e.printStackTrace();

            }

            if (imageUrl != null && precision != null && !precision.isEmpty()) {
                System.out.println("Intentando guardar/actualizar ResultadoRN para boleta: " + boleta + ", idets: " + idets);
                ResultadoRN resultadoRN = resultadoRNRepositorio.findById(id).orElse(null);
                Float precisionFloat = Float.parseFloat(precision);
                if (resultadoRN == null) {
                    resultadoRN = new ResultadoRN(id, imageUrl, precisionFloat, finalIngresoSalon);
                    System.out.println("ResultadoRN creado: " + resultadoRN);
                } else {
                    resultadoRN.setImagenAlumno(imageUrl);
                    resultadoRN.setPrecision(precisionFloat);
                    System.out.println("ResultadoRN actualizado: " + resultadoRN);
                }
                resultadoRNRepositorio.save(resultadoRN);
                System.out.println("ResultadoRN guardado/actualizado");
            } else {
                System.out.println("No se cumplen las condiciones para guardar/actualizar ResultadoRN o la URL de la imagen es nula.");
            }

            if (razon != null && !razon.isEmpty()){
                System.out.println("Intentando guardar MotivoRechazo para boleta: " + boleta + ", idets: " + idets + ", razón: " + razon);
                MotivoRechazo motivoRechazo = new MotivoRechazo(id, razon);
                motivoRechazo.setIngresoSalon(finalIngresoSalon);
                motivoRechazoRepositorio.save(motivoRechazo);
                System.out.println("MotivoRechazo guardado");
            }

        } catch (DocenteNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int obtenerEstadoNumero(String tipo) {
        return switch (tipo) {
            case "Aceptado: Verificado por el profesor." -> 6;
            case "Aceptado: Verificado con el código QR de la credencial." -> 5;
            case "Aceptado: Verificado con el reconocimiento facial." -> 4;
            case "Rechazado: Verificado por el profesor." -> 3;
            case "Rechazado: Verificado con el código QR de la credencial." -> 2;
            case "Rechazado: Verificado con el reconocimiento facial." -> 1;
            default -> 0; 
        };
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
