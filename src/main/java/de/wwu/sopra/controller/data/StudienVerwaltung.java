package de.wwu.sopra.controller.data;

import de.wwu.sopra.model.Benutzer;
import de.wwu.sopra.model.Studie;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Verwaltet die Studien Wird mit dem Entwurfsmuster Singleton realisiert
 * @author Gruppe 5
 */
public class StudienVerwaltung {

    private static StudienVerwaltung instance;
    private Set<Studie> studienSet = new HashSet<Studie>();

    /**
     * Erstellt eine neue StudienVerwaltung
     */
    private StudienVerwaltung() {

    }

    /**
     * Gibt die Instance der StudienVerwaltung zurueck
     * @return Instance der StudienVerwaltung
     */
    public static synchronized StudienVerwaltung getInstance() {
        if (instance == null) {
            instance = new StudienVerwaltung();
        }
        return instance;
    }

    /**
     * Fuegt eine Studie der Kollektion an Studien hinzu
     * @param studie die Studie ,die hinzugefuegt werden soll
     */
    public void addStudie(Studie studie) {
        if (studie != null) {
            studienSet.add(studie);
        }
    }

    /**
     * Entfernt eine Studie aus der Kollektion an Studien
     * @param studie die Studie, die entfernt werden soll
     */
    public void removeStudie(Studie studie) {

        studienSet.remove(studie);        
    }

    /**
     * Prueft, ob eine Studie schon Teil der Kollektion an Studien ist
     * @param studie die studie, von dem man wissen will, ob sie Teil der Kolletion
     *               ist
     * @return true, wenn die Studie Teil der Kollektion ist, sonst false
     */
    public boolean hasStudie(Studie studie) {
        if (studienSet.contains(studie)) {
            return true;
        }
        return false;
    }

    /**
     * Gibt das Set aller Studien wieder
     * @return Set aller Studien
     */
    public Set<Studie> getStudienSet() {
        return studienSet;
    }

    /**
     * Setzt das Set aller Studien auf den uebergebenen Parameter
     * @param studienSet das Set auf das der Parameter studienSet gesetzt wird
     */
    public void setStudienSet(Set<Studie> studienSet) {
        this.studienSet = studienSet;
    }


}
