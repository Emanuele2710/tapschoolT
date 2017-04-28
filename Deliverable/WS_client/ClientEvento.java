/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientevento;

/**
 *
 * @author Emanuele
 */
public class ClientEvento {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String data="2017/05/27";
        String nota="assemblea";
        String ora="12:00:00";
        int tipo=0;
        int idUtente=001;
        
        if(eliminaEvento(data,ora,idUtente)==true)
            {
                System.out.print("inserimento eseguito correttamante");
            }   
        else
            {
                System.out.print("inserimento non eseguito correttamante");
            }
        /*if(inserisciEvento(data,ora,nota,tipo,idUtente)==true)
            {
                System.out.print("inserimento eseguito correttamante");
            }   
        else
            {
                System.out.print("inserimento non eseguito correttamante");
            }  */
    }

    private static boolean inserisciEvento(java.lang.String data, java.lang.String ora, java.lang.String nota, int tipo, java.lang.String idUtente) {
        ww.tosetti.com.GestioneEvento_Service service = new ww.tosetti.com.GestioneEvento_Service();
        ww.tosetti.com.GestioneEvento port = service.getGestioneEventoPort();
        return port.inserisciEvento(data, ora, nota, tipo, idUtente);
    }

    private static boolean modificaEvento(java.lang.String data, java.lang.String ora, java.lang.String nota, java.lang.String idUtente) {
        ww.tosetti.com.GestioneEvento_Service service = new ww.tosetti.com.GestioneEvento_Service();
        ww.tosetti.com.GestioneEvento port = service.getGestioneEventoPort();
        return port.modificaEvento(data, ora, nota, idUtente);
    }

    private static boolean eliminaEvento(java.lang.String data, java.lang.String ora, int idUtente) {
        ww.tosetti.com.GestioneEvento_Service service = new ww.tosetti.com.GestioneEvento_Service();
        ww.tosetti.com.GestioneEvento port = service.getGestioneEventoPort();
        return port.eliminaEvento(data, ora, idUtente);
    }

    

    

    
    
    
}
