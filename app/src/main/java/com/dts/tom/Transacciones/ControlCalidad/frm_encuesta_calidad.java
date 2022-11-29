package com.dts.tom.Transacciones.ControlCalidad;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.RadioButton;

import com.dts.classes.Transacciones.ControlCalidad.clsBeEncuesta;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.ArrayList;
import java.util.List;

import static com.dts.tom.Transacciones.ControlCalidad.ControlCalidad.AuxItem;
import static br.com.zbra.androidlinq.Linq.stream;

public class frm_encuesta_calidad extends PBase {

    private clsBeEncuesta PreguntaRes = new clsBeEncuesta();
    private ArrayList<clsBeEncuesta> ListPregunta = new ArrayList<clsBeEncuesta>();
    private boolean respuesta = false, cambio = false;
    private int TipoLlanta;

    private CheckBox chk1, chk2, chk3, chk4;
    private RadioButton chkFrontal, chkTrasera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_encuesta_calidad);

        super.InitBase();

        chk1 = findViewById(R.id.chk1);
        chk2 = findViewById(R.id.chk2);
        chk3 = findViewById(R.id.chk3);
        chk4 = findViewById(R.id.chk4);
        chkFrontal = findViewById(R.id.chkFrontal);
        chkTrasera = findViewById(R.id.chkTrasera);

        setPreguntas();
        setHandlers();
    }

    private void setPreguntas(){

        ListPregunta = new ArrayList<clsBeEncuesta>();

        PreguntaRes = new clsBeEncuesta();
        PreguntaRes.IdPregunta = 1;
        PreguntaRes.Pregunta = "Profundidad del dibujo";
        PreguntaRes.Respuesta = false;
        PreguntaRes.Tipo = 1;
        ListPregunta.add(PreguntaRes);

        PreguntaRes = new clsBeEncuesta();
        PreguntaRes.IdPregunta = 2;
        PreguntaRes.Pregunta = "Asienta parejo";
        PreguntaRes.Respuesta = false;
        PreguntaRes.Tipo = 1;
        ListPregunta.add(PreguntaRes);


        PreguntaRes = new clsBeEncuesta();
        PreguntaRes.IdPregunta = 3;
        PreguntaRes.Pregunta = "Protuberancias";
        PreguntaRes.Respuesta = false;
        PreguntaRes.Tipo = 1;
        ListPregunta.add(PreguntaRes);

        PreguntaRes = new clsBeEncuesta();
        PreguntaRes.IdPregunta = 4;
        PreguntaRes.Pregunta = "Objetos incrustados";
        PreguntaRes.Respuesta = false;
        PreguntaRes.Tipo = 1;
        ListPregunta.add(PreguntaRes);

        PreguntaRes = new clsBeEncuesta();
        PreguntaRes.IdPregunta = 1;
        PreguntaRes.Pregunta = "Profundidad del dibujo";
        PreguntaRes.Respuesta = false;
        PreguntaRes.Tipo = 2;
        ListPregunta.add(PreguntaRes);

        PreguntaRes = new clsBeEncuesta();
        PreguntaRes.IdPregunta = 2;
        PreguntaRes.Pregunta = "Asienta parejo";
        PreguntaRes.Respuesta = false;
        PreguntaRes.Tipo = 2;
        ListPregunta.add(PreguntaRes);


        PreguntaRes = new clsBeEncuesta();
        PreguntaRes.IdPregunta = 3;
        PreguntaRes.Pregunta = "Protuberancias";
        PreguntaRes.Respuesta = false;
        PreguntaRes.Tipo = 2;
        ListPregunta.add(PreguntaRes);

        PreguntaRes = new clsBeEncuesta();
        PreguntaRes.IdPregunta = 4;
        PreguntaRes.Pregunta = "Profundidad del dibujo";
        PreguntaRes.Respuesta = false;
        PreguntaRes.Tipo = 2;
        ListPregunta.add(PreguntaRes);

    }

    private void setHandlers() {
        try {

            chkFrontal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        TipoLlanta = 1;
                    }
                    if (ListPregunta.size() == 0) {
                        //cambio = true;
                        chk1.setChecked(false);
                        chk2.setChecked(false);
                        chk3.setChecked(false);
                        chk4.setChecked(false);
                        //cambio = false;
                    } else {
                        LlenaValoresChk();
                    }

                }
            });

            chkTrasera.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    if (b) {
                        TipoLlanta = 2;
                    }

                    if (ListPregunta.size() == 0) {
                        cambio = true;
                        chk1.setChecked(false);
                        chk2.setChecked(false);
                        chk3.setChecked(false);
                        chk4.setChecked(false);
                        cambio = false;
                    } else {
                        LlenaValoresChk();
                    }

                }
            });


            chk1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean value) {

                    if (!cambio) {
                        if (chkFrontal.isChecked() || chkTrasera.isChecked()) {
                            PreguntaRes = new clsBeEncuesta();
                            PreguntaRes.IdPregunta = 1;
                            PreguntaRes.Pregunta = chk1.getText().toString();
                            //SetTipoLlanta();
                            SetRespuesta(value);
                            GuardarRespuesta();
                        } else {
                            toast("Debe seleccionar llanta frontal o trasera");
                        }
                    }
                }
            });

            chk2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean value) {

                    if (!cambio) {
                        if (chkFrontal.isChecked() || chkTrasera.isChecked()) {
                            PreguntaRes = new clsBeEncuesta();
                            PreguntaRes.IdPregunta = 2;
                            PreguntaRes.Pregunta = chk2.getText().toString();
                            //SetTipoLlanta();
                            SetRespuesta(value);
                            GuardarRespuesta();
                        } else {
                            toast("Debe seleccionar llanta frontal o trasera");
                        }
                    }
                }
            });

            chk3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean value) {
                    if (!cambio) {
                        if (chkFrontal.isChecked() || chkTrasera.isChecked()) {
                            PreguntaRes = new clsBeEncuesta();
                            PreguntaRes.IdPregunta = 3;
                            PreguntaRes.Pregunta = chk3.getText().toString();
                           // SetTipoLlanta();
                            SetRespuesta(value);
                            GuardarRespuesta();
                        } else {
                            toast("Debe seleccionar llanta frontal o trasera");
                        }
                    }
                }
            });

            chk4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean value) {
                    if (!cambio) {
                        if (chkFrontal.isChecked() || chkTrasera.isChecked()) {
                            PreguntaRes = new clsBeEncuesta();
                            PreguntaRes.IdPregunta = 4;
                            PreguntaRes.Pregunta = chk4.getText().toString();
                            //SetTipoLlanta();
                            SetRespuesta(value);
                            GuardarRespuesta();
                        } else {
                            toast("Debe seleccionar llanta frontal o trasera");
                        }
                    }
                }
            });
        } catch (Exception e) {
            mu.msgbox(new Object() {} .getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
        }
    }

    private void LlenaValoresChk() {

        cambio = true;
        List<Boolean> AuxList= null;
        AuxList =  stream(ListPregunta).where(c->c.Tipo == TipoLlanta).select(c->c.Respuesta).toList();

        if (AuxList.size() != 0) {

            chk1.setChecked(AuxList.get(0));
            chk2.setChecked(AuxList.get(1));
            chk3.setChecked(AuxList.get(2));
            chk4.setChecked(AuxList.get(3));

        } else {
            chk1.setChecked(false);
            chk2.setChecked(false);
            chk3.setChecked(false);
            chk4.setChecked(false);
        }

        cambio = false;
    }

    private void SetTipoLlanta() {

        if (chkFrontal.isChecked()) {
            //Frontal
            TipoLlanta = 1;
        } else if (chkTrasera.isChecked()) {
            //Trasera
            TipoLlanta = 2;
        }
    }

    private void SetRespuesta(boolean value) {
        if (value) {
            respuesta = true;
        } else {
            respuesta = false;
        }
    }

    private void GuardarRespuesta() {
        boolean existe = false;
        try {

            PreguntaRes.Tipo = TipoLlanta;

            if (ListPregunta.size() > 0) {
                for (int i = 0; i < ListPregunta.size(); i++) {
                    if (PreguntaRes.IdPregunta == ListPregunta.get(i).IdPregunta &&  ListPregunta.get(i).Tipo == TipoLlanta) {
                        ListPregunta.get(i).Respuesta = respuesta;
                        existe = true;
                        break;
                    }
                }

                if (!existe) {
                    PreguntaRes.Respuesta = respuesta;
                    ListPregunta.add(PreguntaRes);
                }
            } else {
                PreguntaRes.Respuesta = respuesta;
                ListPregunta.add(PreguntaRes);
            }

        } catch (Exception e) {
            mu.msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
        }
    }

    public void Exit(View view) {
        super.finish();
    }
}