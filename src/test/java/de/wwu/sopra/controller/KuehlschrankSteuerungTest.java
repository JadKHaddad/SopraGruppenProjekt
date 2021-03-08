package de.wwu.sopra.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.wwu.sopra.model.Kuehlschrank;
import de.wwu.sopra.model.Raum;

/**
 * Testet die KuehlschrankSteuerung
 * @author Gruppe 5
 */
public class KuehlschrankSteuerungTest {
    
    /**
     * Testet, ob ein kuehlschrank vorhanden ist.
     */
    @Test
    public void testIstKuehlschrankVorhanden() {
        Raum raum = new Raum("raum1",39 ,40);
        Kuehlschrank k1 =  new Kuehlschrank("k1",2,2,2,2,2,raum);
        raum.addKuehlschrank(k1);
        assertTrue(KuehlschrankSteuerung.getInstance().istKuehlschrankVorhanden("k1", null, raum));
        assertFalse(KuehlschrankSteuerung.getInstance().istKuehlschrankVorhanden("k1", k1, raum));
        assertFalse(KuehlschrankSteuerung.getInstance().istKuehlschrankVorhanden("k2", null, raum));
        
    }

}
