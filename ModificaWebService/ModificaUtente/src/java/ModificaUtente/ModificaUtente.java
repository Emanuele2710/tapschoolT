/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModificaUtente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Leo
 */
public class ModificaUtente {
    public static void main(String[] args) {
        
    InputStreamReader reader = new InputStreamReader(System.in);
    BufferedReader myInput = new BufferedReader(reader);
        
    String Email="";
    String Password="";
    String Password1="";     
    String Nome = "";
    String Cognome="";
        

    try {
        System.out.print("Nuovo Nome: ");
        Nome = myInput.readLine();
        
        System.out.print("Nuovo Cognome: ");
        Cognome = myInput.readLine();
        
        System.out.print("Nuova Email: ");
        Email = myInput.readLine();
        
        System.out.print("Nuova Password: ");
        Password1 = myInput.readLine();

    } catch (IOException e) {

        
    }
}
    
}
