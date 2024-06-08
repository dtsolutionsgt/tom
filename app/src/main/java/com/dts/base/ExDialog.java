package com.dts.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.dts.tom.R;

public class ExDialog extends  AlertDialog.Builder {

    public ExDialog(Context context) {
        super(context);

        Activity activity=(Activity) context;
        View titleView = activity.getLayoutInflater().inflate(R.layout.dialogstyle, null);
        setCustomTitle(titleView);

    }

    @Override
    public AlertDialog show() {
        Button btnPos=null,btnNeg = null,btnNeut=null;

        AlertDialog adg=super.show();

        TextView textView = adg.getWindow().findViewById(android.R.id.message);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(24);
        textView.setTypeface(Typeface.DEFAULT,Typeface.NORMAL);
        textView.setGravity(Gravity.CENTER);

        int btntextsize=18;
        int btnbackcolor=Color.parseColor("#F27C29");

        try {
            btnPos=adg.getButton(DialogInterface.BUTTON_POSITIVE);
            btnPos.setTextSize(btntextsize);
            btnPos.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
            btnPos.setBackgroundColor(btnbackcolor);
            btnPos.setTextColor(Color.BLACK);
        } catch (Exception e) {}

        try {
            btnNeg=adg.getButton(DialogInterface.BUTTON_NEGATIVE);
            btnNeg.setTextSize(btntextsize);
            btnNeg.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
            btnNeg.setBackgroundColor(btnbackcolor);
            btnNeg.setTextColor(Color.BLACK);
        } catch (Exception e) {}

        try {
            btnNeut=adg.getButton(DialogInterface.BUTTON_NEUTRAL);
            btnNeut.setTextSize(btntextsize);
            btnNeut.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
            btnNeut.setBackgroundColor(btnbackcolor);
            btnNeut.setTextColor(Color.BLACK);
        } catch (Exception e) {}

        if (btnNeg != null) {
            ViewGroup buttonPanel = (ViewGroup) btnNeg.getParent();
            buttonPanel.removeView(btnNeg); // Elimina el botón negativo de su posición actual
            buttonPanel.addView(btnNeg, 0); // Añade el botón negativo al principio del panel
        }

        return adg;
    }

}
