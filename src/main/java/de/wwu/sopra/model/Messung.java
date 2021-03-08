package de.wwu.sopra.model;
/**
 * Messung werden definiert
 * @author Gruppe 5
 */
public class Messung {
    private String wert;
    private Messtyp typ;
    private PatientenTermin termin;
    /**
     * Erstellt eine Messung und setzt einen Termin
     * @param wert der Wert der Messung
     * @param typ der Typ der Messung
     * @param termin der Termin der Messung
     */
    public Messung(String wert, Messtyp typ, PatientenTermin termin) {
        this.wert = wert;
        this.typ = typ;
        this.termin = termin;
        if (this.termin != null && !this.termin.hasMessung(this))
            this.termin.addMessung(this); 
    }

    /**
     * Gibt den Wert der Messung zurueck
     * @return wert Der Wert der Messung
     */
    public String getWert() {
        return wert;
    }

    /**
     * Weist der Messung einen neuen Wert zu
     * @param wert der neue Wert
     */
    public void setWert(String wert) {
        this.wert = wert;
    }

    /**
     * Gibt den Typ der Messung zurueck
     * @return typ Der Typ der Messung
     */
    public Messtyp getTyp() {
        return typ;
    }

    /**
     * Weist der Messung einen Typ zu
     * @param typ neue Typ der Messung
     */
    public void setTyp(Messtyp typ) {
        this.typ = typ;
    }

    /**
     * Gibt den Termin der Messung zurueck
     * @return termin Den Termin der Messung
     */
    public PatientenTermin getTermin() {
        return termin;
    }

    /**
     * Weist der Messung einen neuen Termin zu
     * @param termin der neue Termin der Messung
     */
    public void setTermin(PatientenTermin termin) {
        if(this.termin != termin) {
        if (this.termin != null && this.termin.hasMessung(this))
            this.termin.removeMessung(this);
        this.termin = termin;
        if (this.termin != null && !termin.hasMessung(this))
            this.termin.addMessung(this);
        }
    }
}
