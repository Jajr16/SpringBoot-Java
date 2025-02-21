package com.example.PruebaCRUD.BD.Initializer;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/***
 * Clase que servirá para crear los triggers, functions y todo lo que se necesite para la base.
 */
@Service // Anotación que indica que esta clase es un servicio de negocio
public class DatabaseInitializer {

    private final JdbcTemplate jdbcTemplate; // Clase Spring que ayuda a interactuar con la base de datos

    // Constructor
    public DatabaseInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void initDatabase() {
        // ####################### FUNCIONS ####################
        // Validar correcta asignación de carreras/escuelas
        String validar_programa_academico = """
            CREATE OR REPLACE FUNCTION validar_programa_academico()
                    RETURNS TRIGGER AS $$
                    DECLARE
                        escuela_alumno INT;
                        escuela_programa INT;
                    BEGIN
                        -- Obtener la unidad académica del alumno
                        SELECT id_escuela INTO escuela_alumno\s
                        FROM persona\s
                        WHERE curp = NEW.curp;
                
                        -- Obtener la unidad académica del programa académico
                        SELECT id_escuela INTO escuela_programa\s
                        FROM escuelaprograma\s
                        WHERE idpa = NEW.idpa;
                
                        -- Validar si coinciden
                        IF escuela_alumno IS NULL OR escuela_programa IS NULL THEN
                            RAISE EXCEPTION 'Error: No se encontró la unidad académica del alumno o del programa académico.';
                        END IF;
                
                        IF escuela_alumno != escuela_programa THEN
                            RAISE EXCEPTION 'Error: El programa académico no pertenece a la unidad académica del alumno.';
                        END IF;
                
                        RETURN NEW;
                    END;
                    $$ LANGUAGE plpgsql;
        """;

        String ListAplica = """
            CREATE OR REPLACE FUNCTION ListAplica(
                boletaC VARCHAR(18)
            )
            RETURNS TABLE(
                idets INTEGER,
                periodo VARCHAR,
                turno_nombre VARCHAR,
                fecha DATE,
                unidad_aprendizaje_nombre VARCHAR
            )
            LANGUAGE plpgsql
            AS $$
            BEGIN
                RETURN QUERY

                SELECT aplica.idets, periodoets.periodo, turno.nombre as turno, ets.fecha, unidadaprendizaje.nombre FROM aplica
                INNER JOIN ets ON aplica.idets = ets.idets
                INNER JOIN periodoets ON ets.id_periodo = periodoets.id_periodo\s
                INNER JOIN turno ON turno.id_turno = ets.turno
                INNER JOIN unidadaprendizaje ON unidadaprendizaje.idua = ets.idua WHERE aplica.docente_rfc = boletaC;

               \s
            END;
            $$;
        """;

        String ListInscripcionesETS = """
            CREATE OR REPLACE FUNCTION ListInscripcionesETS(
               boletaC VARCHAR(18)
           )
           RETURNS TABLE(
               idets INTEGER,
               periodo VARCHAR,
               turno_nombre VARCHAR,
               fecha DATE,
               unidad_aprendizaje_nombre VARCHAR
           )
           LANGUAGE plpgsql
           AS $$
           BEGIN
            RETURN QUERY
    
               SELECT inscripcionets.idets, periodoets.periodo, turno.nombre as turno, ets.fecha, unidadaprendizaje.nombre FROM inscripcionets
            INNER JOIN ets ON inscripcionets.idets = ets.idets
            INNER JOIN periodoets ON ets.id_periodo = periodoets.id_periodo\s
            INNER JOIN turno ON turno.id_turno = ets.turno
            INNER JOIN unidadaprendizaje ON unidadaprendizaje.idua = ets.idua WHERE inscripcionets.boleta = boletaC;
    
              \s
           END;
           $$;
        """;

        String login = """
        CREATE OR REPLACE FUNCTION login(
              p_username VARCHAR(18),
              p_password VARCHAR(100),
              OUT p_message VARCHAR(255),
              OUT error_code INT,
              OUT p_rol VARCHAR(255)
          )
          LANGUAGE plpgsql
          AS $$
          DECLARE
              hashed_password TEXT; -- Cambiar a TEXT
              user_found INT;
          BEGIN
              -- Verificar si el usuario existe
              SELECT COUNT(*)
              INTO user_found
              FROM usuario
              WHERE usuario = p_username;
    
              IF user_found = 0 THEN
                  -- Usuario no encontrado
                  error_code := -2;
                  p_message := 'Usuario o contraseña incorrectos.';
                  p_rol := NULL;
    
              ELSE
                  -- Obtener la contraseña encriptada y el rol
                  SELECT u.password::TEXT, t.tipo
                  INTO hashed_password, p_rol
                  FROM usuario u
                  INNER JOIN tipousuario t ON u.tipou = t.idtu
                  WHERE u.usuario = p_username;
    
                  -- Comparar contraseñas usando crypt()
                  IF crypt(p_password::TEXT, hashed_password::TEXT) = hashed_password THEN
                      -- Contraseña correcta
                      error_code := 0;
                      p_message := 'Inicio de sesión exitoso.';
                  ELSE
                      -- Contraseña incorrecta
                      error_code := -1;
                      p_message := 'Usuario o contraseña incorrectos.';
                      p_rol := NULL;
                  END IF;
              END IF;
    
              RETURN;
          END;
          $$;
        """;
        // ##################### TRIGGERS ##########################
        String triggerSql = """
            CREATE TRIGGER trigger_validar_programa
                    BEFORE INSERT OR UPDATE ON alumno
                    FOR EACH ROW
                    EXECUTE FUNCTION validar_programa_academico();
        """;

        // ##################### EJECUTAR FUNCIONES ########################
        try {
            // Ejecutar instrucciones sql anteriores
            jdbcTemplate.execute(validar_programa_academico);
            jdbcTemplate.execute(ListAplica);
            jdbcTemplate.execute(ListInscripcionesETS);
            jdbcTemplate.execute(login);
            jdbcTemplate.execute(triggerSql);
            System.out.println("Trigger y función creados correctamente.");
        } catch (Exception e) {
            System.err.println("Error creando el trigger: " + e.getMessage());
        }
    }
}
