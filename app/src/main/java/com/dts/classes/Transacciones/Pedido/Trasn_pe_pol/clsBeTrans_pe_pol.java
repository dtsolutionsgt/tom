package com.dts.classes.Transacciones.Pedido.Trasn_pe_pol;

import org.simpleframework.xml.Element;

public class clsBeTrans_pe_pol {

    @Element(required=false) public int IdOrdenPedidoPol=0;
    @Element(required=false) public int IdOrdenPedidoEnc=0;
    @Element(required=false) public String Bl_No="";
    @Element(required=false) public String NoPoliza="";
    @Element(required=false) public String Pto_Descarga="";
    @Element(required=false) public String Viaje_no="";
    @Element(required=false) public String Buque_no="";
    @Element(required=false) public String Remitente="";
    @Element(required=false) public String Fecha_abordaje="";
    @Element(required=false) public String Destino="";
    @Element(required=false) public String Dir_destino="";
    @Element(required=false) public String Descripcion="";
    @Element(required=false) public String Po_number="";
    @Element(required=false) public int Cantidad=0;
    @Element(required=false) public int Piezas=0;
    @Element(required=false) public double Total_kgs=0;
    @Element(required=false) public double Cbm=0;
    @Element(required=false) public String Dua="";
    @Element(required=false) public String Fecha_poliza="";
    @Element(required=false) public String Pais_procede="";
    @Element(required=false) public double Tipo_cambio=0;
    @Element(required=false) public double Total_valoraduana=0;
    @Element(required=false) public int Total_lineas=0;
    @Element(required=false) public int Total_bultos=0;
   // @Element(required=false) public double Total_bultos_Peso_Bruto=0;
   // @Element(required=false) public double Total_bultos_Peso_Neto=0;
   @Element(required=false) public double Total_bultos_Peso=0;
    @Element(required=false) public double Total_usd=0;
    @Element(required=false) public double Total_flete=0;
    @Element(required=false) public double Total_seguro=0;
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="";
    @Element(required=false) public String codigo_poliza="";
    @Element(required=false) public String ticket="";
    @Element(required=false) public String numero_orden="";
    @Element(required=false) public String fecha_aceptacion="";
    @Element(required=false) public String fecha_llegada="";
    @Element(required=false) public double total_otros=0;
    @Element(required=false) public String clave_aduana="";
    @Element(required=false) public String nit_imp_exp="";
    @Element(required=false) public String clase="";
    @Element(required=false) public String mod_transporte="";
    @Element(required=false) public double total_liquidar=0;
    @Element(required=false) public double total_general=0;
    @Element(required=false) public boolean IsNew=false;
    @Element(required=false) public int IdRegimen=0;


    public clsBeTrans_pe_pol() {
    }

    public clsBeTrans_pe_pol(int IdOrdenPedidoPol,int IdOrdenPedidoEnc,String Bl_No,String NoPoliza,
                             String Pto_Descarga,String Viaje_no,String Buque_no,String Remitente,
                             String Fecha_abordaje,String Destino,String Dir_destino,String Descripcion,
                             String Po_number,int Cantidad,int Piezas,double Total_kgs,
                             double Cbm,String Dua,String Fecha_poliza,String Pais_procede,
                             double Tipo_cambio,double Total_valoraduana,int Total_lineas,int Total_bultos,
                             double Total_bultos_Peso,double Total_usd,double Total_flete,
                             double Total_seguro,String User_agr,String Fec_agr,String User_mod,
                             String Fec_mod,String codigo_poliza,String ticket,String numero_orden,
                             String fecha_aceptacion,String fecha_llegada,double total_otros,String clave_aduana,
                             String nit_imp_exp,String clase,String mod_transporte,double total_liquidar,
                             double total_general,boolean IsNew,int IdRegimen) {

        this.IdOrdenPedidoPol=IdOrdenPedidoPol;
        this.IdOrdenPedidoEnc=IdOrdenPedidoEnc;
        this.Bl_No=Bl_No;
        this.NoPoliza=NoPoliza;
        this.Pto_Descarga=Pto_Descarga;
        this.Viaje_no=Viaje_no;
        this.Buque_no=Buque_no;
        this.Remitente=Remitente;
        this.Fecha_abordaje=Fecha_abordaje;
        this.Destino=Destino;
        this.Dir_destino=Dir_destino;
        this.Descripcion=Descripcion;
        this.Po_number=Po_number;
        this.Cantidad=Cantidad;
        this.Piezas=Piezas;
        this.Total_kgs=Total_kgs;
        this.Cbm=Cbm;
        this.Dua=Dua;
        this.Fecha_poliza=Fecha_poliza;
        this.Pais_procede=Pais_procede;
        this.Tipo_cambio=Tipo_cambio;
        this.Total_valoraduana=Total_valoraduana;
        this.Total_lineas=Total_lineas;
        this.Total_bultos=Total_bultos;
        //this.Total_bultos_Peso_Bruto=Total_bultos_Peso_Bruto;
       // this.Total_bultos_Peso_Neto=Total_bultos_Peso_Neto;
        this.Total_bultos_Peso=Total_bultos_Peso;
        this.Total_usd=Total_usd;
        this.Total_flete=Total_flete;
        this.Total_seguro=Total_seguro;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.codigo_poliza=codigo_poliza;
        this.ticket=ticket;
        this.numero_orden=numero_orden;
        this.fecha_aceptacion=fecha_aceptacion;
        this.fecha_llegada=fecha_llegada;
        this.total_otros=total_otros;
        this.clave_aduana=clave_aduana;
        this.nit_imp_exp=nit_imp_exp;
        this.clase=clase;
        this.mod_transporte=mod_transporte;
        this.total_liquidar=total_liquidar;
        this.total_general=total_general;
        this.IsNew=IsNew;
        this.IdRegimen=IdRegimen;

    }


    public int getIdOrdenPedidoPol() {
        return IdOrdenPedidoPol;
    }
    public void setIdOrdenPedidoPol(int value) {
        IdOrdenPedidoPol=value;
    }
    public int getIdOrdenPedidoEnc() {
        return IdOrdenPedidoEnc;
    }
    public void setIdOrdenPedidoEnc(int value) {
        IdOrdenPedidoEnc=value;
    }
    public String getBl_No() {
        return Bl_No;
    }
    public void setBl_No(String value) {
        Bl_No=value;
    }
    public String getNoPoliza() {
        return NoPoliza;
    }
    public void setNoPoliza(String value) {
        NoPoliza=value;
    }
    public String getPto_Descarga() {
        return Pto_Descarga;
    }
    public void setPto_Descarga(String value) {
        Pto_Descarga=value;
    }
    public String getViaje_no() {
        return Viaje_no;
    }
    public void setViaje_no(String value) {
        Viaje_no=value;
    }
    public String getBuque_no() {
        return Buque_no;
    }
    public void setBuque_no(String value) {
        Buque_no=value;
    }
    public String getRemitente() {
        return Remitente;
    }
    public void setRemitente(String value) {
        Remitente=value;
    }
    public String getFecha_abordaje() {
        return Fecha_abordaje;
    }
    public void setFecha_abordaje(String value) {
        Fecha_abordaje=value;
    }
    public String getDestino() {
        return Destino;
    }
    public void setDestino(String value) {
        Destino=value;
    }
    public String getDir_destino() {
        return Dir_destino;
    }
    public void setDir_destino(String value) {
        Dir_destino=value;
    }
    public String getDescripcion() {
        return Descripcion;
    }
    public void setDescripcion(String value) {
        Descripcion=value;
    }
    public String getPo_number() {
        return Po_number;
    }
    public void setPo_number(String value) {
        Po_number=value;
    }
    public int getCantidad() {
        return Cantidad;
    }
    public void setCantidad(int value) {
        Cantidad=value;
    }
    public int getPiezas() {
        return Piezas;
    }
    public void setPiezas(int value) {
        Piezas=value;
    }
    public double getTotal_kgs() {
        return Total_kgs;
    }
    public void setTotal_kgs(double value) {
        Total_kgs=value;
    }
    public double getCbm() {
        return Cbm;
    }
    public void setCbm(double value) {
        Cbm=value;
    }
    public String getDua() {
        return Dua;
    }
    public void setDua(String value) {
        Dua=value;
    }
    public String getFecha_poliza() {
        return Fecha_poliza;
    }
    public void setFecha_poliza(String value) {
        Fecha_poliza=value;
    }
    public String getPais_procede() {
        return Pais_procede;
    }
    public void setPais_procede(String value) {
        Pais_procede=value;
    }
    public double getTipo_cambio() {
        return Tipo_cambio;
    }
    public void setTipo_cambio(double value) {
        Tipo_cambio=value;
    }
    public double getTotal_valoraduana() {
        return Total_valoraduana;
    }
    public void setTotal_valoraduana(double value) {
        Total_valoraduana=value;
    }
    public int getTotal_lineas() {
        return Total_lineas;
    }
    public void setTotal_lineas(int value) {
        Total_lineas=value;
    }
    public int getTotal_bultos() {
        return Total_bultos;
    }
    public void setTotal_bultos(int value) {
        Total_bultos=value;
    }
   /* public double getTotal_bultos_Peso_Bruto() {
        return Total_bultos_Peso_Bruto;
    }
    public void setTotal_bultos_Peso_Bruto(double value) {
        Total_bultos_Peso_Bruto=value;
    }*/
  /*  public double getTotal_bultos_Peso_Neto() {
        return Total_bultos_Peso_Neto;
    }
    public void setTotal_bultos_Peso_Neto(double value) {
        Total_bultos_Peso_Neto=value;
    }*/
   public double getTotal_bultos_Peso() {
       return Total_bultos_Peso;
   }
    public void setTotal_bultos_Peso(double value) {
        Total_bultos_Peso=value;
    }
    public double getTotal_usd() {
        return Total_usd;
    }
    public void setTotal_usd(double value) {
        Total_usd=value;
    }
    public double getTotal_flete() {
        return Total_flete;
    }
    public void setTotal_flete(double value) {
        Total_flete=value;
    }
    public double getTotal_seguro() {
        return Total_seguro;
    }
    public void setTotal_seguro(double value) {
        Total_seguro=value;
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
    public String getUser_mod() {
        return User_mod;
    }
    public void setUser_mod(String value) {
        User_mod=value;
    }
    public String getFec_mod() {
        return Fec_mod;
    }
    public void setFec_mod(String value) {
        Fec_mod=value;
    }
    public String getcodigo_poliza() {
        return codigo_poliza;
    }
    public void setcodigo_poliza(String value) {
        codigo_poliza=value;
    }
    public String getticket() {
        return ticket;
    }
    public void setticket(String value) {
        ticket=value;
    }
    public String getnumero_orden() {
        return numero_orden;
    }
    public void setnumero_orden(String value) {
        numero_orden=value;
    }
    public String getfecha_aceptacion() {
        return fecha_aceptacion;
    }
    public void setfecha_aceptacion(String value) {
        fecha_aceptacion=value;
    }
    public String getfecha_llegada() {
        return fecha_llegada;
    }
    public void setfecha_llegada(String value) {
        fecha_llegada=value;
    }
    public double gettotal_otros() {
        return total_otros;
    }
    public void settotal_otros(double value) {
        total_otros=value;
    }
    public String getclave_aduana() {
        return clave_aduana;
    }
    public void setclave_aduana(String value) {
        clave_aduana=value;
    }
    public String getnit_imp_exp() {
        return nit_imp_exp;
    }
    public void setnit_imp_exp(String value) {
        nit_imp_exp=value;
    }
    public String getclase() {
        return clase;
    }
    public void setclase(String value) {
        clase=value;
    }
    public String getmod_transporte() {
        return mod_transporte;
    }
    public void setmod_transporte(String value) {
        mod_transporte=value;
    }
    public double gettotal_liquidar() {
        return total_liquidar;
    }
    public void settotal_liquidar(double value) {
        total_liquidar=value;
    }
    public double gettotal_general() {
        return total_general;
    }
    public void settotal_general(double value) {
        total_general=value;
    }
    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNew(boolean value) {
        IsNew=value;
    }
    public int getIdRegimen() {
        return IdRegimen;
    }
    public void setIdRegimen(int value) {
        IdRegimen=value;
    }

}

