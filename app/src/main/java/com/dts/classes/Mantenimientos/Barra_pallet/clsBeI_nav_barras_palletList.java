package com.dts.classes.Mantenimientos.Barra_pallet;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeI_nav_barras_palletList {
    @ElementList(inline=true,required = false)
    public List<clsBeI_nav_barras_pallet> items;
}