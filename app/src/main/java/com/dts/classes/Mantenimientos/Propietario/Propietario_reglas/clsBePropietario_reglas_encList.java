package com.dts.classes.Mantenimientos.Propietario.Propietario_reglas;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBePropietario_reglas_encList {
    @ElementList(inline=true,required = false)
    public List<clsBePropietario_reglas_enc> items;
}