package com.consumer.gestioneorari.ttapschool;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import java.util.ArrayList;

public class EliminaNotaActivity extends AppCompatActivity {


    static Spinner spDataElimina,spOraElimina;
    static LinearLayout llNota;
    ScrollView svNota;
    static Context context;
    Button btnEliminaNota;
    String idUtente,tipo;
    static ArrayList<String[]> arrayListNote;
    static TextView textView;
    public final static int id=2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elimina_nota);

        spDataElimina=(Spinner)findViewById(R.id.spDataElimina);
        spOraElimina=(Spinner)findViewById(R.id.spOraElimina);
        llNota=(LinearLayout)findViewById(R.id.llNota);
        svNota=(ScrollView)findViewById(R.id.svNota);
        btnEliminaNota=(Button)findViewById(R.id.btnEliminaNota);
        textView=new TextView(getApplicationContext());
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(19);

        context=getApplicationContext();

        Bundle bundle = getIntent().getExtras();
        idUtente=bundle.getString("idUtente");
        tipo=bundle.getString("tipo");

        WS.id=this.id;

        WS ws = new WS(getApplicationContext());
        ws.execute(ws.GET_DATE,idUtente,tipo);


        btnEliminaNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WS ws = new WS(getApplicationContext());
                try {
                    ws.execute(ws.ELIMINA_EVENTO, idUtente, tipo, spDataElimina.getSelectedItem().toString(), spOraElimina.getSelectedItem().toString(), textView.getText().toString());
                }catch(Exception e){
                    Toast.makeText(context, "Non hai note da eliminare",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    static public void setArrayListNote( ArrayList<String[]> arrayList) {
        arrayListNote = arrayList;
        int position=0;
        String[] vett;
        String nota="";
        ArrayList<String> array = new ArrayList<>();;
        for (int i = 0; i < arrayListNote.size(); i++) {
            vett = arrayListNote.get(i);
            array.add(vett[0]);
        }

        ArrayList<String> array1 = new ArrayList<>();
        boolean presente=false;
        for(int i =0;i<array.size();i++){
            for(int k=0;k<array1.size();k++){
                if(array.get(i).equals(array1.get(k))){
                    presente=true;
                    break;
                }
            }
            if(presente==false)
                array1.add(array.get(i));
            presente=false;
        }

        array=array1;

        inizializeSpinner(spDataElimina,array);

        array=new ArrayList<>();
        for (int i = 0; i < arrayListNote.size(); i++) {
            vett = arrayListNote.get(i);
            if(vett[0].equals(spDataElimina.getSelectedItem().toString())){
                array.add(vett[1]);

            }
        }

        inizializeSpinner(spOraElimina,array);


        for (int i = 0; i < arrayListNote.size(); i++) {
            vett = arrayListNote.get(i);
            if(vett[0].equals(spDataElimina.getSelectedItem().toString())&& vett[1].equals(spOraElimina.getSelectedItem().toString())){
                try {
                    setScrollViewText(vett[2].toString());
                }catch(Exception e){
                    System.out.print(e);
                }
                break;
            }
        }

    }
    public static void inizializeSpinner(final Spinner s, ArrayList<String> arrayList) {

        ArrayAdapter<String> adp = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, arrayList);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                if(s.getId()==spDataElimina.getId()){
                    ChangeDate(s.getSelectedItem().toString());
                }
                else if(s.getId()==spOraElimina.getId()){
                    ChangeOra(s.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        s.setAdapter(adp);

    }

    static public void ChangeDate(String data){

        String[] vett;
        ArrayList<String> array=new ArrayList<>();
        for (int i = 0; i < arrayListNote.size(); i++) {
            vett = arrayListNote.get(i);
            if(vett[0].equals(spDataElimina.getSelectedItem().toString())){
                array.add(vett[1]);

            }
        }

        inizializeSpinner(spOraElimina,array);


        for (int i = 0; i < arrayListNote.size(); i++) {
            vett = arrayListNote.get(i);
            if(vett[0].equals(spDataElimina.getSelectedItem().toString())&& vett[1].equals(spOraElimina.getSelectedItem().toString())){
               setScrollViewText(vett[2].toString());
                break;

            }
        }
    }

    static public void ChangeOra(String ora){

        String[] vett;
        ArrayList<String> array=new ArrayList<>();

        for (int i = 0; i < arrayListNote.size(); i++) {
            vett = arrayListNote.get(i);
            if(vett[0].equals(spDataElimina.getSelectedItem().toString())&& vett[1].equals(spOraElimina.getSelectedItem().toString())){
                setScrollViewText(vett[2].toString());
                break;

            }
        }
    }


    public static  void setScrollViewText(String stringa){
        textView.setText(stringa);
        llNota.removeAllViews();
        llNota.addView(textView);
    }



}
