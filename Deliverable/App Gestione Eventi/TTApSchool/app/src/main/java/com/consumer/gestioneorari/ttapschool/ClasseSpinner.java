package com.consumer.gestioneorari.ttapschool;

import android.app.Activity;
import android.widget.Spinner;

/**
 * Created by Emanuele on 08/05/2017.
 */

public class ClasseSpinner extends Activity{


    Spinner classe,sezione,indirizzo;

    public ClasseSpinner(){
        classe=null;
        sezione=null;
        indirizzo=null;
    }


    public ClasseSpinner (Spinner classe, Spinner sezione, Spinner indirizzo){
        this.classe=classe;
        this.sezione=sezione;
        this.indirizzo=indirizzo;
    }

    public Spinner getClasse(){
        return classe;
    }

    public Spinner getSezione(){
        return sezione;
    }

    public Spinner getIndirizzo(){
        return indirizzo;
    }




}
