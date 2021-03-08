package de.wwu.sopra.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testet ein Gestell
 * @author Grupppe 5
 */
public class GestellTest {

	/**
	 * testet die getSchublade Methode und den Konstruktor der Gestell Klasse
	 */
    @Test
	public void testKonstruktorUndGetSchublade() {
        Gestell g = new Gestell(null, 0);
        for(int i = 0; i < Gestell.SCHUBLADEN_PRO_GESTELL; i++) {
            assertNotNull(g.getSchublade(i));
        }
        assertThrows(IndexOutOfBoundsException.class, () -> {
            g.getSchublade(Gestell.SCHUBLADEN_PRO_GESTELL);
        });
	}
    
    /**
     * Testet die Stelle des Gestells im Segment
     */
    @Test
    public void testGetStelleImSegment() {
          Gestell g = new Gestell(null, 2);
          assertEquals(2,g.getStelleImSegment());
    }

    /**
     * Testet die GetSegmentMethode
     */
    @Test
	public void testGetSegment() {
        Segment segment = new Segment(null, 0);
        Gestell gestell = segment.getGestell(2);
        assertEquals(segment, gestell.getSegment());
        assertEquals(2, gestell.getStelleImSegment());
    }
}
