package com.dts.classes.Mantenimientos.Producto.Indice_Rotacion;

import org.simpleframework.xml.Element;

public class clsBeIndice_rotacion {

    @Element(required=false) public int IdIndiceRotacion=0;
    @Element(required=false) public String Descripcion="";
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public int IndicePrioridad=0;
    @Element(required=false) public int Grupo=0;

}
