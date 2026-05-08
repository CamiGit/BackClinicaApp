package clinica;

import clinica.datos.DatosCSV;
import clinica.model.*;
import clinica.service.ClinicaService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {

    // Colores ANSI
    static final String RESET = "\u001B[0m";
    static final String BOLD = "\u001B[1m";
    static final String CYAN = "\u001B[36m";
    static final String GREEN = "\u001B[32m";
    static final String YELLOW = "\u001B[33m";
    static final String RED = "\u001B[31m";
    static final String BLUE = "\u001B[34m";
    static final String MAGENTA = "\u001B[35m";
    static final String WHITE = "\u001B[37m";

    // Helpers visuales
    static void linea() {System.out.println(CYAN + "═".repeat(50) + RESET);}
    static void lineaThin(){System.out.println(CYAN + "─".repeat(50) + RESET);}

    static void exito(String msg){System.out.println(GREEN + "✔  " + msg + RESET);}
    static void error(String msg){System.out.println(RED + "✘  " + msg + RESET);}
    static void info(String msg){System.out.println(YELLOW + "ℹ" + msg + RESET);}
    static void titulo(String msg){System.out.println(BOLD + CYAN + msg + RESET);}

    static void mostrarMenu() {
        System.out.println();
        linea();
        System.out.println(BOLD + CYAN + "    🏥  CLINICAAPP — SISTEMA DE TURNOS  🏥" + RESET);
        linea();
        System.out.println(MAGENTA + "  PACIENTES" + RESET);
        System.out.println(WHITE   + "   1.  Registrar paciente" + RESET);
        System.out.println(WHITE   + "   2.  Listar pacientes"   + RESET);
        lineaThin();
        System.out.println(MAGENTA + "  MÉDICOS" + RESET);
        System.out.println(WHITE   + "   3.  Registrar médico"   + RESET);
        System.out.println(WHITE   + "   4.  Listar médicos"     + RESET);
        lineaThin();
        System.out.println(MAGENTA + "  TURNOS" + RESET);
        System.out.println(WHITE   + "   5.  Asignar turno"           + RESET);
        System.out.println(WHITE   + "   6.  Listar turnos del día"   + RESET);
        System.out.println(WHITE   + "   7.  Cancelar turno"          + RESET);
        System.out.println(WHITE   + "   8.  Ver turnos por médico"   + RESET);
        System.out.println(WHITE   + "   9.  Ver turnos por paciente" + RESET);
        System.out.println(WHITE   + "   10. Cambiar estado de turno" + RESET);
        lineaThin();
        System.out.println(RED     + "   0.  Salir/Guardar" + RESET);
        linea();
        System.out.print(BOLD + YELLOW + "  Seleccione una opción: " + RESET);
    }

    static String pedir(Scanner sc, String campo) {
        System.out.print(CYAN + "  " + campo + ": " + RESET);
        return sc.nextLine();
    }

    // ── Main ─────────────────────────────────────────────────────────────────
    public static void main(String[] args) {

        ClinicaService servicio = new ClinicaService();
        DatosCSV.cargar(servicio);
        Scanner sc = new Scanner(System.in);
        int opcion;

        System.out.println();
        linea();
        titulo("  Bienvenido a ClinicaApp");
        info("Datos cargados correctamente.");
        linea();

        do {
            mostrarMenu();

            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                error("Ingresa un número válido.");
                opcion = -1;
                continue;
            }

            System.out.println();

            switch (opcion) {

                case 1:
                    titulo("  ── Registrar Paciente ──");
                    try {
                        String ced = pedir(sc, "Cédula");
                        String nom = pedir(sc, "Nombre");
                        String ape = pedir(sc, "Apellido");
                        String tel = pedir(sc, "Teléfono");
                        servicio.registrarPaciente(new Paciente(ced, nom, ape, tel));
                    } catch (IllegalArgumentException e) {
                        error(e.getMessage());
                    }
                    break;

                case 2:
                    titulo("  ── Lista de Pacientes ──");
                    servicio.listarPacientes();
                    break;

                case 3:
                    titulo("  ── Registrar Médico ──");
                    try {
                        String nom = pedir(sc, "Nombre");
                        String ape = pedir(sc, "Apellido");

                        info("Especialidades:");
                        System.out.println(WHITE + "   1. GENERAL" + RESET);
                        System.out.println(WHITE + "   2. PEDIATRIA" + RESET);
                        System.out.println(WHITE + "   3. CARDIOLOGIA" + RESET);
                        System.out.println(WHITE + "   4. URGENCIAS" + RESET);

                        int opEsp = Integer.parseInt(pedir(sc, "Selecciona una opción"));

                        Especialidad especialidad = null;
                        switch (opEsp) {
                            case 1:
                                especialidad = Especialidad.GENERAL;
                                break;
                            case 2:
                                especialidad = Especialidad.PEDIATRIA;
                                break;
                            case 3:
                                especialidad = Especialidad.CARDIOLOGIA;
                                break;
                            case 4:
                                especialidad = Especialidad.URGENCIAS;
                                break;
                            default:
                                error("Opción inválida. Elige entre 1 y 4.");
                                break;
                        }
                        if (especialidad == null)
                            break;

                        servicio.registrarMedico(new Medico(nom, ape, especialidad));

                    } catch (NumberFormatException e) {
                        error("Debes ingresar un número entre 1 y 4.");
                    } catch (IllegalArgumentException e) {
                        error("No se pudo registrar el médico: ");
                    }
                    break;

                case 4:
                    titulo("  ── Lista de Médicos ──");
                    servicio.listarMedicos();
                    break;

                case 5:
                    titulo("  ── Asignar Turno ──");
                    try {
                        String cedPac = pedir(sc, "Cédula del paciente");
                        String nomMed = pedir(sc, "Nombre del médico");
                        String apeMed = pedir(sc, "Apellido del médico");

                        Paciente pac = servicio.buscarPorCedula(cedPac);
                        Medico med = servicio.buscarPorNombreApellido(nomMed, apeMed);
                        if (pac == null){
                            error("No existe un paciente con esa cédula.");
                            break;
                        }
                        if (med == null){
                            error("No existe un médico con ese nombre y apellido.");
                            break;
                        }

                        info("Ingresa la fecha y hora del turno.");
                        int anio = Integer.parseInt(pedir(sc, "Año"));
                        int mes = Integer.parseInt(pedir(sc, "Mes"));
                        int dia = Integer.parseInt(pedir(sc, "Día"));
                        int hora = Integer.parseInt(pedir(sc, "Hora"));
                        int minuto = Integer.parseInt(pedir(sc, "Minuto"));

                        servicio.asignarTurno(new Turno(pac, med,
                                LocalDateTime.of(anio, mes, dia, hora, minuto)));
                    }catch (NumberFormatException e) {
                        error("La fecha y hora deben ser números enteros.");
                    } catch (Exception e){
                        error("Fecha u hora inválida, verifica los valores ingresados.");
                    }
                    break;

                case 6:
                    titulo("  ── Turnos del Día ──");
                    try {
                        int a = Integer.parseInt(pedir(sc, "Año"));
                        int m = Integer.parseInt(pedir(sc, "Mes"));
                        int d = Integer.parseInt(pedir(sc, "Día"));
                        List<Turno> lista = servicio.listarTurnosDelDia(LocalDate.of(a, m, d));
                        if (lista.isEmpty()) {
                            info("No hay turnos registrados para ese día.");
                        } else {
                            lineaThin(); lista.forEach(t -> System.out.println(GREEN + "  " + t + RESET));
                            lineaThin();
                        }
                    } catch (NumberFormatException e) {
                        error("El año, mes y día deben ser números enteros.");
                    } catch (Exception e) {
                        error("Fecha inválida. Verifica que el mes esté entre 1-12 y el día sea correcto.");
                    }
                    break;

                case 7:
                    titulo("  ── Cancelar Turno ──");
                    try {
                        int id = Integer.parseInt(pedir(sc, "ID del turno"));
                        servicio.cancelarTurno(id);
                    } catch (NumberFormatException e){
                        error("ID inválido.");
                    }
                    break;

                case 8:
                    titulo("  ── Turnos por Médico ──");
                    try {
                        String nom = pedir(sc, "Nombre del médico");
                        String ape = pedir(sc, "Apellido del médico");
                        Medico med = servicio.buscarPorNombreApellido(nom, ape);
                        if (med == null) {
                            error("Médico no encontrado.");
                            break;
                        }
                        List<Turno> lista = servicio.buscarPorMedico(med);
                        if (lista.isEmpty()) {
                            info("No hay turnos para ese médico.");
                        } else{
                            lineaThin();
                            lista.forEach(t -> System.out.println(GREEN + "  " + t + RESET));
                            lineaThin();
                        }
                    } catch (Exception e){
                        error("Ocurrió un error al buscar los turnos del médico.");
                    }
                    break;

                case 9:
                    titulo("  ── Turnos por Paciente ──");
                    try {
                        String ced = pedir(sc, "Cédula del paciente");
                        Paciente pac = servicio.buscarPorCedula(ced);
                        if (pac == null){
                            error("Paciente no encontrado.");
                            break;
                        }
                        List<Turno> lista = servicio.buscarPorPaciente(pac);
                        if (lista.isEmpty()){
                            info("No hay turnos para ese paciente.");
                        } else { lineaThin();
                            lista.forEach(t -> System.out.println(GREEN + "  " + t + RESET));
                            lineaThin();
                        }
                    } catch (Exception e){
                        error("Ocurrió un error al buscar los turnos del paciente.");
                    }
                    break;

                case 10:
                    titulo("  ── Cambiar Estado de Turno ──");
                    try {
                        int id = Integer.parseInt(pedir(sc, "ID del turno"));

                        info("Estados:");
                        System.out.println(WHITE + "   1. PENDIENTE" + RESET);
                        System.out.println(WHITE + "   2. ATENDIDO"  + RESET);
                        System.out.println(WHITE + "   3. CANCELADO" + RESET);

                        int opEst = Integer.parseInt(pedir(sc, "Selecciona una opción"));

                        EstadoTurno nuevoEstado = null;
                        switch (opEst) {
                            case 1:
                                nuevoEstado = EstadoTurno.PENDIENTE;
                                break;
                            case 2:
                                nuevoEstado = EstadoTurno.ATENDIDO;
                                break;
                            case 3:
                                nuevoEstado = EstadoTurno.CANCELADO;
                                break;
                            default:
                                error("Opción inválida. Elige entre 1 y 3.");
                                break;
                        }

                        if (nuevoEstado == null) break;

                        servicio.cambiarEstadoTurno(id, nuevoEstado);

                    } catch (NumberFormatException e) {
                        error("Debes ingresar un número entero.");
                    }
                    break;

                case 0:
                    DatosCSV.guardar(servicio);
                    System.out.println();
                    linea();
                    titulo("  Hasta pronto. Datos guardados correctamente.");
                    linea();
                    break;

                default:
                    error("Opción no válida. Elige entre 0 y 10.");
            }

        } while (opcion != 0);

        sc.close();
    }
}