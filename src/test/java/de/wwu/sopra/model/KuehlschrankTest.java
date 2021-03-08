package de.wwu.sopra.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Testet den Kuehlschrank
 * 
 * @author Gruppe 5
 */

public class KuehlschrankTest {

    /**
     * testet die set und get Name Methode
     */
    @Test
    public void testSetGetName() {
        Raum r = new Raum("asd", 0.2f, 0.2f);
        Kuehlschrank k = new Kuehlschrank("asdf", 0.1f, 0.1f, 0.1f, 0.1f, 1, r);
        assertEquals("asdf",k.getName());
        k.setName("mimimi");
        assertEquals("mimimi",k.getName());
    }

    /**
     * testet die setBreite Methode der Kuehlschrank Klasse
     */
    @Test
    public void testSetBreite() {
        Raum r = new Raum("asd", 0.2f, 0.2f);
        Kuehlschrank k = new Kuehlschrank("asdf", 0.1f, 0.1f, 0.1f, 0.1f, 1, r);
        k.setBreite(0.5f);
        assertEquals(0.5f, k.getBreite());
    }

    /**
     * testet die setTiefe Methode der Kuehlschrank Klasse
     */
    @Test
    public void testSetTiefe() {
        Raum r = new Raum("asd", 0.2f, 0.2f);
        Kuehlschrank k = new Kuehlschrank("asdf", 0.1f, 0.1f, 0.1f, 0.1f, 1, r);
        k.setTiefe(0.5f);
        assertEquals(0.5f, k.getTiefe());
    }

    /**
     * testet die setHoehe Methode der Kuehlschrank Klasse
     */
    @Test
    public void testSetHoehe() {
        Raum r = new Raum("asd", 0.2f, 0.2f);
        Kuehlschrank k = new Kuehlschrank("asdf", 0.1f, 0.1f, 0.1f, 0.1f, 1, r);
        k.setHoehe(0.5f);
        assertEquals(0.5f, k.getHoehe());
    }

    /**
     * testet die setTemperatur Methode der Kuehlschrank Klasse
     */
    @Test
    public void testSetTemperatur() {
        Raum r = new Raum("asd", 0.2f, 0.2f);
        Kuehlschrank k = new Kuehlschrank("asdf", 0.1f, 0.1f, 0.1f, 0.1f, 1, r);
        k.setTemperatur(0.5f);
        assertEquals(0.5f, k.getTemperatur());
    }

    /**
     * testet die setRaum Methode der Kuehlschrank Klasse
     */
    @Test
    public void testSetRaum() {
        Raum r = new Raum("asd", 0.2f, 0.2f);
        Raum r2 = new Raum("qwe", 0.3f, 0.3f);
        Kuehlschrank k = new Kuehlschrank("asdf", 0.1f, 0.1f, 0.1f, 0.1f, 1, r);
        k.setRaum(r2);
        assertEquals(r2, k.getRaum());
    }

    /**
     * testet die getBreite Methode der Kuehlschrank Klasse
     */
    @Test
    public void testGetBreite() {
        Raum r = new Raum("asd", 0.2f, 0.2f);
        Kuehlschrank k = new Kuehlschrank("asdf", 0.1f, 0.1f, 0.1f, 0.1f, 1, r);
        assertEquals(0.1f, k.getBreite());
    }

    /**
     * testet die getTiefe Methode der Kuehlschrank Klasse
     */
    @Test
    public void testGetTiefe() {
        Raum r = new Raum("asd", 0.2f, 0.2f);
        Kuehlschrank k = new Kuehlschrank("asdf", 0.1f, 0.1f, 0.1f, 0.1f, 1, r);
        assertEquals(0.1f, k.getTiefe());
    }

    /**
     * testet die getHoehe Methode der Kuehlschrank Klasse
     */
    @Test
    public void testGetHoehe() {
        Raum r = new Raum("asd", 0.2f, 0.2f);
        Kuehlschrank k = new Kuehlschrank("asdf", 0.1f, 0.1f, 0.1f, 0.1f, 1, r);
        assertEquals(0.1f, k.getHoehe());
    }

    /**
     * testet die getTemperatur Methode der Kuehlschrank Klasse
     */
    @Test
    public void testGetTemperatur() {
        Raum r = new Raum("asd", 0.2f, 0.2f);
        Kuehlschrank k = new Kuehlschrank("asdf", 0.1f, 0.1f, 0.1f, 0.1f, 1, r);
        assertEquals(0.1f, k.getTemperatur());
    }

    /**
     * testet die getAnzSegmente Methode der Kuehlschrank Klasse
     */
    @Test
    public void testGetAnzahlSegmente() {
        Raum r = new Raum("asd", 0.2f, 0.2f);
        Kuehlschrank k = new Kuehlschrank("asdf", 0.1f, 0.1f, 0.1f, 0.1f, 1, r);
        assertEquals(1, k.getAnzahlSegmente());
    }

    /**
     * testet die getRaum Methode der Kuehlschrank Klasse
     */
    @Test
    public void testGetRaum() {
        Raum r = new Raum("asd", 0.2f, 0.2f);
        Kuehlschrank k = new Kuehlschrank("asdf", 0.1f, 0.1f, 0.1f, 0.1f, 1, r);
        assertEquals(r, k.getRaum());
    }

    /**
     * testet die getSegment Methode und den Konstruktor der Segment Klasse
     */
    @Test
    public void testKonstruktorUndGetSegment() {
        Kuehlschrank kuehlschrank = new Kuehlschrank("asdf", 0.1f, 0.1f, 0.1f, 0.1f, 4, null);
        for (int i = 0; i < kuehlschrank.getAnzahlSegmente(); i++) {
            assertNotNull(kuehlschrank.getSegment(i));
        }
        assertThrows(IndexOutOfBoundsException.class, () -> {
            kuehlschrank.getSegment(kuehlschrank.getAnzahlSegmente());
        });
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
