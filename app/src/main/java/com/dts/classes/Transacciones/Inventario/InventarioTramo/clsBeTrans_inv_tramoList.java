package com.dts.classes.Transacciones.Inventario.InventarioTramo;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_inv_tramoList {
    @ElementList(inline=true,required=false)
    public List<clsBeTrans_inv_tramo> items;
}