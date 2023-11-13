package com.dts.classes.Transacciones.Picking.Trans_Picking_Img;
import org.simpleframework.xml.Element;

public class clsBeTrans_picking_img {

    @Element(required=false) public int IdImagen=0;
    @Element(required=false) public int IdPickingEnc=0;
    @Element(required=false) public int IdPickingDet=0;
    @Element(required=false) public int IdPedidoEnc=0;
    @Element(required=false) public int IdPedidoDet=0;
    @Element(required=false) public String Imagen="";
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="";
    @Element(required=false) public String Observacion="";


    public clsBeTrans_picking_img() {
    }

    public clsBeTrans_picking_img(int IdImagen,int IdPickingEnc,int IdPickingDet,int IdPedidoEnc,
                                  int IdPedidoDet,String Imagen,String User_agr,String Fec_agr,
                                  String Observacion) {

        this.IdImagen=IdImagen;
        this.IdPickingEnc=IdPickingEnc;
        this.IdPickingDet=IdPickingDet;
        this.IdPedidoEnc=IdPedidoEnc;
        this.IdPedidoDet=IdPedidoDet;
        this.Imagen=Imagen;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.Observacion=Observacion;

    }


    public int getIdImagen() {
        return IdImagen;
    }
    public void setIdImagen(int value) {
        IdImagen=value;
    }
    public int getIdPickingEnc() {
        return IdPickingEnc;
    }
    public void setIdPickingEnc(int value) {
        IdPickingEnc=value;
    }
    public int getIdPickingDet() {
        return IdPickingDet;
    }
    public void setIdPickingDet(int value) {
        IdPickingDet=value;
    }
    public int getIdPedidoEnc() {
        return IdPedidoEnc;
    }
    public void setIdPedidoEnc(int value) {
        IdPedidoEnc=value;
    }
    public int getIdPedidoDet() {
        return IdPedidoDet;
    }
    public void setIdPedidoDet(int value) {
        IdPedidoDet=value;
    }
    public String getImagen() {
        return Imagen;
    }
    public void setImagen(String value) {
        Imagen=value;
    }
    public String getUser_agr() {
        return User_agr;
    }
    public void setUser_agr(String value) {
        User_agr=value;
    }
    public String getFec_agr() {
        return Fec_agr;
    }
    public void setFec_agr(String value) {
        Fec_agr=value;
    }
    public String getObservacion() {
        return Observacion;
    }
    public void setObservacion(String value) {
        Observacion=value;
    }

}


