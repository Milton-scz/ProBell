package milo.probell.Model.ClienteModel;

public class Cliente {
    private int id;
    private String nombre;
    private String cedula;
    private String celular;
    private String direccion;

    // Constructor

    public Cliente() {
    }

    public Cliente(int id, String nombre, String cedula, String celular, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.cedula = cedula;
        this.celular = celular;
        this.direccion = direccion;
    }

    public Cliente(String nombre, String cedula, String celular, String direccion) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.celular = celular;
        this.direccion = direccion;
    }

    // Getter y Setter para id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter y Setter para nombre
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Getter y Setter para cedula
    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    // Getter y Setter para celular
    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    // Getter y Setter para direccion
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return nombre;
    }
}