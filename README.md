# Sistema de Gestión Integral - Veterinaria Happy Feet

## 📋 Descripción del Proyecto

La clínica veterinaria "Happy Feet" enfrentaba serios problemas operativos debido a un sistema obsoleto basado en fichas de papel, agendas manuales y hojas de cálculo. Los principales desafíos incluían:

- **Historiales clínicos incompletos** que ponían en riesgo la salud de los pacientes
- **Fugas de inventario** sin control en tiempo real
- **Agendamiento caótico** con citas solapadas y largos tiempos de espera
- **Facturación lenta** propensa a errores manuales

Este sistema integral digitaliza y centraliza todas las operaciones de la clínica, desde la ficha del paciente hasta la facturación, permitiendo una gestión profesional y eficiente.

## 🚀 Tecnologías Utilizadas

- **Java 17** - Lenguaje de programación principal
- **MySQL 8.0+** - Sistema de gestión de base de datos
- **JDBC** - Conectividad con base de datos
- **Maven** - Gestión de dependencias y construcción del proyecto
- **Arquitectura MVC** - Patrón de diseño Modelo-Vista-Controlador
- **Git** - Control de versiones

## ✨ Funcionalidades Implementadas

### 1. Gestión de Pacientes (Mascotas y Dueños)
- Registro completo de mascotas con datos médicos, microchip, fotografías
- Gestión de dueños con información de contacto única
- Transferencia de propiedad de mascotas
- Búsqueda y consulta de fichas completas
- Listado optimizado con información esencial

### 2. Servicios Médicos y Citas
- Agenda veterinaria con múltiples estados (Programada, En Proceso, Finalizada, Cancelada, Reprogramada, No Asistió)
- Asignación de veterinarios a citas
- Registro de motivos y seguimiento
- Gestión de servicios asociados a cada cita

### 3. Historial Médico
- Registro de eventos médicos por tipo (Consulta, Vacunación, Cirugía, Emergencia, etc.)
- Seguimiento de tratamientos y diagnósticos
- Vinculación de productos y servicios utilizados
- Historial completo por mascota

### 4. Inventario y Farmacia
- Control detallado de medicamentos, vacunas, insumos y alimentos
- **Deducción automática de inventario** al registrar ventas
- **Alertas de stock mínimo** para reabastecimiento oportuno
- **Alertas de productos próximos a vencer** (30 días)
- Gestión de proveedores
- Registro de compras con detalle

### 5. Facturación y Reportes
- **Generación automática de facturas** en formato texto profesional
- Cálculo automático de totales y subtotales
- Items mixtos: servicios y productos
- Reportes gerenciales:
  - Servicios más solicitados
  - Desempeño del equipo veterinario
  - Estado del inventario
  - Facturación por período

### 6. Actividades Especiales
- **Programa de Adopciones**: Registro de mascotas disponibles y generación de contratos
- **Jornadas de Vacunación**: Organización de campañas masivas
- **Club de Mascotas Frecuentes**: 
  - Sistema de puntos automático (1 punto por cada $10 gastados)
  - Niveles: Bronce (0-199), Plata (200-499), Oro (500-999), Diamante (1000+)
  - Actualización automática de niveles
  - Beneficios exclusivos por nivel

## 🗄️ Modelo de Base de Datos

El sistema utiliza **25 tablas** organizadas en 5 niveles de dependencia:

**Tablas principales:**
- `duenos`, `mascotas`, `especies`, `razas`
- `empleados`, `servicios`, `citas`, `cita_estados`
- `inventario`, `producto_tipos`, `proveedores`, `compras`
- `facturas`, `items_factura`
- `historial_medico`, `evento_tipos`
- `adopciones`, `jornadas_vacunacion`, `club_mascotas`, `beneficios`

**Relaciones clave:**
- Una mascota pertenece a un dueño y una raza
- Las citas vinculan mascotas con empleados y servicios
- El historial médico registra eventos por mascota
- Las facturas deducen automáticamente del inventario
- El club de mascotas acumula puntos por compras

*Para visualizar el diagrama completo, consulte el archivo `database_diagram.png` en el repositorio.*

## 📥 Instrucciones de Instalación y Ejecución

### Requisitos Previos

1. **Java Development Kit (JDK) 17+**
   ```bash
   java -version
   ```

2. **IntelliJ IDEA (Community o Ultimate)**
   ```bash
   mvn -version
   ```

3. **MySQL Server 8.0+**
   ```bash
   mysql --version
   ```

### Paso 1: Clonar el Repositorio

```bash
git clone https://github.com/Brayanmantilla/HappyFeet_Veterinaria_MantillaBrayan-.git
cd HappyFeet_Veterinaria_MantillaBrayan-
```

### Paso 2: Configurar la Base de Datos

1. **Iniciar MySQL Server** y acceder:
   ```bash
   mysql -u root -p
   ```

2. **Ejecutar los scripts en orden**:
   
   a) Crear las tablas:
   ```sql
   source /ruta/al/proyecto/database/schema.sql
   ```
   
   b) Insertar datos iniciales:
   ```sql
   source /ruta/al/proyecto/database/data.sql
   ```

3. **Configurar la conexión** en `src/main/java/happyfeet/util/Conexion.java`:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/happyfeetveterinaria";
   private static final String USER = "tu_usuario";
   private static final String PASSWORD = "tu_contraseña";
   ```

### Paso 3: Compilar el Proyecto

```bash
mvn clean compile
```

### Paso 4: Ejecutar la Aplicación

```bash
mvn exec:java -Dexec.mainClass="happyfeet.main.Main"
```

O ejecutar directamente desde tu IDE (IntelliJ IDEA, Eclipse, NetBeans):
- Ubicar la clase `Main.java` en `src/main/java/happyfeet/main/`
- Ejecutar como aplicación Java

## 📖 Guía de Uso

### Menú Principal

Al iniciar el sistema, encontrará el siguiente menú:

```
=========================================
     SISTEMA DE GESTIÓN INTEGRAL
          VETERINARIA HAPPY FEET
=========================================
[1] Gestión de Pacientes (Mascotas y Dueños)
[2] Servicios Médicos y Citas
[3] Inventario y Farmacia
[4] Facturación y Reportes
[5] Actividades Especiales
[0] Salir del Sistema
```

### Flujo de Trabajo Típico

1. **Registrar un nuevo cliente**:
   - Menú 1 → Opción 1: Registrar dueño
   - Menú 1 → Opción 2: Registrar mascota

2. **Agendar una cita**:
   - Menú 2 → Opción 1: Programar nueva cita

3. **Registrar evento médico**:
   - Menú 1 → Opción 7: Actualizar historial médico

4. **Generar factura**:
   - Menú 4 → Opción 1: Generar factura
   - El sistema deduce automáticamente del inventario

5. **Consultar reportes**:
   - Menú 4 → Opciones 2-5: Diversos reportes gerenciales

## ⚠️ Reglas de Negocio Importantes

- Al vender productos, el inventario se **deduce automáticamente**
- El sistema **no permite** vender productos con stock insuficiente
- Los productos vencidos **no pueden ser utilizados**
- El club de mascotas otorga **1 punto por cada $10** en compras
- Los niveles del club se actualizan **automáticamente** al acumular puntos
- Las citas pueden tener múltiples estados para seguimiento preciso

## 🏗️ Arquitectura del Proyecto

```
src/
├── main/
│   └── java/
│       └── happyfeet/
│           ├── model/
│           │   ├── entities/      # 22 clases de entidades
│           │   └── enums/         # Enumeraciones
│           ├── repository/
│           │   ├── DAO/           # 20+ DAOs implementados
│           │   └── interfaces/    # Interfaces de persistencia
│           ├── service/           # 16 servicios de lógica de negocio
│           ├── controller/        # 7 controladores
│           ├── view/              # 7 vistas de consola
│           ├── util/              # Utilidades (Conexión BD)
│           └── main/              # Punto de entrada (Main)
└── resources/
    └── database/
        ├── schema.sql             # Estructura de BD
        └── data.sql               # Datos iniciales
```

## 👨‍💻 Autor

**Brayan Mantilla**  
*Estudiante de Ingeniería de Sistemas*  
CAMPUSLANDS
---

## 📝 Notas Adicionales

- El sistema incluye **datos de prueba** para facilitar la exploración
- Todos los cálculos monetarios utilizan precisión decimal
- Las fechas se manejan con `LocalDate` de Java 8+
- El sistema valida todas las entradas del usuario

## 🐛 Solución de Problemas

**Error de conexión a MySQL:**
- Verificar que MySQL Server esté ejecutándose
- Confirmar usuario y contraseña en `Conexion.java`
- Verificar que la base de datos `happyfeetveterinaria` exista

**Error al compilar:**
- Verificar versión de JDK (debe ser 17+)
- Ejecutar `mvn clean install`

**Datos no se muestran:**
- Verificar que los scripts SQL se ejecutaron correctamente
- Revisar logs de consola para errores de BD

---

**© 2025 Sistema Happy Feet - Todos los derechos reservados**
