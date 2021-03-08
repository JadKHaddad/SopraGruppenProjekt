package de.wwu.sopra.model;

/**
 * Schubladen werden definiert
 * @author Gruppe 5
 */
public class Schublade {
    public final static int RACKS_PRO_SCHUBLADE = 5;

    private Gestell gestell;
    private int stelleImGestell;

    private Rack[] racks = new Rack[RACKS_PRO_SCHUBLADE];

    public Schublade(Gestell gestell, int stelleImGestell) {
        this.gestell = gestell;
        this.stelleImGestell = stelleImGestell;
    }

    /**
     * Gibt das Gestell wieder
     * @return das zugehoerige Gestell
     */
    public Gestell getGestell() {
        return gestell;
    }

    /**
     * Gibt die Racks zurueck
     * @return die Racks
     */
    public Rack[] getRacks() {
        return racks;
    }

    /**
     * Ueberprueft ob ein Rack in der Schublade ist
     * @param r das zu ueberpruefende Rack
     * @return boolean (true wenn enthalten)
     */
    public boolean hasRack(Rack r) {
        if (r != null) {
            for (int i = 0; i < racks.length; i++) {
                if (r.equals(racks[i]))
                    return true;
            }
        }
        return false;
    }

    /**
     * Setzt das Rack an der Stelle i
     * @param r Das Rack r
     * @param i Die Stelle i
     */
    public void setRack(Rack r, int i) {
        if (this.racks[i] != r) {
            if (this.racks[i] != null) {
                this.racks[i].setSchubladeStopp(null, 0);
            }
            this.racks[i] = r;
            this.stelleImGestell = i;
            if (r != null) {
                this.racks[i].setSchubladeStopp(this, i);
            }
        }
    }
    
    public void setRackStopp(Rack r, int i) {
        this.racks[i] = r;
        this.stelleImGestell = i;
    }

    /**
     * Gibt das Rack an der Stelle i zurück.
     * @param i Die Stelle i
     * @return Das Rack an der Stelle i.
     */
    public Rack getRack(int i) {
        return this.racks[i];
    }

    /**
     * @return Gibt den index an, an dem dieses Gestell in der Schublade liegt.
     */
    public int getStelleImGestell() {
        return stelleImGestell;
    }

}