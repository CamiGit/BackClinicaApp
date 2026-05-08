# ClinicaApp — Sistema de Gestión de Turnos Médicos

## 👥 Equipo BackClinicaApp

| Integrante | Rol |
|---|---|
| Camilo Orjuela | Desarrollador |
| Didier Cuan | Desarrollador |
| Brayan Castro | Desarrollador |
| Leider Merchan | Desarrollador |

---

## Descripción

ClinicaApp es un sistema de consola desarrollado en Java que gestiona pacientes, médicos y turnos de una clínica. Los datos se mantienen en memoria durante la sesión y se persisten en archivos CSV al salir, de modo que sobreviven entre ejecuciones.

---

## Funcionalidades

- ✅ Registrar y listar pacientes
- ✅ Registrar y listar médicos por especialidad
- ✅ Asignar turnos con validación de conflictos de agenda
- ✅ Cancelar y cambiar estado de turnos
- ✅ Consultar turnos por día, médico o paciente
- ✅ Persistencia automática en archivos CSV
- ✅ Interfaz de consola con colores ANSI

---

## Estructura del Proyecto

```
clinicaapp/
├── src/
│   └── clinica/
│       ├── model/
│       │   ├── Paciente.java
│       │   ├── Medico.java
│       │   ├── Turno.java
│       │   ├── EstadoTurno.java
│       │   └── Especialidad.java
│       ├── interfaces/
│       │   ├── Registrable.java
│       │   └── Consultable.java
│       ├── service/
│       │   └── ClinicaService.java
│       ├── datos/
│       │   └── DatosCSV.java
│       └── Main.java
└── datos/
    ├── pacientes.csv
    ├── medicos.csv
    └── turnos.csv
```

---

## Cómo correr el proyecto en IntelliJ IDEA

### Requisitos
- IntelliJ IDEA (cualquier versión)
- Java JDK 11 o superior

### Pasos

**1. Clonar el repositorio**
```bash
git clone https://github.com/tu-usuario/clinicaapp.git
```

**2. Abrir el proyecto en IntelliJ**
- Abre IntelliJ IDEA
- Selecciona `File → Open`
- Navega hasta la carpeta del proyecto y haz clic en `OK`

**3. Configurar el SDK**
- Ve a `File → Project Structure → Project`
- Selecciona un JDK instalado (versión 11 o superior)
- Haz clic en `Apply` y luego `OK`

**4. Ejecutar el programa**
- Abre el archivo `Main.java` ubicado en `src/clinica/`
- Haz clic en el botón ▶ verde junto al método `main`
- O usa el atajo `Shift + F10`

### Nota sobre los archivos CSV
Los archivos CSV se generan automáticamente en la carpeta `datos/` la primera vez que cierras el programa con la opción **0 — Salir**. No necesitas crearlos manualmente.

---

## Uso del Sistema

Al ejecutar verás el menú principal:

```
══════════════════════════════════════════════════
    🏥  CLINICAAPP — SISTEMA DE TURNOS  🏥
══════════════════════════════════════════════════
  PACIENTES
   1.  Registrar paciente
   2.  Listar pacientes
  ──────────────────────────────────────────────
  MÉDICOS
   3.  Registrar médico
   4.  Listar médicos
  ──────────────────────────────────────────────
  TURNOS
   5.  Asignar turno
   6.  Listar turnos del día
   7.  Cancelar turno
   8.  Ver turnos por médico
   9.  Ver turnos por paciente
   10. Cambiar estado de turno
  ──────────────────────────────────────────────
   0.  Salir/Guardar
══════════════════════════════════════════════════
```

### Flujo recomendado para pruebas
1. Registra al menos un paciente **(opción 1)**
2. Registra al menos un médico **(opción 3)**
3. Asigna un turno **(opción 5)**
4. Sal con la opción **0** para guardar
5. Vuelve a ejecutar y verifica que los datos persisten

---

## Tecnologías utilizadas

- **Java** — Lenguaje principal
- **POO** — Herencia, interfaces, encapsulamiento, polimorfismo
- **CSV** — Persistencia de datos
- **ANSI** — Colores en consola

---

## Persistencia de datos

Los datos se guardan en tres archivos dentro de la carpeta `datos/`:

| Archivo | Contenido |
|---|---|
| `pacientes.csv` | id, cédula, nombre, apellido, teléfono |
| `medicos.csv` | id, nombre, apellido, especialidad |
| `turnos.csv` | id, cédula paciente, nombre médico, apellido médico, fechaHora, estado |

> Puedes agregar `datos/` al `.gitignore` si no quieres subir datos de prueba al repositorio.

---

*Hackathon Generation Colombia · 2026*
