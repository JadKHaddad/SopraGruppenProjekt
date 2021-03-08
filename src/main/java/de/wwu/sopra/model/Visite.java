package de.wwu.sopra.model;

import java.util.ArrayList;
/**
 * Visite wird definiert
 * @author Gruppe 5
 */
public class Visite {
    private String name;
    private int studienstart;
    private ArrayList<Messtyp> messtypen;
    private Studie studie;
    private ArrayList<PatientenTermin> patientenTermine;
    private ArrayList<ProbenInfo> infos;
    /**
     * Erstellt eine neue Visite und setzt Studie
     * @param name Der name der Visite
     * @param time Der Studienstartzeitpunkt
     * @param studie Die Studie der Visite
     */
    public Visite(String name, int time, Studie studie) {
        this.name = name;
        studienstart = time;
        this.studie = studie;
        if (this.studie != null && !this.studie.hasVisite(this))
            this.studie.addVisite(this);

        messtypen = new ArrayList<Messtyp>();
        patientenTermine = new ArrayList<PatientenTermin>();
        infos = new ArrayList<ProbenInfo>();
    }

    /**
     * Gibt die Messtypen der Visite zurueck
     * @return messtypen die Messtypen der Visite
     */
    public ArrayList<Messtyp> getMesstypen() {
        return messtypen;
    }

    /**
     * Fuegt einen Messtypen zur Visite hinzu
     * @param messtypen der Messtyp
     */
    public void addMesstyp(Messtyp messtypen) {
        this.messtypen.add(messtypen);
    }

    /**
     * Entfernt einen Messtypen von der Visite
     * @param messtyp der Messtyp
     */
    public void removeMesstyp(Messtyp messtyp) {
        this.messtypen.remove(messtyp);
    }

    /**
     * Gibt zurueck, ob der Messtyp in der Visite enthalten ist
     * @param messtyp der Messtyp
     * @return ob der Messtyp in der Visite enthalten ist
     */
    public boolean hasMesstyp(Messtyp messtyp) {
        return messtypen.contains(messtyp);
    }

    /**
     * Gibt die Studie der Visite zurueck
     * @return studie die Studie der Visite
     */
    public Studie getStudie() {
        return studie;
    }

    /**
     * Setzt die Studie der Visite
     * @param studie die neue Studie fuer die Visite
     */
    public void setStudie(Studie studie) {
        if(this.studie != studie) {
        if (studie != null && studie.hasVisite(this))
            studie.removeVisite(this);
        this.studie = studie;
        if (this.studie != null && !this.studie.hasVisite(this))
            this.studie.addVisite(this);
        }
    }

    /**
     * Gibt den Namen der Visite zurueck
     * @return name der Name der Visite
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen der Visite
     * @param name der zu setzende Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gibt zurueck wann die Visite startet
     * @return studienstart der StudienStart
     */
    public int getStudienstart() {
        return studienstart;
    }

    /**
     * Legt fest wann die Visite startet
     * @param studienstart wann die Visite starten soll
     */
    public void setStudienstart(int studienstart) {
        this.studienstart = studienstart;
    }

    /**
     * Gibt die Patienten Termine der Visite zurueck
     * @return patientenTermine Die PatientetnTermine
     */
    public ArrayList<PatientenTermin> getPatientenTermine() {
        return patientenTermine;
    }

    /**
     * Fuegt einen neues Patiententermin hinzu
     * @param patiententermin der hinzuzufuegende Termin
     */
    public void addPatientenTermin(PatientenTermin patiententermin) {
        this.patientenTermine.add(patiententermin);
        if (patiententermin != null && patiententermin.getVisite() != this)
            patiententermin.setVisite(this);
    }

    /**
     * Entfernt einen Patiententermin
     * @param patiententermin der zu entfernende Termin
     */
    public void removePatientenTermin(PatientenTermin patiententermin) {
        this.patientenTermine.remove(patiententermin);
        if (patiententermin != null)
            patiententermin.setVisite(null);
    }

    /**
     * Prueft ob die Visite einen bestimmten Patiententermin enthaelt
     * @param patiententermin der zu ueberpruefende Termin
     * @return boolean (true wenn der Termin vorhanden ist)
     */
    public boolean hasPatientenTermin(PatientenTermin patiententermin) {
        return patientenTermine.contains(patiententermin);
    }


    /**
     * Gibt die ProbenInfos der Visite zurueck
     * @return probenInfo Die Probeninformation der Visite
     */
    public ArrayList<ProbenInfo> getProbenInfos() {
        return infos;
    }

    /**
     * Fuegt eine ProbenInfo zur Visite hinzu
     * @param probeninfo die ProbenInfo der Visite
     */
    public void addProbenInfo(ProbenInfo probeninfo) {
        infos.add(probeninfo);
    }

    /**
     * Entfernt eine ProbenInfo von der Visite
     * @param probeninfo der ProbenInfo der Visite
     */ 
    public void removeProbenInfo(ProbenInfo probeninfo) {
        infos.remove(probeninfo);
    }

    /**
     * Gibt zurueck, ob die ProbenInfo in der Visite enthalten ist
     * @param probeninfo die ProbenInfo
     * @return ob die ProbenInfo in der Visite enthalten ist
     */
    public boolean hasProbenInfo(ProbenInfo probeninfo) {
        return infos.contains(probeninfo);
    }
}
