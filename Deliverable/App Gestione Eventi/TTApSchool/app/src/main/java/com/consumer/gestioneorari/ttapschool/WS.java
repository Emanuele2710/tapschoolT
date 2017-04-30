package com.consumer.gestioneorari.ttapschool;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

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

    public WS (Context c){
        context=c;
    }

    private static String NAMESPACE = "http://login.vise.org/";
    //Webservice URL - WSDL File location
    private static String URL = "http://192.168.0.102:8080/ws_Login/ws_login?WSDL";
    //SOAP Action URI again Namespace + Web method name
    private static String SOAP_ACTION = "http://login.vise.org/login";

    public static String metodoLogin = "login";
    public static String REGISTRA_PROF = "RegistraProfessore";
    public static String REGISTRA_STUD = "RegistraStudente";
    public static String GetMaterie = "GetMaterie";

    protected String doInBackground(String... params) {

        ArrayList<String> risposta = new ArrayList<String>();
        if (params[0].equals(metodoLogin)) {
            SoapObject request = new SoapObject(NAMESPACE, params[0]);

            PropertyInfo sayHelloPI = new PropertyInfo();

            sayHelloPI.setName("username");
            sayHelloPI.setValue(params[1]);
            sayHelloPI.setType(String.class);
            request.addProperty(sayHelloPI);

            sayHelloPI = new PropertyInfo();
            sayHelloPI.setName("password");
            sayHelloPI.setValue(params[2]);
            sayHelloPI.setType(String.class);
            request.addProperty(sayHelloPI);

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

            } catch (Exception e) {
                //Print error
                e.printStackTrace();
                //Assign error message to resTxt
                // resTxt = "Error occured";
            }
            //Return resTxt to calling object
            return risposta.get(0).toString();
        }
        else if(params[0].equals(REGISTRA_PROF)){

            String ritorna="false";
            SoapObject request = new SoapObject(NAMESPACE, params[0]);

            PropertyInfo sayHelloPI = new PropertyInfo();

            sayHelloPI.setName("nome");
            sayHelloPI.setValue(params[1]);
            sayHelloPI.setType(String.class);
            request.addProperty(sayHelloPI);

            sayHelloPI = new PropertyInfo();
            sayHelloPI.setName("cognome");
            sayHelloPI.setValue(params[2]);
            sayHelloPI.setType(String.class);
            request.addProperty(sayHelloPI);

            sayHelloPI = new PropertyInfo();
            sayHelloPI.setName("username");
            sayHelloPI.setValue(params[3]);
            sayHelloPI.setType(String.class);
            request.addProperty(sayHelloPI);

            sayHelloPI = new PropertyInfo();
            sayHelloPI.setName("password");
            sayHelloPI.setValue(params[4]);
            sayHelloPI.setType(String.class);
            request.addProperty(sayHelloPI);

            for (int i = 0; i < classiArray.size(); i++) {
                request.addProperty("classe", classiArray.get(i));
                request.addProperty("sezione", sezioniArray.get(i));
                request.addProperty("indirizzo", indirizziArray.get(i));
            }

            for(int i=0;i<arrayListMaterie.size();i++){
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
                ritorna=response.getProperty("return") + "prof";

            } catch (Exception e) {
                //Print error
                e.printStackTrace();
                //Assign error message to resTxt
                // resTxt = "Error occured";
            }
            //Return resTxt to calling object
            return ritorna;



        }
        else if(params[0].equals(REGISTRA_STUD)){
            String ritorna="false";
            SoapObject request = new SoapObject(NAMESPACE, params[0]);

            PropertyInfo sayHelloPI = new PropertyInfo();

            sayHelloPI.setName("nome");
            sayHelloPI.setValue(params[1]);
            sayHelloPI.setType(String.class);
            request.addProperty(sayHelloPI);

            sayHelloPI = new PropertyInfo();
            sayHelloPI.setName("cognome");
            sayHelloPI.setValue(params[2]);
            sayHelloPI.setType(String.class);
            request.addProperty(sayHelloPI);

            sayHelloPI = new PropertyInfo();
            sayHelloPI.setName("username");
            sayHelloPI.setValue(params[3]);
            sayHelloPI.setType(String.class);
            request.addProperty(sayHelloPI);

            sayHelloPI = new PropertyInfo();
            sayHelloPI.setName("password");
            sayHelloPI.setValue(params[4]);
            sayHelloPI.setType(String.class);
            request.addProperty(sayHelloPI);

            sayHelloPI = new PropertyInfo();
            sayHelloPI.setName("classe");
            sayHelloPI.setValue(Integer.parseInt(params[5]));
            sayHelloPI.setType(int.class);
            request.addProperty(sayHelloPI);

            sayHelloPI = new PropertyInfo();
            sayHelloPI.setName("sezione");
            sayHelloPI.setValue(params[6]);
            sayHelloPI.setType(String.class);
            request.addProperty(sayHelloPI);

            sayHelloPI = new PropertyInfo();
            sayHelloPI.setName("indirizzo");
            sayHelloPI.setValue(params[7]);
            sayHelloPI.setType(String.class);
            request.addProperty(sayHelloPI);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {
                // Invoke web service
                androidHttpTransport.call(SOAP_ACTION, envelope);
                // Get the response
                SoapObject response = (SoapObject) envelope.bodyIn;
                // Assign it to resTxt variable static variable
                ritorna=response.getProperty("return") + "stud";

            } catch (Exception e) {
                //Print error
                e.printStackTrace();
                //Assign error message to resTxt
                // resTxt = "Error occured";
            }
            //Return resTxt to calling object
            return ritorna;
        }






        return null;
    }





    protected  void onPostExecute(String esito){
      if(esito.equals("true")){

          Intent intent = new Intent(context,MainActivity.class);
          context.startActivity(intent);
      }
      else if(esito.equals("trueprof")){
          Intent intent = new Intent(context,LoginActivity.class);
          context.startActivity(intent);
      }
      else if(esito.equals("truestud")){
          Intent intent = new Intent(context,LoginActivity.class);
          context.startActivity(intent);
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


