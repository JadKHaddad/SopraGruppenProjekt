package de.wwu.sopra.controller;

import de.wwu.sopra.model.Messung;
import de.wwu.sopra.model.Patient;
import de.wwu.sopra.model.PatientenTermin;
import de.wwu.sopra.model.Probe;
import de.wwu.sopra.model.ProbenStatus;
import de.wwu.sopra.model.Studienteilnehmer;

public class PatientenSteuerung {

    private static PatientenSteuerung instance;

    private PatientenSteuerung() {      
    }
    
    /**
     * PatientenSteuerung als Singleton um sicherzustellen, dass nur eine
     * PatientenSteuerung mit korrektem, synchronisiertem Inhalt existiert
     * 
     * @return die PatientenSteuerung instance
     */
    public static synchronized PatientenSteuerung getInstance() {
        if (instance == null)
            instance = new PatientenSteuerung();
        return instance;
    }
    
    /**
     * fuer einen Patienten werden alle Proben auf "soll vernichtet werden" gesetzt. Ausßerdem werden alle Messungen entfernt
     * @param patient der aus dem System geloescht werden soll
     */
    public void patientEntfernen(Patient patient) {    
        for(int i = 0; i< patient.getStudienteilnehmer().size(); i++) {
            Studienteilnehmer studienTeilnehmer = patient.getStudienteilnehmer().get(i);
            studienTeilnehmer.setStudie(null);
            studienTeilnehmer.setPatient(null);
            for(int j = 0; j < studienTeilnehmer.getPatientenTermine().size(); j++) {
                PatientenTermin patientenTermin = studienTeilnehmer.getPatientenTermine().get(j);
                patientenTermin.setVisite(null);
                for(int k = 0; k < patientenTermin.getProben().size(); k++) {
                    patientenTermin.getProben().get(k).setStatus(ProbenStatus.SOLL_VERNICHTET_WERDEN);
                }
                for(int k = 0; k < patientenTermin.getProben().size(); k++) {
                    patientenTermin.getProben().get(k).setTermin(null);
                }
            }            
        }      
    }
}
