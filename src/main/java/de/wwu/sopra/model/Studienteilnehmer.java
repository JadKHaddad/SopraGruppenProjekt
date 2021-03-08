package de.wwu.sopra.model;

import java.util.ArrayList;
/**
 * Studienteilnehmer wird definiert
 * @author Gruppe 5
 */
public class Studienteilnehmer {

    private Patient patient;
    private Studie studie;
    private ArrayList<PatientenTermin> patientenTermine;
    /**
     * Erstellt einen neuen Studienteilnehmer und setzt die Patienten und Studien
     * @param patient der Patient der Studienteilnehmer wird
     * @param studie die Studie des Studienteilnehmers
     */
    public Studienteilnehmer(Patient patient, Studie studie) {
        patientenTermine = new ArrayList<PatientenTermin>();
        this.patient = patient;
        if (patient != null && !patient.hasStudienteilnehmer(this))
            patient.addStudienteilnehmer(this);
        this.studie = studie;
        if (studie != null && !studie.hasTeilnehmer(this))
            studie.addTeilnehmer(this);
    }

    /**
     * Gibt die Studie zurueck
     * @return die Studie
     */
    public Studie getStudie() {
        return studie;
    }

    /**
     * Setzt die Studie
     * @param studie die neue Studie
     */
    public void setStudie(Studie studie) {
        if (studie != this.studie) {
            if (this.studie != null && this.studie.hasTeilnehmer(this))
                this.studie.removeTeilnehmer(this);
            this.studie = studie;
            if (this.studie != null && !this.studie.hasTeilnehmer(this)) {
                this.studie.addTeilnehmer(this);
            }
        }
    }
    /**
     * Gibt den Patienten zurueck
     * @return den Patienten
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Setzt den Patienten in der Studienteilnehmerliste
     * @param patient Der Patient der Studienteilnehmer werden soll
     */
    public void setPatient(Patient patient) {
        if (patient != this.patient) {
            if (this.patient != null && this.patient.hasStudienteilnehmer(this)) {
                this.patient.removeStudienteilnehmer(this);
            }
            this.patient = patient;
            if (patient != null && this.patient.hasStudienteilnehmer(this)) {
                patient.addStudienteilnehmer(this);
            }
        }
    }

    /**
     * Gibt die Patiententermin zurueck
     * @return die Patiententermine
     */
    public ArrayList<PatientenTermin> getPatientenTermine() {
        return patientenTermine;
    }

    /**
     * Gibt an ob der Teilnehmer einen bestimmten Termin hat
     * @param patientenTermin der PatientenTermin
     * @return boolean (true, wenn der Termin vorhanden ist)
     */
    public boolean hasPatientenTermin(PatientenTermin patientenTermin) {
        return patientenTermine.contains(patientenTermin);
    }


    /**
     * Fuegt dem Studienteilnehmer einen neuen Termin hinzu
     * @param patientenTermin der neue Termin
     */
    public void addPatientenTermin(PatientenTermin patientenTermin) {
        if (patientenTermin != null && !hasPatientenTermin(patientenTermin)) {
            this.patientenTermine.add(patientenTermin);
            patientenTermin.setStudienTeilnehmer(this);
        }
    }

    /**
     * Entfernt einen Patiententermin
     * @param patientenTermin der zu entfernende Termin
     */
    public void removePatientenTermin(PatientenTermin patientenTermin) {
        if (patientenTermin != null && hasPatientenTermin(patientenTermin)) {
            this.patientenTermine.remove(patientenTermin);
            patientenTermin.setStudienTeilnehmer(null);
        }
    }

}
