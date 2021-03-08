package de.wwu.sopra.controller;

import de.wwu.sopra.controller.data.StudienVerwaltung;
import de.wwu.sopra.model.*;
import javafx.scene.paint.Color;

import java.util.Collection;

public class StudienSteuerung {

    private static StudienSteuerung instance;

    private StudienSteuerung() {

    }

    public static synchronized StudienSteuerung getInstance() {
        if(instance == null) {
            instance = new StudienSteuerung();
        }
        return instance;
    }

    /**
     * Erstellt eine Studie und fuegt sie zur Studienverwaltung hinzu
     * @param name Der Name der Studie.
     * @param teilnehmerAnz Die Teilnehmeranzahl der Studie.
     * @param visiten Eine Liste/Ein Set der Visiten
     * @param nurses Eine Liste/Ein Set der Nurses
     * @return Die Studie, die zur Verwaltung hinzugefuegt wurde, oder null, falls keine Probenplaetze fuer die Studie
     * reserviert werden konnten.
     */
    public Studie erstelleStudie(String name, int teilnehmerAnz, Collection<Visite> visiten, Collection<Benutzer> nurses) {
        Studie studie = new Studie(name, teilnehmerAnz);

        for (Visite v : visiten) {
            v.setStudie(studie);
        }

        for (Benutzer n : nurses) {
            n.addStudie(studie);
        }

        StudienVerwaltung.getInstance().addStudie(studie);

        return studie;
    }

    /**
     * Entfernt eine Studie
     * @param studie die Studie
     */
    public void entferneStudie(Studie studie) {
        if (studie != null) {
            while(!studie.getBenutzer().isEmpty()) {
                studie.getBenutzer().get(0).removeStudie(studie);
            }
            for(Visite visite: studie.getVisiten()) {
                for(PatientenTermin patientenTermin: visite.getPatientenTermine()) {
                    for(Probe probe: patientenTermin.getProben()) {
                        probe.setStatus(ProbenStatus.SOLL_VERNICHTET_WERDEN);
                    }
                }
            }
        }
        ProbenSteuerung.getInstance().entferneReservierungen(studie);
        StudienVerwaltung.getInstance().removeStudie(studie);
    }

    public boolean starteStudie(Studie studie) {
        if (!ProbenSteuerung.getInstance().reserviereProben(studie)) {
            return false;
        }
        studie.setBearbeitbar(false);
        return true;
    }
}
