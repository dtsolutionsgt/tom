package com.dts.classes.Mantenimientos.Proveedor.Proveedor_tiempos;

import com.dts.classes.Mantenimientos.Cliente.clsBeCliente_tiempos.clsBeCliente_tiempos;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeProveedor_tiemposList {
    @ElementList(inline=true,required=false)
    public List<clsBeProveedor_tiempos> items;
}
