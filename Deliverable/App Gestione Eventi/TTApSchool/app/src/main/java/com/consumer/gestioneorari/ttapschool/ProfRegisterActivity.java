package com.consumer.gestioneorari.ttapschool;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class ProfRegisterActivity extends AppCompatActivity {

    private TextView etNome,etCognome,etPassword,etRipetiPassword,etUsername;
    private Button btnAggiungiMateria,btnAggiungiClasse,btnRegistra;
    private Spinner spMateria,spSezione,spIndirizzo,spClasse;
    private LinearLayout lyMaterie,llClasse,llContainerSpinners;


    ArrayList<String> arrayListMaterie;
    ArrayList<String> arrayListMaterieScelte;
    ArrayList<Spinner> arrayListSpinnerMaterie;

    ArrayList<String> arrayListClassi;
    ArrayList<String> arrayListClassiScelte;
    ArrayList<String> arrayListSezioni;
    ArrayList<String> arrayListSezioniScelte;
    ArrayList<String> arrayListIndirizzi;
    ArrayList<String> arrayListIndirizziScelti;
    ArrayList<ClasseSpinner> arrayListCLasseSpinner;





    ArrayAdapter<String> spinnerArrayAdapter;


    ClasseSpinner classeSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_register);

        etNome=(TextView)findViewById(R.id.etNome);
        etCognome=(TextView)findViewById(R.id.etCognome);
        etUsername=(TextView)findViewById(R.id.etUsername);
        etPassword=(TextView)findViewById(R.id.etRipetiPassword);
        etRipetiPassword=(TextView)findViewById(R.id.etRipetiPassword);
        btnAggiungiMateria=(Button)findViewById(R.id.btnAggiungiMateria);
        btnAggiungiClasse=(Button)findViewById(R.id.btnAggiungiClasse);
        btnRegistra=(Button)findViewById(R.id.btnRegistra);
        spMateria=(Spinner)findViewById(R.id.spMateria);
        spClasse=(Spinner)findViewById(R.id.spClasse);
        spSezione=(Spinner)findViewById(R.id.spSezione);
        spIndirizzo=(Spinner)findViewById(R.id.spIndirizzo);
        lyMaterie=(LinearLayout) findViewById(R.id.lyMaterie);
        llClasse=(LinearLayout) findViewById(R.id.llClasse);


        classeSpinner = new ClasseSpinner();

        arrayListMaterieScelte = new ArrayList<String>();
        arrayListCLasseSpinner = new ArrayList<ClasseSpinner>();

        arrayListClassiScelte = new ArrayList<String>();
        arrayListSezioniScelte = new ArrayList<String>();
        arrayListIndirizziScelti = new ArrayList<String>();

        arrayListSpinnerMaterie = new ArrayList<Spinner>();

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

        //inizializzo gli spinner iniziali
        inizializeSpinner(spMateria,arrayListMaterie);
        inizializeSpinner(spClasse,arrayListClassi);
        inizializeSpinner(spSezione,arrayListSezioni);
        inizializeSpinner(spIndirizzo,arrayListIndirizzi);


        classeSpinner = new ClasseSpinner(spClasse,spSezione,spIndirizzo);
        arrayListCLasseSpinner.add(classeSpinner);


        arrayListSpinnerMaterie.add(spMateria);

        btnAggiungiMateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Spinner spinner = new Spinner(getApplicationContext());
                spinner=createSpinner(spinner,arrayListMaterie);
                arrayListSpinnerMaterie.add(spinner);
                lyMaterie.addView(spinner);
            }
        });


        btnAggiungiClasse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llContainerSpinners = new LinearLayout(getApplicationContext());
                llContainerSpinners.setLayoutParams(new ActionBar.LayoutParams(android.app.ActionBar.LayoutParams.WRAP_CONTENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT));
                Spinner spinner;
                classeSpinner = new ClasseSpinner(createSpinner((spinner= new Spinner(getApplicationContext())),arrayListClassi),createSpinner((spinner= new Spinner(getApplicationContext())),arrayListSezioni),createSpinner((spinner= new Spinner(getApplicationContext())),arrayListIndirizzi));
                arrayListCLasseSpinner.add(classeSpinner);
                llContainerSpinners.addView(classeSpinner.getClasse());
                llContainerSpinners.addView(classeSpinner.getSezione());
                llContainerSpinners.addView(classeSpinner.getIndirizzo());
                llClasse.addView(llContainerSpinners);

            }
        });



        btnRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Classe> arrayListClassi = new ArrayList<Classe>();

                if(arrayListSpinnerMaterie.size()>0)
                    for(int i = 0;i<arrayListSpinnerMaterie.size();i++)
                        arrayListMaterieScelte.add(arrayListSpinnerMaterie.get(i).getSelectedItem().toString());



                for(int i=0;i<arrayListCLasseSpinner.size();i++) {
                    String A=arrayListCLasseSpinner.get(i).getClasse().getSelectedItem().toString();
                    String B=arrayListCLasseSpinner.get(i).getSezione().getSelectedItem().toString();
                    String C=arrayListCLasseSpinner.get(i).getIndirizzo().getSelectedItem().toString();

                    Classe c = new Classe(A,B,C);
                    arrayListClassi.add(c);
                }

                WS ws = new WS(getApplicationContext());
                ws.setClasse(arrayListClassi);
                ws.setMaterie(arrayListMaterieScelte);
                ws.execute(ws.REGISTRA_PROF,etNome.getText().toString(),etCognome.getText().toString(),etUsername.getText().toString(),etPassword.getText().toString());

            }
        });




    }



    public void inizializeSpinner(Spinner s, ArrayList<String> arrayList) {

        ArrayAdapter<String> adp = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, arrayList);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adp);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    public Spinner createSpinner(Spinner s,ArrayList<String> arrayList) {

        s = new Spinner(getApplicationContext());
        spinnerArrayAdapter = new ArrayAdapter<String>(
                getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);
        s.setAdapter(spinnerArrayAdapter);


        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        return s;

    }



}




