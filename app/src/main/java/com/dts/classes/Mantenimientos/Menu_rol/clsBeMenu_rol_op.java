package com.dts.classes.Mantenimientos.Menu_rol;


import org.simpleframework.xml.Element;

public class clsBeMenu_rol_op {

    @Element(required=false) public String IdMenuSistemaOP="";
    @Element(required=false) public int IdRolOperador=0;
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:01";
    @Element(required=false) public boolean Visible=false;
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public clsBeMenu_sistema_op MenuSistemaOp=new clsBeMenu_sistema_op();


    public clsBeMenu_rol_op() {
    }

    public clsBeMenu_rol_op(String IdMenuSistemaOP,int IdRolOperador,String User_agr,String Fec_agr,
                            String User_mod,String Fec_mod,boolean Visible,boolean Activo,
                            clsBeMenu_sistema_op MenuSistemaOp) {

        this.IdMenuSistemaOP=IdMenuSistemaOP;
        this.IdRolOperador=IdRolOperador;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Visible=Visible;
        this.Activo=Activo;
        this.MenuSistemaOp=MenuSistemaOp;

    }


    public String getIdMenuSistemaOP() {
        return IdMenuSistemaOP;
    }
    public void setIdMenuSistemaOP(String value) {
        IdMenuSistemaOP=value;
    }
    public int getIdRolOperador() {
        return IdRolOperador;
    }
    public void setIdRolOperador(int value) {
        IdRolOperador=value;
    }
    public String getUser_agr() {
        return User_agr;
    }
    public void setUser_agr(String value) {
        User_agr=value;
    }
    public String getFec_agr() {
        return Fec_agr;
    }
    public void setFec_agr(String value) {
        Fec_agr=value;
    }
    public String getUser_mod() {
        return User_mod;
    }
    public void setUser_mod(String value) {
        User_mod=value;
    }
    public String getFec_mod() {
        return Fec_mod;
    }
    public void setFec_mod(String value) {
        Fec_mod=value;
    }
    public boolean getVisible() {
        return Visible;
    }
    public void setVisible(boolean value) {
        Visible=value;
    }
    public boolean getActivo() {
        return Activo;
    }
    public void setActivo(boolean value) {
        Activo=value;
    }
    public clsBeMenu_sistema_op getMenuSistemaOp() {
        return MenuSistemaOp;
    }
    public void setMenuSistemaOp(clsBeMenu_sistema_op value) {
        MenuSistemaOp=value;
    }

}

