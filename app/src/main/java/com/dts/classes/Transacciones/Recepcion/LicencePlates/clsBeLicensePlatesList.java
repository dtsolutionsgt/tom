package com.dts.classes.Transacciones.Recepcion.LicencePlates;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeLicensePlatesList {
    @ElementList(inline=true,required = false)
    public List<clsBeLicensePlates> items;
}

