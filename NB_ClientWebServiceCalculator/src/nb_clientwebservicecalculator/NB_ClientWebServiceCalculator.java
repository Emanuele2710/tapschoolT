/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nb_clientwebservicecalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author simom
 */
public class NB_ClientWebServiceCalculator {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        BufferedReader tastiera = new BufferedReader (new InputStreamReader(System.in));
        //faccio inserire da tasteira tutte le informazioni da registrare del nuovo alunno
        String n="", c="",dN="",lN="",cF="",cR="",vR="",cl="",in="";
        System.out.println("Nome: ");
        n = tastiera.readLine();
        System.out.println("Cognome: ");
        c = tastiera.readLine();
        System.out.println("Data di nascita: ");
        dN = tastiera.readLine();
        System.out.println("Luogo di nascita: ");
        lN = tastiera.readLine();
        System.out.println("Codice Fiscale: ");
        cF = tastiera.readLine();
        System.out.println("Comune di residenza: ");
        cR = tastiera.readLine();
        System.out.println("Via di residenza: ");
        vR = tastiera.readLine();
        System.out.println("Classe: ");
        cl = tastiera.readLine();
        System.out.println("Indirizzo: ");
        in = tastiera.readLine();
        
    }

    private static String studente(java.lang.String nome, java.lang.String cognome, java.lang.String dataNascita, java.lang.String luogoNascita, java.lang.String codiceFiscale, java.lang.String comuneResidenza, java.lang.String viaResidenza, java.lang.String classe, java.lang.String indirizzo) {
        org.giodabg.calculator.CalculatorWS_Service service = new org.giodabg.calculator.CalculatorWS_Service();
        org.giodabg.calculator.CalculatorWS port = service.getCalculatorWSPort();
        return port.studente(nome, cognome, dataNascita, luogoNascita, codiceFiscale, comuneResidenza, viaResidenza, classe, indirizzo);
    }
    
}
