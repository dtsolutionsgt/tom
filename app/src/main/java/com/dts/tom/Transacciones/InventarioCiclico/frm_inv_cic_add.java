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


    public void setCurrentDateOnView() {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH)+1;
        day = c.get(Calendar.DAY_OF_MONTH);


        /*cmbVence_cic.setText(new StringBuilder()
                .append(day).append("-").append(month).append("-")
                .append(year).append(" "));
        dpResult.init(year, month, day, null);*/


        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy");
        Date d = new Date(year, month, day);
        String strDate = dateFormatter.format(d);
        cmbVence_cic.setText(strDate);

    }


    public void Exit(View view) {
    }

    public void msgboxValidaFechaVence(String msg) {

        try{

            if (!(msg.isEmpty())){

                AlertDialog.Builder dialog = new AlertDialog.Builder(this);

                dialog.setTitle(R.string.app_name);
                dialog.setMessage(msg);
                dialog.setCancelable(false);

                dialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        cmbVence_cic.requestFocus();
                        cmbVence_cic.selectAll();
                        cmbVence_cic.setSelectAllOnFocus(true);
                    }
                });
                dialog.show();

            }

        } catch (Exception ex) {
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
}