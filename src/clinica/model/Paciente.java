package clinica.model;


import clinica.interfaces.Registrable;

public class Paciente implements Registrable {
    private  int id;
    private String cedula;
    private String nombre;
    private String apellido;
    private String telefono;

    public Paciente(int id, String cedula, String nombre, String apellido, String telefono) {
        this.id = id;
        setCedula(cedula);
        setNombre(nombre);
        setApellido(apellido);
        setTelefono(telefono);

    }

    public Paciente(String cedula, String nombre, String apellido, String telefono) {
        setCedula(cedula);
        setNombre(nombre);
        setApellido(apellido);
        setTelefono(telefono);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        if (cedula == null || cedula.isEmpty()){
            throw new IllegalArgumentException("Cedula invalida");
        }
        if (!cedula.matches("^[0-9]{6,12}$"))
            throw new IllegalArgumentException("la cedula debe contener solo números.");
        this.cedula = cedula.trim();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null){
            throw new IllegalArgumentException("Nombre invalido, digite otra vez");
        }
        if (nombre.length() == 0)
            throw new IllegalArgumentException("Nombre invalido, digite otra vez");
        this.nombre = nombre.trim();
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        if (apellido == null){
            throw new IllegalArgumentException("Apellido invalido, digite otra vez");
        }
        if (apellido.length() == 0)
            throw new IllegalArgumentException("Appellido invalido, digite otra vez");
        this.apellido = apellido.trim();
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {

        if(telefono == null)
            throw new IllegalArgumentException("Telefono invalido, digite otra vez");

        if(!telefono.matches("^[0-9]{7,10}$"))
            throw new IllegalArgumentException("Telefono invalido, digite otra vez");
        this.telefono = telefono.trim();
    }

    @Override
    public boolean esValido() {

        if (cedula == null || cedula.isEmpty()) {
            return false;
        }
        if (nombre == null || nombre.isEmpty()) {
            return false;
        }
        if (apellido == null || apellido.isEmpty()) {
            return false;
        }
        if (telefono == null || telefono.isEmpty()) {
            return false;
        }
        if (!telefono.matches("[0-9]{7,10}")) {
            return false;
        }
        return true;
    }

    @Override
    public String getDatosRegistro() {
        return toString();
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " - " + cedula + " - " + telefono;
    }

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Paciente p = (Paciente) o;
        return cedula.equals(p.cedula);
    }
}


