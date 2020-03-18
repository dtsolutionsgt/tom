package com.dts.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;
import com.dts.tom.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppMethods {

	private Context cont;
	private appGlobals gl;


	public AppMethods(Context context,appGlobals global) {
		cont=context;
		gl=global;
	}

	//region Public
	


    //endregion


	//region Aux


    //endregion

    //region Private

    private void toast(String msg) {
		Toast toast= Toast.makeText(cont,msg, Toast.LENGTH_SHORT);  
		toast.setGravity(Gravity.TOP, 0, 0);
		toast.show();
	}

	private boolean emptystr(String s){
		if (s==null || s.isEmpty()) {
			return true;
		} else{
			return false;
		}
	}

    private void msgbox(String msg) {

		try{

			if (!emptystr(msg)){

				AlertDialog.Builder dialog = new AlertDialog.Builder(cont);

				dialog.setTitle(R.string.app_name);
				dialog.setMessage(msg);

				dialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						//Toast.makeText(getApplicationContext(), "Yes button pressed",Toast.LENGTH_SHORT).show();
					}
				});
				dialog.show();

			}

		}catch (Exception ex)
			{toast(ex.getMessage());}
	}

	public void readOnly(EditText txt){
		try{

			txt.setFocusable(false);
			txt.setFocusableInTouchMode(false);
			txt.setClickable(false);

		}catch (Exception ex){

		}
	}
    //endregion

	//region Public

	public String strFecha(String fecha){

		String fechaVence="";

		try{

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			Date date = dateFormat.parse(fecha);
			dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			fechaVence=dateFormat.format(date);

		}catch (Exception ex){
			toast(ex.getMessage());
		}
		return fechaVence;
	}
	//endregion
}
