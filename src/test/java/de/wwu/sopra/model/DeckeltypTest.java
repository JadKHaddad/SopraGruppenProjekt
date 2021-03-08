package de.wwu.sopra.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 * Testet den Deckeltypen
 * @author Gruppe 5
 *
 */
public class DeckeltypTest {
    
    Deckeltyp deckeltyp;
    
    @BeforeEach
    public void setUp() {
        deckeltyp =  new Deckeltyp(null);
    }
    /**
     * setter und getter Test
     */
    @Test
    public void testSetGetName() {
        
        String name = "Deckeltyp1";
        deckeltyp.setName(name);
        assertEquals(name, deckeltyp.getName());
        
        
    }

}
