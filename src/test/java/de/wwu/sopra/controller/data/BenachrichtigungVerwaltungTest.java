package de.wwu.sopra.controller.data;

import de.wwu.sopra.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test der BenachrichtigungVerwaltung Klasse
 *
 * @author Gruppe 5
 */

public class BenachrichtigungVerwaltungTest {

    /**
     * Testvorbereitung
     */
    @BeforeEach
    public void setUp(){
        BenachrichtigungVerwaltung benachrichtigungVerwaltung = BenachrichtigungVerwaltung.getInstance();
    }

    /**
     * testet ob die getInstance Methode ein Objekt erstellt.
     */
    @Test
    public void testGetBenachrichtigungVerwaltung(){
        assertNotNull(BenachrichtigungVerwaltung.getInstance());
    }

    /**
     * testet, ob die getNachrichtNeueProbe korrekt zurueckgibt
     */
    @Test
    public  void testGetNachrichtNeueProbe(){
        BenachrichtigungVerwaltung benachrichtigungVerwaltung = BenachrichtigungVerwaltung.getInstance();
        assertEquals(benachrichtigungVerwaltung.getNachrichtNeueProbe().size(), 0);

        Probe probe = new Probe(1, LocalDateTime.MAX, null, null, null, null, null);
        probe.setStatus(ProbenStatus.NEU);
        ProbenVerwaltung probenVerwaltung = ProbenVerwaltung.getInstance();
        probenVerwaltung.addProbe(probe);
        assertTrue(benachrichtigungVerwaltung.getNachrichtNeueProbe().contains(probe));
    }

    /**
     * testet, ob die getNachrichtLoeschProbe korrekt zurueckgibt
     */
    @Test
    public  void testGetNachrichtLoeschProbe(){
        BenachrichtigungVerwaltung benachrichtigungVerwaltung = BenachrichtigungVerwaltung.getInstance();
        assertEquals(benachrichtigungVerwaltung.getNachrichtLoeschProbe().size(), 0);

        Probe probe = new Probe(1, LocalDateTime.MAX, null, null, null, null, null);
        probe.setStatus(ProbenStatus.SOLL_VERNICHTET_WERDEN);
        ProbenVerwaltung probenVerwaltung = ProbenVerwaltung.getInstance();
        probenVerwaltung.addProbe(probe);
        assertTrue(benachrichtigungVerwaltung.getNachrichtLoeschProbe().contains(probe));
    }
}
