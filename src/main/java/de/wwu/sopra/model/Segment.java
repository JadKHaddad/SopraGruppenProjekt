package de.wwu.sopra.model;

/**
 * Segment wird definiert
 * @author Gruppe 5
 */
public class Segment {
    public final static int GESTELLE_PRO_SEGMENT = 4;

    private Kuehlschrank kuehlschrank;
    private int stelleImKuehlschrank;
    private Gestell[] gestelle = new Gestell[GESTELLE_PRO_SEGMENT];

    public Segment(Kuehlschrank kuehlschrank, int stelleImKuehlschrank) {
        this.kuehlschrank = kuehlschrank;
        this.stelleImKuehlschrank = stelleImKuehlschrank;
        for(int i = 0; i < GESTELLE_PRO_SEGMENT; i++) {
            gestelle[i] = new Gestell(this, i);
        }
    }

    /**
     * Gibt den Kuehlschrank wieder
     * @return den zugehoerigen Kuehlschrank
     */
    public Kuehlschrank getKuehlschrank() {
        return kuehlschrank;
    }

    /**
     * @return den Index des Segments in Kuehlschrank
     */
    public int getStelleImKuehlschrank() {
        return stelleImKuehlschrank;
    }

    /**
     * Gibt ein Gestell des Segments wieder
     * @param i Stelle im Array
     * @return das Gestell an der Stelle i
     */
    public Gestell getGestell(int i) {
        return gestelle[i];
    }
}
