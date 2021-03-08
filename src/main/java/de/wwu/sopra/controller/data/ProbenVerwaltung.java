package de.wwu.sopra.controller.data;

import de.wwu.sopra.model.Probe;

import java.util.HashSet;
import java.util.Set;

/**
 * Verwaltet die Proben
 * Wird mit dem Entwurfsmuster Singleton realisiert
 * @author Gruppe 5
 */
public class ProbenVerwaltung {

    private static ProbenVerwaltung instance;
    private Set<Probe> probenSet = new HashSet<Probe>();

    private ProbenVerwaltung() {

    }

    /**
     * gibt die Instance der ProbenVerwaltung zurueck
     * @return Instance der ProbenVerwaltung
     */
    public static synchronized ProbenVerwaltung getInstance() {
        if (instance == null) {
            instance = new ProbenVerwaltung();
        }
        return instance;
    }

    /**
     * Fuegt eine Probe der Kollektion an Proben hinzu
     * @param probe die Probe, die hinzugefuegt werden soll
     */
    public void addProbe(Probe probe) {
        if (probe != null) {
            probenSet.add(probe);
        }
    }

    /**
     * Entfernt eine Probe aus der Kollektion an Proben
     * @param probe die Probe, die entfernt werden soll
     */
    public void removeProbe(Probe probe) {
        probenSet.remove(probe);
    }

    /**
     * Prueft, ob Probe schon Teil der Kollektion an Proben ist
     * @param probe die Probe von der man wissen will, ob sie Teil der Kolletion
     *              ist
     * @return true, wenn der Probe Teil der Kollektion ist, sonst false
     */
    public boolean hasProbe(Probe probe) {
        if (probenSet.contains(probe)) {
            return true;
        }
        return false;
    }

    /**
     * Gibt das Set aller Proben zurueck
     * @return das Set aller Proben
     */
    public Set<Probe> getProbenSet() {
        return probenSet;
    }

    /**
     * Setzt das Set aller Proben
     * @param probenSet Set auf der Parameter probenSet gesetzt wird
     */
    public void setProbenSet(Set<Probe> probenSet) {
        this.probenSet = probenSet;
    }

}
