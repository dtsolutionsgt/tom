package com.dts.classes.Mantenimientos.Configuracion_barra_pallet;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeConfiguracion_barra_palletList {
    @ElementList(inline=true)
    public List<clsBeConfiguracion_barra_pallet> items;
}