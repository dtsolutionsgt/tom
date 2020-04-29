package com.dts.classes.Transacciones.Movimiento.USUbicStrucStage1;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsUbicList {
    @ElementList(inline=true,required=false)
    public List<clsUbic> items;
}
