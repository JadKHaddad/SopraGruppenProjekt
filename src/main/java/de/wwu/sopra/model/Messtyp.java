package de.wwu.sopra.model;
/**
 * Enums, die die unterschiedlichen Status von Messungen wiedergeben
 * @author Gurppe 5
 */
public class Messtyp {
    

    private String einheit, name;
    /**
     * Erstellt einen Messtyp
     * @param name Der Name des Messtyps
     * @param einheit Die Einheit des Messtyps
     */
    public Messtyp(String name, String einheit) {
        this.einheit = einheit;
        this.name = name;
    }

    /**
     * Gibt die Einheit des Messtyps zurueck
     * @return einheit
     */
    public String getEinheit() {
        return einheit;
    }
    /**
     * Setzt die Einheit des Messtyps
     * @param einheit Die Einheit des Messtyps
     */
    public void setEinheit(String einheit) {
        this.einheit = einheit;
    }

    /**
     * 
     * Gibt den Namen des Messtyps zurueck
     * @return name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Setzt den Namen des Messtyps
     * @param name Der Name des Messtyps
     */
    public void setName(String name) {
        this.name = name;
    }


}
