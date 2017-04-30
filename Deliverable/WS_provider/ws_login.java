/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vise.login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fabio
 */
@WebService(serviceName = "ws_login")
public class ws_login {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11171432";

    static final String USER = "sql11171432";
    static final String PASS = "7vFvmUVweQ";

    private String ToMD5(String string) {
        StringBuffer stringMD5 = new StringBuffer();

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(string.getBytes());
            byte[] digest = md.digest();
            for (byte b : digest) {
                stringMD5.append(String.format("%02x", b & 0xff));
            }
        } catch (NoSuchAlgorithmException e3) {
            System.err.println(e3);
        }
        return stringMD5.toString();
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "login")
    public List<String> login(@WebParam(name = "username") String username, @WebParam(name = "password") String password) {
        //TODO write your implementation code here:
        Connection conn = null;
        Statement stmt = null;
        boolean trovato = false;
        List<String> arrayList = new ArrayList<String>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql = "SELECT * FROM studenti WHERE username = '" + username + "' AND password = '" + ToMD5(password) + "'";
            if (stmt.execute(sql) == true) {
                trovato = true;
                arrayList.add("true");
                arrayList.add("0");
            } else {
                sql = "SELECT * FROM professori  WHERE username = '" + username + "' AND password = '" + ToMD5(password) + '"';
                if (stmt.execute(sql) == true) {
                    trovato = true;
                    arrayList.add("true");
                    arrayList.add("1");
                }
            }
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            arrayList.add(se.toString());
        } catch (Exception e) {
            arrayList.add(e.toString());
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
                arrayList.add(se2.toString());
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
                arrayList.add(se.toString());
            }
        }

        if (trovato == false) {
            arrayList.add("false");
            arrayList.add(null);
        }

        return arrayList;

    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "RegistraStudente")
    public boolean RegistraStudente(@WebParam(name = "nome") String nome, @WebParam(name = "cognome") String cognome, @WebParam(name = "username") String username, @WebParam(name = "password") String password, @WebParam(name = "classe") int classe, @WebParam(name = "sezione") String sezione, @WebParam(name = "indirizzo") String indirizzo) {

        Connection conn = null;
        Statement stmt = null;
        int id_classe = 0;
        boolean ritorna = true;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql = "SELECT * FROM classi WHERE classe = '" + classe + "' AND sezione = '" + sezione + "' AND indirizzo = '" + indirizzo + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs != null) {
                while (rs.next()) {
                    id_classe = rs.getInt("ID");
                }
            } else {
                ritorna = false;
            }

            sql = "INSERT INTO studenti (nome,cognome,username,password,classe) VALUES ('" + nome + "','" + cognome + "','" + username + "','" + ToMD5(password) + "','" + id_classe + "')";

            if (stmt.executeUpdate(sql) == 1) {

            } else {
                ritorna = false;
            }

            stmt.close();
            conn.close();

        } catch (SQLException se) {
            ritorna = false;
            //  return se.toString();
        } catch (Exception e) {
            // return e.toString();
            ritorna = false;
            // System.out.println(e.toString());
            // e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
                ritorna = false;
                //   return se2.toString();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
                // return se.toString();
                ritorna = false;

            }
        }

        return ritorna;
    }

    @WebMethod(operationName = "RegistraProfessore")
    public boolean RegistraProfessore(@WebParam(name = "nome") String nome, @WebParam(name = "cognome") String cognome, @WebParam(name = "username") String username, @WebParam(name = "password") String password, @WebParam(name = "classe") ArrayList<String> classe, @WebParam(name = "sezione") ArrayList<String> sezione, @WebParam(name = "indirizzo") ArrayList<String> indirizzo , @WebParam(name = "materia") ArrayList<String> materia) {

        Connection conn = null;
        Statement stmt = null;
        ArrayList<Integer> id_classe = new ArrayList<Integer>();
        ArrayList<Integer> id_materia = new ArrayList<Integer>();
        boolean ritorna = true;
        String sql = null;
        int id_prof=0;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            
            //prendo gli id delle classi che gli ho passato
            
            sql = "SELECT * FROM classi";
            ResultSet rs = stmt.executeQuery(sql);
            int i = 0;
            while (rs.next()) {
               if(i<classe.size())
                if (Integer.toString(rs.getInt("classe")).equals(classe.get(i)) && rs.getString("sezione").equals(sezione.get(i)) && rs.getString("indirizzo").equals(indirizzo.get(i))) {
                    id_classe.add(rs.getInt("ID"));
                    i++;
                }
            }

            
            
            sql = "INSERT INTO professori (nome,cognome,username,password) VALUES ('" + nome + "','" + cognome + "','" + username + "','" + ToMD5(password) + "')";
            if(stmt.executeUpdate(sql)==1){
                
            }else{
                return false;
            }
            
            sql = "SELECT * FROM professori WHERE username = '"+username+"'";
            rs = stmt.executeQuery(sql);
             while (rs.next()) {
                id_prof=rs.getInt("ID");
            }
            
            
            
            
            for (int k = 0; k < i; k++) {

                 sql = "INSERT INTO tengono (ID_Professore,ID_Classe) VALUES ('" + id_prof + "','" + id_classe.get(k)+ "')";
                 if (stmt.executeUpdate(sql) == 1) {
                 } else {
                    ritorna = false;
                }
              
            }
            
            sql = "SELECT * FROM materie";
            rs = stmt.executeQuery(sql);
            i = 0;
            while (rs.next()) {
                for(int k =0;k<materia.size();k++)
                    if (rs.getString("nome").equals(materia.get(k))) {
                        id_materia.add(rs.getInt("ID"));
                        i++;
                    }
            }
            for(int k=0;k<i;k++){
             sql = "INSERT INTO insegnano (ID_Professore,ID_Materia) VALUES ('" + id_prof + "','" + id_materia.get(k)+ "')";
                 if (stmt.executeUpdate(sql) == 1) {

                } else {
                    ritorna = false;
                }
            
            }
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            ritorna = false;
            //  return se.toString();
        } catch (Exception e) {
            // return e.toString();
            ritorna = false;
            // System.out.println(e.toString());
            // e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
                ritorna = false;
                //   return se2.toString();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
                // return se.toString();
                ritorna = false;

            }
        }

        return ritorna;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "GetMaterie")
    public ArrayList<String> GetMaterie() {
        
        Connection conn = null;
        Statement stmt = null;
        boolean trovato = false;
        ArrayList<String> arrayList = new ArrayList<String>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql = "SELECT * FROM materie";
            ResultSet rs = stmt.executeQuery(sql);
            int i = 0;
            while (rs.next()) {
                arrayList.add(rs.getString("nome"));
            }
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            arrayList.add(se.toString());
        } catch (Exception e) {
            arrayList.add(e.toString());
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
                arrayList.add(se2.toString());
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
                arrayList.add(se.toString());
            }
        }

        return arrayList;
    }

}
