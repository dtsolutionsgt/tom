package com.dts.classes.Transacciones.Inventario.TipoInventario;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTipoInventarioList {
    @ElementList(inline=true,required=false)
    public List<clsBeTipoInventario> items;
}