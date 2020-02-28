package com.dts.classes.Mantenimientos.Empresa;


import org.simpleframework.xml.Element;

public class clsBeFont_det {

    @Element(required=false) public int IdFontDet;
    @Element(required=false) public int IdFontEnc;
    @Element(required=false) public String Letra;
    @Element(required=false) public double Tamaño;
    @Element(required=false) public boolean Negrita;
    @Element(required=false) public boolean Cursiva;
    @Element(required=false) public boolean Subrayado;
    @Element(required=false) public String ColorFont;
    @Element(required=false) public String ColorFondo;


    public clsBeFont_det() {
    }

    public clsBeFont_det(int IdFontDet,int IdFontEnc,String Letra,double Tamaño,
                         boolean Negrita,boolean Cursiva,boolean Subrayado,String ColorFont,
                         String ColorFondo) {

        this.IdFontDet=IdFontDet;
        this.IdFontEnc=IdFontEnc;
        this.Letra=Letra;
        this.Tamaño=Tamaño;
        this.Negrita=Negrita;
        this.Cursiva=Cursiva;
        this.Subrayado=Subrayado;
        this.ColorFont=ColorFont;
        this.ColorFondo=ColorFondo;

    }


    public int getIdFontDet() {
        return IdFontDet;
    }
    public void setIdFontDet(int value) {
        IdFontDet=value;
    }
    public int getIdFontEnc() {
        return IdFontEnc;
    }
    public void setIdFontEnc(int value) {
        IdFontEnc=value;
    }
    public String getLetra() {
        return Letra;
    }
    public void setLetra(String value) {
        Letra=value;
    }
    public double getTamaño() {
        return Tamaño;
    }
    public void setTamaño(double value) {
        Tamaño=value;
    }
    public boolean getNegrita() {
        return Negrita;
    }
    public void setNegrita(boolean value) {
        Negrita=value;
    }
    public boolean getCursiva() {
        return Cursiva;
    }
    public void setCursiva(boolean value) {
        Cursiva=value;
    }
    public boolean getSubrayado() {
        return Subrayado;
    }
    public void setSubrayado(boolean value) {
        Subrayado=value;
    }
    public String getColorFont() {
        return ColorFont;
    }
    public void setColorFont(String value) {
        ColorFont=value;
    }
    public String getColorFondo() {
        return ColorFondo;
    }
    public void setColorFondo(String value) {
        ColorFondo=value;
    }

}

