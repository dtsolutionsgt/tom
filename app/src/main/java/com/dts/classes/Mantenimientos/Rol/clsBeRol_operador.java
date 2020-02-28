package com.dts.classes.Mantenimientos.Rol;


import com.dts.classes.Mantenimientos.Menu_rol.clsBeMenu_rol_opList;

import org.simpleframework.xml.Element;

public class clsBeRol_operador {

    @Element(required=false) public int IdRolOperador;
    @Element(required=false) public String Nombre;
    @Element(required=false) public clsBeMenu_rol_opList ListMenuRolOp=new clsBeMenu_rol_opList();


    public clsBeRol_operador() {
    }

    public clsBeRol_operador(int IdRolOperador,String Nombre,clsBeMenu_rol_opList ListMenuRolOp) {

        this.IdRolOperador=IdRolOperador;
        this.Nombre=Nombre;
        this.ListMenuRolOp=ListMenuRolOp;

    }


    public int getIdRolOperador() {
        return IdRolOperador;
    }
    public void setIdRolOperador(int value) {
        IdRolOperador=value;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String value) {
        Nombre=value;
    }
    public clsBeMenu_rol_opList getListMenuRolOp() {
        return ListMenuRolOp;
    }
    public void setListMenuRolOp(clsBeMenu_rol_opList value) {
        ListMenuRolOp=value;
    }

}

