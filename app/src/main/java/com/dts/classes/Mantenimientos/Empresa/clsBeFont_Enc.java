package com.dts.classes.Mantenimientos.Empresa;


import org.simpleframework.xml.Element;

public class clsBeFont_Enc {

    @Element(required=false) public int IdFontEnc;
    @Element(required=false) public String Nombre;
    @Element(required=false) public clsBeFont_detList lDet= new clsBeFont_detList();
    @Element(required=false) public boolean IsNew;

    public clsBeFont_Enc() {
    }

    public clsBeFont_Enc(int IdFontEnc, String Nombre,clsBeFont_detList lDet,boolean IsNew) {

        this.IdFontEnc=IdFontEnc;
        this.Nombre=Nombre;
        this.lDet = lDet;
        this.IsNew=IsNew;

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
    public clsBeFont_detList getlDet() {
        return lDet;
    }
    public void setlDet(clsBeFont_detList value) {
        lDet=value;
    }
    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNew(boolean value) {
        IsNew=value;
    }


}

