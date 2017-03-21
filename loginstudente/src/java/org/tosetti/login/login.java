
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tosetti.login;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Emanuele
 */
@WebService(serviceName = "login")
public class login {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "username")
    public String username(@WebParam(name = "username") String username, @WebParam(name = "password") String password) throws IOException {
        //TODO write your implementation code here:
        int okUsername=0;
        int okPassword=0;
        
        
        
        
        
        
    File file;
    file=new File("login.txt");
        if(!file.exists())
        {
             file.createNewFile();
        }
        
    String nome = "";
     String pssw = "";
System.out.print("inserisci il tuo username ");
Scanner sc = new Scanner(System.in);
 nome = sc.next(); //èer le stringhe

 System.out.print("inserisci la tua password ");
 Scanner zc = new Scanner(System.in);
 pssw=zc.next();

 FileReader f;
    f=new FileReader("login.txt");
 BufferedReader b;
    b=new BufferedReader(f);

    
String s="";
    while(true) {
      s=b.readLine();
      if(s.equals(nome)){
          okUsername=1;
          break;}
      if(s==null)
        break;
      
    }       
     
    String x="";
    while(true) {
      x=b.readLine();
      if(s.equals(pssw)){
          okPassword=1;
          break;}
      if(s==null)
        break;
      
    } 
        if(okPassword==1 && okUsername==1)
        System.out.print("sei loggato");
        else 
            System.out.print("login falliro");
            
        return null;
        
    }
}
