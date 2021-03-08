package de.wwu.sopra.controller.data;

import de.wwu.sopra.controller.ProbenSteuerung;
import de.wwu.sopra.controller.RackSteuerung;
import de.wwu.sopra.model.Gestell;
import de.wwu.sopra.model.Kuehlschrank;
import de.wwu.sopra.model.Rack;
import de.wwu.sopra.model.Raum;
import de.wwu.sopra.model.Schublade;
import de.wwu.sopra.model.Segment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Klasse zur Verwaltung der Raeume
 * 
 * @author Gruppe 5
 */

public class LagerVerwaltung {
    private static LagerVerwaltung instance;
    private Set<Raum> raumSet = new HashSet<Raum>();

    private LagerVerwaltung() {

    }

    /**
     * LagerVerwaltung als Singleton um sicherzustellen, dass nur eine
     * LagerVerwaltung mit korrektem, synchronisiertem Inhalt existiert
     * 
     * @return die LagerVerwaltung instance
     */
    public static synchronized LagerVerwaltung getInstance() {
        if (LagerVerwaltung.instance == null) {
            LagerVerwaltung.instance = new LagerVerwaltung();
        }
        return LagerVerwaltung.instance;
    }

    /**
     * Fuegt einen Raum in das Set hinzu
     * 
     * @param raum der Raum der hinzugefuegt werden soll
     */
    public void addRaum(Raum raum) {
        if (raum != null) {
            raumSet.add(raum);
        }
    }

    /**
     * Entfernt einen Raum aus dem Set
     * 
     * @param raum der Raum der entfernt werden soll
     */
    public void removeRaum(Raum raum) {
        if (raum != null) {
            raumSet.remove(raum);
        }
    }

    /**
     * Checkt ob der Raum in der Liste existiert
     * 
     * @param raum der Raum der gesucht werden soll
     * @return true, falls der raum in der Liste ist, ansonsten false
     */
    public boolean hasRaum(Raum raum) {
        return raumSet.contains(raum);
    }

    /**
     * Gibt raumSet zurueck
     * 
     * @return gibt den Set der Raueme zurueck
     */
    public Set<Raum> getRaumSet() {
        return raumSet;
    }

    /**
     * Setzt den Set des Raums neu
     * 
     * @param raumSet der Set der gesetzt werden soll
     */
    public void setRaumSet(Set<Raum> raumSet) {
        this.raumSet = raumSet;
    }

    public int getAnzProbenplaetze() {
        int anzPlaetze = 0;
        for (Raum raum : raumSet) {
            for (Kuehlschrank kuelschrank : raum.getKuehlschraenke()) {
                for (int i = 0; i < kuelschrank.getAnzahlSegmente(); i++) {
                    for (int j = 0; j < kuelschrank.getSegment(i).GESTELLE_PRO_SEGMENT; j++) {
                        for (int k = 0; k < kuelschrank.getSegment(i).getGestell(j).SCHUBLADEN_PRO_GESTELL; k++) {
                            for (int l = 0; l < kuelschrank.getSegment(i).getGestell(j).getSchublade(k)
                                    .getRacks().length; l++) {
                                if (kuelschrank.getSegment(i).getGestell(j).getSchublade(k).getRack(l) != null) {
                                    anzPlaetze += (kuelschrank.getSegment(i).getGestell(j).getSchublade(k).getRack(l)
                                            .getSpalten()
                                            * kuelschrank.getSegment(i).getGestell(j).getSchublade(k).getRack(l)
                                                    .getZeilen());
                                }
                            }

                        }
                    }
                }
            }
        }
        return anzPlaetze;
    }

    public int getAnzMaxRacks() {
        int anzRacks = 0;
        for (Raum raum : raumSet) {
            for (Kuehlschrank kuehlschrank : raum.getKuehlschraenke()) {
                anzRacks += kuehlschrank.getAnzahlSegmente() * Segment.GESTELLE_PRO_SEGMENT
                        * Gestell.SCHUBLADEN_PRO_GESTELL * Schublade.RACKS_PRO_SCHUBLADE;
            }
        }

        return anzRacks;
    }

    public int getAnzBelegterRacks() {
        int anzRacks = 0;
        for (Raum raum : raumSet) {
            for (Kuehlschrank kuelschrank : raum.getKuehlschraenke()) {
                for (int i = 0; i < kuelschrank.getAnzahlSegmente(); i++) {
                    for (int j = 0; j < kuelschrank.getSegment(i).GESTELLE_PRO_SEGMENT; j++) {
                        for (int k = 0; k < kuelschrank.getSegment(i).getGestell(j).SCHUBLADEN_PRO_GESTELL; k++) {
                            for (int l = 0; l < kuelschrank.getSegment(i).getGestell(j).getSchublade(k)
                                    .getRacks().length; l++) {
                                Rack rack = kuelschrank.getSegment(i).getGestell(j).getSchublade(k).getRack(l);
                                if (rack != null) {
                                    if (ProbenSteuerung.getInstance().isRackReserviert(rack))
                                        anzRacks++;
                                }
                            }

                        }
                    }
                }
            }
        }
        return anzRacks;
    }
}