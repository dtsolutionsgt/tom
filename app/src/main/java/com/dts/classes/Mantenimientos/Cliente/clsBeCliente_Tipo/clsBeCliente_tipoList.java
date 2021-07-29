package com.dts.classes.Mantenimientos.Cliente.clsBeCliente_Tipo;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeCliente_tipoList {
    @ElementList(inline=true,required=false)
    public List<clsBeCliente_tipo> items;
}

