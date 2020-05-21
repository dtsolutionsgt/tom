package com.dts.classes.Transacciones.Inventario.Inventario_Resumen;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_inv_resumen_gridList {
    @ElementList(inline=true,required=false)
    public List<clsBeTrans_inv_resumen_grid> items;
}