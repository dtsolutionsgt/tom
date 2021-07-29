package com.dts.classes.Transacciones.Inventario.Inventario_Detalle;


import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_inv_detalleList {
    @ElementList(inline=true,required=false)
    public List<clsBeTrans_inv_detalle> items;
}
