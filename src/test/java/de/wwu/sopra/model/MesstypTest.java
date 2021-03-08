package de.wwu.sopra.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 * @author Gruppe 5
 *
 */
public class MesstypTest {
    
    public Messtyp messtyp;
    
    @BeforeEach
    public void setUp() {
        messtyp =  new Messtyp(null, null);
    }
    /**
     * setName und getName Test
     */
    @Test
    public void testGetSetName() {

        String name = "name";
        messtyp.setName(name);
        assertEquals(name, messtyp.getName());

    }
    
    /**
     * setEinheit und getEinheit Test
     */
    @Test
    public void testSetGetEinheit() {
        
        String einheit = "einheit";
        messtyp.setEinheit(einheit);
        assertEquals(messtyp.getEinheit(), "einheit");
    }

}
