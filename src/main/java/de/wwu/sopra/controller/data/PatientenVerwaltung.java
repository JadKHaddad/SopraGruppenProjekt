package de.wwu.sopra.controller.data;

import de.wwu.sopra.controller.PatientenSteuerung;
import de.wwu.sopra.model.Patient;

import java.util.HashSet;
import java.util.Set;

/**
 * Verwaltet die Patienten
 * Wird mit dem Entwurfsmuster Singleton realisiert
 * @author ankem
 */
public class PatientenVerwaltung {

    private static PatientenVerwaltung instance;
    private Set<Patient> patientenSet = new HashSet<Patient>();

    /**
     * Erstellt eine neue PatientenVerwaltung
     */
    private PatientenVerwaltung() {

    }

    /**
     * Gibt die Instance der PatientenVerwaltung zurueck
     * @return Instance der PatientenVerwaltung
     */
    public static synchronized PatientenVerwaltung getInstance() {
        if (instance == null) {
            instance = new PatientenVerwaltung();
        }
        return instance;
    }

    /**
     * Fuegt einen Patienten der Kollektion an Patienten hinzu
     * @param patient der Patient der hinzugefuegt werden soll
     */
    public void addPatient(Patient patient) {
        if (patient != null) {
            patientenSet.add(patient);
        }
    }

    /**
     * Entfernt einen Patienten aus der Kollektion an Patienten
     * @param patient der Patient der entfernt werden soll
     */
    public void removePatient(Patient patient) {
        patientenSet.remove(patient);
        PatientenSteuerung.getInstance().patientEntfernen(patient);
    }

    /**
     * Prueft, ob Patient schon Teil der Kollektion an Patienten ist
     * @param patient der Patient, von dem man wissen will, ob er Teil der Kolletion
     *                ist
     * @return true, wenn der Patient Teil der Kollektion ist, sonst false
     */
    public boolean hasPatient(Patient patient) {
        if (patientenSet.contains(patient)) {
            return true;
        }
        return false;
    }

    /**
     * Gibt das Set an Patienten zurueck
     * @return Set an Patienten
     */
    public Set<Patient> getPatientenSet() {
        return patientenSet;
    }

    /**
     * Setzt das Set an Patienten
     * @param patientenSet das Set auf das das Attribut patientenSet gesetzt wird
     */
    public void setPatientenSet(Set<Patient> patientenSet) {
        this.patientenSet = patientenSet;
    }

}
