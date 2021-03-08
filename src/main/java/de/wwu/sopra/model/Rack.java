package de.wwu.sopra.model;

/**
 * Definiert ein rack
 * 
 * @author Gruppe 5
 */
public class Rack {
    private int bid;
    private int zeilen;
    private int spalten;
    private int stelleInSchublade;

    private Schublade schublade;
    private Behaeltertyp behaeltertyp;
    private ProbenPlatz[][] probenplaetze;

    /**
     * Erstellt ein neues Rack und setzt Probenplaetze und eine Schublade
     * 
     * @param bid             Die BID des Rakcs
     * @param spalten         Die Spalten des Racks
     * @param zeilen          Die Zeilen des Racks
     * @param behaeltertyp    Der Behaeltertyp des Racks
     * @param schublade       Die Schublade des Racks
     * @param schubladenPlatz der SchubladenPlatz des Racks
     */
    public Rack(int bid, int spalten, int zeilen, Behaeltertyp behaeltertyp, Schublade schublade, int schubladenPlatz) {
        this.bid = bid;
        this.spalten = spalten;
        this.zeilen = zeilen;
        this.behaeltertyp = behaeltertyp;
        this.probenplaetze = new ProbenPlatz[spalten][zeilen];
        // Probenplaetze werden pro Rack einmal initialisiert und bleiben danach so
        // erhalten.
        for (int x = 0; x < spalten; x++) {
            for (int y = 0; y < zeilen; y++) {
                probenplaetze[x][y] = new ProbenPlatz(x, y, this, null);
            }
        }
        this.schublade = schublade;
        this.stelleInSchublade = schubladenPlatz;
        if (schublade != null)
            schublade.setRack(this, schubladenPlatz);
    }

    /**
     * get Funktion fuer bid
     * 
     * @return bid Barcode ID
     */
    public int getBid() {
        return bid;
    }

    /**
     * set Funktion fuer bid
     * 
     * @param bid die ID fuer das Rack
     */
    public void setBid(int bid) {
        this.bid = bid;
    }

    /**
     * get Funktion fuer zeilen
     * 
     * @return zeilen Anzahl an Zeilen
     */
    public int getZeilen() {
        return zeilen;
    }

    /**
     * set Funktion fuer zeilen
     * 
     * @param zeilen die Zeilen fuer das Rack
     */
    public void setZeilen(int zeilen) {
        this.zeilen = zeilen;
    }

    /**
     * get Funktion fuer spalten
     * 
     * @return spalten Anzahl an Spalten
     */
    public int getSpalten() {
        return spalten;
    }

    /**
     * set Funktion fuer spalten
     * 
     * @param spalten die Spalten fuer das Rack
     */
    public void setSpalten(int spalten) {
        this.spalten = spalten;
    }

    /**
     * get Funktion fuer den Behaeltertyp
     * 
     * @return behaeltertyp Behaeltertyp des Racks
     */
    public Behaeltertyp getBehaeltertyp() {
        return behaeltertyp;
    }

    /**
     * set Funktion fuer den Behaeltertyp
     * 
     * @param behaeltertyp der neue Behaeltertyp
     */
    public void setBehaeltertyp(Behaeltertyp behaeltertyp) {
        this.behaeltertyp = behaeltertyp;
    }

    /**
     * Gibt die Probe an den uebergeben Koordinaten zurueck
     * 
     * @param x x-Koordinate
     * @param y y-Koordinate
     * @return die Probe an dem Platz
     */
    public Probe getProbe(int x, int y) {
        return probenplaetze[x][y].getProbe();
    }

    /**
     * Setzt an dem uebergebenen Platz eine Probe
     * 
     * @param x x-Koordinate
     * @param y y-Koordinate
     * @param p die zu setztende Probe
     */
    public void setProbe(int x, int y, Probe p) {
        if (p == null || behaeltertyp == p.getBehaeltertyp()) {
            if (p != probenplaetze[x][y].getProbe()) {
                if (probenplaetze[x][y].getProbe() != null) {
                    probenplaetze[x][y].getProbe().setProbenPlatz(null);
                }
                probenplaetze[x][y].setProbe(p);
                if (p != null) {
                    p.setProbenPlatz(probenplaetze[x][y]);
                }
            }
        }
    }

    /**
     * Gibt den Probenplatz zurueck
     * 
     * @param x x-Koordinate
     * @param y y-Koordiante
     * @return den Probenplatz an den Koordinaten
     */
    public ProbenPlatz getProbenplatz(int x, int y) {
        return probenplaetze[x][y];
    }

    /**
     * Gibt die Schublade zurueck
     * 
     * @return schublade die Schublade des Racks
     */
    public Schublade getSchublade() {
        return schublade;
    }

    /**
     * Setzt die Schublade des Racks
     * 
     * @param schublade die neue Schublade
     * @param i         der Index des Schubladenplatz
     */
    public void setSchublade(Schublade schublade, int i) {
        if (this.schublade != schublade || i != stelleInSchublade) {
            if (this.schublade != null) {
                this.schublade.setRackStopp(null, stelleInSchublade);
            }
            this.schublade = schublade;
            this.stelleInSchublade = i;
            if (schublade != null) {
                schublade.setRackStopp(this, i);
            }
        }
    }

    public void setSchubladeStopp(Schublade schublade, int i) {
        this.schublade = schublade;
        this.stelleInSchublade = i;
    }

    /**
     * @return stelleInSchublade Gibt den Index zurueck, an dem dieses Rack in der
     *         Schublade liegt.
     */
    public int getStelleInSchublade() {
        return stelleInSchublade;
    }
}
