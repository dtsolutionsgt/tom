package com.dts.tom.Transacciones.ProcesaImagen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.dts.classes.clsBeImagen;
import com.dts.ladapt.list_adapt_producto_imagen;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.ArrayList;

public class frm_imagenes extends PBase {
    public static ArrayList<Bitmap> Imgs = new ArrayList<>();
    private list_adapt_producto_imagen adapter;
    private ArrayList<clsBeImagen> ListImagen = new ArrayList<clsBeImagen>();

    private GridView gridFotos;
    private TextView lbldDetProducto, lblTituloForma;
    private clsBeImagen BeImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_imagenes);

        super.InitBase();

        gridFotos = findViewById(R.id.gridFotos);
        lbldDetProducto = findViewById(R.id.lbldDetProducto);
        lblTituloForma = findViewById(R.id.lblTituloForma);

        ListImagen.clear();
        ListImagen = gl.ListImagen;

        String texto = ListImagen.size() > 1 ? "Imágenes":"Imagen";
        lbldDetProducto.setText(ListImagen.get(0).Descripcion);
        lblTituloForma.setText(texto+ " ("+ListImagen.size()+")");

        LoadImagen();
        setHandles();
    }

    private void setHandles() {

        gridFotos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                gl.imagen = i;
                startActivity(new Intent(frm_imagenes.this  , frm_imagen_completa.class));
            }
        });
    }

    private void LoadImagen() {

        try {
            Imgs.clear();

            runOnUiThread(() -> {
                for (int i = 0; i < ListImagen.size(); i++) {

                    //#AT 20220322 Se convierte el String a byte
                    byte[] tmpImg = Base64.decode(ListImagen.get(i).Imagen, Base64.DEFAULT);

                    //#AT 20220322 Se obtienen la dimensión(alto,ancho) de las imagenes sin cargrse en memoria
                    BitmapFactory.Options opciones = new BitmapFactory.Options();
                    opciones.inJustDecodeBounds = true;
                    BitmapFactory.decodeByteArray(tmpImg, 0, tmpImg.length, opciones);

                    //#AT 20220322 Se crean las miniaturas de las imagenes en base a las
                    //dimensiones obtenidas arriba
                    opciones.inSampleSize = calculateInSampleSize(opciones, 200, 200);
                    opciones.inJustDecodeBounds = false;
                    Bitmap bmImagen = BitmapFactory.decodeByteArray(tmpImg, 0, tmpImg.length, opciones);
                    bmImagen.getByteCount();

                    ListImagen.get(i).bmImg = bmImagen;
                    Imgs.add(bmImagen);
                }
            });

            adapter = new list_adapt_producto_imagen(this, ListImagen);
            gridFotos.setAdapter(adapter);

        } catch (Exception e) {
            mu.msgbox("LoadImagen :"+e.getMessage());
        }

    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
