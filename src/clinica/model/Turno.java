package clinica.model;

import java.time.LocalDateTime;

public class Turno {

    private int id;
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime fechaHora;
    private EstadoTurno estado;

    public Turno(Paciente paciente, Medico medico, LocalDateTime fechaHora) {

        setPaciente(paciente);
        setMedico(medico);
        setFechaHora(fechaHora);

        this.estado = EstadoTurno.PENDIENTE;
    }

    public Turno(int id,
                 Paciente paciente,
                 Medico medico,
                 LocalDateTime fechaHora,
                 EstadoTurno estado) {

        this.id = id;

        setPaciente(paciente);
        setMedico(medico);
        setFechaHora(fechaHora);
        setEstado(estado);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("Paciente inválido");
        }
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        if (medico == null) {
            throw new IllegalArgumentException("Médico inválido");
        }
        this.medico = medico;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        if (fechaHora == null) {
            throw new IllegalArgumentException("Fecha inválida");
        }

        this.fechaHora = fechaHora;

    }

    public EstadoTurno getEstado() {
        return estado;
    }

    public void setEstado(EstadoTurno estado) {
        if (estado == null) {
            throw new IllegalArgumentException("Estado inválido");
        }

        this.estado = estado;

    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (!(obj instanceof Turno))
            return false;

        Turno otro = (Turno) obj;

        return medico.equals(otro.medico)
                && fechaHora.equals(otro.fechaHora);
    }

    @Override
    public String toString() {

        return "[" + estado + "] " +
                paciente.getNombre() + " " + paciente.getApellido() +
                " — Dr. " + medico.getNombre() + " " + medico.getApellido() +
                " (" + medico.getEspecialidad() + ") — " +
                fechaHora;
    }
}