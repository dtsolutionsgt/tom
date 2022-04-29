package com.dts.tom.Transacciones.ProcesaImagen;

import android.view.ScaleGestureDetector;
import android.widget.ImageView;

public class ImagenZoom extends ScaleGestureDetector.SimpleOnScaleGestureListener {
    ImageView imageView;
    private float scale = 1f;

    public ImagenZoom(ImageView imageView) {
        this.imageView = imageView;
    }

    public boolean onScale(ScaleGestureDetector detector) {
        scale *= detector.getScaleFactor();
        imageView.setScaleX(scale);
        imageView.setScaleY(scale);
        return  true;


    }

}
