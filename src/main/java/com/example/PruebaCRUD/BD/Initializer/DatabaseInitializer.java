package com.example.PruebaCRUD.BD.Initializer;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatabaseInitializer {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void initDatabase() {
        try {
            // Ejecutar todas las funciones
            jdbcTemplate.execute(loginFunction());
            jdbcTemplate.execute(listInscripcionesETSFunction());
            jdbcTemplate.execute(listAplicaFunction());
            jdbcTemplate.execute(buscarDatosEstudianteFunction());
            jdbcTemplate.execute(obtenerAsistenciaDetallesFunction());
            jdbcTemplate.execute(obtenerPersonaFunction());
            jdbcTemplate.execute(obtenerDatosReporteFunction());
            jdbcTemplate.execute(obtenerImagenAlumnoFunction());
            jdbcTemplate.execute(verificarIngresoSalonFunction());
            jdbcTemplate.execute(eliminarReporteAlumnoFunction());
            jdbcTemplate.execute(obtenerDocenteRfcFunction());

            // Ejecutar funciones de validación y creación de usuarios
            jdbcTemplate.execute(validarProgramaAcademicoFunction());
            jdbcTemplate.execute(crearUsuarioAFunction());
            jdbcTemplate.execute(crearUsuarioPAFunction());
            jdbcTemplate.execute(crearUsuarioPSFunction());

            // Ejecutar triggers
            jdbcTemplate.execute(triggerValidarPrograma());
            jdbcTemplate.execute(triggerCreateUser());
            jdbcTemplate.execute(triggerCreateUserPA());
            jdbcTemplate.execute(triggerCreateUserPS());

            // Ejecutar alter tables para las constraints
            jdbcTemplate.execute(alterConstraints());

            System.out.println("Funciones, triggers y constraints creados correctamente.");
        } catch (Exception e) {
            System.err.println("Error al inicializar la base de datos: " + e.getMessage());
        }
    }

    private String loginFunction() {
        return """
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
    }

    private String listInscripcionesETSFunction() {
        return """
                CREATE OR REPLACE FUNCTION ListInscripcionesETS(
                       boletaC VARCHAR(18)
                   )
                   RETURNS TABLE(
                       idets INTEGER,
                       periodo VARCHAR,
                       turno_nombre VARCHAR,
                       fecha DATE,
                       unidad_aprendizaje_nombre VARCHAR,
                   	inscrito Boolean
                   )
                   LANGUAGE plpgsql
                   AS $$
                   BEGIN
                   	RETURN QUERY
                
                   	SELECT\s
                   	    ets.idets,
                   	    periodoets.periodo,\s
                   	    turno.nombre AS turno,\s
                   	    ets.fecha,
                   	    unidadaprendizaje.nombre AS unidad_aprendizaje,
                   	    EXISTS (
                               SELECT 1\s
                               FROM inscripcionets i\s
                               WHERE i.idets = ets.idets\s
                               AND i.boleta = boletaC
                           ) AS inscrito
                   	FROM ets
                   	INNER JOIN periodoets ON ets.id_periodo = periodoets.id_periodo\s
                   	INNER JOIN turno ON turno.id_turno = ets.turno
                   	INNER JOIN unidadaprendizaje ON unidadaprendizaje.idua = ets.idua;
                      \s
                   END;
                   $$;
            """;
    }

    private String listAplicaFunction() {
        return """
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
    }

    private String buscarDatosEstudianteFunction() {
        return """
            CREATE OR REPLACE FUNCTION buscardatosestudiante(boleta_input VARCHAR)
            RETURNS TABLE (
                boleta VARCHAR,
                curp VARCHAR,
                apellido_p VARCHAR,
                apellido_m VARCHAR,
                nombre VARCHAR,
                unidadAcademica VARCHAR
            ) AS $$
            BEGIN
                -- Retornar los datos deseados
                RETURN QUERY
                SELECT\s
                    e.boleta,
                    e.curp,
                    p.apellido_p,
                    p.apellido_m,
                    p.nombre,
                    pa.nombre AS unidadAcademica
                FROM alumno e
                INNER JOIN persona p ON e.curp = p.curp
                INNER JOIN programaacademico pa ON e.idpa = pa.idpa
                WHERE e.boleta = boleta_input;
            END;
            $$ LANGUAGE plpgsql;
            """;
    }

    private String obtenerAsistenciaDetallesFunction() {
        return """
            CREATE OR REPLACE FUNCTION ObtenerAsistenciaDetalles(
                  etsprueba INTEGER
              )
              RETURNS TABLE(
                  idETS INTEGER,
                  Boleta VARCHAR(18),
                  CURP VARCHAR(18),
                  NombreA VARCHAR,
                  ApellidoP VARCHAR,
                  ApellidoM VARCHAR,
                  Sexo VARCHAR,
                  Correo VARCHAR,
                  Carrera VARCHAR,
                  Aceptado INTEGER
              )
              LANGUAGE plpgsql
              AS $$
              BEGIN
                  RETURN QUERY
                  SELECT
                      ai.idets AS idETS,
                      ai.boleta AS Boleta,
                      a.curp AS CURP,
                      p.nombre AS NombreA,
                      p.apellido_p AS ApellidoP,
                      p.apellido_m AS ApellidoM,
                      s.nombre AS Sexo,
                      a.correoi AS Correo,
                      a.idpa::VARCHAR AS Carrera,
                      COALESCE(ax.estado, 0) AS Aceptado
                  FROM inscripcionets ai
                  INNER JOIN alumno a ON ai.boleta = a.boleta
                  INNER JOIN persona p ON a.curp = p.curp
                  INNER JOIN sexo s ON p.sexo = s.id_sexo
                  LEFT JOIN ingreso_salon ax ON ai.boleta = ax.boleta AND ax.idets = etsprueba -- Added condition here
                  WHERE ai.idets = etsprueba;
              END;
              $$;
            """;
    }

    private String obtenerPersonaFunction() {
        return """
            CREATE OR REPLACE FUNCTION obtenerpersona(
                   boletaC VARCHAR(18)
               )\s
               RETURNS TABLE(
                   nombre VARCHAR,
                   apellido_p VARCHAR,\s
                   apellido_m VARCHAR\s
                  \s
               )\s
               LANGUAGE plpgsql
               AS $$
               BEGIN
                   RETURN QUERY
                   SELECT\s
                       p.nombre::VARCHAR,
                       p.apellido_p::VARCHAR,\s
                       p.apellido_m::VARCHAR
                      \s
                   FROM usuario u
                   INNER JOIN persona p ON u.curp = p.curp
                   WHERE u.usuario = boletaC;
               END;
               $$;
            """;
    }

    private String obtenerDatosReporteFunction() {
        return """
            CREATE OR REPLACE FUNCTION obtener_datos_reporte(p_idets INTEGER, p_boleta VARCHAR)
                RETURNS TABLE (
                    curp VARCHAR,
                    nombre VARCHAR,
                    apellido_p VARCHAR,
                    apellido_m VARCHAR,
                    escuela VARCHAR,
                    carrera VARCHAR,
                    periodo VARCHAR,
                    tipo VARCHAR,
                    turno VARCHAR,
                    materia VARCHAR,
                    fecha_ingreso DATE,
                    hora_ingreso TIME,
                    nombre_docente VARCHAR,
                    tipo_estado VARCHAR,
                    presicion REAL,
                    motivo VARCHAR
                ) AS $$
                BEGIN
                    RETURN QUERY
                    SELECT
                        p.curp,
                        p.nombre,
                        p.apellido_p,
                        p.apellido_m,
                        ua.nombre AS escuela,
                        pa.nombre AS carrera,
                        pe.periodo,
                        pe.tipo::VARCHAR,
                        t.nombre AS turno,
                        ua2.nombre AS materia,
                        isalon.fecha AS fecha_ingreso,
                        isalon.hora AS hora_ingreso,
                        isalon.docente AS nombre_docente,
                        te.tipo AS tipo_estado,
                        COALESCE(rn.precision, 0.0) AS presicion, -- Valor predeterminado
                        COALESCE(mr.motivo, 'No Motivo') AS motivo -- Valor predeterminado
                    FROM
                        alumno a
                    INNER JOIN
                        persona p ON a.curp = p.curp
                    INNER JOIN
                        unidadacademica ua ON p.id_escuela = ua.id_escuela
                    INNER JOIN
                        programaacademico pa ON a.idpa = pa.idpa
                    INNER JOIN
                        ets e ON e.idets = p_idets
                    INNER JOIN
                        periodoets pe ON e.id_periodo = pe.id_periodo
                    INNER JOIN
                        turno t ON e.turno = t.id_turno
                    INNER JOIN
                        unidadaprendizaje ua2 ON e.idua = ua2.idua
                    INNER JOIN
                        ingreso_salon isalon ON e.idets = isalon.idets AND a.boleta = isalon.boleta
                    INNER JOIN
                        tipo_estado te ON isalon.estado = te.idtipo
                    LEFT JOIN
                        resultadorn rn ON e.idets = rn.idets AND a.boleta = rn.boleta -- LEFT JOIN para permitir NULLs
                    LEFT JOIN
                        motivo_rechazo mr ON a.boleta = mr.boleta AND e.idets = mr.idets -- LEFT JOIN para permitir NULLs
                    WHERE
                        a.boleta = p_boleta AND e.idets = p_idets;
                END;
                $$ LANGUAGE plpgsql;
            """;
    }

    private String obtenerImagenAlumnoFunction() {
        return """
            CREATE OR REPLACE FUNCTION obtener_imagen_alumno(p_idets INTEGER, p_boleta VARCHAR)
            RETURNS VARCHAR AS $$
            DECLARE
                ruta_imagen VARCHAR;
            BEGIN
                SELECT rn.imagen_alumno INTO ruta_imagen
                FROM resultadorn rn
                INNER JOIN ets e ON e.idets = rn.idets
                INNER JOIN alumno a ON a.boleta = rn.boleta
                WHERE e.idets = p_idets AND a.boleta = p_boleta;

                RETURN ruta_imagen;
            END;
            $$ LANGUAGE plpgsql;
            """;
    }

    private String verificarIngresoSalonFunction() {
        return """
            CREATE OR REPLACE FUNCTION verificar_ingreso_salon (p_boleta VARCHAR, p_idets INTEGER)
            RETURNS BOOLEAN
            AS $$
            BEGIN
                RETURN EXISTS (SELECT 1 FROM ingreso_salon WHERE boleta = p_boleta AND idets = p_idets);
            END;
            $$ LANGUAGE plpgsql;
            """;
    }

    private String eliminarReporteAlumnoFunction() {
        return """
            CREATE OR REPLACE FUNCTION eliminar_reporte_alumno(p_idets INTEGER, p_boleta VARCHAR)
            RETURNS BOOLEAN AS $$
            DECLARE
                registros_eliminados INTEGER := 0;
            BEGIN
                -- Eliminar de la tabla resultadorn (dependiente de ingreso_salon)
                DELETE FROM resultadorn
                WHERE idets = p_idets AND boleta = p_boleta;
                IF FOUND THEN
                    registros_eliminados := registros_eliminados + 1;
                END IF;

                -- Eliminar de la tabla motivo_rechazo (dependiente de ingreso_salon)
                DELETE FROM motivo_rechazo
                WHERE idets = p_idets AND boleta = p_boleta;
                IF FOUND THEN
                    registros_eliminados := registros_eliminados + 1;
                END IF;

                -- Eliminar de la tabla ingreso_salon (tabla principal)
                DELETE FROM ingreso_salon
                WHERE idets = p_idets AND boleta = p_boleta;
                IF FOUND THEN
                    registros_eliminados := registros_eliminados + 1;
                END IF;

                -- Si al menos un registro fue eliminado, consideramos la operación exitosa
                IF registros_eliminados > 0 THEN
                    RETURN TRUE;
                ELSE
                    RETURN FALSE;
                END IF;
            END;
            $$ LANGUAGE plpgsql;
            """;
    }

    private String obtenerDocenteRfcFunction() {
        return """
            CREATE OR REPLACE FUNCTION obtener_docente_rfc(p_idets INTEGER) RETURNS VARCHAR AS $$
            DECLARE
                v_docente_rfc VARCHAR;
            BEGIN
                -- Obtener docente_rfc de la tabla aplica
                SELECT docente_rfc INTO v_docente_rfc
                FROM aplica
                WHERE idets = p_idets;

                -- Verificar si se encontró el docente_rfc
                IF v_docente_rfc IS NULL THEN
                    RETURN 'No se encontró docente RFC para idets ' || p_idets;
                ELSE
                    RETURN v_docente_rfc;
                END IF;
            EXCEPTION
                WHEN OTHERS THEN
                    RETURN 'Error al obtener docente RFC: ' || SQLERRM;
            END;
            $$ LANGUAGE plpgsql;
            """;
    }

    private String validarProgramaAcademicoFunction() {
        return """
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
    }

    private String crearUsuarioAFunction() {
        return """
            CREATE OR REPLACE FUNCTION crear_usuarioA()
            RETURNS TRIGGER AS $$
            BEGIN
                BEGIN

                    INSERT INTO usuario
                    VALUES (NEW.boleta, crypt(NEW.boleta, gen_salt('bf')), NEW.curp, (SELECT idtu FROM tipousuario WHERE tipo = 'Alumno'));
               \s
                EXCEPTION WHEN OTHERS THEN
                    -- Si hay un error, cancelar la transacción y lanzar excepción
                    RAISE EXCEPTION 'Error al crear usuario, operación cancelada: %', SQLERRM;
                    RETURN NULL; -- Evita que se inserte el alumno si hay un error
                END;
               \s
                RETURN NEW;
            END;
            $$ LANGUAGE plpgsql;
            """;
    }

    private String crearUsuarioPAFunction() {
        return """
            CREATE OR REPLACE FUNCTION crear_usuarioPA()
            RETURNS TRIGGER AS $$
            BEGIN
                BEGIN

                    INSERT INTO usuario
                    VALUES (NEW.rfc, crypt(NEW.rfc, gen_salt('bf')), NEW.curp, (SELECT idtu FROM tipousuario WHERE tipo = 'Personal Academico'));
               \s
                EXCEPTION WHEN OTHERS THEN
                    -- Si hay un error, cancelar la transacción y lanzar excepción
                    RAISE EXCEPTION 'Error al crear usuario, operación cancelada: %', SQLERRM;
                    RETURN NULL; -- Evita que se inserte el personal academico si hay un error
                END;
               \s
                RETURN NEW;
            END;
            $$ LANGUAGE plpgsql;
            """;
    }

    private String crearUsuarioPSFunction() {
        return """
            CREATE OR REPLACE FUNCTION crear_usuariopPS()
            RETURNS TRIGGER AS $$

            BEGIN

                BEGIN

                    INSERT INTO usuario
                    VALUES (NEW.rfc, crypt(NEW.rfc, gen_salt('bf')), NEW.curp,
                    (SELECT idtu FROM tipousuario WHERE tipo = 'Personal Seguridad'));

                EXCEPTION WHEN OTHERS THEN
                    -- Si hay un error, cancelar la transacción y lanzar excepción
                    RAISE EXCEPTION 'Error al crear usuario, operación cancelada: %', SQLERRM;
                    RETURN NULL; -- Evita que se inserte el personal academico si hay un error
                END;

                RETURN NEW;
            END;
            $$ LANGUAGE plpgsql;
            """;
    }

    private String triggerValidarPrograma() {
        return """
            CREATE OR REPLACE TRIGGER trigger_validar_programa
            BEFORE INSERT OR UPDATE ON alumno
            FOR EACH ROW
            EXECUTE FUNCTION validar_programa_academico();
            """;
    }

    private String triggerCreateUser() {
        return """
            CREATE OR REPLACE TRIGGER create_user\s
            AFTER INSERT ON alumno
            FOR EACH ROW
            EXECUTE FUNCTION crear_usuarioA();
            """;
    }

    private String triggerCreateUserPA() {
        return """
            CREATE OR REPLACE TRIGGER create_userPA
            AFTER INSERT ON personalacademico
            FOR EACH ROW
            EXECUTE FUNCTION crear_usuarioPA();
            """;
    }

    private String triggerCreateUserPS() {
        return """
            CREATE OR REPLACE TRIGGER create_userPS
            AFTER INSERT ON personalseguridad
            FOR EACH ROW
            EXECUTE FUNCTION crear_usuariopPS();
            """;
    }

    private String alterConstraints() {
        return """
                ALTER TABLE public.personalacademico
                DROP CONSTRAINT fkoannh428gwaa99dj2ghpfnek7;
                
                ALTER TABLE public.personalacademico\s
                ADD CONSTRAINT fkoannh428gwaa99dj2ghpfnek7\s
                FOREIGN KEY (curp)\s
                REFERENCES public.persona(curp)\s
                ON DELETE CASCADE
                ON UPDATE CASCADE;
                
                ALTER TABLE public.cargodocente
                DROP CONSTRAINT fk3mtxsq6vvg52q7ok4or8cbs3o;
                
                ALTER TABLE public.cargodocente\s
                ADD CONSTRAINT fk3mtxsq6vvg52q7ok4or8cbs3o\s
                FOREIGN KEY (rfc)\s
                REFERENCES public.personalacademico(rfc)\s
                ON DELETE CASCADE
                ON UPDATE CASCADE;
                
                
                ALTER TABLE public.personalseguridad
                DROP CONSTRAINT fkaqfhf3tuec5c7e0787a3dc21i;
                
                ALTER TABLE public.personalseguridad\s
                ADD CONSTRAINT fkaqfhf3tuec5c7e0787a3dc21i\s
                FOREIGN KEY (curp)\s
                REFERENCES public.persona(curp)\s
                ON DELETE CASCADE
                ON UPDATE CASCADE;
                
                ALTER TABLE public.alumno\s
                DROP CONSTRAINT fkop2ux5hhbenpakvtwjeb1iswo;
                
                ALTER TABLE public.alumno\s
                ADD CONSTRAINT fkop2ux5hhbenpakvtwjeb1iswo\s
                FOREIGN KEY (curp)\s
                REFERENCES public.persona(curp)\s
                ON DELETE CASCADE
                ON UPDATE CASCADE;
                
                ALTER TABLE public.usuario\s
                DROP CONSTRAINT fk7k5mbe2uawbnfhr2d7h8jxlo0;
                
                ALTER TABLE public.usuario\s
                ADD CONSTRAINT fk7k5mbe2uawbnfhr2d7h8jxlo0\s
                FOREIGN KEY (curp)\s
                REFERENCES public.persona(curp)\s
                ON DELETE CASCADE
                ON UPDATE CASCADE;
            """;
    }
}