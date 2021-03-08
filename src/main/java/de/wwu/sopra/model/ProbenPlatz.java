package de.wwu.sopra.model;
/**
 * Probenplatz wird definiert
 * @author Grupe 5
 *
 */
public class ProbenPlatz {

    private int zeile;
    private int spalte;

    private Rack rack;
    private Probe probe;
    /**
     * Erstellt einen ProbenPlatz
     * @param spalte Die Spalte des Probenplatzes
     * @param zeile Die Zeile des Probenplatzes
     * @param rack Das Rack des Probenplatzes
     * @param probe Die Probe des Probenplatzes
     */
    public ProbenPlatz(int spalte, int zeile, Rack rack, Probe probe) {
        this.spalte = spalte;
        this.zeile = zeile;
        this.rack = rack;
        this.probe = probe;
    }

    /**
     * get Funktion fuer zeile
     * @return zeile Zeile des Platzes
     */
    public int getZeile() {
        return zeile;
    }

    /**
     * set Funktion fuer zeile
     * @param zeile die Zeile fuer den Probenplatz
     */
    public void setZeile(int zeile) {
        this.zeile = zeile;
    }

    /**
     * get Funktion fuer spalte
     * @return spalte Spalte des Platzes
     */
    public int getSpalte() {
        return spalte;
    }

    /**
     * set Funktion fuer spalte
     * @param spalte die Spalte fuer den Probenplatz
     */
    public void setSpalte(int spalte) {
        this.spalte = spalte;
    }

    /**
     * get Funktion fuer rack
     * @return rack Der Rack zu dem der Platz gehoert
     */
    public Rack getRack() {
        return rack;
    }

    /**
     * Weist dem ProbenPlatz ein Rack zu
     * @param rack das zuzuweisende Rack
     */
    public void setRack(Rack rack) {
        this.rack = rack;
    }

    /**
     * Gibt die Probe des Probenplatzes zurueck
     * @return probe die zurueckgegebene Probe
     */
    public Probe getProbe() {
        return probe;
    }

    /**
     * Weist dem Platz eine Probe zu
     * @param probe die zuzuweisende Probe
     */
    public void setProbe(Probe probe) {
        if (this.probe != probe) {
            if (this.probe != null) {
                this.probe.setProbenPlatz(null);
            }
            this.probe = probe;
            if (probe != null) {
                probe.setProbenPlatz(this);
            }
        }

    }
    
    @Override
    public String toString() {
        return "Rack ID: " + this.getRack().getBid() + " \nin Kühlschrank: " +this.getRack().getSchublade().getGestell().getSegment().getKuehlschrank().getName();
    }
}
