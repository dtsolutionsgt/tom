package com.dts.classes.Transacciones.Inventario.Productos_Sugeridos.Inv_Stock_Prod;

import org.simpleframework.xml.Element;

public class clsBeTrans_inv_stock_prod_sug {

    @Element(required=false) public String Codigo="";

    public clsBeTrans_inv_stock_prod_sug() {
    }

    public clsBeTrans_inv_stock_prod_sug(String Codigo) {
        this.Codigo=Codigo;
    }

    public String getCodigo() {
        return Codigo;
    }
    public void setCodigo(String value) {
        Codigo=value;
    }

}

