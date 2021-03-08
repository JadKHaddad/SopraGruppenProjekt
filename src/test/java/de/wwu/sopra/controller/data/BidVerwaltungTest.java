package de.wwu.sopra.controller.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * Testet Die BID Verwaltung
 * @author Gruppe 5
 *
 */
public class BidVerwaltungTest {

    /**
     * testet getCounter und setCounter
     */
    @Test
    public void testGetCounterSetCounter() {
        BidVerwaltung bidVerwaltung = BidVerwaltung.getInstance();
        bidVerwaltung.setCounter(5);
        assertEquals(bidVerwaltung.getCounter(), 5);
    }
    
    /**
     * testet getBid
     */
    @Test
    public void testGetBid() {
        BidVerwaltung bidVerwaltung = BidVerwaltung.getInstance();
        assertNotEquals(bidVerwaltung.getBid(), bidVerwaltung.getBid());
    }
}
