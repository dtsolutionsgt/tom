package com.dts.classes.Transacciones.ControlCalidad;

public class clsBeCalidad {

    public int id;
    public String Texto = "";
    public boolean Estado = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexto() {
        return Texto;
    }

    public void setTexto(String texto) {
        Texto = texto;
    }

    public boolean getEstado() {
        return Estado;
    }

    public void setEstado(boolean estado) {
        Estado = estado;
    }
}
