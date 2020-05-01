package com.dts.classes.Transacciones.Movimiento.USUbicStrucStage5;

import com.dts.classes.Transacciones.Movimiento.USUbicStrucStage1.USUbicStrucStage1;

import org.simpleframework.xml.Element;

public class USUbicStrucStage5 extends USUbicStrucStage1 {

    @Element(required=false) public int IdTramo=0;
    @Element(required=false) public double AvgPrediction=0;

    public USUbicStrucStage5()
    {
        //Constructor....
    }

    public USUbicStrucStage5 (int IdTramo,double AvgPrediction)
    {
        this.IdTramo=IdTramo;
        this.AvgPrediction=AvgPrediction;
    }

    public int getIdTramo()
    {
        return IdTramo;
    }

    public void setIdTramo(int value)
    {
        IdTramo=value;
    }

    public double getAvgPrediction()
    {
        return AvgPrediction;
    }

    public void setAvgPrediction(double value)
    {
        AvgPrediction=value;
    }

}

