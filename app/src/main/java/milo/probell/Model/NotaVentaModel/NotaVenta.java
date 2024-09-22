package milo.probell.Model.NotaVentaModel;

public class NotaVenta {
    private int id;
    private String fecha;
    private  double monto;
    private int idCliente;

    public NotaVenta() {
    }

    public NotaVenta(int id, String fecha, double monto, int idCliente) {
        this.id = id;
        this.fecha = fecha;
        this.monto = monto;
        this.idCliente = idCliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
}

