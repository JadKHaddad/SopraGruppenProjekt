/**
 * 
 */
package de.wwu.sopra.controller.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import de.wwu.sopra.model.Messtyp;
import de.wwu.sopra.model.Raum;

/**
 * @author Gruppe 5
 *
 */
public class MesstypVerwaltungTest {

    
    
    /**
     * testet ob bei getMesstypVerwaltung ein Objekt erstellt wird
     */
    @Test
    public void testGetMesstypVerwaltung() {
        assertNotNull(MesstypVerwaltung.getInstance());
    }

    
    /**
     * testet die addMesstyp und hasMesstyp Methode der MesstypVerwaltung
     */
    @Test
    public void testAddMesstypHasMesstyp() {
        MesstypVerwaltung messtypVerwaltung = MesstypVerwaltung.getInstance();
        Messtyp messtyp = new Messtyp("a", "a");
        messtypVerwaltung.addMesstyp(messtyp);
        assertTrue(messtypVerwaltung.hasMesstyp(messtyp));
    }
    
    
    /**
     * testet die removeMesstyp Methode der MesstypVerwaltung
     */
    @Test
    public void testRemoveMesstyp() {
        MesstypVerwaltung messtypVerwaltung = MesstypVerwaltung.getInstance();
        Messtyp messtyp = new Messtyp("a", "a");
        messtypVerwaltung.addMesstyp(messtyp);
        messtypVerwaltung.removeMesstyp(messtyp);
        assertFalse(messtypVerwaltung.hasMesstyp(messtyp));
    }
    
    /**
     * testet die getMesstypen und setMesstypen Methode der MesstypenVerwaltung
     */
    @Test
    public void testGetMesstypSetSetMesstypSet() {
        MesstypVerwaltung messtypVerwaltung = MesstypVerwaltung.getInstance();
        Messtyp messtyp = new Messtyp("a", "a");
        ArrayList<Messtyp> messtypen = new ArrayList<Messtyp>();
        messtypen.add(messtyp);
        messtypVerwaltung.setMesstypen(messtypen);
        assertEquals(messtypen, messtypVerwaltung.getMesstypen());
        assertTrue(messtypVerwaltung.hasMesstyp(messtyp));
    }
    
    /**
     * Testet NameIsUnique
     */
    @Test
    public void testNameIsUnique() {
        MesstypVerwaltung mt = MesstypVerwaltung.getInstance();
        mt.addMesstyp(new Messtyp("asdf", ""));
        assertTrue(mt.nameIsUnique("qwer"));
        assertFalse(mt.nameIsUnique("asdf"));
        
    }

}
