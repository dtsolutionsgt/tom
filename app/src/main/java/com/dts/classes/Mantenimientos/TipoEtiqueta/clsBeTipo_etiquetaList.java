package com.dts.classes.Mantenimientos.TipoEtiqueta;

import org.simpleframework.xml.ElementList;
import java.util.List;

public class clsBeTipo_etiquetaList {
    @ElementList(inline=true,required=false)
    public List<clsBeTipo_etiqueta> items;
}