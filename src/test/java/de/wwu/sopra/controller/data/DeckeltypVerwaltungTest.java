package de.wwu.sopra.controller.data;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.wwu.sopra.model.Deckeltyp;
import de.wwu.sopra.model.Messtyp;

/**
 * Testet die DeckeltapVErwaltung
 * @author Gruppe 5
 */
public class DeckeltypVerwaltungTest {
    /**
     * testet hasDeckeltyp
     */
    @Test
    public void testHasDeckelTyp() {
        DeckeltypVerwaltung dkt = DeckeltypVerwaltung.getInstance();
        Deckeltyp dt = new Deckeltyp("R2d2");
        Deckeltyp dt2 = new Deckeltyp("c3po");
        dkt.addDeckeltyp(dt);
        assertTrue(dkt.hasDeckeltyp(dt));
        assertFalse(dkt.hasDeckeltyp(dt2));
    }
    
    /**
     * Testet NameIsUnique
     */
    @Test
    public void testNameIsUnique() {
        DeckeltypVerwaltung dkt = DeckeltypVerwaltung.getInstance();
        dkt.addDeckeltyp(new Deckeltyp("asdf"));
        assertTrue(dkt.nameIsUnique("qwer"));
        assertFalse(dkt.nameIsUnique("asdf"));     
    }

}
