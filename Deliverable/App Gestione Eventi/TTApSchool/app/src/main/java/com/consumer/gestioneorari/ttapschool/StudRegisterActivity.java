package com.consumer.gestioneorari.ttapschool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class StudRegisterActivity extends AppCompatActivity {
    private TextView etNome,etCognome,etPassword,etUsername;
    private Button btnRegistra;
    private Spinner spSezione,spIndirizzo,spClasse;

    ArrayList<String> arrayListClassi;
    ArrayList<String> arrayListSezioni;
    ArrayList<String> arrayListIndirizzi;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud_register);

        etNome=(TextView)findViewById(R.id.etNome);
        etCognome=(TextView)findViewById(R.id.etCognome);
        etUsername=(TextView)findViewById(R.id.etUsername);
        etPassword=(TextView)findViewById(R.id.etRipetiPassword);
        btnRegistra=(Button)findViewById(R.id.btnRegistra);
        spClasse=(Spinner)findViewById(R.id.spClasse);
        spSezione=(Spinner)findViewById(R.id.spSezione);
        spIndirizzo=(Spinner)findViewById(R.id.spIndirizzo);

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


        inizializeSpinner(spIndirizzo,arrayListIndirizzi);
        inizializeSpinner(spClasse,arrayListClassi);
        inizializeSpinner(spSezione,arrayListSezioni);


        btnRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WS ws = new WS(getApplicationContext());
                ws.execute(ws.REGISTRA_STUD,etNome.getText().toString(),etCognome.getText().toString(),etUsername.getText().toString(),etPassword.getText().toString(),spClasse.getSelectedItem().toString(),spSezione.getSelectedItem().toString(),spIndirizzo.getSelectedItem().toString());
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
}
