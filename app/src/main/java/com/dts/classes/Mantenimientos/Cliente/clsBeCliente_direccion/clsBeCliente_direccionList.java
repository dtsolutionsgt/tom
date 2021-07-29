package com.dts.classes.Mantenimientos.Cliente.clsBeCliente_direccion;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeCliente_direccionList {
    @ElementList(inline=true,required=false)
    public List<clsBeCliente_direccion> items;
}
