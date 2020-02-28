package com.dts.classes.Mantenimientos.Arancel;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeArancelList {
    @ElementList(inline=true,required = false)
    public List<clsBeArancel> items;
}