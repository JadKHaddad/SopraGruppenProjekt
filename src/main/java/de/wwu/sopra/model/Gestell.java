package de.wwu.sopra.model;

/**
 * Gestelle werden definiert
 * @author Gruppe 5
 */
public class Gestell {

    public final static int SCHUBLADEN_PRO_GESTELL = 5;

    private Segment segment;
    private int stelleImSegment;
    private Schublade[] schubladen = new Schublade[SCHUBLADEN_PRO_GESTELL];

    public Gestell(Segment segment, int stelleImSegment) {
        this.segment = segment;
        this.stelleImSegment = stelleImSegment;
        for(int i = 0; i < SCHUBLADEN_PRO_GESTELL; i++) {
            schubladen[i] = new Schublade(this, i);
        }
    }

    /**
     * Gibt das Segment aus
     * @return das zugehoerige Segment
     */
    public Segment getSegment() {
        return segment;
    }

    /**
     * @return Der Index, an dem das Gestell im Segment liegt.
     */
    public int getStelleImSegment() {
        return stelleImSegment;
    }

    /**
     * Gibt die Schublade an der Stelle i aus
     * @param i die Stelle im Array die ausgegeben werden soll
     * @return die zugehoerige Schublade
     */
    public Schublade getSchublade(int i) {
        return schubladen[i];
    }
}