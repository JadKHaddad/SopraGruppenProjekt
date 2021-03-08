package de.wwu.sopra.model;
/**
 * Klasse, der Deckeltypen
 * @author Gruppe 5
 */
public class Deckeltyp {
    
    private String name;
    
    /**
     * Erstellt einen neuen Deckeltypen
     * @param name Der Name des Deckeltypen
     */
    public Deckeltyp(String name) {
        
        this.name = name;
        
    }

    /**
     * Gibt den Namen des Deckeltypen zurueck
     * @return  name Name des Deckeltypen
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen des Deckeltypen
     * @param name Name des Deckeltypen
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Gibt den Namen als String zurueck aus GUI technischen Grunden
     * @return name Name des Deckeltypen
     */
    @Override
    public String toString() {
        return name;
    }
}
