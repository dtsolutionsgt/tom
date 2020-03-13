package com.dts.classes.Transacciones.CambioUbicacion.clsBeTrans_ubic_hh_enc;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_ubic_hh_encList {
    @ElementList(inline=true, required = false)
    public List<clsBeTrans_ubic_hh_enc> items;
}

