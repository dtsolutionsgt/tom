package com.dts.classes.Mantenimientos.Color;
import org.simpleframework.xml.ElementList;
import java.util.List;

public class clsBeColorList {
    @ElementList(inline=true,required=false)
    public List<clsBeColor> items;
}
