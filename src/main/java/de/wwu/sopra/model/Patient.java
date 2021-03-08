package de.wwu.sopra.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Patienten werden definiert
 * @author Gruppe 5
 */
public class Patient {

    private String name;
    private String vorname;
    private int alter;
    private String anschrift;
    private ArrayList<Studienteilnehmer> studienteilnehmer = new ArrayList<Studienteilnehmer>();

    /**
     * Erstellt einen Patienten
     * @param name      Nachname des Patienten
     * @param vorname   Vorname des Patienten
     * @param alter     Alter des Patienten
     * @param anschrift Die Anschrift des Patienten als String
     */
    public Patient(String name, String vorname, int alter, String anschrift) {
        this.name = name;
        this.vorname = vorname;
        this.alter = alter;
        this.anschrift = anschrift;
    }

    public ArrayList<Studienteilnehmer> getStudienteilnehmer(){
        return studienteilnehmer;
    }
    
    /**
     * Fuegt eine Teilnahme des Patienten an einer Studie als Studienteilnehmer hinzu
     * @param studienteilnehmer Studienteilnehmer
     */
    public void addStudienteilnehmer(Studienteilnehmer studienteilnehmer) {
        if (studienteilnehmer != null && !this.studienteilnehmer.contains(studienteilnehmer)) {
            this.studienteilnehmer.add(studienteilnehmer);
            studienteilnehmer.setPatient(this);
        }
    }

    /**
     * Zieht die Teilnahme des Patienten als Studienteilnehmer an einer Studie zurueck
     * @param studienteilnehmer Studienteilnehmer
     */
    public void removeStudienteilnehmer(Studienteilnehmer studienteilnehmer) {
        if (studienteilnehmer != null && hasStudienteilnehmer(studienteilnehmer)) {
            this.studienteilnehmer.remove(studienteilnehmer);
            studienteilnehmer.setPatient(null);
        }
    }

    /**
     * Gibt zurueck, ob der Teilnehmer einer Studie zugeordnet ist
     * @param s Studienteilnehmer
     * @return Studien zu denen der Teilnehmer gehoert
     */
    public boolean hasStudienteilnehmer(Studienteilnehmer s) {
        return studienteilnehmer.contains(s);
    }

    /**
     * Gibt den Nachnamen des Patienten zurueck
     * @return name Nachname des Patient
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Nachnamen des Patienten auf den uebergebenen Parameter
     * @param name Nachname des Patienten
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gibt den Vornamen des Patienten zurueck
     * @return vorname Vorname des Patienten
     */
    public String getVorname() {
        return vorname;
    }

    /**
     * Setzt den Vornamen des Patienten auf den uebergebenen Wert
     * @param vorname Vorname des Patienten
     */
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    /**
     * Gibt das Alter des Patienten zurueck
     * @return alter Das Alter des Patienten
     */
    public int getAlter() {
        return alter;
    }

    /**
     * Setzt das Alter des Patienten auf den uebergebenen Wert
     * @param alter Das Alter des Patienten
     */
    public void setAlter(int alter) {
        this.alter = alter;
    }

    /**
     * Gibt die Anschrift des Patienten zurueck
     * @return anschrift Die Anschrift des Patienten
     */
    public String getAnschrift() {
        return anschrift;
    }

    /**
     * Setzt die Anschrift des Patienten auf den uebergebenen Wert
     * @param anschrift Die Anschrift des Patienten
     */
    public void setAnschrift(String anschrift) {
        this.anschrift = anschrift;
    }
}
