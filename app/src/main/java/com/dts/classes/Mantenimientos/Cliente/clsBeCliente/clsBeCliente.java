package com.dts.classes.Mantenimientos.Cliente.clsBeCliente;

import com.dts.classes.Mantenimientos.Cliente.clsBeCliente_Tipo.clsBeCliente_tipo;
import com.dts.classes.Mantenimientos.Cliente.clsBeCliente_direccion.clsBeCliente_direccionList;
import com.dts.classes.Mantenimientos.Cliente.clsBeCliente_tiempos.clsBeCliente_tiemposList;
import com.dts.classes.Mantenimientos.Empresa.clsBeEmpresa;
import com.dts.classes.Mantenimientos.Propietario.Propietario.clsBePropietarios;

import org.simpleframework.xml.Element;

public class clsBeCliente {

    @Element(required=false) public int IdCliente=0;
    @Element(required=false) public int IdEmpresa=0;
    @Element(required=false) public int IdPropietario=0;
    @Element(required=false) public int IdTipoCliente=0;
    @Element(required=false) public int IdUbicacionManufactura=0;
    @Element(required=false) public String Codigo="";
    @Element(required=false) public String Nombre_comercial="";
    @Element(required=false) public String Nombre_contacto="";
    @Element(required=false) public String Telefono="";
    @Element(required=false) public String Nit="";
    @Element(required=false) public String Direccion="";
    @Element(required=false) public String Correo_electronico="";
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public boolean Realiza_manufactura=false;
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="";
    @Element(required=false) public boolean Despachar_lotes_completos=false;
    @Element(required=false) public boolean Sistema=false;
    @Element(required=false) public boolean Es_bodega_recepcion=false;
    @Element(required=false) public boolean Es_Bodega_Traslado=false;
    @Element(required=false) public int IdUbicacionVirtual=0;
    @Element(required=false) public boolean Control_Ultimo_Lote=false;
    @Element(required=false) public String Referencia="";
    @Element(required=false) public clsBeCliente_tipo Tipo=new clsBeCliente_tipo();
    @Element(required=false) public clsBeCliente_direccionList Drecciones=new clsBeCliente_direccionList();
    @Element(required=false) public clsBeCliente_tiemposList Tiempos=new clsBeCliente_tiemposList();
    @Element(required=false) public clsBeEmpresa Empresa=new clsBeEmpresa();
    @Element(required=false) public clsBePropietarios Propietario=new clsBePropietarios();
    @Element(required=false) public clsBeCliente_tipo ClienteTipo=new clsBeCliente_tipo();

    //#EJC20220314: BYB, Certificado_Calidad:
    @Element(required=false) public boolean Control_Calidad=false;
    @Element(required=false) public int IdUbicacionAbastecerCon = 0;
    @Element(required=false) public int IdBodegaAreaSAP = 0;
    @Element(required=false) public boolean Es_Proveedor = false;

    public clsBeCliente() {
    }

    public clsBeCliente(int IdCliente,int IdEmpresa,int IdPropietario,int IdTipoCliente,
                        int IdUbicacionManufactura,String Codigo,String Nombre_comercial,String Nombre_contacto,
                        String Telefono,String Nit,String Direccion,String Correo_electronico,
                        boolean Activo,boolean Realiza_manufactura,String User_agr,String Fec_agr,
                        String User_mod,String Fec_mod,boolean Despachar_lotes_completos,boolean Sistema,
                        boolean Es_bodega_recepcion,boolean Es_Bodega_Traslado,int IdUbicacionVirtual,boolean Control_Ultimo_Lote,
                        String Referencia,clsBeCliente_tipo Tipo,clsBeCliente_direccionList Drecciones,clsBeCliente_tiemposList Tiempos,
                        clsBeEmpresa Empresa,clsBePropietarios Propietario,clsBeCliente_tipo ClienteTipo,
                        int IdUbicacionAbastecerConz, int IdBodegaAreaSAP, boolean Es_Proveedor) {

        this.IdCliente=IdCliente;
        this.IdEmpresa=IdEmpresa;
        this.IdPropietario=IdPropietario;
        this.IdTipoCliente=IdTipoCliente;
        this.IdUbicacionManufactura=IdUbicacionManufactura;
        this.Codigo=Codigo;
        this.Nombre_comercial=Nombre_comercial;
        this.Nombre_contacto=Nombre_contacto;
        this.Telefono=Telefono;
        this.Nit=Nit;
        this.Direccion=Direccion;
        this.Correo_electronico=Correo_electronico;
        this.Activo=Activo;
        this.Realiza_manufactura=Realiza_manufactura;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Despachar_lotes_completos=Despachar_lotes_completos;
        this.Sistema=Sistema;
        this.Es_bodega_recepcion=Es_bodega_recepcion;
        this.Es_Bodega_Traslado=Es_Bodega_Traslado;
        this.IdUbicacionVirtual=IdUbicacionVirtual;
        this.Control_Ultimo_Lote=Control_Ultimo_Lote;
        this.Referencia=Referencia;
        this.Tipo=Tipo;
        this.Drecciones=Drecciones;
        this.Tiempos=Tiempos;
        this.Empresa=Empresa;
        this.Propietario=Propietario;
        this.ClienteTipo=ClienteTipo;
        this.IdUbicacionAbastecerCon = IdUbicacionAbastecerCon;
        this.IdBodegaAreaSAP = IdBodegaAreaSAP;
        this.Es_Proveedor = Es_Proveedor;
    }


    public int getIdCliente() {
        return IdCliente;
    }
    public void setIdCliente(int value) {
        IdCliente=value;
    }
    public int getIdEmpresa() {
        return IdEmpresa;
    }
    public void setIdEmpresa(int value) {
        IdEmpresa=value;
    }
    public int getIdPropietario() {
        return IdPropietario;
    }
    public void setIdPropietario(int value) {
        IdPropietario=value;
    }
    public int getIdTipoCliente() {
        return IdTipoCliente;
    }
    public void setIdTipoCliente(int value) {
        IdTipoCliente=value;
    }
    public int getIdUbicacionManufactura() {
        return IdUbicacionManufactura;
    }
    public void setIdUbicacionManufactura(int value) {
        IdUbicacionManufactura=value;
    }
    public String getCodigo() {
        return Codigo;
    }
    public void setCodigo(String value) {
        Codigo=value;
    }
    public String getNombre_comercial() {
        return Nombre_comercial;
    }
    public void setNombre_comercial(String value) {
        Nombre_comercial=value;
    }
    public String getNombre_contacto() {
        return Nombre_contacto;
    }
    public void setNombre_contacto(String value) {
        Nombre_contacto=value;
    }
    public String getTelefono() {
        return Telefono;
    }
    public void setTelefono(String value) {
        Telefono=value;
    }
    public String getNit() {
        return Nit;
    }
    public void setNit(String value) {
        Nit=value;
    }
    public String getDireccion() {
        return Direccion;
    }
    public void setDireccion(String value) {
        Direccion=value;
    }
    public String getCorreo_electronico() {
        return Correo_electronico;
    }
    public void setCorreo_electronico(String value) {
        Correo_electronico=value;
    }
    public boolean getActivo() {
        return Activo;
    }
    public void setActivo(boolean value) {
        Activo=value;
    }
    public boolean getRealiza_manufactura() {
        return Realiza_manufactura;
    }
    public void setRealiza_manufactura(boolean value) {
        Realiza_manufactura=value;
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
    public boolean getDespachar_lotes_completos() {
        return Despachar_lotes_completos;
    }
    public void setDespachar_lotes_completos(boolean value) {
        Despachar_lotes_completos=value;
    }
    public boolean getSistema() {
        return Sistema;
    }
    public void setSistema(boolean value) {
        Sistema=value;
    }
    public boolean getEs_bodega_recepcion() {
        return Es_bodega_recepcion;
    }
    public void setEs_bodega_recepcion(boolean value) {
        Es_bodega_recepcion=value;
    }
    public boolean getEs_Bodega_Traslado() {
        return Es_Bodega_Traslado;
    }
    public void setEs_Bodega_Traslado(boolean value) {
        Es_Bodega_Traslado=value;
    }
    public int getIdUbicacionVirtual() {
        return IdUbicacionVirtual;
    }
    public void setIdUbicacionVirtual(int value) {
        IdUbicacionVirtual=value;
    }
    public boolean getControl_Ultimo_Lote() {
        return Control_Ultimo_Lote;
    }
    public void setControl_Ultimo_Lote(boolean value) {
        Control_Ultimo_Lote=value;
    }
    public String getReferencia() {
        return Referencia;
    }
    public void setReferencia(String value) {
        Referencia=value;
    }
    public clsBeCliente_tipo getTipo() {
        return Tipo;
    }
    public void setTipo(clsBeCliente_tipo value) {
        Tipo=value;
    }
    public clsBeCliente_direccionList getDrecciones() {
        return Drecciones;
    }
    public void setDrecciones(clsBeCliente_direccionList value) {
        Drecciones=value;
    }
    public clsBeCliente_tiemposList getTiempos() {
        return Tiempos;
    }
    public void setTiempos(clsBeCliente_tiemposList value) {
        Tiempos=value;
    }
    public clsBeEmpresa getEmpresa() {
        return Empresa;
    }
    public void setEmpresa(clsBeEmpresa value) {
        Empresa=value;
    }
    public clsBePropietarios getPropietario() {
        return Propietario;
    }
    public void setPropietario(clsBePropietarios value) {
        Propietario=value;
    }
    public clsBeCliente_tipo getClienteTipo() {
        return ClienteTipo;
    }
    public void setClienteTipo(clsBeCliente_tipo value) {
        ClienteTipo=value;
    }
    public boolean getControl_Calidad() {
        return Control_Calidad;
    }
    public void setControl_Calidad(boolean value) {
        Control_Calidad=value;
    }

    public int getIdUbicacionAbastecerCon() {
        return IdUbicacionAbastecerCon;
    }
    public void setIdUbicacionAbastecerCon(int value) {
        IdUbicacionAbastecerCon=value;
    }

    public int getIdBodegaAreaSAP() {
        return IdBodegaAreaSAP;
    }
    public void setIdBodegaAreaSAP(int value) {
        IdBodegaAreaSAP=value;
    }

    public boolean getEs_Proveedor() {
        return Es_Proveedor;
    }
    public void setEs_Proveedor(boolean value) {
        Es_Proveedor=value;
    }

}