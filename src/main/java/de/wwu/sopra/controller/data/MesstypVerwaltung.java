/**
 * 
 */
package de.wwu.sopra.controller.data;

import java.util.ArrayList;

import de.wwu.sopra.model.Deckeltyp;
import de.wwu.sopra.model.Messtyp;


/**
 * @author Gruppe 5
 * 
 * Klasse zum Verwalten von Messtypen
 *
 */
public class MesstypVerwaltung {
    
    
    private static MesstypVerwaltung instance;
    private ArrayList<Messtyp> messtypen = new ArrayList<Messtyp>();
    
    private MesstypVerwaltung() {
        
    }
    

    /**
     * MesstypVerwaltung als Singleton um sicherzustellen, dass nur eine
     * MesstypVerwaltung mit korrektem, synchronisiertem Inhalt existiert
     * @return die MesstypVerwaltung instance
     */
    public static synchronized MesstypVerwaltung getInstance() {
        if(instance == null) {
            MesstypVerwaltung.instance = new MesstypVerwaltung();
        }
        return instance;
            
    }
    
    /**
     * Fuegt einen Messtyp der Liste von Messtypen hinzu
     * @param messtyp Der Messtyp, der hinzugefuegt werden sollen
     */
    
    public void addMesstyp(Messtyp messtyp) {
        if(messtyp != null) {
            messtypen.add(messtyp);
        }
    }
    
    /**
     * Loescht einen Messtyp aus der Liste von Messtypen
     * @param messtyp Der Messtyp, der geloescht werden soll
     */
    public void removeMesstyp(Messtyp messtyp) {
        messtypen.remove(messtyp);
    }
    
    /**
     * Prueft, ob Messtyp schon Teil der Kollektion an Messtypen ist
     * @param messtyp der Messtyp von dem man wissen will, ob er Teil der Kolletion
     *              ist
     * @return true, wenn der Messtyp Teil der Kollektion ist, sonst false
     */
    public boolean hasMesstyp(Messtyp messtyp) {
        if (messtypen.contains(messtyp)) {
            return true;
        }
        return false;
    }
    
    /**
     * Gibt alle verfuegbaren Messtypen zurueck
     * @return Die Liste von Messtypen
     */
    
    public ArrayList<Messtyp> getMesstypen(){
        return messtypen;
    }
    /**
     * Setzt die Messtypenliste

     * @param messtypen Die Liste von Messtypen

     */
    public void setMesstypen(ArrayList<Messtyp> messtypen) {
        this.messtypen = messtypen;
    }
    
    /**
     * Prueft, ob es schon einen Messtypen mit einem bestimmten Namen gibt
     * @param name Name fuer den geprueft werden soll, ob er schon vergeben ist
     * @return true, wenn der Name noch nicht vergeben ist, sonst false
     */
    public boolean nameIsUnique(String name) {
        for(Messtyp m: messtypen) {
            if(m.getName().equals(name)) {
                return false;
            }
        }
        return true;
        
        
    }
}
