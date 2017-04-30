package com.consumer.gestioneorari.ttapschool;

import java.io.Serializable;

/**
 * Created by Uugure on 4/30/2017.
 */

public class Classe implements Serializable {

    private String classe,indirizzo,sezione;


    public Classe(){
        classe="";
        indirizzo="";
        sezione="";
    }


    public Classe(String classe,String sezione, String indirizzo){
        this.classe=classe;
        this.sezione=sezione;
        this.indirizzo=indirizzo;
    }


    public String getClasse(){
        return classe;
    }
    public String getSezione(){
        return sezione;
    }
    public String getIndirizzo(){
        return indirizzo;
    }


}
