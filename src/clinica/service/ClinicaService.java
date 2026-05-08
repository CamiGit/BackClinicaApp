package clinica.service;

import clinica.interfaces.Consultable;
import clinica.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClinicaService implements Consultable{

    private List<Paciente> pacientes = new ArrayList<>();
    private List<Medico> medicos = new ArrayList<>();
    private List<Turno> turnos = new ArrayList<>();

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public List<Medico> getMedicos() {
        return medicos;
    }

    public List<Turno> getTurnos() {
        return turnos;
    }

//    PACIENTES

    public void registrarPaciente(Paciente p){
        if(!p.esValido()){
            System.out.println("Error: los datos del paciente no son validos");
            return;
        }
        if (pacientes.contains(p)){
            System.out.println("Error: ya existe un paciente con esa cedula");
            return;
        }
        int maxId = 0;
        for (Paciente paciente : pacientes){
            if (paciente.getId() > maxId){
                maxId = paciente.getId();
            }
        }
        p.setId(maxId +1);

        pacientes.add(p);
        System.out.println("Paciente registrado: " + p);
    }

    public Paciente buscarPorCedula(String cedula){
        for (Paciente paciente : pacientes){
            if (cedula.equals(paciente.getCedula())){
                return paciente;
            }
        }
        return null;
    }

    public void listarPacientes(){
        if (pacientes.isEmpty()){
            System.out.println("En este momento el sistema no cuenta con pacientes registrados");
            return;
        }

        List<Paciente> ordenados = new ArrayList<>(pacientes);

        for (int i = 0; i < ordenados.size() -1; i++){
            for (int j = 0; j < ordenados.size() -1 -i; j++){
                String apellidoA = ordenados.get(j).getApellido();
                String apellidoB = ordenados.get(j + 1).getApellido();
                if (apellidoA.compareToIgnoreCase(apellidoB) > 0){
                    Paciente temp = ordenados.get(j);
                    ordenados.set(j, ordenados.get(j+1));
                    ordenados.set(j + 1, temp);
                }
            }
        }
        System.out.println("\n====== LISTA DE PACIENTES ======");
        for (Paciente p : ordenados){
            System.out.println(p);
        }
    }

// MEDICOS
    public void registrarMedico(Medico m){
        if (!m.esValido()){
            System.out.println("Error, los datos del medico no son validos");
            return;
        }
        if (medicos.contains(m)){
            System.out.println("Error: ya existe un medico con ese nombre");
            return;
        }
        int maxId = 0;
        for (Medico medico : medicos){
            if (medico.getId() > maxId){
                maxId = medico.getId();
            }
        }
        m.setId(maxId +1);

        medicos.add(m);
        System.out.println("Medico registrado: " + m);
    }

    public Medico buscarPorNombreApellido(String nombre, String apellido){
        for (Medico medico : medicos){
            if (nombre.equalsIgnoreCase(medico.getNombre()) && apellido.equalsIgnoreCase(medico.getApellido())){
                return medico;
            }
        }
        return null;
    }

    public void listarMedicos(){
        if (medicos.isEmpty()){
            System.out.println("En este momento el sistema no cuenta con medicos registrados");
            return;
        }

        List<Medico> ordenados = new ArrayList<>(medicos);

        for (int i = 0; i < ordenados.size() -1; i++){
            for (int j = 0; j < ordenados.size() -1 -i; j++){
                if (ordenados.get(j).getEspecialidad().ordinal() > ordenados.get(j+1).getEspecialidad().ordinal()){
                    Medico temp = ordenados.get(j);
                    ordenados.set(j, ordenados.get(j+1));
                    ordenados.set(j + 1, temp);
                }
                if (ordenados.get(j).getEspecialidad().ordinal() == ordenados.get(j+1).getEspecialidad().ordinal()) {
                    String apellidoA = ordenados.get(j).getApellido();
                    String apellidoB = ordenados.get(j + 1).getApellido();
                    if (apellidoA.compareToIgnoreCase(apellidoB) > 0){
                        Medico temp = ordenados.get(j);
                        ordenados.set(j, ordenados.get(j+1));
                        ordenados.set(j + 1, temp);
                    }
                }
            }
        }
        System.out.println("\n====== LISTA DE MEDICOS ======");
        for (Medico m : ordenados){
            System.out.println(m);
        }
    }

//    TURNOS
    public void asignarTurno(Turno t){
        Paciente pacEncontrado = buscarPorCedula(t.getPaciente().getCedula());
        if (pacEncontrado == null){
            System.out.println("Error, el paciente no esta registrado");
            return;
        }
        Medico medEncontrado = buscarPorNombreApellido(t.getMedico().getNombre(), t.getMedico().getApellido());
        if (medEncontrado == null){
            System.out.println("Error, el medico no esta registrado");
            return;
        }
        if (turnos.contains(t)) {
            System.out.println("Error: ya existe un turno con esos datos");
            return;
        }
        int maxId = 0;
        for (Turno turno : turnos){
            if (turno.getId() > maxId){
                maxId = turno.getId();
            }
        }
        t.setId(maxId +1);

        turnos.add(t);
        System.out.println("Turno registrado: " + t);

    }

    public void cancelarTurno(int idTurno){
        Turno turEncontrado = buscarTurnoPorId(idTurno);
        if (turEncontrado == null){
            System.out.println("Error: el turno no esta registrado");
            return;
        }else if (turEncontrado.getEstado() ==  EstadoTurno.ATENDIDO || turEncontrado.getEstado() == EstadoTurno.CANCELADO){
                System.out.println("No se puede cancelar el Turno, ya que ya fue atendido o esta cancelado");
                return;
        }else {
            turEncontrado.setEstado(EstadoTurno.CANCELADO);
            System.out.println("El trurno con id " + idTurno + "se ha cancelado");
        }
    }

    private Turno buscarTurnoPorId(int id) {
        for (Turno turno : turnos) {
            if (turno.getId() == id) {
                return turno;
            }
        }
        return null;
    }

    public void cambiarEstadoTurno(int idTurno, EstadoTurno nuevo){
        Turno turEncontrado = buscarTurnoPorId(idTurno);
        if (turEncontrado == null){
            System.out.println("Turno no encontrado");
            return;
        }else {
            turEncontrado.setEstado(nuevo);
            System.out.println("El estado del turno ha sido actualizado a: " + nuevo);
        }
    }

    @Override
    public List<Turno> buscarPorMedico(Medico medico){
        List<Turno> turnos1 = new ArrayList<>();
        for (Turno turno : turnos){
            if (turno.getMedico().equals(medico)){
                turnos1.add(turno);
            }
        }
        return turnos1;
    }

    @Override
    public List<Turno> buscarPorPaciente(Paciente paciente){
        List<Turno> turnos1 = new ArrayList<>();
        for (Turno turno : turnos){
            if (turno.getPaciente().equals(paciente)){
                turnos1.add(turno);
            }
        }
        return turnos1;
    }

    @Override
    public List<Turno> listarTurnosDelDia(LocalDate fecha) {
        List<Turno> turnos1 = new ArrayList<>();
        for (Turno turno : turnos) {
            if (turno.getFechaHora().toLocalDate().equals(fecha)) {
                turnos1.add(turno);
            }
        }
        for (int i = 0; i < turnos1.size() -1; i++) {
            for (int j = 0; j < turnos1.size() - 1 - i; j++) {
                LocalDateTime fechaA = turnos1.get(j).getFechaHora();
                LocalDateTime fechaB = turnos1.get(j+1).getFechaHora();
                if (fechaA.isAfter(fechaB)){
                    Turno temp = turnos1.get(j);
                    turnos1.set(j, turnos1.get(j+1));
                    turnos1.set(j + 1, temp);
                }
            }
        }
        return turnos1;
    }
}
