package de.wwu.sopra.controller;

import de.wwu.sopra.controller.data.LagerVerwaltung;
import de.wwu.sopra.controller.data.ProbenVerwaltung;
import de.wwu.sopra.model.*;

import java.util.*;

/**
 * Steuerung der Probenklasse. Fuer die intelligente Probenplatzverteilung zustaendig
 * @author Gruppe 5
 */
public class ProbenSteuerung {

    private static ProbenSteuerung instance;

    private LinkedHashMap<ProbenPlatz, Studie> reservierungen;

    private HashMap<Studie, HashSet<Rack>> studienRacks;
    private LinkedHashMap<ProbenPlatz, Studie> temporaereReservierungen;

    protected ProbenSteuerung() {
        studienRacks = new HashMap<>();
        reservierungen = new LinkedHashMap<>();
        temporaereReservierungen = new LinkedHashMap<>();
    }

    /**
     * ProbenSteuerung als Singleton um sicherzustellen, dass nur eine
     * ProbenSteuerung mit korrektem, synchronisiertem Inhalt existiert
     * @return die BenutzerSteuerung instance
     */
    public static synchronized ProbenSteuerung getInstance() {
        if (instance == null)
            instance = new ProbenSteuerung();
        return instance;
    }

    /**
     * reserviert die noetige Anzahl an Proben fuer eine Studie
     * @param studie die zu reservierende Studie
     * @return true falls die Reservierung abgeschlossen wurde.
     */
    public boolean reserviereProben(Studie studie) {
        Map<ProbenKategorie, Map<Behaeltertyp, Integer>> probenArten = getAnzahlAnBenoetigenProbenplaetzen(studie);
        Set<Rack> hinzugefuegteRacksFuerStudie = new HashSet<>();
        Set<Rack> alleRacksFuerStudie = new HashSet<>();
        if (studienRacks.containsKey(studie)) {
            alleRacksFuerStudie.addAll(studienRacks.get(studie));
        }
        for (ProbenKategorie probenKategorie : probenArten.keySet()) {
            for (Behaeltertyp behaeltertyp : probenArten.get(probenKategorie).keySet()) {
                int anzahl = probenArten.get(probenKategorie).get(behaeltertyp);
                while (anzahl > 0) {
                    int maxFreiePlaetze = 0;
                    Rack zuNutzendesRack = null;
                    for (Rack rack : alleRacksFuerStudie) {
                        Kuehlschrank kuehlschrank = rack.getSchublade().getGestell().getSegment().getKuehlschrank();
                        if (rack.getBehaeltertyp() == behaeltertyp &&
                            probenKategorie.getMinLagerTemp() < kuehlschrank.getTemperatur() &&
                            probenKategorie.getMaxLagerTemp() > kuehlschrank.getTemperatur()) {
                            int i = getAnzahlFreieProbenPlatzAufRack(rack);
                            if (i > maxFreiePlaetze) {
                                maxFreiePlaetze = i;
                                zuNutzendesRack = rack;
                            }
                        }
                    }
                    if (zuNutzendesRack == null) {
                        zuNutzendesRack = findeNeuesZuNutzendesRack(studie, probenKategorie, behaeltertyp);
                        if (zuNutzendesRack != null) {
                            hinzugefuegteRacksFuerStudie.add(zuNutzendesRack);
                            alleRacksFuerStudie.add(zuNutzendesRack);
                        }
                    }
                    // Falls immer noch kein Rack gefunden wurde, ist kein Platz, und reservierungen werden rückgängig gemacht.
                    if (zuNutzendesRack == null) {
                        temporaereReservierungen.clear();
                        Iterator<Rack> it = hinzugefuegteRacksFuerStudie.iterator();
                        while (it.hasNext()) {
                            Rack rack = it.next();
                            rack.setSchublade(null, 0);
                            alleRacksFuerStudie.remove(rack);
                            it.remove();
                        }
                        return false;
                    }
                    // Sonst, reserviere so viele Freie Plätze auf dem Rack, wie möglich und gebraucht.
                    for (ProbenPlatz probenPlatz : getFreieProbenPlaetzeAufRack(zuNutzendesRack)) {
                        if (anzahl == 0)
                            break;
                        temporaereReservierungen.put(probenPlatz, studie);
                        anzahl--;
                    }
                }
            }
        }
        if (!studienRacks.containsKey(studie)) {
            studienRacks.put(studie, new HashSet<>());
        }
        studienRacks.get(studie).addAll(hinzugefuegteRacksFuerStudie);
        reservierungen.putAll(temporaereReservierungen);
        temporaereReservierungen.clear();
        return true;
    }

    /**
     * Findet ein neues zu nutzendes Rack für eine Studie, eine probenKategorie mit einem bestimmten Behaeltertypen.
     * @param studie          Die Studie, fuer die ein neues Rack gefunden werden soll.
     * @param probenKategorie Die Probenkategorie, zu der die Temperatur des Kuehlschranks passen muss.
     * @param behaeltertyp    Der Behaeltertyp, fuer den das Rack sein muss.
     * @return Das Rack, wenn eines gefunden wurde, sonst null.
     */
    protected Rack findeNeuesZuNutzendesRack(Studie studie, ProbenKategorie probenKategorie, Behaeltertyp behaeltertyp) {
        for (Raum raum : LagerVerwaltung.getInstance().getRaumSet()) {
            for (Kuehlschrank kuehlschrank : raum.getKuehlschraenke()) {
                if (probenKategorie.getMinLagerTemp() < kuehlschrank.getTemperatur() &&
                    probenKategorie.getMaxLagerTemp() > kuehlschrank.getTemperatur()) {
                    for (int i = 0; i < kuehlschrank.getAnzahlSegmente(); i++) {
                        for (int j = 0; j < Segment.GESTELLE_PRO_SEGMENT; j++) {
                            for (int k = 0; k < Gestell.SCHUBLADEN_PRO_GESTELL; k++) {
                                for (int l = 0; l < Schublade.RACKS_PRO_SCHUBLADE; l++) {
                                    Schublade schublade = kuehlschrank.getSegment(i).getGestell(j).getSchublade(k);
                                    Rack rack = schublade.getRack(l);
                                    if (rack == null) {
                                        return RackSteuerung.getInstance().erstelleRack(behaeltertyp, schublade, l);
                                    } else if (!isRackReserviert(rack) && rack.getBehaeltertyp() == behaeltertyp && getAnzahlFreieProbenPlatzAufRack(rack) > 0) {
                                        return rack;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Gibt zurueck, ob ein Rack fuer eine Studie reserviert ist.
     * @param r Das Rack.
     * @return Ob es fuer eine Studie reserviert ist.
     */
    public boolean isRackReserviert(Rack r) {
        for (HashSet<Rack> rackSet : studienRacks.values()) {
            if (rackSet.contains(r)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Loescht eine Rackreservierung.
     * @param r Das Rack.
     */
    public void entferneRackReservierung(Rack r) {
        for(Studie studie: studienRacks.keySet()) {
            if (studienRacks.get(studie).remove(r)) {
                return;
            }
        }
    }

    /**
     * Gibt pro ProbenKategorie und Behaeltertyp zurueck, wie viele Probenplaetze gebraucht werden.
     * @param studie Die studie, fuer die die Probenplaetze berechnet werden sollen.
     * @return Die Anzahl der Probenplatze pro Probenkategorie und Behaeltertyp.
     */
    protected Map<ProbenKategorie, Map<Behaeltertyp, Integer>> getAnzahlAnBenoetigenProbenplaetzen(Studie studie) {
        Map<ProbenKategorie, Map<Behaeltertyp, Integer>> probenArten = new HashMap<>();
        for (Visite visite : studie.getVisiten()) {
            for (ProbenInfo probenInfo : visite.getProbenInfos()) {
                if (!probenArten.containsKey(probenInfo.getKategorie())) {
                    probenArten.put(probenInfo.getKategorie(), new HashMap<>());
                }
                Map<Behaeltertyp, Integer> probenKategorieMap = probenArten.get(probenInfo.getKategorie());
                if (probenKategorieMap.containsKey(probenInfo.getBehaeltertyp())) {
                    probenKategorieMap.compute(probenInfo.getBehaeltertyp(), (b, i) -> i += studie.getAnzTeilnehmer());
                } else {
                    probenKategorieMap.put(probenInfo.getBehaeltertyp(), studie.getAnzTeilnehmer());
                }
            }
        }
        return probenArten;
    }

    /**
     * fuegt eine Probe in ein Probenplatz ein, falls ein leerer reservierter Platz gefunden wurde.
     * @param probe die zu einfuegende Probe
     * @return true falls ein leerer reservierter Platz gefunden wurde, ansonsten false
     */
    public boolean fuegeProbeEin(Probe probe) {
        Studie studieDerProbe = probe.getTermin().getStudienTeilnehmer().getStudie();
        for (ProbenPlatz probenPlatz : reservierungen.keySet()) {
            Studie studie = reservierungen.get(probenPlatz);
            if (studie.equals(studieDerProbe)) {
                Kuehlschrank kuehlschrank = probenPlatz.getRack().getSchublade().getGestell().getSegment().getKuehlschrank();
                if (probe.getBehaeltertyp() == probenPlatz.getRack().getBehaeltertyp() &&
                    probe.getInfo().getKategorie().getMinLagerTemp() < kuehlschrank.getTemperatur() &&
                    probe.getInfo().getKategorie().getMaxLagerTemp() > kuehlschrank.getTemperatur()) {
                    if (probenPlatz.getProbe() == null) {
                        probenPlatz.setProbe(probe);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * gibt das Set der freien Probenplaetze eines Racks zurueck
     * @param rack das zu checkende Rack
     * @return das Set der freien Probenplaetze
     */
    public List<ProbenPlatz> getFreieProbenPlaetzeAufRack(Rack rack) {
        LinkedList<ProbenPlatz> plaetze = new LinkedList<>();
        for (int y = 0; y < rack.getZeilen(); y++) {
            for (int x = 0; x < rack.getSpalten(); x++) {
                ProbenPlatz platz = rack.getProbenplatz(x, y);
                if (platz.getProbe() == null && !isProbenPlatzReserviert(platz)) {
                    plaetze.add(platz);
                }
            }
        }
        return plaetze;
    }

    /**
     * gibt die Anzahl an freien Probenplaetzen auf einem Rack zurueck
     * @param rack das zu checkende Rack
     * @return die Anzahl an freien Probenplaetzen
     */
    public int getAnzahlFreieProbenPlatzAufRack(Rack rack) {
        int anzahl = 0;
        for (int y = 0; y < rack.getZeilen(); y++) {
            for (int x = 0; x < rack.getSpalten(); x++) {
                ProbenPlatz platz = rack.getProbenplatz(x, y);
                if (platz.getProbe() == null && !isProbenPlatzReserviert(platz)) {
                    anzahl++;
                }
            }
        }
        return anzahl;
    }

    /**
     * Fuegt eine neue verarbeitete Probe in ein Rack und die ProbenVerwaltung hinzu.
     * @param neueProbe      Die Neue Probe
     * @param ursprungsProbe Die Ursprungsprobe
     * @return ob das geklappt hat
     */
    public ProbenPlatz fuegeNeueVerarbeiteteProbeEin(Probe neueProbe, Probe ursprungsProbe) {
        ProbenPlatz probenPlatz = null;
        Studie studie = neueProbe.getTermin().getStudienTeilnehmer().getStudie();
        for (Rack rack : studienRacks.get(studie)) {
            Kuehlschrank kuehlschrank = rack.getSchublade().getGestell().getSegment().getKuehlschrank();
            if (neueProbe.getInfo().getKategorie().getMinLagerTemp() <= kuehlschrank.getTemperatur() &&
                neueProbe.getInfo().getKategorie().getMaxLagerTemp() >= kuehlschrank.getTemperatur() &&
                rack.getBehaeltertyp() == neueProbe.getBehaeltertyp()) {
                List<ProbenPlatz> probenPlaetze = getFreieProbenPlaetzeAufRack(rack);
                if (probenPlaetze.size() > 0) {
                    probenPlatz = probenPlaetze.get(0);
                    break;
                }
            }
        }
        if (probenPlatz == null) {
            Rack rack = findeNeuesZuNutzendesRack(studie, neueProbe.getInfo().getKategorie(), neueProbe.getBehaeltertyp());
            studienRacks.get(studie).add(rack);
            probenPlatz = rack.getProbenplatz(0, 0);
        }
        if (probenPlatz != null) {
            probenPlatz.setProbe(neueProbe);
            ProbenVerwaltung.getInstance().addProbe(neueProbe);
            return probenPlatz;
        }
        return null;
    }

    /**
     * checkt, ob ein Probenplatz bereits reserviert wurde.
     * @param probenPlatz der zu checkende Probenplatz
     * @return true falls der Probenplatz reserviert ist.
     */
    public boolean isProbenPlatzReserviert(ProbenPlatz probenPlatz) {
        return reservierungen.containsKey(probenPlatz) || temporaereReservierungen.containsKey(probenPlatz);
    }

    /**
     * Entfernt alle Reservierungen fuer eine studie.
     * @param studieZuEntfernen Die Studie.
     */
    public void entferneReservierungen(Studie studieZuEntfernen) {
        reservierungen.entrySet().removeIf(item -> item.getValue() == studieZuEntfernen);
        studienRacks.remove(studieZuEntfernen);
    }

    /**
     * Entfernt eine Probe
     * @param probe Probe
     */
    public void entferneProbe(Probe probe) {
        if (probe != null) {
            probe.setProbenPlatz(null);
            ProbenVerwaltung.getInstance().removeProbe(probe);
        }
    }

    /**
     * @return Die zu Persistierenden Daten.
     */
    public ProbenSteuerungPersistenz getPersistenzDaten() {
        ProbenSteuerungPersistenz persistenz = new ProbenSteuerungPersistenz();
        persistenz.reservierungen = reservierungen;
        persistenz.studienRacks = studienRacks;
        return persistenz;
    }

    /**
     * Setzt Persistzenzdaten
     * @param persistenzDaten Die persistierten Daten.
     */
    public void setPersistenzDaten(ProbenSteuerungPersistenz persistenzDaten) {
        studienRacks = persistenzDaten.studienRacks;
        reservierungen = persistenzDaten.reservierungen;
    }

    /**
     * Innere Klasse, um zu persistierende Daten zu kapseln.
     */
    public static class ProbenSteuerungPersistenz {
        public HashMap<Studie, HashSet<Rack>> studienRacks;
        public LinkedHashMap<ProbenPlatz, Studie> reservierungen;
    }
}
