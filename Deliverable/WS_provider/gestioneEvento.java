/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ww.tosetti.com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Emanuele
 */
@WebService(serviceName = "gestioneEvento")
public class gestioneEvento {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11171432";
    static final String USER = "sql11171432";
    static final String PASS = "7vFvmUVweQ";
    

    /**
     * Web service operation
     */
    @WebMethod(operationName = "modificaEvento")
    public boolean modificaEvento(@WebParam(name = "data") String data, @WebParam(name = "ora") String ora, @WebParam(name = "nota") String nota, @WebParam(name = "id_utente") String id_utente) {
             
        boolean modifica=false;
        Connection conn = null;
        Statement st=null;
        int idEvento=0;
        int iUtenteInt=Integer.parseInt(id_utente);
        boolean trovatoIdEvento=false;
        
        try
        {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        st = conn.createStatement();
        
        String queryCercaEventoprof="SELECT ID_Evento FROM hannoprof WHERE ID_Professore = '"+iUtenteInt+"' AND eventi.data = '"+data+"' AND eventi.ora = '"+ora+"'";
        String queryCercaEventoStud="SELECT ID_Evento FROM hannostudente WHERE ID_Studente = '"+iUtenteInt+"' AND eventi.data = '"+data+"' AND eventi.ora = '"+ora+"'";
        
        
        
        if(st.execute(queryCercaEventoprof)==true)
        { 
            ResultSet rs = st.executeQuery(queryCercaEventoprof);
            if (rs!=null) 
                {
                    while(rs.next())
                        idEvento = rs.getInt("ID_Evento");

                }
               if(idEvento!=0)
                { 
                    trovatoIdEvento=true;
                }
        }
        else
            {
                
                if(st.execute(queryCercaEventoStud)==true)
                    {
                       ResultSet rs = st.executeQuery(queryCercaEventoStud);
                        if (rs!=null) 
                            {
                                while(rs.next())
                                    idEvento = rs.getInt("ID_Evento");

                            }
                           if(idEvento!=0)
                            { 
                                trovatoIdEvento=true;
                            }
                    }
                
            }
        if(trovatoIdEvento==true)
        {
            String updateEvento = "UPDATE evento SET data = '"+data+"' AND ora = '"+ora+"' AND note = '"+nota+"' WHERE idevento = '"+idEvento+"' ";
       
            if(st.executeUpdate(updateEvento)==1)
            {
                modifica=true;
            }
            else
            {                
                modifica=false;
            }
        }
            st.close();
            conn.close();
        }
        catch(SQLException se){
             se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
           try{
               if(st!=null)
                   st.close();
               try{
                   if(conn!=null)
                       conn.close();
               }catch(SQLException se){
                   se.printStackTrace();
               }
           }catch(SQLException ex){
                Logger.getLogger(gestioneEvento.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
        
        return modifica;
    }
    /**
     * Web service operation
     */

    /**
     * Web service operation
     */
    @WebMethod(operationName = "inserisciEvento")
    public boolean inserisciEvento(@WebParam(name = "data") String data, @WebParam(name = "ora") String ora, @WebParam(name = "nota") String nota, @WebParam(name = "tipo") int tipo, @WebParam(name = "idUtente") String idUtente) {
        boolean inserimetoSuccessfull=false;
        boolean ok=false;
        Connection conn = null;
        Statement st=null; 
        int idEvento =0;
        int idUtenteInt=Integer.parseInt(idUtente);
        try
        {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
          st = conn.createStatement();
            String inserisciEvento = "INSERT INTO eventi (data,ora,note) VALUES ('"+data+"','"+ora+"','"+nota+"')";
            
            String cercaIdEvento="SELECT ID_Evento FROM eventi WHERE data = '"+data+"' AND ora = '"+ora+"'";
           
            if(st.executeUpdate(inserisciEvento)==1)
                inserimetoSuccessfull=true;
            else
            {                
                inserimetoSuccessfull=false;
            }
            if(inserimetoSuccessfull==true && tipo==0)
            {
              ResultSet rs = st.executeQuery(cercaIdEvento);
            if (rs!=null) 
            {
                while(rs.next())
                    idEvento = rs.getInt("ID_Evento");
                      
            }
                String inserisciProf = "INSERT INTO hannoprof (ID_Evento,ID_Professore) VALUES ('"+idEvento+"','"+idUtenteInt+"')";
                if(st.executeUpdate(inserisciProf)==1)
                    {
                        ok=true;
                    }
            }
            else if(inserimetoSuccessfull==true && tipo==1)
            {
                ResultSet rs = st.executeQuery(cercaIdEvento);
            if (rs!=null) 
            {
                while(rs.next())
                    idEvento = rs.getInt("ID_Evento");
                      
            }
                String inserisciStudente = "INSERT INTO hannostud (ID_Evento,ID_Studente) VALUES ('"+idEvento+"','"+idUtenteInt+"')";
                if(st.executeUpdate(inserisciStudente)==1)
                    {
                        ok=true;
                    }
            }
                
                
            
                
            
            
            st.close();
            conn.close();
       }catch(SQLException se){
           se.printStackTrace();
           
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
           try{
              if(st!=null)
                 st.close();
           }catch(SQLException se2){  
              
           }
           try{
              if(conn!=null)
                 conn.close();
           }catch(SQLException se){
              se.printStackTrace();
           }
        }
        
        return ok;
        
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "eliminaEvento")
    public boolean eliminaEvento(@WebParam(name = "data") String data, @WebParam(name = "ora") String ora, @WebParam(name = "idUtente") int idUtente) {
         boolean elimina=false;
        boolean elimina2=false;
        boolean trovaEvento=false;
        Connection conn = null;
        Statement st=null;
        int idEvento=0;
        
        
        try
        {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        st = conn.createStatement();
        
        String queryCercaEvento="SELECT ID_Evento FROM eventi WHERE data = '"+data+"' AND ora = '"+ora+"'";
       
        
        
       
        if(st.execute(queryCercaEvento)==true)
        {
            
             trovaEvento=true;
        }
        else
            {
                //metti un errore dove dici che non hai trovato la corrispondenza
                 trovaEvento=false;
            }
        if(trovaEvento==true)
        {
            
            ResultSet rs = st.executeQuery(queryCercaEvento);
            if (rs!=null) 
            {
                while(rs.next())
                    idEvento = rs.getInt("ID_Evento");
                    
            }
            String eliminaEvento = "DELETE FROM eventi WHERE ID_Evento = '"+idEvento+"'";
            String eliminaEvento2 = "DELETE FROM hannoprof WHERE ID_Evento = '"+idEvento+"'";
            String eliminaEvento3 = "DELETE FROM hannostudente WHERE ID_Evento = '"+idEvento+"'";
            if(st.executeUpdate(eliminaEvento)==1)
            {
                if(st.executeUpdate(eliminaEvento2)==1 || st.executeUpdate(eliminaEvento3)==1)
                    {
                            
                            elimina2=true;
                        
                    }
            }
            else
            {                
                elimina2=false;
            }
           } 
            st.close();
            conn.close();
        }
        catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
           try{
               if(st!=null)
                   st.close();
               try{
                   if(conn!=null)
                       conn.close();
               }catch(SQLException se){
                   se.printStackTrace();
               }
           }catch(SQLException ex){
                Logger.getLogger(gestioneEvento.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
        
        return elimina2;
    }


}
