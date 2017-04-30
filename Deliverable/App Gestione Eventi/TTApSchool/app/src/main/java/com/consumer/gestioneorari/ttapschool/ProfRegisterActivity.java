package com.consumer.gestioneorari.ttapschool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ProfRegisterActivity extends AppCompatActivity {

    private TextView etNome,etCognome,etPassword,etRipetiPassword,etUsername;
    private Button btnAggiungiMateria,btnAggiungiClasse,btnRegistra;
    private Spinner spMateria,spSezione,spIndirizzo,spClasse;
    private LinearLayout lyMaterie;

    ArrayList<String> arrayListMaterie;
    ArrayList<String> arrayListMaterieScelte;
    ArrayList<Spinner> arrayListSpinner;

    ArrayList<String> arrayListClassi;
    ArrayList<String> arrayListClassiScelte;
    ArrayList<String> arrayListSezioni;
    ArrayList<String> arrayListSezioniScelte;
    ArrayList<String> arrayListIndirizzi;
    ArrayList<String> arrayListIndirizziScelti;



    Spinner spinner;
    ArrayAdapter<String> spinnerArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_register);

        etNome=(TextView)findViewById(R.id.etNome);
        etCognome=(TextView)findViewById(R.id.etCognome);
        etUsername=(TextView)findViewById(R.id.etUsername);
        etPassword=(TextView)findViewById(R.id.etPassword);
        etRipetiPassword=(TextView)findViewById(R.id.etRipetiPassword);
        btnAggiungiMateria=(Button)findViewById(R.id.btnAggiungiMateria);
        btnAggiungiClasse=(Button)findViewById(R.id.btnAggiungiClasse);
        btnRegistra=(Button)findViewById(R.id.btnRegistra);
        spMateria=(Spinner)findViewById(R.id.spMateria);
        spClasse=(Spinner)findViewById(R.id.spClasse);
        spSezione=(Spinner)findViewById(R.id.spSezione);
        spIndirizzo=(Spinner)findViewById(R.id.spIndirizzo);
        lyMaterie=(LinearLayout) findViewById(R.id.lyMaterie);

        arrayListMaterieScelte = new ArrayList<String>();

        arrayListClassiScelte = new ArrayList<String>();
        arrayListSezioniScelte = new ArrayList<String>();
        arrayListIndirizziScelti = new ArrayList<String>();

        arrayListSpinner = new ArrayList<Spinner>();

        arrayListMaterie = new ArrayList<String>();
        arrayListMaterie.add("storia");
        arrayListMaterie.add("geografia");
        arrayListMaterie.add("scienze");
        arrayListMaterie.add("informatica");


        arrayListClassi=new ArrayList<String>();
        arrayListClassi.add("1");
        arrayListClassi.add("2");
        arrayListClassi.add("3");
        arrayListClassi.add("4");
        arrayListClassi.add("5");


        arrayListSezioni=new ArrayList<String>();
        arrayListSezioni.add("a");
        arrayListSezioni.add("b");
        arrayListSezioni.add("c");


        arrayListIndirizzi=new ArrayList<String>();
        arrayListIndirizzi.add("informatica");
        arrayListIndirizzi.add("chimico");

                 ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, arrayListMaterie);
                adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spMateria.setAdapter(adp1);

                 spMateria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
         });


        ArrayAdapter<String> adp2 = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, arrayListClassi);
        adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spClasse.setAdapter(adp2);

        spClasse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        ArrayAdapter<String> adp3 = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, arrayListSezioni);
        adp3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSezione.setAdapter(adp3);

        spSezione.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        ArrayAdapter<String> adp4 = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, arrayListIndirizzi);
        adp4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spIndirizzo.setAdapter(adp4);

        spIndirizzo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });





        arrayListSpinner.add(spMateria);

        btnAggiungiMateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner = new Spinner(getApplicationContext());
                spinnerArrayAdapter = new ArrayAdapter<String>(
                        getApplicationContext(),android.R.layout.simple_list_item_1,arrayListMaterie);
                spinner.setAdapter(spinnerArrayAdapter);


                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long id) {
                     }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });



                arrayListSpinner.add(spinner);
                lyMaterie.addView(spinner);
            }
        });

        btnRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrayListSpinner.size()>0)
                for(int i = 0;i<arrayListSpinner.size();i++)
                    arrayListMaterieScelte.add(arrayListSpinner.get(i).getSelectedItem().toString());

               /* arrayListIndirizziScelti.add(spIndirizzo.getSelectedItem().toString());
                arrayListClassiScelte.add(spClasse.getSelectedItem().toString());
                arrayListSezioniScelte.add(spSezione.getSelectedItem().toString());*/

                Classe c = new Classe(spClasse.getSelectedItem().toString(),spSezione.getSelectedItem().toString(),spIndirizzo.getSelectedItem().toString());
                ArrayList<Classe> arrayListClassi = new ArrayList<Classe>();
                arrayListClassi.add(c);

                WS ws = new WS(getApplicationContext());
                ws.setClasse(arrayListClassi);
                ws.setMaterie(arrayListMaterieScelte);
                ws.execute(ws.REGISTRA_PROF,etNome.getText().toString(),etCognome.getText().toString(),etUsername.getText().toString(),etPassword.getText().toString());

            }
        });




    }





}




