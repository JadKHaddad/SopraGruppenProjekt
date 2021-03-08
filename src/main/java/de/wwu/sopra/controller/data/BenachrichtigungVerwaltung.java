/**
 * 
 */
package de.wwu.sopra.controller.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import de.wwu.sopra.model.Probe;
import de.wwu.sopra.model.ProbenStatus;

/**
 * @author Gruppe 5
 * 
 *         Klasse zum Verwalten von Benachrichtigungen
 *
 */

public class BenachrichtigungVerwaltung {

    private static BenachrichtigungVerwaltung instance;

    private BenachrichtigungVerwaltung() {

    }

    /**
     * 
     * Benachrichtigungsverwaltung als Singleton, damit sicher gestellt wird, dass
     * alle Benachrichtigungen synchronisiert sind
     * 
     * @return den Sigleton BenachrichtigungVerwaltung
     */
    public static synchronized BenachrichtigungVerwaltung getInstance() {
        if (instance == null) {
            BenachrichtigungVerwaltung.instance = new BenachrichtigungVerwaltung();
        }
        return instance;
    }

    public ArrayList<Probe> getNachrichtNeueProbe() {

        ProbenVerwaltung probenVerwaltung = ProbenVerwaltung.getInstance();
        ArrayList<Probe> nachrichtNeueProbe = new ArrayList<Probe>();

        Set<Probe> proben = probenVerwaltung.getProbenSet();

        Iterator<Probe> iterator = proben.iterator();
        while (iterator.hasNext()) {
            Probe probe = iterator.next();
            if (probe.getStatus() == ProbenStatus.NEU) {
                nachrichtNeueProbe.add(probe);
            }
        }
        return nachrichtNeueProbe;
    }

    public ArrayList<Probe> getNachrichtLoeschProbe() {

        ProbenVerwaltung probenVerwaltung = ProbenVerwaltung.getInstance();
        ArrayList<Probe> nachrichtLoeschProbe = new ArrayList<Probe>();

        Set<Probe> proben = probenVerwaltung.getProbenSet();

        Iterator<Probe> iterator = proben.iterator();

        while (iterator.hasNext()) {
            Probe probe = iterator.next();
            if (probe.getStatus() == ProbenStatus.SOLL_VERNICHTET_WERDEN) {
                nachrichtLoeschProbe.add(probe);
            }
        }
        return nachrichtLoeschProbe;
    }
}
