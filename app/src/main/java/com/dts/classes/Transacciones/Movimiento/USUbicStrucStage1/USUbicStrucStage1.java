package com.dts.classes.Transacciones.Movimiento.USUbicStrucStage1;


import com.dts.classes.Transacciones.Movimiento.USUbicSingle.USUbicSingleList;

import org.simpleframework.xml.Element;

public class USUbicStrucStage1 {

    @Element(required=false) public int Columna=0;
    @Element(required=false) public int Nivel=0;
    @Element(required=false) public USUbicSingleList lUbicacionesVacias=new USUbicSingleList();
    @Element(required=false) public clsUbicList lUbicacionesOcupadas=new clsUbicList();


    public USUbicStrucStage1() {
    }

    public USUbicStrucStage1(int Columna, int Nivel, USUbicSingleList lUbicacionesVacias, clsUbicList lUbicacionesOcupadas
    ) {

        this.Columna=Columna;
        this.Nivel=Nivel;
        this.lUbicacionesVacias=lUbicacionesVacias;
        this.lUbicacionesOcupadas=lUbicacionesOcupadas;

    }


    public int getColumna() {
        return Columna;
    }
    public void setColumna(int value) {
        Columna=value;
    }
    public int getNivel() {
        return Nivel;
    }
    public void setNivel(int value) {
        Nivel=value;
    }
    public USUbicSingleList getlUbicacionesVacias() {
        return lUbicacionesVacias;
    }
    public void setlUbicacionesVacias(USUbicSingleList value) {
        lUbicacionesVacias=value;
    }
    public clsUbicList getlUbicacionesOcupadas() {
        return lUbicacionesOcupadas;
    }
    public void setlUbicacionesOcupadas(clsUbicList value) {
        lUbicacionesOcupadas=value;
    }

}