package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.IngresoSalon;
import com.example.PruebaCRUD.BD.PKCompuesta.BoletaETSPK;
import com.example.PruebaCRUD.BD.ResultadoRN;
import com.example.PruebaCRUD.DTO.PythonResponseDTO;
import com.example.PruebaCRUD.Repositories.IngresoSalonRepository;
import com.example.PruebaCRUD.Repositories.ResultadoRNRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImagenRedService {
    private static final String IMAGES_DIR = "SpringBoot-Java/src/main/java/com/example/PruebaCRUD/IMGRED";

    @Autowired
    private ResultadoRNRepository resultadoRNRepository;

    @Autowired
    private IngresoSalonRepository ingresoSalonRepository;

    public void guardarImagenYActualizarBD(MultipartFile file, String boleta, int idets) {
        try {
            // 1. Crear directorio si no existe
            Path dirPath = Paths.get(IMAGES_DIR, "ETS_" + idets);
            Files.createDirectories(dirPath);

            // 2. Guardar imagen en el directorio
            String fileName = boleta + "_ETS_" + idets + ".jpg";
            Path filePath = dirPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // 3. Actualizar o insertar en la base de datos
            BoletaETSPK id = new BoletaETSPK(boleta, idets); // Usa el constructor de BoletaETSPK



            ResultadoRN resultadoRN = resultadoRNRepository.findById(id).orElse(null);

            IngresoSalon ingresoSalon = ingresoSalonRepository.findById(id).orElse(null);

            if (resultadoRN == null) {
                resultadoRN = new ResultadoRN(id, filePath.toString(), 0f,ingresoSalon);
            } else {
                resultadoRN.setImagenAlumno(filePath.toString());
            }

            System.out.println("XD");

            System.out.println(ingresoSalon);

            System.out.println(resultadoRN);

            resultadoRNRepository.save(resultadoRN);

            // No se envía respuesta a Android aquí
        } catch (IOException e) {
            e.printStackTrace(); // Manejar la excepción según sea necesario
        }
    }

    public void actualizarResultadoRN(String boleta, int idets, PythonResponseDTO pythonResponseDTO) {
        BoletaETSPK id = new BoletaETSPK(boleta, idets); // Usa el constructor de BoletaETSPK

        ResultadoRN resultadoRN = resultadoRNRepository.findById(id).orElse(null);
        if (resultadoRN != null) {
            resultadoRN.setPrecision(pythonResponseDTO.getPresicion());
            resultadoRNRepository.save(resultadoRN);
        }
    }
}
