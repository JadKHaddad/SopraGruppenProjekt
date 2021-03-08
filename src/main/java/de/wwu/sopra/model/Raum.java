package de.wwu.sopra.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Definiert einen Raum
 * @author Gruppe 5
 */

public class Raum {

    private String name;
    private float quadratmeter;
    private float hoehe;
    private Set<Kuehlschrank> kuehlschraenke = new HashSet<Kuehlschrank>();
    /**
     * Erstellt einen neuen Raum
     * @param name Der Name des Raums
     * @param quadratmeter Die Quadratmeter des Raums
     * @param hoehe Die Hoehe des Raums
     */
    public Raum(String name, float quadratmeter, float hoehe) {
        this.name = name;
        this.quadratmeter = quadratmeter;
        this.hoehe = hoehe;
    }

    /**
     * Setze den Namen
     * @param name der neue Name des Raums
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setzt die Quadratmeter-Angabe des Raums
     * @param quadratmeter die neue Groesse des Raums
     */
    public void setQuadratmeter(float quadratmeter) {
        this.quadratmeter = quadratmeter;
    }

    /**
     * Setze die Hoehe des Raums
     * @param hoehe die neue Hoehe des Raums
     */
    public void setHoehe(float hoehe) {
        this.hoehe = hoehe;
    }

    /**
     * Gibt den Namen aus
     * @return name der Namen des Raums
     */
    public String getName() {
        return name;
    }

    /**
     * Gibt die Quadratmeter des Raums wieder
     * @return quadratmeter die Groesse des Raums
     */
    public float getQuadratmeter() {
        return quadratmeter;
    }

    /**
     * Gibt die Hoehe des Raums wieder
     * @return hoehe die Hoehe des Raums
     */
    public float getHoehe() {
        return hoehe;
    }

    /**
     * Gibt die Liste der Kuehlschraenken wieder
     * @return kuehlschaenke die Kuehlschraenke im Raum
     */
    public Set<Kuehlschrank> getKuehlschraenke() {
        return kuehlschraenke;
    }

    /**
     * Prueft ob ein bestimmter Kuehlschrank in der Menge ist
     * @param kuehlschrank der zu suchende Kuehlschrank
     * @return true falls der Kuehlschrank in der Menge ist, ansonsten false
     */
    public boolean hasKuehlschrank(Kuehlschrank kuehlschrank) {
        return kuehlschraenke.contains(kuehlschrank);
    }

    /**
     * Fuegt einen Kuehlschrank in die Menge hinzu
     * @param kuehlschrank der hinzuzufuegende Kuehlschrank
     */
    public void addKuehlschrank(Kuehlschrank kuehlschrank) {
        if (kuehlschrank != null) {
            this.kuehlschraenke.add(kuehlschrank);
            kuehlschrank.setRaum(this);
        }
    }

    /**
     * Entfernt einen Kuehlschrank aus der Menge
     * @param kuehlschrank der zu entfernende Kuehlschrank
     */
    public void removeKuehlschrank(Kuehlschrank kuehlschrank) {
        kuehlschraenke.remove(kuehlschrank);
        if (kuehlschrank != null) {
            if (kuehlschrank.getRaum() == this) {
                kuehlschrank.setRaum(null);
            }
        }
    }

    public String toString(){
        return this.getName();
    }
}