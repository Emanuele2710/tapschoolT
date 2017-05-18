package com.consumer.gestioneorari.ttapschool;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by Uugure on 4/29/2017.
 */

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.Serializable;
import java.util.ArrayList;


public class WS extends AsyncTask<String,Void,String>  {


    Context context;

    ArrayList<Classe> arrayListClasse = new ArrayList<Classe>();
    ArrayList<String> arrayListMaterie = new ArrayList<String>();
    ArrayList<String> classiArray = new ArrayList<String>();
    ArrayList<String> sezioniArray = new ArrayList<String>();
    ArrayList<String> indirizziArray = new ArrayList<String>();
    ArrayList<String> datiUtenteArray = new ArrayList<String>();
    ArrayList<String[]> classiUtenteArray = new ArrayList<>();
    ArrayList<String[]> dateNoteUtenteArray = new ArrayList<>();

    int tipo,idUtente;

    public static int id=0;



    public WS (Context c){
        context=c;
    }

    private static String NAMESPACE = "http://login.vise.org/";
    //Webservice URL - WSDL File location
    private static String URL = "http://192.168.0.106:8080/ws_Login/ws_login?WSDL";
    //SOAP Action URI again Namespace + Web method name
    private static String SOAP_ACTION = "http://login.vise.org/login";

    public static String metodoLogin = "login";
    public static String REGISTRA_PROF = "RegistraProfessore";
    public static String REGISTRA_STUD = "RegistraStudente";
    public static String INSERISCI_EVENTO = "inserisciEvento";
    public static String GET_NOTE = "GetEventi";
    public static String GET_UTENTE = "GetUtente";
    public static String GET_CLASSE = "GetClasse";
    public static String MODIFICA_NOME = "modificaNome";
    public static String MODIFICA_COGNOME = "modificaCognome";
    public static String MODIFICA_PASSWORD = "modificaPass";
    public static String MODIFICA_EVENTO = "modificaEvento";
    public static String GET_DATE = "GetDate";
    public static String ELIMINA_EVENTO = "eliminaEvento";
   // public static String GetMaterie = "GetMaterie";


    String esitoEvento="";


    protected String doInBackground(String... params) {

        ArrayList<String> risposta = new ArrayList<String>();
        if (params[0].equals(metodoLogin)) {
            SoapObject request = new SoapObject(NAMESPACE, params[0]);


            request.addProperty("username", params[1]);
            request.addProperty("password", params[2]);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {
                // Invoke web service
                androidHttpTransport.call(SOAP_ACTION, envelope);
                // Get the response
                SoapObject response = (SoapObject) envelope.bodyIn;
                // Assign it to resTxt variable static variable
                risposta.add(response.getProperty(0).toString());
                risposta.add(response.getProperty(1).toString());
                risposta.add(response.getProperty(2).toString());

            } catch (Exception e) {
                //Print error
                e.printStackTrace();
                //Assign error message to resTxt
                // resTxt = "Error occured";
            }
            //Return resTxt to calling object
            idUtente = Integer.parseInt(risposta.get(1).toString());
            tipo = Integer.parseInt(risposta.get(2).toString());
            return risposta.get(0).toString();
        } else if (params[0].equals(REGISTRA_PROF)) {

            String ritorna = "false";
            SoapObject request = new SoapObject(NAMESPACE, params[0]);


            request.addProperty("nome", params[1]);
            request.addProperty("cognome", params[2]);
            request.addProperty("username", params[3]);
            request.addProperty("password", params[4]);


            for (int i = 0; i < classiArray.size(); i++) {
                request.addProperty("classe", classiArray.get(i));
                request.addProperty("sezione", sezioniArray.get(i));
                request.addProperty("indirizzo", indirizziArray.get(i));
            }

            for (int i = 0; i < arrayListMaterie.size(); i++) {
                request.addProperty("materia", arrayListMaterie.get(i));
            }

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {
                // Invoke web service
                androidHttpTransport.call(SOAP_ACTION, envelope);
                // Get the response
                SoapObject response = (SoapObject) envelope.bodyIn;
                // Assign it to resTxt variable static variable
                ritorna = response.getProperty("return") + "prof";

            } catch (Exception e) {
                //Print error
                e.printStackTrace();
                //Assign error message to resTxt
                // resTxt = "Error occured";
            }
            //Return resTxt to calling object
            return ritorna;


        } else if (params[0].equals(REGISTRA_STUD)) {
            String ritorna = "false";
            SoapObject request = new SoapObject(NAMESPACE, params[0]);


            request.addProperty("nome", params[1]);
            request.addProperty("cognome", params[2]);
            request.addProperty("username", params[3]);
            request.addProperty("password", params[4]);
            request.addProperty("classe", Integer.parseInt(params[5]));
            request.addProperty("sezione", params[6]);
            request.addProperty("indirizzo", params[7]);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {
                // Invoke web service
                androidHttpTransport.call(SOAP_ACTION, envelope);
                // Get the response
                SoapObject response = (SoapObject) envelope.bodyIn;
                // Assign it to resTxt variable static variable
                ritorna = response.getProperty("return") + "stud";

            } catch (Exception e) {
                //Print error
                e.printStackTrace();
                //Assign error message to resTxt
                // resTxt = "Error occured";
            }
            //Return resTxt to calling object
            return ritorna;
        } else if (params[0].equals(GET_NOTE)) {

            SoapObject request = new SoapObject(NAMESPACE, params[0]);

            idUtente = Integer.parseInt(params[1]);
            tipo = Integer.parseInt(params[2]);

            request.addProperty("id_utente", idUtente);
            request.addProperty("tipo", tipo);
            request.addProperty("data", params[3]);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {
                // Invoke web service
                androidHttpTransport.call(SOAP_ACTION, envelope);
                // Get the response
                SoapObject response = (SoapObject) envelope.bodyIn;
                // Assign it to resTxt variable static variable
                esitoEvento = response.getProperty("return").toString();
                return esitoEvento;

            } catch (Exception e) {
                //Print error
                e.printStackTrace();
                //Assign error message to resTxt
                // resTxt = "Error occured";
            }
            //Return resTxt to calling object
            return null;

        } else if (params[0].equals(INSERISCI_EVENTO)) {

            SoapObject request = new SoapObject(NAMESPACE, params[0]);

            request.addProperty("idUtente", params[1]);
            request.addProperty("tipo", Integer.parseInt(params[2]));
            request.addProperty("data", params[3]);
            request.addProperty("ora", params[4]);
            request.addProperty("nota", params[5]);

            idUtente=Integer.parseInt(params[1]);
            tipo=Integer.parseInt(params[2]);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {
                // Invoke web service
                androidHttpTransport.call(SOAP_ACTION, envelope);
                // Get the response
                SoapObject response = (SoapObject) envelope.bodyIn;
                // Assign it to resTxt variable static variable
                return response.getProperty("return").toString() + "AddEvento";


            } catch (Exception e) {
                //Print error
                e.printStackTrace();
                //Assign error message to resTxt
                // resTxt = "Error occured";
            }
            //Return resTxt to calling object
            return null;


        }
        else if (params[0].equals(GET_UTENTE)) {

            SoapObject request = new SoapObject(NAMESPACE, params[0]);

            request.addProperty("idUtente", params[1]);
            request.addProperty("tipo", Integer.parseInt(params[2]));

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {
                // Invoke web service
                androidHttpTransport.call(SOAP_ACTION, envelope);
                // Get the response
                SoapObject response = (SoapObject) envelope.bodyIn;
                // Assign it to resTxt variable static variable
                if(response.getProperty(0).toString().equals("true")){
                    datiUtenteArray.add(response.getProperty(1).toString());
                    datiUtenteArray.add(response.getProperty(2).toString());
                }
                return response.getProperty(0).toString()+"DatiUtente";


            } catch (Exception e) {
                //Print error
                e.printStackTrace();
                //Assign error message to resTxt
                // resTxt = "Error occured";
            }
            //Return resTxt to calling object
            return null;


        } else if (params[0].equals(GET_CLASSE)) {

            SoapObject request = new SoapObject(NAMESPACE, params[0]);

            request.addProperty("idUtente", params[1]);
            request.addProperty("tipo", Integer.parseInt(params[2]));

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {
                // Invoke web service
                androidHttpTransport.call(SOAP_ACTION, envelope);
                // Get the response
                SoapObject response = (SoapObject) envelope.bodyIn;
                SoapObject response1;


                for(int i=0;i<response.getPropertyCount();i++) {
                    response1 = (SoapObject) response.getProperty(i);
                    String[] vett = new String[3];
                    vett[0]=response1.getProperty(0).toString();
                    vett[1]=response1.getProperty(1).toString();
                    vett[2]=response1.getProperty(2).toString();
                    classiUtenteArray.add(vett);
                }


                return "trueClassiUtente";


            } catch (Exception e) {
                //Print error
                e.printStackTrace();
                //Assign error message to resTxt
                // resTxt = "Error occured";
            }
            //Return resTxt to calling object
            return null;


        } else if (params[0].equals(MODIFICA_NOME)) {

            SoapObject request = new SoapObject(NAMESPACE, params[0]);

            request.addProperty("id", Integer.parseInt(params[1]));
            request.addProperty("tipo", Integer.parseInt(params[2]));
            request.addProperty("nome", params[3]);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {
                // Invoke web service
                androidHttpTransport.call(SOAP_ACTION, envelope);
                // Get the response
                SoapObject response = (SoapObject) envelope.bodyIn;

                return response.getProperty("return").toString() + "ModificaNome";


            } catch (Exception e) {
                //Print error
                e.printStackTrace();
                //Assign error message to resTxt
                // resTxt = "Error occured";
            }
            //Return resTxt to calling object
            return null;


        } else if (params[0].equals(MODIFICA_COGNOME)) {

            SoapObject request = new SoapObject(NAMESPACE, params[0]);

            request.addProperty("id", Integer.parseInt(params[1]));
            request.addProperty("tipo", Integer.parseInt(params[2]));
            request.addProperty("cognome", params[3]);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {
                // Invoke web service
                androidHttpTransport.call(SOAP_ACTION, envelope);
                // Get the response
                SoapObject response = (SoapObject) envelope.bodyIn;

                return response.getProperty("return").toString() + "ModificaNome";


            } catch (Exception e) {
                //Print error
                e.printStackTrace();
                //Assign error message to resTxt
                // resTxt = "Error occured";
            }
            //Return resTxt to calling object
            return null;


        }
        else if (params[0].equals(MODIFICA_PASSWORD)) {

            SoapObject request = new SoapObject(NAMESPACE, params[0]);

            request.addProperty("idUtente", Integer.parseInt(params[1]));
            request.addProperty("tipo", Integer.parseInt(params[2]));
            request.addProperty("passwordVecchia", params[3]);
            request.addProperty("passwordNuova", params[4]);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {
                // Invoke web service
                androidHttpTransport.call(SOAP_ACTION, envelope);
                // Get the response
                SoapObject response = (SoapObject) envelope.bodyIn;

                return response.getProperty("return").toString() + "ModificaPassword";


            } catch (Exception e) {
                //Print error
                e.printStackTrace();
                //Assign error message to resTxt
                // resTxt = "Error occured";
            }
            //Return resTxt to calling object
            return null;


        } else if (params[0].equals(GET_DATE)) {

            SoapObject request = new SoapObject(NAMESPACE, params[0]);

            request.addProperty("idUtente", params[1]);
            request.addProperty("tipo", Integer.parseInt(params[2]));

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {
                // Invoke web service
                androidHttpTransport.call(SOAP_ACTION, envelope);
                // Get the response
                SoapObject response = (SoapObject) envelope.bodyIn;
                SoapObject response1;


                for(int i=0;i<response.getPropertyCount();i++) {
                    response1 = (SoapObject) response.getProperty(i);
                    String[] vett = new String[3];
                    vett[0]=response1.getProperty(0).toString();
                    vett[1]=response1.getProperty(1).toString();
                    vett[2]=response1.getProperty(2).toString();
                    dateNoteUtenteArray.add(vett);
                }

                if(dateNoteUtenteArray.size()>0)
                    return "trueDateUtente";
                else
                    return "false";


            } catch (Exception e) {
                //Print error
                e.printStackTrace();
                //Assign error message to resTxt
                // resTxt = "Error occured";
            }
            //Return resTxt to calling object
            return null;


        } else if (params[0].equals(MODIFICA_EVENTO)) {

            SoapObject request = new SoapObject(NAMESPACE, params[0]);


            idUtente=Integer.parseInt(params[1]);
            tipo=Integer.parseInt(params[7]);

            request.addProperty("idUtente", params[1]);
            request.addProperty("oldData", params[2]);
            request.addProperty("oldOra", params[3]);
            request.addProperty("newData", params[4]);
            request.addProperty("newOra", params[5]);
            request.addProperty("nota", params[6]);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {
                // Invoke web service
                androidHttpTransport.call(SOAP_ACTION, envelope);
                // Get the response
                SoapObject response = (SoapObject) envelope.bodyIn;

                return response.getProperty("return").toString() + "ModificaEvento";


            } catch (Exception e) {
                //Print error
                e.printStackTrace();
                //Assign error message to resTxt
                // resTxt = "Error occured";
            }
            //Return resTxt to calling object
            return null;


        } else if (params[0].equals(ELIMINA_EVENTO)) {

            SoapObject request = new SoapObject(NAMESPACE, params[0]);


            idUtente=Integer.parseInt(params[1]);
            tipo=Integer.parseInt(params[2]);

            request.addProperty("idUtente", Integer.parseInt(params[1]));
            request.addProperty("tipo", Integer.parseInt(params[2]));
            request.addProperty("data", params[3]);
            request.addProperty("ora", params[4]);
            request.addProperty("nota", params[5]);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {
                // Invoke web service
                androidHttpTransport.call(SOAP_ACTION, envelope);
                // Get the response
                SoapObject response = (SoapObject) envelope.bodyIn;

                return response.getProperty("return").toString() + "EliminaEvento";


            } catch (Exception e) {
                //Print error
                e.printStackTrace();
                //Assign error message to resTxt
                // resTxt = "Error occured";
            }
            //Return resTxt to calling object
            return null;


        }

return "false";
    }





    protected  void onPostExecute(String esito) {
        if (esito.equals("true")) {

            Bundle bundle = new Bundle();
            bundle.putInt("idUtente", idUtente);
            bundle.putInt("tipo", tipo);
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtras(bundle);
            context.startActivity(intent);
        } else if (esito.equals("trueprof")) {

            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        } else if (esito.equals("truestud")) {
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        } else if (esito.equals(esitoEvento)) {
            MainActivity.setScrollViewText(esitoEvento);
        } else if (esito.equals("trueAddEvento")) {
            Bundle bundle = new Bundle();
            bundle.putInt("idUtente", idUtente);
            bundle.putInt("tipo", tipo);
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtras(bundle);
            context.startActivity(intent);
            Toast.makeText(context, "Evento aggiunto con successo!",
                    Toast.LENGTH_SHORT).show();
        } else if (esito.equals("trueDatiUtente")) {
            ModificaStudenteActivity.setDatiUtente(datiUtenteArray.get(0).toString(), datiUtenteArray.get(1).toString());
        } else if (esito.equals("trueClassiUtente")) {
            ModificaStudenteActivity.setClassiUtente(classiUtenteArray);
        } else if (esito.equals("trueModificaNome")) {
            ModificaStudenteActivity.AggiornaDati(context);
        } else if (esito.equals("trueModificaPassword")) {
            Toast.makeText(context, "Modifica della password avvenuta con successo!",
                    Toast.LENGTH_SHORT).show();
        } else if (esito.equals("trueDateUtente")) {

            if (id==ModificaNotaActivity.id) {
                ModificaNotaActivity.setArrayListNote(dateNoteUtenteArray);
            } else if (id==EliminaNotaActivity.id) {
                EliminaNotaActivity.setArrayListNote(dateNoteUtenteArray);
            }

         }  else if(esito.equals("trueModificaEvento")){

          Bundle bundle = new Bundle();
          bundle.putInt("idUtente",idUtente);
          bundle.putInt("tipo",tipo);

          Intent intent = new Intent(context,MainActivity.class);
          intent.putExtras(bundle);
          context.startActivity(intent);

          Toast.makeText(context,"Evento modificato con successo!",
                  Toast.LENGTH_SHORT).show();

      } else if(esito.equals("trueEliminaEvento")){

            Bundle bundle = new Bundle();
            bundle.putInt("idUtente",idUtente);
            bundle.putInt("tipo",tipo);

            Intent intent = new Intent(context,MainActivity.class);
            intent.putExtras(bundle);
            context.startActivity(intent);

            Toast.makeText(context,"Evento eliminato con successo!",
                    Toast.LENGTH_SHORT).show();

        }

    }



    public void setMaterie(ArrayList<String> materie){
        for(int i = 0;i<materie.size();i++){
            arrayListMaterie.add(materie.get(i));

        }
    }

    public void setClasse(ArrayList<Classe> classi){
       for(int i = 0;i<classi.size();i++){
           arrayListClasse.add(classi.get(i));
           classiArray.add(arrayListClasse.get(i).getClasse().toString());
           sezioniArray.add(arrayListClasse.get(i).getSezione().toString());
           indirizziArray.add(arrayListClasse.get(i).getIndirizzo().toString());
       }
    }


}


