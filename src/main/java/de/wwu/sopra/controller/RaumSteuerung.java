package de.wwu.sopra.controller;

import java.util.ArrayList;

import de.wwu.sopra.controller.data.LagerVerwaltung;
import de.wwu.sopra.model.Gestell;
import de.wwu.sopra.model.Kuehlschrank;
import de.wwu.sopra.model.Probe;
import de.wwu.sopra.model.Rack;
import de.wwu.sopra.model.Raum;
import de.wwu.sopra.model.Schublade;
import de.wwu.sopra.model.Segment;
import javafx.util.Pair;

public class RaumSteuerung {

    private static RaumSteuerung instance;

    private RaumSteuerung() {

    }

    public static synchronized RaumSteuerung getInstance() {
        if (instance == null) {
            instance = new RaumSteuerung();
        }
        return instance;
    }

    private Raum raum = null;

    /**
     * @param name der Name der gecheckt werden soll
     * @param ausserRaum der Raum der nicht gecheckt werden soll.
     * @return ob der Name bereits verwendet wurde
     */
    public boolean istRaumnameVerwendet(String name, Raum ausserRaum) {
        LagerVerwaltung verwaltung = LagerVerwaltung.getInstance();
        for (Raum r : verwaltung.getRaumSet()) {
            if (ausserRaum == null || r != ausserRaum) {
                if (r.getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param raum der zu berechnende Raum
     * @return die benutzte flaeche
     */
    public Float getBenutzteFlaeche(Raum raum){
        float benutzteFlaeche = 0;
        if (raum.getKuehlschraenke().size() > 0){
            for (Kuehlschrank kuehlschrank: raum.getKuehlschraenke()){
                benutzteFlaeche += ((kuehlschrank.getBreite()) * (kuehlschrank.getTiefe()));
            }
        }
        return benutzteFlaeche;
    }

    public Pair<Rack, Probe> bidSuchen(int bid) {

        Rack zielRack = null;
        Probe zielProbe = null;
        for (Raum raum : new ArrayList<Raum>(LagerVerwaltung.getInstance().getRaumSet())) {
            for (Kuehlschrank kuelschrank : raum.getKuehlschraenke()) {
                for (int i = 0; i < kuelschrank.getAnzahlSegmente(); i++) {
                    for (int j = 0; j < Segment.GESTELLE_PRO_SEGMENT; j++) {
                        for (int k = 0; k < Gestell.SCHUBLADEN_PRO_GESTELL; k++) {
                            for (int l = 0; l < Schublade.RACKS_PRO_SCHUBLADE; l++) {
                                Rack rack = kuelschrank.getSegment(i).getGestell(j).getSchublade(k).getRack(l);
                                if (rack != null) {
                                    if (rack.getBid() == bid) {
                                        zielRack = kuelschrank.getSegment(i).getGestell(j).getSchublade(k).getRack(l);
                                        return new Pair<Rack, Probe>(zielRack, null);
                                    } else {
                                        for (int m = 0; m < rack.getSpalten(); m++) {
                                            for (int n = 0; n < rack.getZeilen(); n++) {
                                                if (rack.getProbe(m, n) != null) {
                                                    if (rack.getProbe(m, n).getBid() == bid) {
                                                        zielProbe = kuelschrank.getSegment(i).getGestell(j)
                                                                .getSchublade(k).getRack(l).getProbe(m, n);
                                                        return new Pair<Rack, Probe>(null, zielProbe);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
        return new Pair<Rack, Probe>(null, null);

    }

}
