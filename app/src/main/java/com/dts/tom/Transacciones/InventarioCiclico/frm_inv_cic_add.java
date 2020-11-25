package com.dts.tom.Transacciones.InventarioCiclico;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.dts.tom.PBase;
import com.dts.tom.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class frm_inv_cic_add extends PBase {

    private ImageView imgDate;
    private DatePicker dpResult;
    DatePickerDialog picker;
    private EditText cmbVence_cic,edCalendario;
    private int year;
    private int month;
    private int day;
    static final int DATE_DIALOG_ID = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_inv_cic_add);
        super.InitBase();

        imgDate = (ImageView)findViewById(R.id.imgDate);
        dpResult = (DatePicker) findViewById(R.id.datePicker);
        cmbVence_cic = (EditText) findViewById(R.id.cmbVence_cic);

        setHandlers();

    }

    private void setHandlers() {
        try{

        }
        catch (Exception e){
            mu.msgbox(e.getClass()+" "+e.getMessage());
        }
    }

    public void ChangeDate(View view) {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        cmbVence_cic.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    public void Exit(View view) {
        frm_inv_cic_add.super.finish();
    }

    @Override
    public void onBackPressed() {

        try{

            frm_inv_cic_add.super.finish();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

}