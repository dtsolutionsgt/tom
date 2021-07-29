package com.dts.classes.Mantenimientos.Cliente.clsBeCliente;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeClienteList {
    @ElementList(inline=true,required=false)
    public List<clsBeCliente> items;
}
