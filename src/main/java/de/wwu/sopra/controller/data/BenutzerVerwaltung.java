package de.wwu.sopra.controller.data;

import de.wwu.sopra.model.Benutzer;

import java.util.HashSet;
import java.util.Set;

/**
 * Klasse zur Verwaltung der Benutzer
 * @author Gruppe 5
 */

public class BenutzerVerwaltung {

    private static BenutzerVerwaltung instance;
    private Set<Benutzer> benutzerSet = new HashSet<Benutzer>();

    private BenutzerVerwaltung() {

    }

    /**
     * BenutzerVerwaltung als Singleton um sicherzustellen, dass nur eine
     * BenutzerVerwaltung mit korrektem, synchronisiertem Inhalt existiert
     * @return die BenutzerVerwaltung instance
     */
    public static synchronized BenutzerVerwaltung getInstance() {
        if (BenutzerVerwaltung.instance == null) {
            BenutzerVerwaltung.instance = new BenutzerVerwaltung();
        }
        return BenutzerVerwaltung.instance;
    }

    /**
     * Fuegt einem Benutzer dem Set hinzu
     * @param benutzer der benutzer der hinzugefuegt werden soll
     */

    public void addBenutzer(Benutzer benutzer) {
        if (benutzer != null) {
            benutzerSet.add(benutzer);
        }
    }

    /**
     * Entfernt ein Benutzer aus dem Set
     * @param benutzer der Benutzer der entfernt werden soll
     */
    public void removeBenutzer(Benutzer benutzer) {
        if (benutzer != null) {
            benutzerSet.remove(benutzer);
        }
    }

    /**
     * Gibt zurueck, ob ein Benutzer bereits im Set ist
     * @param benutzer der zu suchende Benutzer
     * @return true, falls der Benutzer im Set ist, ansonsten false
     */
    public boolean hasBenutzer(Benutzer benutzer) {
        return benutzerSet.contains(benutzer);
    }

    /**
     * Gibt das Set der Benutzer zurueck
     * @return das Set der Benutzer
     */
    public Set<Benutzer> getBenutzerSet() {
        return benutzerSet;
    }

    /**
     * Setzt das Set der benutzer auf das uebergebene Set
     * @param benutzerSet das neue Set
     */
    public void setBenutzerSet(Set<Benutzer> benutzerSet) {
        this.benutzerSet = benutzerSet;
    }

}
