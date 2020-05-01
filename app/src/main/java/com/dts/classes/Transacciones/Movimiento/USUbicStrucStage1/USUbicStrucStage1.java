package com.dts.classes.Transacciones.Movimiento.USUbicStrucStage1;

import com.dts.classes.Transacciones.Movimiento.USUbicSingle.USUbicSingleList;

import org.simpleframework.xml.Element;

import java.util.ArrayList;
import java.util.List;

public class USUbicStrucStage1 {

    @Element(required=false) public int Columna=0;
    @Element(required=false) public int Nivel=0;
    @Element(required=false) public USUbicSingleList lUbicacionesVacias=new USUbicSingleList();
    @Element(required=false) public USUbicSingleList lUbicacionesOcupadas = new USUbicSingleList();


    public USUbicStrucStage1() {
    }

    public USUbicStrucStage1(int Columna, int Nivel, USUbicSingleList lUbicacionesVacias, USUbicSingleList lUbicacionesOcupadas)
    {

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
    public USUbicSingleList getlUbicacionesOcupadas() {
        return lUbicacionesOcupadas;
    }
    public void setlUbicacionesOcupadas(USUbicSingleList value) {
        lUbicacionesOcupadas=value;
    }

}