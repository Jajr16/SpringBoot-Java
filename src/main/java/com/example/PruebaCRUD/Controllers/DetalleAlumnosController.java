//package com.example.PruebaCRUD.Controllers;
//
//import com.example.PruebaCRUD.DTO.DetalleAlumnosDTO;
//import com.example.PruebaCRUD.Services.DetalleAlumnosService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/alumno")
//public class DetalleAlumnosController {
//    private final DetalleAlumnosService detalleAlumnosService;
//
//    @Autowired
//    public DetalleAlumnosController(DetalleAlumnosService detalleAlumnosService) {
//        this.detalleAlumnosService = detalleAlumnosService;
//    }
//
//    @GetMapping("/detalle/{boleta}")
//    public List<DetalleAlumnosDTO> findbyBoleta(@PathVariable String boleta) {
//        return detalleAlumnosService.findbyBoleta(boleta);
//    }
//}
