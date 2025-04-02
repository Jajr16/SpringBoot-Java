//package com.example.PruebaCRUD.Controllers;
//
//import com.example.PruebaCRUD.DTO.DetalleAlumnosDTO;
//import com.example.PruebaCRUD.Services.DetalleAlumnosService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/alumno")
//public class DetalleAlumnosController {
//
//    private final DetalleAlumnosService detalleAlumnosService;
//
//    @Autowired
//    public DetalleAlumnosController(DetalleAlumnosService detalleAlumnosService) {
//        this.detalleAlumnosService = detalleAlumnosService;
//    }
//
//    @GetMapping("/detalle/{boleta}")
//    public ResponseEntity<?> findDetalleAlumnoporboleta(@PathVariable String boleta) {
//        try {
//            List<DetalleAlumnosDTO> detalles = detalleAlumnosService.findDetalleAlumnoporboleta(boleta);
//
//            if (detalles == null || detalles.isEmpty()) {
//                return ResponseEntity.noContent().build();
//            }
//
//            return ResponseEntity.ok(detalles);
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().body("Error al procesar la solicitud: " + e.getMessage());
//        }
//    }
//}