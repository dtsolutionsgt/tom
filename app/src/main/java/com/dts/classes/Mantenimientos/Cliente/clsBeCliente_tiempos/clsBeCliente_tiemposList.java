package com.dts.classes.Mantenimientos.Cliente.clsBeCliente_tiempos;

import com.dts.classes.Mantenimientos.Cliente.clsBeCliente_tiempos.clsBeCliente_tiempos;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeCliente_tiemposList {
    @ElementList(inline=true,required=false)
    public List<clsBeCliente_tiempos> items;
}
