
package org.vise.modificaUtente;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Fabio
 */
@WebService(serviceName = "ws_modificaUtente")
public class ws_modificaUtente {

    /**
     * Web service operation
     */
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11171432";

    static final String USER = "sql11171432";
    static final String PASS = "7vFvmUVweQ";
    @WebMethod(operationName = "modificaNome")
    public boolean modificaNome(@WebParam(name = "id") int id, @WebParam(name = "tipo") int tipo, @WebParam(name = "nome") String nome) {
        //TODO write your implementation code here:
        Connection conn = null;
        Statement stmt = null;
        
        boolean modificato = false;
        
        String sql="";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            if(tipo==0)
            {
                sql = "UPDATE Professori SET nome = '"+nome+"' WHERE ID = '"+id+"'";
                if (stmt.execute(sql) == true) 
                    modificato = true;
                
                
            }
            else if(tipo==1)
            {
                sql = "UPDATE Studenti SET nome = '"+nome+"' WHERE ID = '"+id+"'";
                if (stmt.execute(sql) == true) 
                    modificato = true;
                    
                    
                
            }
             else
            {
                modificato=false;
            }
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            
        } catch (Exception e) {
            
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
                
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
               
            }
        }

        

        return modificato;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "modificaCognome")
    public boolean modificaCognome(@WebParam(name = "id") int id, @WebParam(name = "tipo") int tipo, @WebParam(name = "cognome") String cognome) {
        //TODO write your implementation code here:
         Connection conn = null;
        Statement stmt = null;
        
        boolean modificato = false;
        
        String sql="";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            if(tipo==0)
            {
                sql = "UPDATE Professori SET cognome = '"+cognome+"' WHERE ID = '"+id+"'";
                if (stmt.execute(sql) == true) 
                    modificato = true;
                
                
            }
            else if(tipo==1)
            {
                sql = "UPDATE Studenti SET cognome = '"+cognome+"' WHERE ID = '"+id+"'";
                if (stmt.execute(sql) == true) 
                    modificato = true;
                    
                    
                
            }
            else
            {
                modificato=false;
            }
            
            
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            
        } catch (Exception e) {
            
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
                
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
               
            }
        }

        

        return modificato;
    }
}
