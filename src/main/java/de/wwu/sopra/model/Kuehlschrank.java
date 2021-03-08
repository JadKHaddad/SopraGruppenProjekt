package de.wwu.sopra.model;

import java.util.ArrayList;

/**
 * Kuehlschrank
 * @author Gruppe 5
 */

public class Kuehlschrank {

    private String name;
    private float breite;
    private float tiefe;
    private float hoehe;
    private float temperatur;
    private Raum raum;
    private Segment[] segmente;

    /**
     * Erstellt einen Kuehlschrank und setzt einen Raum
     * @param name der Name des Kuehlschranks
     * @param breite die Breite des Kuehlschranks
     * @param tiefe die Tiefe des Kuehlschranks
     * @param hoehe die Hoehe des Kuehlschranks
     * @param temp die Temperatur des Kuehlschranks
     * @param anzahlSegmente die Anzahl der Segmente des Kuehlschranks
     * @param raum der Raum des Kuehlschranks
     */
    public Kuehlschrank(String name, float breite, float tiefe, float hoehe, float temp, int anzahlSegmente, Raum raum) {
        this.name = name;
        this.breite = breite;
        this.tiefe = tiefe;
        this.hoehe = hoehe;
        temperatur = temp;
        this.raum = raum;
        segmente = new Segment[anzahlSegmente];
        for(int i = 0; i < anzahlSegmente; i++) {
            segmente[i] = new Segment(this, i);
        }
        if (raum != null)
            raum.addKuehlschrank(this);
    }

    /**
     * Setzt den Namen des Kuehlschranks
     * @param name Name des Kuehlschranks
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setzt die Breite des Kuehlschranks
     * @param breite die neue Breite
     */
    public void setBreite(float breite) {
        this.breite = breite;
    }

    /**
     * Setzt die Tiefe des Kuehlschranks
     * @param tiefe die neue Tiefe
     */
    public void setTiefe(float tiefe) {
        this.tiefe = tiefe;
    }

    /**
     * Setzt die Hoehe des Kuehlschranks
     * @param hoehe die neue Hoehe
     */
    public void setHoehe(float hoehe) {
        this.hoehe = hoehe;
    }

    /**
     * Setzt die Temperatur des Kuehlschranks
     * @param temp die neue temperatur
     */
    public void setTemperatur(float temp) {
        this.temperatur = temp;
    }


    /**
     * Setzt den zugehoerigen Raum neu
     * @param raum der neue zugehoerige Raum
     */
    public void setRaum(Raum raum) {
        if (this.raum != raum) {
            Raum oldRaum = this.raum;
            this.raum = raum;
            if (oldRaum != null) {
                oldRaum.removeKuehlschrank(this);
            }
            if (raum != null) {
                raum.addKuehlschrank(this);
            }
        }
    }

    /**
     * @return Die Anzahl der Segmente.
     */
    public int getAnzahlSegmente() {
        return segmente.length;
    }

    /**
     * @return Das i-te Segment
     * @pre i &lt; getAnzahlSegmente()
     * @post result != null
     * @param i Index des Segments
     */
    public Segment getSegment(int i) {
        return segmente[i];
    }

    /**
     * Gibt den Namen wieder
     * @return der Name des Kuehlschranks
     */
    public String getName() {
        return name;
    }

    /**
     * Gibt die Breite wieder
     * @return die Breite des Kuehlschranks
     */
    public float getBreite() {
        return breite;
    }

    /**
     * Gibt die Tiefe wieder
     * @return die Tiefe des Kuehlschranks
     */
    public float getTiefe() {
        return tiefe;
    }

    /**
     * Gibt die Hoehe wieder
     * @return die Hoehe des Kuehlschranks
     */
    public float getHoehe() {
        return hoehe;
    }

    /**
     * Gibt die Temperatur wieder
     * @return die Temperatur des Kuehlschranks
     */
    public float getTemperatur() {
        return temperatur;
    }

    /**
     * Gibt den Raum wieder
     * @return den zugehoerigen Raum
     */
    public Raum getRaum() {
        return raum;
    }
}
