package com.dts.classes.Mantenimientos.Menu_rol;


import org.simpleframework.xml.Element;

public class clsBeMenu_sistema_op {

    @Element(required=false) public String IdMenuSistemaOP;
    @Element(required=false) public int IdTipoTarea;
    @Element(required=false) public String Nombre;
    @Element(required=false) public int Nivel;
    @Element(required=false) public String Padre;
    @Element(required=false) public int Posicion;


    public clsBeMenu_sistema_op() {
    }

    public clsBeMenu_sistema_op(String IdMenuSistemaOP,int IdTipoTarea,String Nombre,int Nivel,
                                String Padre,int Posicion) {

        this.IdMenuSistemaOP=IdMenuSistemaOP;
        this.IdTipoTarea=IdTipoTarea;
        this.Nombre=Nombre;
        this.Nivel=Nivel;
        this.Padre=Padre;
        this.Posicion=Posicion;

    }


    public String getIdMenuSistemaOP() {
        return IdMenuSistemaOP;
    }
    public void setIdMenuSistemaOP(String value) {
        IdMenuSistemaOP=value;
    }
    public int getIdTipoTarea() {
        return IdTipoTarea;
    }
    public void setIdTipoTarea(int value) {
        IdTipoTarea=value;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String value) {
        Nombre=value;
    }
    public int getNivel() {
        return Nivel;
    }
    public void setNivel(int value) {
        Nivel=value;
    }
    public String getPadre() {
        return Padre;
    }
    public void setPadre(String value) {
        Padre=value;
    }
    public int getPosicion() {
        return Posicion;
    }
    public void setPosicion(int value) {
        Posicion=value;
    }

}

