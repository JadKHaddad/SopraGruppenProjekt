package de.wwu.sopra.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
/**
 * Definiert und verwaltet die Patiententermine
 * @author Gruppe 5
 */
public class PatientenTermin {
    private boolean durchgefuehrt;
    private boolean abgesagt;
    private Visite visite;
    private LocalDateTime termin;
    private ArrayList<Messung> messungen;
    private Studienteilnehmer teilnehmer;
    private ArrayList<Probe> proben;
    /**
     * Erstellt einen Patiententermin und setzt einen Teilnehmer, sowie eine Visite
     * @param visite Die Übergebene Visite
     * @param termin Der Übergebene Termin
     * @param teilnehmer Die Studienteilnehmer
     */
    public PatientenTermin(Visite visite, LocalDateTime termin, Studienteilnehmer teilnehmer) {
        messungen = new ArrayList<Messung>();
        proben = new ArrayList<Probe>();
        durchgefuehrt = false;
        abgesagt = false;
        this.visite = visite;
        this.teilnehmer = teilnehmer;
        if (this.teilnehmer != null)
            this.teilnehmer.addPatientenTermin(this);
        ;
        this.termin = termin;
        if (this.visite != null && !this.visite.hasPatientenTermin(this))
            this.visite.addPatientenTermin(this);
    }


    /**
     * Gibt den Termin zurueck
     * @return termin Denn Termin des Patienten
     */
    public LocalDateTime getTermin() {
        return termin;
    }

    /**
     * Setzt den Termin des Patiententermins
     * @param termin der zu setztende Termin (LoacalDateTime)
     */
    public void setTermin(LocalDateTime termin) {
        this.termin = termin;
    }

    /**
     * Gibt an ob der Termin bereits druchgefuert wurde
     * @return durchgefuehrt Ob der Termin schon durchgefuehrt wurde
     */
    public boolean isDurchgefuehrt() {
        return durchgefuehrt;
    }

    /**
     * Setzt den Termin auf durchgefuert
     * @param durchgefuehrt boolean (true wenn durchgefuehrt)
     */
    public void setDurchgefuehrt(boolean durchgefuehrt) {
        this.durchgefuehrt = durchgefuehrt;
    }

    /**
     * Gibt an ob der Termin abgesagt
     * @return abgesagt Ob der Termin Abgesagt wurde
     */
    public boolean isAbgesagt() {
        return abgesagt;
    }

    /**
     * Setzt den Termin auf abgesagt
     * @param abgesagt boolean (true wenn abgesagt)
     */
    public void setAbgesagt(boolean abgesagt) {
        this.abgesagt = abgesagt;
    }

    /**
     * Gibt die Visite zurueck
     * @return visite Die Visite des Termins
     */
    public Visite getVisite() {
        return visite;
    }

    /**
     * Setzt die Visite des Termins
     * @param visite die neue Visite
     */
    public void setVisite(Visite visite) {
        if (this.visite != null && this.visite.hasPatientenTermin(this))
            this.visite.removePatientenTermin(this);
        this.visite = visite;
        if (this.visite != null && !this.visite.hasPatientenTermin(this))
            visite.addPatientenTermin(this);
    }

    /**
     * Gibt die Proben der Visite zurueck
     * @return proben Die entnommenen Proben
     */
    public ArrayList<Probe> getProben() {
        return proben;
    }

    /**
     * Fuegt eine neue Probe hinzu
     * @param probe die Probe
     */
    public void addProbe(Probe probe) {

        if (probe != null && !hasProbe(probe)) {
            proben.add(probe);
            probe.setTermin(this);
        }
    }

    /**
     * Loescht eine Probe
     * @param probe die Probe
     */
    public void removeProbe(Probe probe) {
        proben.remove(probe);
        if (probe != null)
            probe.setTermin(null);
    }

    /**
     * Prueft, ob eine bestimmte Probe zu diesem PatientenTermin gehoehrt.
     * @param probe die Probe
     * @return ob die Probe enthalten ist
     */
    public boolean hasProbe(Probe probe) {
        return proben.contains(probe);
    }

    /**
     * Gibt die Messungen der Visite zurueck
     * @return messungen
     */
    public ArrayList<Messung> getMessungen() {
        return messungen;
    }

    /**
     * Fuegt eine neue Messung hinzu
     * @param messung die neue Messung
     */
    public void addMessung(Messung messung) {
        if (messung != null && !hasMessung(messung)) {
            messungen.add(messung);
            messung.setTermin(this);
        }
    }

    /**
     * Loescht eine Messung
     * @param messung die zu loeschende Messung
     */
    public void removeMessung(Messung messung) {
        if (messung != null) {
            messungen.remove(messung);
            messung.setTermin(null);
        }
    }

    /**
     * Prueft, ob eine bestimmte Messung vorgenommen wird
     * @param messung die zu ueberpruefende Messung
     * @return boolean (true wenn Messung vorgenommen wird)
     */
    public boolean hasMessung(Messung messung) {
        return messungen.contains(messung);
    }

    /**
     * Gibt den Studienteilnehmer zurueck
     * @return den Studienteilnehmer
     */
    public Studienteilnehmer getStudienTeilnehmer() {
        return teilnehmer;
    }

    /**
     * weist dem Termin einen neuen Teilnehmer zu
     * @param studienteilnehmer der neue Studieteilnehmer
     */
    public void setStudienTeilnehmer(Studienteilnehmer studienteilnehmer) {
        if (studienteilnehmer != teilnehmer) {
            if (teilnehmer != null && teilnehmer.hasPatientenTermin(this)) {
                teilnehmer.removePatientenTermin(this);
            }
            teilnehmer = studienteilnehmer;
            if (studienteilnehmer != null && !studienteilnehmer.hasPatientenTermin(this)) {
                studienteilnehmer.addPatientenTermin(this);
            }
        }
    }
}
