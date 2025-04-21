# Reorganización del Proyecto

## Problemas Identificados

1. **Clases de servicio demasiado grandes**: Algunas clases como `PersonaService.java` (27,980 bytes) tienen demasiadas responsabilidades.
2. **Demasiadas clases pequeñas**: Hay muchas clases pequeñas que podrían consolidarse.
3. **Organización por capas técnicas**: El proyecto está organizado por capas técnicas (BD, Repositories, Services) en lugar de por dominios funcionales.
4. **Inconsistencia en la nomenclatura**: Existen clases con nombres similares (ListETSService y ListETSService2).
5. **Acoplamiento excesivo**: Algunas clases de servicio tienen muchas dependencias (PersonaService tiene 14 repositorios inyectados).

## Plan de Reorganización

### 1. Reorganización por Dominios Funcionales

Reorganizar el proyecto por dominios funcionales en lugar de por capas técnicas:

```
com.example.PruebaCRUD/
├── common/                  # Componentes comunes y utilidades
│   ├── config/              # Configuraciones globales
│   ├── exception/           # Manejo de excepciones
│   ├── security/            # Configuración de seguridad
│   └── util/                # Clases de utilidad
├── domain/                  # Dominios funcionales
│   ├── alumno/              # Todo lo relacionado con alumnos
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── entity/
│   │   ├── repository/
│   │   └── service/
│   ├── academico/           # Todo lo relacionado con personal académico
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── entity/
│   │   ├── repository/
│   │   └── service/
│   ├── seguridad/           # Todo lo relacionado con personal de seguridad
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── entity/
│   │   ├── repository/
│   │   └── service/
│   ├── ets/                 # Todo lo relacionado con ETS
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── entity/
│   │   ├── repository/
│   │   └── service/
│   ├── salon/               # Todo lo relacionado con salones
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── entity/
│   │   ├── repository/
│   │   └── service/
│   └── usuario/             # Todo lo relacionado con usuarios y autenticación
│       ├── controller/
│       ├── dto/
│       ├── entity/
│       ├── repository/
│       └── service/
└── infrastructure/          # Componentes de infraestructura
    ├── cloudinary/          # Integración con Cloudinary
    ├── firebase/            # Integración con Firebase
    ├── image/               # Procesamiento de imágenes
    └── scraping/            # Funcionalidad de scraping
```

### 2. Refactorización de Clases Grandes

#### PersonaService

Dividir `PersonaService` en múltiples servicios más pequeños:

1. `AlumnoManagementService`: Gestión de alumnos
   - `getAlumnos()`
   - `newAlumno()`
   - `newVideoAlumno()`

2. `DocenteManagementService`: Gestión de docentes
   - `getDocentes()`
   - `getDocentesToETS()`
   - `newDocente()`

3. `SeguridadManagementService`: Gestión de personal de seguridad
   - `getPS()`
   - `newPersonalSeguridad()`

4. `PersonaBaseService`: Operaciones básicas de persona
   - `getPersona()`
   - `newPersona()`
   - `deletePersona()`

#### ETSService

Dividir `ETSService` en servicios más específicos:

1. `ETSManagementService`: Gestión básica de ETS
2. `ETSInscripcionService`: Gestión de inscripciones a ETS
3. `ETSReporteService`: Generación de reportes de ETS

### 3. Consolidación de Clases Pequeñas

Consolidar clases pequeñas relacionadas:

1. Combinar `ListETSService` y `ListETSService2` en un solo servicio `ETSListService`
2. Consolidar servicios de catálogos pequeños (SexoService, TurnoService, etc.) en un `CatalogoService`

### 4. Mejora de la Cohesión y Reducción del Acoplamiento

1. Crear interfaces de servicio para definir contratos claros
2. Utilizar DTOs específicos para cada operación
3. Implementar el patrón Repository para abstraer el acceso a datos
4. Utilizar el patrón Facade para simplificar interfaces complejas

### 5. Implementación de Patrones de Diseño

1. **Patrón Repository**: Ya implementado, pero mejorar la abstracción
2. **Patrón Service**: Mejorar la separación de responsabilidades
3. **Patrón DTO**: Utilizar DTOs específicos para cada operación
4. **Patrón Factory**: Para la creación de objetos complejos
5. **Patrón Strategy**: Para algoritmos intercambiables (por ejemplo, diferentes estrategias de validación)

## Plan de Implementación

### Fase 1: Preparación

1. Crear la nueva estructura de directorios
2. Definir interfaces para los nuevos servicios
3. Crear clases DTO específicas para cada dominio

### Fase 2: Migración por Dominios

1. Migrar el dominio de alumnos
2. Migrar el dominio de personal académico
3. Migrar el dominio de personal de seguridad
4. Migrar el dominio de ETS
5. Migrar el dominio de salones
6. Migrar el dominio de usuarios

### Fase 3: Refactorización de Clases Grandes

1. Refactorizar PersonaService
2. Refactorizar ETSService
3. Refactorizar ImagenRedService

### Fase 4: Consolidación de Clases Pequeñas

1. Consolidar servicios de catálogos
2. Consolidar repositorios pequeños relacionados

### Fase 5: Pruebas y Validación

1. Crear pruebas unitarias para los nuevos servicios
2. Validar la funcionalidad completa del sistema
3. Realizar pruebas de integración

## Beneficios Esperados

1. **Mayor cohesión**: Cada clase tendrá una única responsabilidad
2. **Menor acoplamiento**: Reducción de dependencias entre clases
3. **Mejor mantenibilidad**: Código más fácil de entender y modificar
4. **Mayor testabilidad**: Clases más pequeñas son más fáciles de probar
5. **Escalabilidad mejorada**: Facilita la adición de nuevas funcionalidades
6. **Mejor organización**: Estructura más intuitiva basada en dominios funcionales