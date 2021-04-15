package com.dts.classes.Mantenimientos.Version;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeVersion_wms_hh_andList {
    @ElementList(inline=true,required=false)
    public List<clsBeVersion_wms_hh_and> items;
}