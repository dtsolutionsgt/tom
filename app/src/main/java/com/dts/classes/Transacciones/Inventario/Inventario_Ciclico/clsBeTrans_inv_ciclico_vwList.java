package com.dts.classes.Transacciones.Inventario.Inventario_Ciclico;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_inv_ciclico_vwList {

    @ElementList(inline=true,required=false)
    public List<clsBeTrans_inv_ciclico_vw> items;

}
