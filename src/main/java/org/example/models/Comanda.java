package org.example.models;

import java.util.List;

public class Comanda {
    private int id;
    private Mesa mesa;
    private List<Producto> productos;
    private int cantidad;
    private double preciototal;

    public Comanda(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public void addProducto(Producto producto){
        this.productos.add(producto);
    }

    public void removeProducto(Producto producto){
        this.productos.remove(producto);
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPreciototal() {
        return preciototal;
    }

    public void setPreciototal(double preciototal) {
        this.preciototal = preciototal;
    }
}
