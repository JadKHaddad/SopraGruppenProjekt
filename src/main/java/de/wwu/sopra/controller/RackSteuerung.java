package de.wwu.sopra.controller;

import de.wwu.sopra.controller.data.BidVerwaltung;
import de.wwu.sopra.model.Behaeltertyp;
import de.wwu.sopra.model.ProbenPlatz;
import de.wwu.sopra.model.Rack;
import de.wwu.sopra.model.Schublade;

public class RackSteuerung {

    private static RackSteuerung instance;

    private RackSteuerung() {

    }

    public static synchronized RackSteuerung getInstance() {
        if (instance == null) {
            instance = new RackSteuerung();
        }
        return instance;
    }

    /**
     * erstellt ein neues Rack
     * @param behaeltertyp der Behaeltertyp der im Rack sein soll
     * @param schublade die zugehoerige Schublade
     * @param schubladenplatz der Platz in der Schublade
     * @return das neue Rack
     */
    public Rack erstelleRack(Behaeltertyp behaeltertyp, Schublade schublade, int schubladenplatz) {
        return new Rack(BidVerwaltung.getInstance().getBid(), (int) (25f / behaeltertyp.getRadius()),
            (int) (25f / behaeltertyp.getRadius()), behaeltertyp, schublade, schubladenplatz);
    }

    /**
     * überprüft, ob ein Rack keine Proben enthält
     * @param rack Das Rack.
     * @return das Rack ist leer bwz. nicht leer
     */
    public boolean istRackLeer(Rack rack) {
        for (int i = 0; i < rack.getZeilen(); i++) {
            for (int j = 0; j < rack.getSpalten(); j++) {
                if (rack.getProbe(i, j) != null ||
                    ProbenSteuerung.getInstance().isProbenPlatzReserviert(rack.getProbenplatz(j, i))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Entfernt ein Rack aus einer Schublade an der (index) Stelle
     * @param rack Das Rack, welches entfernt werden soll
     * @return Ob das Rack leer war und somit erfolgreich entfernt wurde.
     */
    public boolean removeRack(Rack rack) {
        if (istRackLeer(rack)) {
            rack.setSchublade(null, rack.getStelleInSchublade());
            ProbenSteuerung.getInstance().entferneRackReservierung(rack);
            return true;
        }
        return false;
    }

    /**
     * Gibt einen/mehrere Buchstaben zurueck, die fuer eine Zahl stehen.
     * Z.B.: 1=A, 2=B, 26=Z, 27=AA usw.
     * @param i Die Zahl
     * @return Der String.
     */
    public String getLetterString(int i) {
        if (i > 26) {
            return getLetterString(i / 26) + getLetterString(i % 26);
        } else {
            return Character.toString(i + 64);
        }
    }

    /**
     * Gibt den Namen fuer einen Probenplatz zurueck.
     * @param p Der Probenplatz p.
     * @return Der Name.
     */
    public String getNameForProbenPlatz(ProbenPlatz p) {
        return getLetterString(p.getZeile() + 1) + (p.getSpalte() + 1);
    }

}