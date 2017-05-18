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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


@WebService(serviceName = "ws_login")
public class ws_login {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/gestioneorari";

    static final String USER = "root";
    static final String PASS = "";

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
	 
	@WebMethod(operationName = "logout")
	 public String logout() {
		 return "true";
	 }
	 
    @WebMethod(operationName = "login")
    public List<String> login(@WebParam(name = "username") String username, @WebParam(name = "password") String password) {
        //TODO write your implementation code here:
        Connection conn = null;
        Statement stmt = null;
        boolean trovato = false;
        List<String> arrayList = new ArrayList<String>();
        ResultSet rs;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql = "SELECT * FROM studenti WHERE username = '" + username + "' AND password = '" + ToMD5(password) + "'";
            try {
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    trovato = true;
                    arrayList.add("true");
                    arrayList.add(Integer.toString(rs.getInt("ID")));
                    arrayList.add("0");
                }
            } catch (Exception e) {
            }

            if (trovato == false) {
                sql = "SELECT * FROM professori  WHERE username = '" + username + "' AND password = '" + ToMD5(password) + "'";
                try {
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        trovato = true;
                        arrayList.add("true");
                        arrayList.add(Integer.toString(rs.getInt("ID")));
                        arrayList.add("1");
                    }
                } catch (Exception e) {

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
    public boolean RegistraProfessore(@WebParam(name = "nome") String nome, @WebParam(name = "cognome") String cognome, @WebParam(name = "username") String username, @WebParam(name = "password") String password, @WebParam(name = "classe") ArrayList<String> classe, @WebParam(name = "sezione") ArrayList<String> sezione, @WebParam(name = "indirizzo") ArrayList<String> indirizzo, @WebParam(name = "materia") ArrayList<String> materia) {

        Connection conn = null;
        Statement stmt = null;
        ArrayList<Integer> id_classe = new ArrayList<Integer>();
        ArrayList<Integer> id_materia = new ArrayList<Integer>();
        boolean ritorna = true;
        String sql = null;
        int id_prof = 0;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            //prendo gli id delle classi che gli ho passato
            sql = "SELECT * FROM classi";
            ResultSet rs = stmt.executeQuery(sql);
            int i = 0;
            while (rs.next()) {

                if (i < classe.size()) {
                    if (Integer.toString(rs.getInt("classe")).equals(classe.get(i)) && rs.getString("sezione").equals(sezione.get(i)) && rs.getString("indirizzo").equals(indirizzo.get(i))) {
                        id_classe.add(rs.getInt("ID"));
                        i++;
                    }
                }
            }

            sql = "INSERT INTO professori (nome,cognome,username,password) VALUES ('" + nome + "','" + cognome + "','" + username + "','" + ToMD5(password) + "')";
            if (stmt.executeUpdate(sql) == 1) {

            } else {
                return false;
            }

            sql = "SELECT * FROM professori WHERE username = '" + username + "'";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                id_prof = rs.getInt("ID");
            }

            for (int k = 0; k < i; k++) {

                sql = "INSERT INTO tengono (ID_Professore,ID_Classe) VALUES ('" + id_prof + "','" + id_classe.get(k) + "')";
                if (stmt.executeUpdate(sql) == 1) {
                } else {
                    ritorna = false;
                }

            }

            sql = "SELECT * FROM materie";
            rs = stmt.executeQuery(sql);
            i = 0;
            while (rs.next()) {
                for (int k = 0; k < materia.size(); k++) {
                    if (rs.getString("nome").equals(materia.get(k))) {
                        id_materia.add(rs.getInt("ID"));
                        i++;
                    }
                }
            }
            for (int k = 0; k < i; k++) {
                sql = "INSERT INTO insegnano (ID_Professore,ID_Materia) VALUES ('" + id_prof + "','" + id_materia.get(k) + "')";
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

    /**
     * Web service operation
     */
    @WebMethod(operationName = "GetEventi")
    public String GetEventi(@WebParam(name = "id_utente") int id_utente, @WebParam(name = "tipo") int tipo, @WebParam(name = "data") String data) {
        Connection conn = null;
        Statement stmt = null;

        String testo = "";
        ArrayList<Integer> arrayListIdEventi = new ArrayList<Integer>();
        ArrayList<String> arrayListTesti = new ArrayList<String>();
        String sql = "";
        String idEventi = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            sql = "SELECT * FROM eventi WHERE data= '" + data + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                arrayListIdEventi.add(rs.getInt("ID_Evento"));
            }

            for (int i = 0; i < arrayListIdEventi.size(); i++) {
                if (i == 0) {
                    idEventi += Integer.toString(arrayListIdEventi.get(i)) + "'";
                } else {
                    idEventi += " OR ID_Evento = '" + Integer.toString(arrayListIdEventi.get(i)) + "'";
                }
            }

            if (tipo == 0) {
                sql = "SELECT * FROM hannostud WHERE Id_Studente = '" + id_utente + "' AND Id_Evento = '" + idEventi;
            } else if (tipo == 1) {
                sql = "SELECT * FROM hannoprof WHERE Id_Professore = '" + id_utente + "' AND Id_Evento = '" + idEventi;
            }
            arrayListIdEventi = new ArrayList<Integer>();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                arrayListIdEventi.add(rs.getInt("ID_Evento"));
            }

            idEventi = "";

            for (int i = 0; i < arrayListIdEventi.size(); i++) {
                if (i == 0) {
                    idEventi += Integer.toString(arrayListIdEventi.get(i)) + "'";
                } else {
                    idEventi += " OR ID_Evento = '" + Integer.toString(arrayListIdEventi.get(i)) + "'";
                }
            }

            sql = "SELECT * FROM eventi WHERE ID_Evento='" + idEventi;
            rs = stmt.executeQuery(sql);
            int i = 0;

            while (rs.next()) {
                testo += rs.getTime("ora").toString() + " - " + rs.getString("note") + "\r\n\r\n";
                i++;
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

        return testo;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "GetClasse")
    public ArrayList<String[]> GetClassi(@WebParam(name = "idUtente") String idUtente, @WebParam(name = "tipo") String tipo) {

        Connection conn = null;
        Statement stmt = null;
        ArrayList<Integer> arrayListIdClassi = new ArrayList<>();
        ArrayList<String[]> arrayList = new ArrayList<>();
        String[] vett = new String[3];
        String sql = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            if (tipo.equals("0")) {
                sql = "SELECT * FROM studenti WHERE ID='" + idUtente + "'";
                ResultSet rs = stmt.executeQuery(sql);

                int id = 0;

                while (rs.next()) {
                    id = rs.getInt("classe");
                }

                sql = "SELECT * FROM classi WHERE ID='" + id + "'";
                rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    vett[0] = Integer.toString(rs.getInt("classe"));
                    vett[1] = rs.getString("sezione");
                    vett[2] = rs.getString("indirizzo");
                }

                arrayList.add(vett);

            } else if (tipo.equals("1")) {
                sql = "SELECT * FROM tengono WHERE ID_Professore = '" + idUtente + "'";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    arrayListIdClassi.add(rs.getInt("ID_Classe"));
                }

                String idClassi = "";

                for (int i = 0; i < arrayListIdClassi.size(); i++) {
                    if (i == 0) {
                        idClassi += Integer.toString(arrayListIdClassi.get(i)) + "'";
                    } else {
                        idClassi += " OR ID = '" + Integer.toString(arrayListIdClassi.get(i)) + "'";
                    }
                }

                sql = "SELECT * FROM classi WHERE ID='" + idClassi;
                rs = stmt.executeQuery(sql);
                int i = 0;

                while (rs.next()) {
                    vett[0] = Integer.toString(rs.getInt("classe"));
                    vett[1] = rs.getString("sezione");
                    vett[2] = rs.getString("indirizzo");
                    arrayList.add(vett);
                }

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

        return arrayList;
    }

    @WebMethod(operationName = "modificaNome")
    public boolean modificaNome(@WebParam(name = "id") int id, @WebParam(name = "tipo") int tipo, @WebParam(name = "nome") String nome) {
        //TODO write your implementation code here:
        Connection conn = null;
        Statement stmt = null;

        boolean modificato = false;

        String sql = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            if (tipo == 1) {
                sql = "UPDATE professori SET nome = '" + nome + "' WHERE ID = '" + id + "'";
                if (stmt.executeUpdate(sql) == 1) {
                    modificato = true;
                }

            } else if (tipo == 0) {
                sql = "UPDATE studenti SET nome = '" + nome + "' WHERE ID = '" + id + "'";
                if (stmt.executeUpdate(sql) == 1) {
                    modificato = true;
                }

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

        String sql = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            if (tipo == 1) {
                sql = "UPDATE professori SET cognome = '" + cognome + "' WHERE ID = '" + id + "'";
                if (stmt.executeUpdate(sql) == 1) {
                    modificato = true;
                }

            } else if (tipo == 0) {
                sql = "UPDATE studenti SET cognome = '" + cognome + "' WHERE ID = '" + id + "'";
                if (stmt.executeUpdate(sql) == 1) {
                    modificato = true;
                }

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

    @WebMethod(operationName = "modificaEvento")
    public boolean modificaEvento(@WebParam(name = "oldData") String data, @WebParam(name = "oldOra") String ora, @WebParam(name = "nota") String nota, @WebParam(name = "newData") String data1, @WebParam(name = "newOra") String ora1, @WebParam(name = "idUtente") String idUtente, @WebParam(name = "tipo") String tipo ) {

        boolean modifica = false;
        Connection conn = null;
        Statement stmt = null;
        int idEvento = 0;
        String sql="";
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            sql = "SELECT * FROM eventi";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                if(rs.getDate("data").toString().equals(data) && rs.getTime("ora").toString().equals(ora)){
                    idEvento=rs.getInt("ID_Evento");
                }
            }

            sql = "UPDATE eventi SET data = '" + data1 + "',ora='"+ora1+"',note='"+nota+"' WHERE ID_Evento = '" + idEvento + "'";
            
            if(stmt.executeUpdate(sql)==1)
                return true;
            
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            } catch (SQLException ex) {
                // Logger.getLogger(gestioneEvento.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return false;
    }

    /**
     * Web service operation
     */

    /**
     * Web service operation
     */
    @WebMethod(operationName = "inserisciEvento")
    public boolean inserisciEvento(@WebParam(name = "data") String data, @WebParam(name = "ora") String ora, @WebParam(name = "nota") String nota, @WebParam(name = "tipo") int tipo, @WebParam(name = "idUtente") String idUtente) {
        boolean inserimetoSuccessfull = false;
        boolean ok = false;
        Connection conn = null;
        Statement st = null;
        int idEvento = 0;
        int idUtenteInt = Integer.parseInt(idUtente);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            st = conn.createStatement();
            String inserisciEvento = "INSERT INTO eventi (data,ora,note) VALUES ('" + data + "','" + ora + "','" + nota + "')";

            String cercaIdEvento = "SELECT ID_Evento FROM eventi WHERE data = '" + data + "' AND ora = '" + ora + "'";

            if (st.executeUpdate(inserisciEvento) == 1) {
                inserimetoSuccessfull = true;
            } else {
                inserimetoSuccessfull = false;
            }
            if (inserimetoSuccessfull == true && tipo == 1) {
                ResultSet rs = st.executeQuery(cercaIdEvento);
                if (rs != null) {
                    while (rs.next()) {
                        idEvento = rs.getInt("ID_Evento");
                    }

                }
                String inserisciProf = "INSERT INTO hannoprof (ID_Evento,ID_Professore) VALUES ('" + idEvento + "','" + idUtenteInt + "')";
                if (st.executeUpdate(inserisciProf) == 1) {
                    ok = true;
                }
            } else if (inserimetoSuccessfull == true && tipo == 0) {
                ResultSet rs = st.executeQuery(cercaIdEvento);
                if (rs != null) {
                    while (rs.next()) {
                        idEvento = rs.getInt("ID_Evento");
                    }

                }
                String inserisciStudente = "INSERT INTO hannostud (ID_Evento,ID_Studente) VALUES ('" + idEvento + "','" + idUtenteInt + "')";
                if (st.executeUpdate(inserisciStudente) == 1) {
                    ok = true;
                }
            }

            st.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
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

        return ok;

    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "eliminaEvento")
    public boolean eliminaEvento(@WebParam(name = "data") String data, @WebParam(name = "ora") String ora, @WebParam(name = "idUtente") int idUtente) {
        boolean elimina = false;
        boolean elimina2 = false;
        boolean trovaEvento = false;
        Connection conn = null;
        Statement st = null;
        int idEvento = 0;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            st = conn.createStatement();

            String queryCercaEvento = "SELECT ID_Evento FROM eventi WHERE data = '" + data + "' AND ora = '" + ora + "'";

            if (st.execute(queryCercaEvento) == true) {

                trovaEvento = true;
            } else {
                //metti un errore dove dici che non hai trovato la corrispondenza
                trovaEvento = false;
            }
            if (trovaEvento == true) {

                ResultSet rs = st.executeQuery(queryCercaEvento);
                if (rs != null) {
                    while (rs.next()) {
                        idEvento = rs.getInt("ID_Evento");
                    }

                }
                String eliminaEvento = "DELETE FROM eventi WHERE ID_Evento = '" + idEvento + "'";
                String eliminaEvento2 = "DELETE FROM hannoprof WHERE ID_Evento = '" + idEvento + "'";
                String eliminaEvento3 = "DELETE FROM hannostudente WHERE ID_Evento = '" + idEvento + "'";
                if (st.executeUpdate(eliminaEvento) == 1) {
                    if (st.executeUpdate(eliminaEvento2) == 1 || st.executeUpdate(eliminaEvento3) == 1) {

                        elimina2 = true;

                    }
                } else {
                    elimina2 = false;
                }
            }
            st.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            } catch (SQLException ex) {
                //  Logger.getLogger(gestioneEvento.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return elimina2;
    }

    @WebMethod(operationName = "GetUtente")
    public ArrayList<String> GetUtente(@WebParam(name = "idUtente") String idUtente, @WebParam(name = "tipo") String tipo) {

        Connection conn = null;
        Statement stmt = null;
        String query = "";
        ResultSet rs = null;
        ArrayList<String> ritorno = new ArrayList<String>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            if (tipo.equals("0")) {
                query = "SELECT * FROM studenti WHERE ID = '" + idUtente + "'";
            } else if (tipo.equals("1")) {
                query = "SELECT * FROM professori WHERE ID = '" + idUtente + "'";
            }

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                ritorno.add("true");
                ritorno.add(rs.getString("nome"));
                ritorno.add(rs.getString("cognome"));

            }

            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
            ritorno.add("false");
        } catch (Exception e) {
            e.printStackTrace();
            ritorno.add("false");
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                    ritorno.add("false");
                }
            } catch (SQLException ex) {
                //  Logger.getLogger(gestioneEvento.class.getName()).log(Level.SEVERE, null, ex);
                ritorno.add("false");
            }
        }

        return ritorno;
    }

    @WebMethod(operationName = "modificaPass")
    public boolean modificaPass(@WebParam(name = "tipo") int tipo, @WebParam(name = "idUtente") int idUtente, @WebParam(name = "passwordVecchia") String passwordVecchia, @WebParam(name = "passwordNuova") String passwordNuova) {
        Connection conn = null;
        Statement stmt = null;
        String a = passwordVecchia;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql = "";
            if (tipo == 0) {
                sql = "UPDATE studenti SET password = '" + ToMD5(passwordNuova) + "' WHERE ID = '" + idUtente + "' AND password = '" + ToMD5(passwordVecchia) + "'";
            } else if (tipo == 1) {
                sql = "UPDATE professori SET password = '" + ToMD5(passwordNuova) + "' WHERE ID = '" + idUtente + "' AND password = '" + ToMD5(passwordVecchia) + "'";
            }

            if (stmt.executeUpdate(sql) == 1) {
                return true;
            }

            stmt.close();
            conn.close();

        } catch (SQLException se) {
            se.printStackTrace();
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
        return false;
    }

    @WebMethod(operationName = "GetDate")
    public ArrayList<String[]> GetDate(@WebParam(name = "idUtente") int idUtente, @WebParam(name = "tipo") int tipo) {
        Connection conn = null;
        Statement stmt = null;
        ArrayList<Integer> arrayList = new ArrayList<>();
        String sql = "";

        ArrayList<String[]> arrayDate = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            ResultSet rs = null;
            stmt = conn.createStatement();
            if (tipo == 0) {
                sql = "SELECT * FROM hannostud";
            } else if (tipo == 1) {
                sql = "SELECT * FROM hannoprof";
            }

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                if (tipo == 0) {
                    if (rs.getInt("ID_Studente") == idUtente) {
                        arrayList.add(rs.getInt("ID_Evento"));
                    }
                } else if (tipo == 1) {
                    if (rs.getInt("ID_Professore") == idUtente) {
                        arrayList.add(rs.getInt("ID_Evento"));
                    }
                }
            }

            String idEventi = "";

            for (int i = 0; i < arrayList.size(); i++) {
                if (i == 0) {
                    idEventi += Integer.toString(arrayList.get(i)) + "'";
                } else {
                    idEventi += " OR ID_Evento = '" + Integer.toString(arrayList.get(i)) + "'";
                }
            }

            sql = "SELECT * FROM eventi WHERE ID_Evento='" + idEventi;

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String[] vett = new String[3];
                vett[0] = rs.getDate("data").toString();
                vett[1] = rs.getTime("ora").toString();
                vett[2] = rs.getString("note");
                arrayDate.add(vett);

            }

            stmt.close();
            conn.close();

        } catch (SQLException se) {
            se.printStackTrace();
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
        return arrayDate;
    }

}
