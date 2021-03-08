package de.wwu.sopra.controller.data;

import de.wwu.sopra.model.Behaeltertyp;
import de.wwu.sopra.model.Benutzer;

import java.util.HashSet;
import java.util.Set;

/**
 * Klasse zur Verwaltung der Behaeltertypen
 * @author Gruppe 5
 */

public class BehaelterVerwaltung {

    private static BehaelterVerwaltung instance;
    private Set<Behaeltertyp> behaeltertypSet = new HashSet<Behaeltertyp>();

    private BehaelterVerwaltung() {

    }

    /**
     * BehaelterVerwaltung als Singleton um sicherzustellen, dass nur eine
     * BehaelterVerwaltung mit korrektem, synchronisiertem Inhalt existiert
     * @return die BehaelterVerwaltung instance
     */
    public static synchronized BehaelterVerwaltung getInstance() {
        if (BehaelterVerwaltung.instance == null) {
            BehaelterVerwaltung.instance = new BehaelterVerwaltung();
        }
        return BehaelterVerwaltung.instance;
    }

    /**
     * Prueft, ob es schon einen Behaeltertypen mit einem bestimmten Namen gibt
     * @param name Name fuer den geprueft werden soll, ob er schon vergeben ist
     * @return true, wenn der Name noch nicht vergeben ist, sonst false
     */
    public boolean nameIsUnique(String name) {
        boolean unique = true;
        for(Behaeltertyp b: behaeltertypSet) {
            if(b.getName().equals(name)) {
                unique=false;
            }
        }
        return unique;
    }
    
    /**
     * fuegt einen Behaeltertypen dem Set hinzu
     * @param behaeltertyp der Behaeltertyp der hinzugefuegt werden soll
     */
    public void addBehaeltertyp(Behaeltertyp behaeltertyp) {
        behaeltertypSet.add(behaeltertyp);
    }

    /**
     * entfernt einen Behaeltertypen aus dem Set
     * @param behaeltertyp der Behaeltertyp der entfernt werden soll
     */
    public void removeBehaeltertyp(Behaeltertyp behaeltertyp) {
        behaeltertypSet.remove(behaeltertyp);
    }

    /**
     * checkt ob der Behaeltertyp in dem Set ist
     * @param behaeltertyp der Behaeltertyp der gecheckt werden soll
     * @return true falls der Behaeltertyp im Set gefunden wurde, ansonsten false
     */
    public boolean hasBehaeltertyp(Behaeltertyp behaeltertyp) {
        return behaeltertypSet.contains(behaeltertyp);
    }

    /**
     * gibt das Set der derzeitigen Behaeltertypen zurueck
     * @return das Set der derzeitigen Behaeltertypen in der Verwaltung
     */
    public Set<Behaeltertyp> getBehaeltertypSet() {
        return behaeltertypSet;
    }

    /**
     * setzt das Behaeltertypen-Set auf ein uebergebenes Behaeltertypen set
     * @param behaeltertypen das Set auf das es gesetzt werden soll
     */
    public void setBehaeltertypSet(Set<Behaeltertyp> behaeltertypen) {
        behaeltertypSet = behaeltertypen;
    }
}
