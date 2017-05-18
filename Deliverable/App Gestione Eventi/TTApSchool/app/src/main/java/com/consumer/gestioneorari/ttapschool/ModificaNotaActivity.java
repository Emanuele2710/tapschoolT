package com.consumer.gestioneorari.ttapschool;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ModificaNotaActivity extends AppCompatActivity {

    static EditText etNewData,etNewOra,etNewNota;
    static Spinner spData,spOra;
    static Context context;
    Button btnModificaNota;
    String idUtente,tipo;
   static ArrayList<String[]> arrayListNote;
    final Calendar c = Calendar.getInstance();
    public final static int id=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_nota);



        etNewData=(EditText)findViewById(R.id.etNewData);
        etNewOra=(EditText)findViewById(R.id.etNewOra);
        etNewNota=(EditText)findViewById(R.id.etNewNota);
        spData=(Spinner)findViewById(R.id.spData);
        spOra=(Spinner)findViewById(R.id.spOra);
        btnModificaNota = (Button) findViewById(R.id.btnModificaNota);
        arrayListNote= new ArrayList<>();

        context=getApplicationContext();

        Bundle bundle = getIntent().getExtras();
        idUtente=bundle.getString("idUtente");
        tipo=bundle.getString("tipo");

        WS.id=this.id;

        WS ws = new WS(getApplicationContext());
        ws.execute(ws.GET_DATE,idUtente,tipo);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();


            }

        };


        etNewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(ModificaNotaActivity.this, date, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();
            }

        });

        etNewOra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ModificaNotaActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        etNewOra.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


        btnModificaNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spData.getChildCount()>0) {
                    WS ws = new WS(getApplicationContext());
                    ws.execute(ws.MODIFICA_EVENTO, idUtente, spData.getSelectedItem().toString(), spOra.getSelectedItem().toString(), etNewData.getText().toString(), etNewOra.getText().toString(), etNewNota.getText().toString(), tipo);
                }
                else {
                    Toast.makeText(ModificaNotaActivity.this,"Non ci sono eventi da modificare",
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

       inizializeSpinner(spData,array);
       etNewData.setText(spData.getSelectedItem().toString());


       array=new ArrayList<>();
       for (int i = 0; i < arrayListNote.size(); i++) {
           vett = arrayListNote.get(i);
           if(vett[0].equals(spData.getSelectedItem().toString())){
               array.add(vett[1]);

           }
       }

       inizializeSpinner(spOra,array);
       etNewOra.setText(spOra.getSelectedItem().toString());

       for (int i = 0; i < arrayListNote.size(); i++) {
           vett = arrayListNote.get(i);
           if(vett[0].equals(spData.getSelectedItem().toString())&& vett[1].equals(spOra.getSelectedItem().toString())){
               etNewNota.setText(vett[2]);
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
                if(s.getId()==spData.getId()){
                    ChangeDate(s.getSelectedItem().toString());
                }
                else if(s.getId()==spOra.getId()){
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
        etNewData.setText(spData.getSelectedItem().toString());
        String[] vett;
        ArrayList<String> array=new ArrayList<>();
        for (int i = 0; i < arrayListNote.size(); i++) {
            vett = arrayListNote.get(i);
            if(vett[0].equals(spData.getSelectedItem().toString())){
                array.add(vett[1]);

            }
        }

        inizializeSpinner(spOra,array);
        etNewOra.setText(spOra.getSelectedItem().toString());

        for (int i = 0; i < arrayListNote.size(); i++) {
            vett = arrayListNote.get(i);
            if(vett[0].equals(spData.getSelectedItem().toString())&& vett[1].equals(spOra.getSelectedItem().toString())){
                etNewNota.setText(vett[2]);
                break;

            }
        }
    }

    static public void ChangeOra(String ora){

        String[] vett;
        ArrayList<String> array=new ArrayList<>();

        for (int i = 0; i < arrayListNote.size(); i++) {
            vett = arrayListNote.get(i);
            if(vett[0].equals(spData.getSelectedItem().toString())&& vett[1].equals(spOra.getSelectedItem().toString())){
                etNewNota.setText(vett[2]);
                break;

            }
        }
    }

    private void updateLabel() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etNewData.setText(sdf.format(c.getTime()));
    }




}
