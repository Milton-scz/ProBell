package milo.probell.Model.ProductoModel;

import java.io.Serializable;

public class Producto implements Serializable {
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private int categoriaId;
    private byte[] imagen;

    public Producto() {
    }

    public Producto(int id, String nombre, String descripcion, double precio, int stock, int categoriaId, byte[] imagen) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.categoriaId = categoriaId;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagenUri) {
        this.imagen = imagenUri;
    }
}
