/**
 * 
 */
package de.wwu.sopra.controller.data;

import java.util.ArrayList;

import de.wwu.sopra.model.Behaeltertyp;
import de.wwu.sopra.model.Deckeltyp;
import de.wwu.sopra.model.Messtyp;

/**
 * @author Gruppe 5
 *
 * Klasse zum verwalten von Deckeltypen
 */
public class DeckeltypVerwaltung {
    
    private static DeckeltypVerwaltung instance;
    private ArrayList<Deckeltyp> deckeltypen = new ArrayList<Deckeltyp>();
    
    private DeckeltypVerwaltung() {
        
    }
    

    /**
     * DeckeltypVerwaltung als Singleton um sicherzustellen, dass nur eine
     * DeckeltypVerwaltung mit korrektem, synchronisiertem Inhalt existiert
     * @return die ProbenKategorieVerwaltung instance
     */
    public static synchronized DeckeltypVerwaltung getInstance() {
        if(instance == null) {
            DeckeltypVerwaltung.instance = new DeckeltypVerwaltung();
        }
        return instance;
    }
    /**
    * Prueft, ob es schon einen Deckeltypen mit einem bestimmten Namen gibt
    * @param name Name fuer den geprueft werden soll, ob er schon vergeben ist
    * @return true, wenn der Name noch nicht vergeben ist, sonst false
    */
   public boolean nameIsUnique(String name) {
       boolean unique = true;
       for(Deckeltyp d: deckeltypen) {
           if(d.getName().equals(name)) {
               unique = false;
           }
       }
       return unique;
   }
    /**
     * Fuegt einen Deckeltypen zu den vorhandenen Deckeltypen hinzu
     * @param deckeltyp Der Deckeltyp
     */
    public void addDeckeltyp(Deckeltyp deckeltyp) {
        if(deckeltyp != null) {
            deckeltypen.add(deckeltyp);
        }
    }
    
    /**
     * Prueft, ob Deckeltyp schon Teil der Kollektion an Deckeltypen ist
     * @param deckeltyp der Deckeltyp von dem man wissen will, ob er Teil der Kolletion
     *              ist
     * @return true, wenn der Deckeltyp Teil der Kollektion ist, sonst false
     */
    public boolean hasDeckeltyp(Deckeltyp deckeltyp) {
        if (deckeltypen.contains(deckeltyp)) {
            return true; 
        }
        return false; 
    }
    
    /**
     * Gibt alle verfuegbaren Deckeltypen zurueck
     * @return Die Liste von Deckeltypen
     */
    
    public ArrayList<Deckeltyp> getDeckeltypen(){
        return deckeltypen;
    }
    /**
     * Setzt die Liste aller Deckeltypen
     * @param deckeltypen Die Liste von allen Deckeltypen
     */
    public void setDeckeltypen(ArrayList<Deckeltyp> deckeltypen){
        this.deckeltypen = deckeltypen;
        
    }
    
    
    

}
