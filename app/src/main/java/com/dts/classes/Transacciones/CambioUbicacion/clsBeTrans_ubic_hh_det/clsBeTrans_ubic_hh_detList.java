package com.dts.classes.Transacciones.CambioUbicacion.clsBeTrans_ubic_hh_det;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_ubic_hh_detList {
    @ElementList(inline=true)
    public List<clsBeTrans_ubic_hh_det> items;
}
