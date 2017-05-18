package com.consumer.gestioneorari.ttapschool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ModificaStudenteActivity extends AppCompatActivity {

    ImageView imgModificaNome,imgModificaCognome,imgModificaPassword,imgModificaClasse;
    EditText etNewName,etNewCognome,etNewPass,etOldPass,etNewPass2;
    TextView tvNomePopup,tvCognomePopup;
    static Spinner spListaClassiPopup;
    static TextView tvNome,tvCognome, tvClasse,tvSezione,tvIndirizzo;;
    private AbsoluteLayout modificaStudenteLayout;
    static String idUtente,tipo;
    static Activity activity;
    PopupWindow popup;
    static ArrayList<String[]> classiUtenteArray;
   static ArrayList<String> spClassiArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_studente);

        activity=ModificaStudenteActivity.this;

        imgModificaNome=(ImageView)findViewById(R.id.imgModificaNome);
        imgModificaCognome=(ImageView)findViewById(R.id.imgModificaCognome);
        imgModificaPassword=(ImageView)findViewById(R.id.imgModificaPassword);
        imgModificaClasse=(ImageView)findViewById(R.id.imgModificaClasse);
        modificaStudenteLayout=(AbsoluteLayout)findViewById(R.id.modificaStudenteLayout);

        tvNome=(TextView) findViewById(R.id.tvNome);
        tvCognome=(TextView) findViewById(R.id.tvCognome);
        tvClasse = (TextView) findViewById(R.id.tvClasse);
        tvSezione = (TextView) findViewById(R.id.tvSezione);
        tvIndirizzo = (TextView) findViewById(R.id.tvIndirizzo);
        classiUtenteArray = new ArrayList<>();
        spClassiArray = new ArrayList<>();


        Bundle bundle = getIntent().getExtras();
        idUtente = bundle.getString("idUtente");
        tipo = bundle.getString("tipo");

        popup=new PopupWindow();


        imgModificaNome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    ShowPopup(R.layout.pupup_modifica_nome);
            }
        });

        imgModificaCognome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    ShowPopup(R.layout.popup_modifica_cognome);
            }
        });


        imgModificaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShowPopup(R.layout.popup_modifica_password);
            }
        });



        imgModificaClasse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopup(R.layout.popup_modifica_classe);
            }
        });

       WS ws = new WS(getApplicationContext());
       ws.execute(ws.GET_CLASSE,idUtente,tipo);


        ws = new WS(getApplicationContext());
        ws.execute(ws.GET_UTENTE,idUtente,tipo);

    }


    public void ShowPopup(final int layout){
        if(!popup.isShowing()) {

                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                final View customView = inflater.inflate(layout, null);
                customView.setId(layout);
                popup = new PopupWindow(

                        customView,
                        ActionMenuView.LayoutParams.WRAP_CONTENT,
                        ActionMenuView.LayoutParams.WRAP_CONTENT
                );

                popup.setFocusable(true);

                if(layout == R.layout.pupup_modifica_nome){
                    tvNomePopup = (TextView) customView.findViewById(R.id.tvNomePopup);
                    etNewName = (EditText) customView.findViewById(R.id.etNewName);
                    tvNomePopup.setText(tvNome.getText().toString());
                }
                else if(layout == R.layout.popup_modifica_cognome){
                    tvCognomePopup = (TextView) customView.findViewById(R.id.tvCognomePopup);
                    etNewCognome = (EditText) customView.findViewById(R.id.etNewCognome);
                    tvCognomePopup.setText(tvCognome.getText().toString());
                }
                else if(layout == R.layout.popup_modifica_classe){
                    spListaClassiPopup = (Spinner) customView.findViewById(R.id.spListaClassiPopup);
                    inizializeSpinner(spListaClassiPopup,spClassiArray);
                }
                else if(layout == R.layout.popup_modifica_password){
                    etOldPass = (EditText) customView.findViewById(R.id.etOldPass);
                    etNewPass = (EditText) customView.findViewById(R.id.etNewPass);
                    etNewPass2 = (EditText) customView.findViewById(R.id.etNewPass2);
                }



                Button btnConfirm = (Button) customView.findViewById(R.id.btnConferma);
                Button btnDeny = (Button) customView.findViewById(R.id.btnChiudi);

                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean disappear=true;
                        if(layout == R.layout.pupup_modifica_nome){
                            if(etNewName.getText().toString().length()>0){
                                WS ws = new WS(getApplicationContext());
                                ws.execute(ws.MODIFICA_NOME,idUtente,tipo,etNewName.getText().toString());
                            }
                            else{
                                disappear=false;
                                Toast.makeText(ModificaStudenteActivity.this,"Nome troppo corto!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if(layout == R.layout.popup_modifica_cognome ){
                            if(etNewCognome.getText().toString().length()>0) {
                                WS ws = new WS(getApplicationContext());
                                ws.execute(ws.MODIFICA_COGNOME, idUtente, tipo, etNewCognome.getText().toString());
                            } else{
                                disappear=false;
                                Toast.makeText(ModificaStudenteActivity.this,"Cognome troppo corto!",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                        else if(layout == R.layout.popup_modifica_password ){
                            if(etOldPass.getText().toString().length()>0 && etNewPass.getText().toString().length()>0 && etNewPass2.getText().toString().length()>0 ) {
                               if(etNewPass2.getText().toString().equals(etNewPass.getText().toString())){
                                   WS ws = new WS(getApplicationContext());
                                   ws.execute(ws.MODIFICA_PASSWORD, idUtente, tipo, etOldPass.getText().toString(),etNewPass.getText().toString() );
                               }
                               else{
                                   disappear=false;
                                   Toast.makeText(ModificaStudenteActivity.this,"Le password non coincidono!",
                                           Toast.LENGTH_SHORT).show();
                               }

                            } else{
                                disappear=false;
                                Toast.makeText(ModificaStudenteActivity.this,"Riempi tutti i campi!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }


                        if(disappear==true)
                            popup.dismiss();
                    }
                });

                btnDeny.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Dismiss the popup window
                        popup.dismiss();
                    }
                });

                popup.setAnimationStyle(R.style.AnimationPopup);
                popup.showAtLocation(modificaStudenteLayout, Gravity.CENTER, 0, 0);
            }

    }


    public static void setClassiUtente(ArrayList<String[]> arrayList){
        classiUtenteArray=arrayList;
        if(tipo.equals("0")){
            String[] vett;
            vett=classiUtenteArray.get(0);
            tvClasse.setText(vett[0]);
            tvSezione.setText(vett[1]);
            tvIndirizzo.setText(vett[2]);
            String stringa = tvClasse.getText().toString() + " " + tvSezione.getText().toString() + " " + tvIndirizzo.getText().toString();
            spClassiArray.add(stringa);

        }
        else if(tipo.equals("1")){
            for(int i=0;i<classiUtenteArray.size();i++){
                String[] vett;
                vett=classiUtenteArray.get(i);
                String stringa = vett[0] + " " + vett[1] + " " + vett[2];
                spClassiArray.add(stringa);
            }

            tvClasse.setText("CLASSI");
            tvSezione.setText("");
            tvIndirizzo.setText("");


        }
    }



    public static void setDatiUtente(String nome,String cognome){
        tvNome.setText(nome);
        tvCognome.setText(cognome);
    }


    public void inizializeSpinner(Spinner s, ArrayList<String> arrayList) {


            ArrayAdapter<String> adp = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_1, arrayList);
            adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s.setAdapter(adp);


    }


    public static void AggiornaDati(Context context){

        WS ws = new WS(context);
        ws.execute(ws.GET_UTENTE,idUtente,tipo);
        Toast.makeText(activity,"Modifica avvenuta con successo!",
                Toast.LENGTH_SHORT).show();
    }

    }










