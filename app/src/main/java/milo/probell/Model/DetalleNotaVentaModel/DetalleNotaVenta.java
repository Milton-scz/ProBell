package milo.probell.Model.DetalleNotaVentaModel;

public class DetalleNotaVenta {
    private  int id;
    private int id_venta;
    private  int id_producto;
    private  int cantidad;
    private double preciov;

    public DetalleNotaVenta() {
    }

    public DetalleNotaVenta(int id, int idVenta, int idProducto, int cantidad, double preciov) {
        this.id = id;
        this.id_venta = idVenta;
        this.id_producto = idProducto;
        this.cantidad = cantidad;
        this.preciov = preciov;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdVenta() {
        return id_venta;
    }

    public void setIdVenta(int idVenta) {
        this.id_venta = idVenta;
    }

    public int getIdProducto() {
        return id_producto;
    }

    public void setIdProducto(int idProducto) {
        this.id_producto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPreciov() {
        return preciov;
    }

    public void setPreciov(double preciov) {
        this.preciov = preciov;
    }
}
