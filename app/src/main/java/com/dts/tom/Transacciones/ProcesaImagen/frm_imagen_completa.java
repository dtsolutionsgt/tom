package com.dts.tom.Transacciones.ProcesaImagen;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import com.dts.tom.PBase;
import com.dts.tom.R;

public class frm_imagen_completa extends PBase {
    private ImageView imgCompleta;
    private ScaleGestureDetector detector;

    private float xBegin = 0;
    private float yBegin = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen_completa);

        super.InitBase();
        imgCompleta = (ImageView) findViewById(R.id.imgCompleta);

        xBegin = imgCompleta.getScaleX();
        yBegin = imgCompleta.getScaleY();
        //imgCompleta.setRotation((float) 90.0);
        imgCompleta.setImageBitmap(gl.imagen);

        detector = new ScaleGestureDetector(this,new ImagenZoom(imgCompleta));

    }

    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

}
