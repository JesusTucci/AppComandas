package org.example.models;

public class Mesa {
    private int id;
    private int num_mesa;
    private String estado;

    public Mesa(int id, int num_mesa, String estado) {
        this.id = id;
        this.num_mesa = num_mesa;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNum_mesa() {
        return num_mesa;
    }

    public void setNum_mesa(int num_mesa) {
        this.num_mesa = num_mesa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
