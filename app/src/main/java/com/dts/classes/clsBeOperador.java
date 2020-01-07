package com.dts.classes;

import java.util.Hashtable;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class clsBeOperador implements KvmSerializable {

    public int  idoperador;
    public int  idempresa;
    public int  idroloperador;
    public int  idjornada;
    public String nombres;
    public String apellidos;
    public String direccion;
    public String telefono;
    public String codigo;
    public String clave;
    public int  activo;
    public String user_agr;
    public int  fec_agr;
    public String user_mod;
    public int  fec_mod;
    public int costo_hora;
    public int  usa_hh;

    public clsBeOperador() {}

    public clsBeOperador(
            int  _idoperador,
            int  _idempresa,
            int  _idroloperador,
            int  _idjornada,
            String _nombres,
            String _apellidos,
            String _direccion,
            String _telefono,
            String _codigo,
            String _clave,
            int  _activo,
            String _user_agr,
            int  _fec_agr,
            String _user_mod,
            int  _fec_mod,
            int _costo_hora,
            int  _usa_hh) {

        idoperador=_idoperador;
        idempresa=_idempresa;
        idroloperador=_idroloperador;
        idjornada=_idjornada;
        nombres=_nombres;
        apellidos=_apellidos;
        direccion=_direccion;
        telefono=_telefono;
        codigo=_codigo;
        clave=_clave;
        activo =_activo;
        user_agr=_user_agr;
        fec_agr=_fec_agr;
        user_mod=_user_mod;
        fec_mod=_fec_mod;
        costo_hora=_costo_hora;
        usa_hh=_usa_hh;

    }


    public int getPropertyCount() {
        return 17;
    }

    public Object getProperty(int arg0) {

        switch(arg0)    {
            case 0:
                return idoperador;
            case 1:
                return idempresa;
            case 2:
                return idroloperador;
            case 3:
                return idjornada;
            case 4:
                return nombres;
            case 5:
                return apellidos;
            case 6:
                return direccion;
            case 7:
                return telefono;
            case 8:
                return codigo;
            case 9:
                return clave;
            case 10:
                return activo;
            case 11:
                return user_agr;
            case 12:
                return fec_agr;
            case 13:
                return user_mod;
            case 14:
                return fec_mod;
            case 15:
                return costo_hora;
            case 16:
                return usa_hh;
        }

        return null;
    }

    public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
        switch(index)   {

            case 0:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "idoperador";break;
            case 1:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "idempresa";break;
            case 2:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "idroloperador";break;
            case 3:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "idjornada";break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "nombres";break;
            case 5:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "apellidos";break;
            case 6:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "direccion";break;
            case 7:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "telefono";break;
            case 8:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "codigo";break;
            case 9:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "clave";break;
            case 10:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "activo";break;
            case 11:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "user_agr";break;
            case 12:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "fec_agr";break;
            case 13:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "user_mod";break;
            case 14:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "fec_mod";break;
            case 15:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "costo_hora";break;
            case 16:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "usa_hh";break;
        }
    }

    public void setProperty(int index, Object value) {

        switch(index)   {

            case 0:
                idoperador = Integer.parseInt(value.toString());break;
            case 1:
                idempresa = Integer.parseInt(value.toString());break;
            case 2:
                idroloperador = Integer.parseInt(value.toString());break;
            case 3:
                idjornada = Integer.parseInt(value.toString());break;
            case 4:
                nombres = value.toString();break;
            case 5:
                apellidos = value.toString();break;
            case 6:
                direccion = value.toString();break;
            case 7:
                telefono = value.toString();break;
            case 8:
               //codigo = value.toString();break;
                codigo ="";break;
            case 9:
                clave = value.toString();break;
            case 10:
                activo = Integer.parseInt(value.toString());break;
            case 11:
                user_agr = value.toString();break;
            case 12:
                fec_agr = Integer.parseInt(value.toString());break;
            case 13:
                user_mod = value.toString();break;
            case 14:
                fec_mod = Integer.parseInt(value.toString());break;
            case 15:
                costo_hora = 0;break;
            case 16:
                usa_hh = Integer.parseInt(value.toString());break;
        }

    }
}

