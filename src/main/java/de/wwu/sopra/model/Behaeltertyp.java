package de.wwu.sopra.model;
/**
 * Definiert einen Behaeltertypen
 * @author Gruppe 5
 */
public class Behaeltertyp {

    private float radius;
    private float hoehe;
    private float volumen;
    private Deckeltyp deckel;
    private String name;
    /**
     * Erstellt einen neueun Behealtertypen
     * @param name der Name des Behaelters
     * @param radius der Radius des Behaelters
     * @param hoehe die Hoehe des Behaelters
     * @param volumen das Volumen des Behaelters
     * @param deckel der Deckeltyp des Behaelters
     */
    public Behaeltertyp(String name, float radius, float hoehe, float volumen, Deckeltyp deckel) {
        this.radius = radius;
        this.hoehe = hoehe;
        this.volumen = volumen;
        this.deckel = deckel;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
    
    
    /**
     * get Funktion fuer Namen
     * 
     * @return Name des BehaelterTypen
     */
    public String getName() {
        return name;
    }

    /**
     * set Funktion fuer namen
     * 
     * @param name der Name des Behaelters.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get Funktion fuer radius
     * 
     * @return Radius des Behaelters
     */
    public float getRadius() {
        return radius;
    }

    /**
     * set Funktion fuer radius
     * 
     * @param radius der Radius des Behaelters.
     */
    public void setRadius(float radius) {
        this.radius = radius;
    }

    /**
     * get Funktion fuer hoehe
     * 
     * @return Hoehe des Behaelters
     */
    public float getHoehe() {
        return hoehe;
    }

    /**
     * set Funktion fuer hoehe
     * 
     * @param hoehe Hoehe des Behaelters
     */
    public void setHoehe(float hoehe) {
        this.hoehe = hoehe;
    }

    /**
     * get Funktion fuer volumen
     * 
     * @return Volumen des Behaelters
     */
    public float getVolumen() {
        return volumen;
    }

    /**
     * set Funktion fuer volumen
     * 
     * @param volumen Volumen des Behaelters
     */
    public void setVolumen(float volumen) {
        this.volumen = volumen;
    }

    /**
     * get Funktion fuer Deckel
     * 
     * @return Der Deckeltyp
     */
    public Deckeltyp getDeckel() {
        return deckel;
    }

    /**
     * set Funktion fuer deckel
     * 
     * @param deckel der Deckeltyp
     */
    public void setDeckel(Deckeltyp deckel) {
        this.deckel = deckel;
    }
}
