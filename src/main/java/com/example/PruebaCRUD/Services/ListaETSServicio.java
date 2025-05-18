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
        private final InscripcionETSRepositorio inscripcionETSRepositorio;
        private final AplicaRepositorio aplicaRepositorio;
    
        @Autowired // Notación que permite inyectar dependencias en este caso, InscripcionETSRepository
        public ListaETSServicio(InscripcionETSRepositorio inscripcionETSRepositorio, AplicaRepositorio aplicaRepositorio) {
            this.inscripcionETSRepositorio = inscripcionETSRepositorio;
            this.aplicaRepositorio = aplicaRepositorio;
        }

      public List<ListaETSRespuestaDTO> inscripcionesETS(String boleta) {
              List<Object[]> resultados = inscripcionETSRepositorio.callListInscripcionesETS(boleta);

              List<ListaETSRespuestaDTO> respuestaLista = new ArrayList<>();

              // Iterar sobre cada resultado y mapearlo a un DTO
              for (Object[] resultado : resultados) {
                  Integer idets = (Integer) resultado[0];
                  String periodo = (String) resultado[1];
                  String turno = (String) resultado[2];
                  Date fecha = (Date) resultado[3];
                  String materia = (String) resultado[4];
                  String carrera = (String) resultado[5];
                  Boolean inscrito = (Boolean) resultado[6];

                  // Convertir la fecha a String (o al formato que necesites)
                  String fechaString = fecha.toString();

                  // Crear un DTO y agregarlo a la lista de respuestas
                  respuestaLista.add(new ListaETSRespuestaDTO(idets, periodo, turno, fechaString, materia, inscrito, carrera));
              }

              return respuestaLista;
          }
    
        public Boolean confirmarInscripcion(String boleta) {
            return inscripcionETSRepositorio.existsByBoletaInsBoleta(boleta);
        }
    
    
        public List<ListaETSRespuestaDTO> aplicacionETS(String docente_rfc) {
          List<Object[]> resultados = aplicaRepositorio.callListAplica(docente_rfc);
    
          List<ListaETSRespuestaDTO> respuestaLista = new ArrayList<>();
    
          // Iterar sobre cada resultado y mapearlo a un DTO
          for (Object[] resultado : resultados) {
              Integer idets = (Integer) resultado[0];
              String periodo = (String) resultado[1];
              String turno = (String) resultado[2];
              Date fecha = (Date) resultado[3];
              String materia = (String) resultado[4];
              String carrera = (String) resultado[5];
    
              // Convertir la fecha a String (o al formato que necesites)
              String fechaString = fecha.toString();
    
              // Crear un DTO y agregarlo a la lista de respuestas
              respuestaLista.add(new ListaETSRespuestaDTO(idets, periodo, turno, fechaString, materia, carrera));
          }
    
          return respuestaLista;
        }
    
    }
