package de.wwu.sopra.controller;

import de.wwu.sopra.controller.data.LagerVerwaltung;
import de.wwu.sopra.model.Kuehlschrank;
import de.wwu.sopra.model.Raum;

public class KuehlschrankSteuerung {
    
private static KuehlschrankSteuerung instance;
    
    private KuehlschrankSteuerung() {
        
    }
    
    public static synchronized KuehlschrankSteuerung getInstance() {
        if(instance == null) {
            instance = new KuehlschrankSteuerung();
        }
        return instance;
    }
     
    public boolean istKuehlschrankVorhanden(String name, Kuehlschrank ausserkuehlschrank, Raum raum ) {
        for(Kuehlschrank k: raum.getKuehlschraenke()) {
            if(ausserkuehlschrank == null || k != ausserkuehlschrank) {
                if(k.getName().equals(name)) {
                    return true;
                }
            }
        }        
        return false;
    }
    

}
