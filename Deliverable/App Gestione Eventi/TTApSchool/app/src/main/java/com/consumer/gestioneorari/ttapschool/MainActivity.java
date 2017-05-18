package com.consumer.gestioneorari.ttapschool;

import android.app.DatePickerDialog;
import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView imgAccount, imgLogout, imgAddEvento, imgModifyEvento, imgDeleteEvento;
    ScrollView svNote;
    private AbsoluteLayout mainActivityLayout;
    static LinearLayout llNote;
    EditText etData;
    final Calendar c = Calendar.getInstance();
    static TextView textView;
    String idUtente="";
    String tipo="";
    PopupWindow popup;
    WS ws;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Bundle bundle = getIntent().getExtras();
        idUtente=Integer.toString(bundle.getInt("idUtente"));
        tipo=Integer.toString(bundle.getInt("tipo"));

        imgAccount = (ImageView) findViewById(R.id.imgAccount);
        imgLogout = (ImageView) findViewById(R.id.imgLogout);
        imgAddEvento = (ImageView) findViewById(R.id.imgAddEvento);
        imgModifyEvento = (ImageView) findViewById(R.id.imgModifyEvento);
        imgDeleteEvento = (ImageView) findViewById(R.id.imgDeleteEvento);
        mainActivityLayout=(AbsoluteLayout)findViewById(R.id.mainActivityLayout);
        svNote = (ScrollView) findViewById(R.id.svNote);
        llNote = (LinearLayout) findViewById(R.id.llNote);
        etData = (EditText) findViewById(R.id.etData);
        textView = new TextView(this);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(19);
        popup=new PopupWindow();


        c.set(Calendar.YEAR,c.get(Calendar.YEAR));
        c.set(Calendar.MONTH,c.get(Calendar.MONTH));
        c.set(Calendar.DAY_OF_YEAR,c.get(Calendar.DAY_OF_YEAR));
        updateLabel();
        ws = new WS(getApplicationContext());
        ws.execute(ws.GET_NOTE,idUtente,tipo,etData.getText().toString());


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
                ws = new WS(getApplicationContext());
                ws.execute(ws.GET_NOTE,idUtente,tipo,etData.getText().toString());

            }

        };






         etData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(MainActivity.this, date, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();
            }

        });


        imgAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("idUtente",idUtente);
                bundle.putString("tipo",tipo);
                Intent intent = new Intent(getApplicationContext(),ModificaStudenteActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        imgAddEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("idUtente",idUtente);
                bundle.putString("tipo",tipo);
                Intent intent = new Intent(getApplicationContext(),AddEventoActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        imgModifyEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("idUtente",idUtente);
                bundle.putString("tipo",tipo);
                Intent intent = new Intent(getApplicationContext(),ModificaNotaActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        imgDeleteEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("idUtente",idUtente);
                bundle.putString("tipo",tipo);
                Intent intent = new Intent(getApplicationContext(),EliminaNotaActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopup(R.layout.popup_logout);
            }
        });


    }


    private void updateLabel() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etData.setText(sdf.format(c.getTime()));
    }

    public static void setScrollViewText(String stringa){
        textView.setText(stringa);
        llNote.removeAllViews();
        llNote.addView(textView);
    }

    public void ShowPopup(int layout){
        if(!popup.isShowing()) {

            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View customView = inflater.inflate(layout, null);
            popup = new PopupWindow(

                    customView,
                    ActionMenuView.LayoutParams.WRAP_CONTENT,
                    ActionMenuView.LayoutParams.WRAP_CONTENT
            );
            Button btnConfirm = (Button) customView.findViewById(R.id.btnConferma);
            Button btnDeny = (Button) customView.findViewById(R.id.btnChiudi);
            popup.setFocusable(true);
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popup.dismiss();
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
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
            popup.showAtLocation(mainActivityLayout, Gravity.CENTER, 0, 0);
        }

    }




}







