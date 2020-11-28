package com.dts.classes.Transacciones.Inventario.InventarioReconteo;

import org.simpleframework.xml.Element;

public class clsBe_inv_ciclico_bool {

    @Element(required=false) public boolean Control_peso= false;
    @Element(required=false) public boolean Control_lote= false;
    @Element(required=false) public boolean Control_vencimiento=false;
    @Element(required=false) public Integer idproducto=0;

    public clsBe_inv_ciclico_bool(){}

    public clsBe_inv_ciclico_bool(boolean Control_peso, boolean Control_lote, boolean Control_vencimiento, Integer idproducto ){

        this.Control_peso = Control_peso;
        this.Control_lote = Control_lote;
        this.Control_vencimiento = Control_vencimiento;
        this.idproducto = idproducto;
    }

    public boolean getControl_peso() {
        return Control_peso;
    }
    public void setControl_peso(boolean value) {
        Control_peso=value;
    }

    public boolean getControl_lote() {
        return Control_lote;
    }
    public void setControl_lote(boolean value) {
        Control_lote=value;
    }

    public boolean getControl_vencimiento() {
        return Control_vencimiento;
    }
    public void setControl_vencimiento(boolean value) {
        Control_vencimiento=value;
    }

    public Integer getidproducto() {
        return idproducto;
    }
    public void setidproducto(Integer value) {
        idproducto=value;
    }


}
