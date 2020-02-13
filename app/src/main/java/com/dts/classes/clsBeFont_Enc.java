package com.dts.classes;


import org.simpleframework.xml.Element;

public class clsBeFont_Enc {

    @Element(required=false) public int IdFontEnc;
    @Element(required=false) public String Nombre;


    public clsBeFont_Enc() {
    }

    public clsBeFont_Enc(int IdFontEnc, String Nombre) {

        this.IdFontEnc=IdFontEnc;
        this.Nombre=Nombre;

    }


    public int getIdFontEnc() {
        return IdFontEnc;
    }
    public void setIdFontEnc(int value) {
        IdFontEnc=value;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String value) {
        Nombre=value;
    }

}

