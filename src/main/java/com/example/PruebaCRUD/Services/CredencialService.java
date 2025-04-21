package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.DTO.ComparacionDTO;
import com.example.PruebaCRUD.DTO.CredencialDTO;
import com.example.PruebaCRUD.DTO.DatosWebDTO;
import com.example.PruebaCRUD.Repositories.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CredencialService {
    @Autowired
    private AlumnoRepository alumnoRepository;

    public List<CredencialDTO> findCredencialPorBoleta(String boleta) {
        return alumnoRepository.findbyBoleta(boleta);
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
}