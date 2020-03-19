package com.dts.classes.Transacciones.Movimiento.clsBeVW_Movimientos;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeVW_MovimientosList {
    @ElementList(inline=true, required = false)
    public List<clsBeVW_Movimientos> items;
}