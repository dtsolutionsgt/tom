package com.dts.tom.Transacciones.ProcesaImagen;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import com.dts.ladapt.list_adapt_lista_imagen;
import com.dts.tom.PBase;
import com.dts.tom.R;
import static com.dts.tom.Transacciones.ProcesaImagen.frm_imagenes.Imgs;
import androidx.viewpager2.widget.ViewPager2;

public class frm_imagen_completa extends PBase {
    private ViewPager2 vistaPag;
    private ScaleGestureDetector detector;
    public list_adapt_lista_imagen adapterImgs;

    private float xBegin = 0;
    private float yBegin = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen_completa);

        super.InitBase();
        vistaPag = findViewById(R.id.vistaPag);
        /*xBegin = imgCompleta.getScaleX();
        yBegin = imgCompleta.getScaleY();*/
        //detector = new ScaleGestureDetector(this,new ImagenZoom(imgCompleta));
        CargarImagen();
    }

    private void CargarImagen() {
        try {

            adapterImgs = new list_adapt_lista_imagen(this, Imgs);
            vistaPag.setAdapter(adapterImgs);
            vistaPag.setCurrentItem(gl.imagen);

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

}
