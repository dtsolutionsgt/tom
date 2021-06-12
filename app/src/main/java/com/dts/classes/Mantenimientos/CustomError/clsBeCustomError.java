package com.dts.classes.Mantenimientos.CustomError;


import org.simpleframework.xml.Element;

public class clsBeCustomError {

    @Element(required=false) public String Error;
    @Element(required=false) public String CustomError;

    public clsBeCustomError() {
    }

    public clsBeCustomError(String Error) {

        this.Error=Error;
    }

    public String getError() {
        return Error;
    }
    public void setError(String value) {
        Error=value;
    }

    public String getCustomError() {
        return CustomError;
    }
    public void setCustomError(String value) {
        CustomError=value;
    }

}

