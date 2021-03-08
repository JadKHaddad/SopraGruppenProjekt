package de.wwu.sopra.model;

import java.util.ArrayList;

/**
 * Studie wird definiert
 * 
 * @author Gruppe 5
 */
public class Studie {
    private String name;
    private int anzTeilnehmer;
    private ArrayList<Visite> visiten;
    private ArrayList<Studienteilnehmer> teilnehmer;
    private ArrayList<Benutzer> benutzer;
    private boolean bearbeitbar;

    /**
     * Erstellt eine Neue Studie, und setzt Visite, Teilnehmer und Benutzer
     * 
     * @param name          der Name der Studie
     * @param anzTeilnehmer Die Anzahl der Teilnehmer
     */
    public Studie(String name, int anzTeilnehmer) {
        bearbeitbar = true;
        this.name = name;
        this.anzTeilnehmer = anzTeilnehmer;
        teilnehmer = new ArrayList<Studienteilnehmer>();
        benutzer = new ArrayList<Benutzer>();
        visiten = new ArrayList<Visite>();
    }

    /**
     * gibt an, ob man die Studie noch bearbeiten darf
     * 
     * @return boolean (true, wenn noch bearbeitbar)
     */
    public boolean isBearbeitbar() {
        return bearbeitbar;
    }

    /**
     * setzt den Bearbeitbar-Status der Studie
     * 
     * @param bearbeitbar der neue Status
     */
    public void setBearbeitbar(boolean bearbeitbar) {
        this.bearbeitbar = bearbeitbar;
    }

    /**
     * Gibt den Namen der Studie zurueck
     * 
     * @return name der Name der Studie
     */
    public String getName() {
        return name;
    }

    /**
     * Gibt der Studie einen neuen Namen
     * 
     * @param name der neue Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gibt die Anzahl der Teilnehmer zurueck
     * 
     * @return anzTeilnehmer die Anzahl der Teilnehmer
     */
    public int getAnzTeilnehmer() {
        return anzTeilnehmer;
    }

    /**
     * Setzt die Anzahl der Teilnehmer
     * 
     * @param anzTeilnehmer die Anzahl der Teilnehmer
     */
    public void setAnzTeilnehmer(int anzTeilnehmer) {
        this.anzTeilnehmer = anzTeilnehmer;
    }

    /**
     * Gibt die Visiten der Studie zurueck
     * 
     * @return visiten die Visiten der Studie
     */
    public ArrayList<Visite> getVisiten() {
        return visiten;
    }

    /**
     * Fuegt eine neue Visite hinzu
     * 
     * @param visite die hinzuzufuegende Visite
     */
    public void addVisite(Visite visite) {
        if (visite != null && !hasVisite(visite)) {
            visiten.add(visite);
            visite.setStudie(this);
        }
    }

    /**
     * Loescht eine Visite
     * 
     * @param visite die zu loeschende Visite
     */
    public void removeVisite(Visite visite) {
        if (visite != null && hasVisite(visite)) {
            visiten.remove(visite);
            visite.setStudie(null);
        }
    }

    /**
     * Prueft ob die Studie eine bestimmte Visite enthaelt
     * 
     * @param visite die zu ueberpruefende Visite
     * @return boolean (true wenn v in der Studie enthalten)
     */
    public boolean hasVisite(Visite visite) {
        return visiten.contains(visite);
    }

    /**
     * Gibt die Studienteilnehmer zurueck
     * 
     * @return teilnehmer die Studienteilnehmer
     */
    public ArrayList<Studienteilnehmer> getTeilnehmer() {
        return teilnehmer;
    }

    /**
     * Prueft ob ein Teilnehmer vorhanden ist
     * 
     * @param studienTeilnehmer der zu ueberpruefende Teilnehmer
     * @return boolean ob der Teilnehmer vorhanden ist
     */
    public boolean hasTeilnehmer(Studienteilnehmer studienTeilnehmer) {
        return teilnehmer.contains(studienTeilnehmer);
    }

    /**
     * Fuegt der Studie einen neuen Teilnehmer hinzu
     * 
     * @param studienTeilnehmer den neuen Teilnehmer
     */
    public void addTeilnehmer(Studienteilnehmer studienTeilnehmer) {
        if (studienTeilnehmer != null && !hasTeilnehmer(studienTeilnehmer)) {
            teilnehmer.add(studienTeilnehmer);
            studienTeilnehmer.setStudie(this);
        }
    }

    /**
     * Enfternt einen Studienteilnehmer
     * 
     * @param be der zu entfernende Teilnehmer
     */
    public void removeTeilnehmer(Studienteilnehmer be) {
        if (be != null && hasTeilnehmer(be)) {
            teilnehmer.remove(be);
            be.setStudie(null);
        }
    }

    /**
     * Gibt die Benutzer zurueck
     * 
     * @return benutzer die Benutzer
     */
    public ArrayList<Benutzer> getBenutzer() {
        return benutzer;
    }

    /**
     * Fuegt einen neuen Benutzer hinzu
     * 
     * @param benutzer der neue Benutzer
     */
    public void addBenutzer(Benutzer benutzer) {
        if (benutzer != null &&!hasBenutzer(benutzer)) {
            this.benutzer.add(benutzer);
            benutzer.addStudie(this);
        }
    }

    /**
     * Entfernt einen Benutzer
     * 
     * @param benutzer der zu entfernende Benutzer
     */
    public void removeBenutzer(Benutzer benutzer) {
        if (hasBenutzer(benutzer)) {
            this.benutzer.remove(benutzer);
            if (benutzer != null)
                benutzer.removeStudie(this);
        }
    }

    /**
     * Prueft ob ein Benutzer vorhanden ist
     * 
     * @param benutzer der zu pruefende Benuter
     * @return boolean (true, wenn Benutzer vorhanden)
     */
    public boolean hasBenutzer(Benutzer benutzer) {
        return this.benutzer.contains(benutzer);
    }
}
