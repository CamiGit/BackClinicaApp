package clinica.model;

import clinica.interfaces.Registrable;

public class Medico implements Registrable
{

    private int id;
    private String nombre;
    private String apellido;
    private Especialidad especialidad;

    public Medico(String nombre, String apellido,
                  Especialidad especialidad)
    {

        setNombre(nombre);
        setApellido(apellido);
        setEspecialidad(especialidad);
    }

    public Medico(int id, String nombre,
                  String apellido,
                  Especialidad especialidad)
    {

        this.id = id;

        setNombre(nombre);
        setApellido(apellido);
        setEspecialidad(especialidad);
    }

    @Override
    public String getDatosRegistro()
    {
        return toString();
    }

    @Override
    public boolean esValido() {

        return nombre != null &&
                !nombre.isBlank() &&
                apellido != null &&
                !apellido.isBlank() &&
                especialidad != null;
    }

    @Override
    public boolean equals(Object obj)
    {

        if (this == obj) return true;

        if (!(obj instanceof Medico)) return false;

        Medico m = (Medico) obj;

        return nombre.equalsIgnoreCase(m.nombre)
                && apellido.equalsIgnoreCase(m.apellido);
    }

    @Override
    public String toString()
    {

        return "Dr. " + nombre + " " + apellido +
                " - " + especialidad;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }


    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {

        if (nombre == null || nombre.isBlank())
        {
            throw new IllegalArgumentException("Nombre invalido");
        }

        this.nombre = nombre.trim();
    }

    public String getApellido()
    {
        return apellido;
    }

    public void setApellido(String apellido)
    {

        if (apellido == null || apellido.isBlank()) {
            throw new IllegalArgumentException("Apellido invalido");
        }

        this.apellido = apellido.trim();
    }

    public Especialidad getEspecialidad()
    {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad)
    {

        if (especialidad == null)
        {
            throw new IllegalArgumentException("Especialidad invalida");
        }

        this.especialidad = especialidad;
    }
}