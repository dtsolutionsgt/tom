package com.dts.classes.Mantenimientos.Propietario.Propietario;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBePropietariosList {
    @ElementList(inline=true,required = false)
    public List<clsBePropietarios> items;
}
