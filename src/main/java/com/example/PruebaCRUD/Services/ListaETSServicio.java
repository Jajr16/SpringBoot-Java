    package com.example.PruebaCRUD.Services;
    
    import com.example.PruebaCRUD.DTO.ListaETSRespuestaDTO;
    import com.example.PruebaCRUD.Repositories.AplicaRepositorio;
    import com.example.PruebaCRUD.Repositories.InscripcionETSRepositorio;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    
    import java.util.ArrayList;
    import java.util.Date;
    import java.util.List;
    
      /**
     * Clase que contendrá la lógica que para realizar las funciones principales de los endpoints
     */
    @Service // Anotación que indica que esta clase es un servicio de negocio
    public class ListaETSServicio {
        private final InscripcionETSRepositorio inscripcionETSRepository;
        private final AplicaRepositorio aplicaRepositorio;
    
        @Autowired // Notación que permite inyectar dependencias en este caso, InscripcionETSRepository
        public ListaETSServicio(InscripcionETSRepositorio inscripcionETSRepository, AplicaRepositorio aplicaRepositorio) {
            this.inscripcionETSRepository = inscripcionETSRepository;
            this.aplicaRepositorio = aplicaRepositorio;
        }
    
        public List<ListaETSRespuestaDTO> inscripcionesETS(String boleta) {
            List<Object[]> results = inscripcionETSRepository.callListInscripcionesETS(boleta);
    
            List<ListaETSRespuestaDTO> responseList = new ArrayList<>();
    
            // Iterar sobre cada resultado y mapearlo a un DTO
            for (Object[] result : results) {
                Integer idets = (Integer) result[0];
                String periodo = (String) result[1];
                String turno = (String) result[2];
                Date fecha = (Date) result[3];
                String materia = (String) result[4];
                Boolean inscrito = (Boolean) result[5];
                String carrera = (String) result[6];
    
                // Convertir la fecha a String (o al formato que necesites)
                String fechaString = fecha.toString();
    
                // Crear un DTO y agregarlo a la lista de respuestas
                responseList.add(new ListaETSRespuestaDTO(idets, periodo, turno, fechaString, materia, inscrito, carrera));
            }
    
            return responseList;
        }
    
        public Boolean confirmarInscripcion(String boleta) {
            return inscripcionETSRepository.existsByBoletaInsBoleta(boleta);
        }
    
    
        public List<ListaETSRespuestaDTO> aplicacionETS(String docente_rfc) {
          List<Object[]> results = aplicaRepositorio.callListAplica(docente_rfc);
    
          List<ListaETSRespuestaDTO> responseList = new ArrayList<>();
    
          // Iterar sobre cada resultado y mapearlo a un DTO
          for (Object[] result : results) {
              Integer idets = (Integer) result[0];
              String periodo = (String) result[1];
              String turno = (String) result[2];
              Date fecha = (Date) result[3];
              String materia = (String) result[4];
              String carrera = (String) result[5];
    
              // Convertir la fecha a String (o al formato que necesites)
              String fechaString = fecha.toString();
    
              // Crear un DTO y agregarlo a la lista de respuestas
              responseList.add(new ListaETSRespuestaDTO(idets, periodo, turno, fechaString, materia, carrera));
          }
    
          return responseList;
        }
    
    }
