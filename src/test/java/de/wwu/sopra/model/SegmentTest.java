package de.wwu.sopra.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testet das Segment  
 * @author GRuppe 5
 */
public class SegmentTest {

    /**
     * testet die getSchublade Methode und den Konstruktor der Segment Klasse
     */
    @Test
    public void testKonstruktorUndGetGestell() {
        Segment segment = new Segment(null, 0);
        for(int i = 0; i < Segment.GESTELLE_PRO_SEGMENT; i++) {
            assertNotNull(segment.getGestell(i));
        }
        assertThrows(IndexOutOfBoundsException.class, () -> segment.getGestell(Segment.GESTELLE_PRO_SEGMENT));
    }

    /**
     * Testet die GetKuehlschrank Methode
     */
    @Test
    public void testGetKuehlschrank() {
        Kuehlschrank kuehlschrank = new Kuehlschrank("asdf", 0.1f, 0.1f, 0.1f, 0.1f, 4, null);
        Segment segment = kuehlschrank.getSegment(2);
        assertEquals(kuehlschrank, segment.getKuehlschrank());
        assertEquals(2, segment.getStelleImKuehlschrank());
    }
}
