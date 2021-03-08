package de.wwu.sopra.controller.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import de.wwu.sopra.controller.data.ProbenKategorieVerwaltung;
import org.junit.jupiter.api.Test;

import de.wwu.sopra.model.Behaeltertyp;
import de.wwu.sopra.model.ProbenKategorie;

/**
 * Testet die ProbenKategorieVerwaltung
 * 
 * @author Gruppe 5
 *
 */
public class ProbenKategorieVerwaltungTest {

    /**
     * testet ob die ProbenKategorieVerwaltung die instance initialisiert
     */
    @Test
    public void testGetProbenKategorieVerwaltung() {
        assertNotNull(ProbenKategorieVerwaltung.getInstance());
    }

    /**
     * testet die addProbenKategorie und hasProbenKategorie Methoden
     */
    @Test
    public void testAddProbenKategorieHasProbenKategorie() {
        ProbenKategorieVerwaltung probenKategorieVerwaltung = ProbenKategorieVerwaltung.getInstance();
        ProbenKategorie probenKategorie = new ProbenKategorie("a", "n", 10f, 0f);
        probenKategorieVerwaltung.addProbenKategorie(probenKategorie);
        assertTrue(probenKategorieVerwaltung.hasProbenKategorie(probenKategorie));
    }

    /**
     * testet die removeProbenKategorie Methode
     */
    @Test
    public void testRemoveProbenKategorie() {
        ProbenKategorieVerwaltung probenKategorieVerwaltung = ProbenKategorieVerwaltung.getInstance();
        ProbenKategorie probenKategorie = new ProbenKategorie("a", "n", 10f, 0f);
        probenKategorieVerwaltung.addProbenKategorie(probenKategorie);
        probenKategorieVerwaltung.removeProbenKategorie(probenKategorie);
        assertFalse(probenKategorieVerwaltung.hasProbenKategorie(probenKategorie));
    }

    /**
     * testet die getProbenKategorieSet und setProbenKategorieSet Methoden
     */
    @Test
    public void testGetProbenKategorieSetSetProbenKategorieSet() {
        ProbenKategorieVerwaltung probenKategorieVerwaltung = ProbenKategorieVerwaltung.getInstance();
        ProbenKategorie probenKategorie = new ProbenKategorie("a", "n", 10f, 0f);
        Set<ProbenKategorie> probenKategorien = new HashSet<ProbenKategorie>();
        probenKategorien.add(probenKategorie);
        probenKategorieVerwaltung.setProbenKategorieSet(probenKategorien);
        assertEquals(probenKategorien, probenKategorieVerwaltung.getProbenKategorieSet());
        assertTrue(probenKategorieVerwaltung.hasProbenKategorie(probenKategorie));
    }

    /**
     * Testet NameisUNique
     */
    @Test
    public void testNameIsUnique() {
        assertTrue(ProbenKategorieVerwaltung.getInstance().nameIsUnique("b2"));
        ProbenKategorie probenkategorie = new ProbenKategorie("b2", null, 0, 0);
        ProbenKategorieVerwaltung.getInstance().addProbenKategorie(probenkategorie);
        assertTrue(!(ProbenKategorieVerwaltung.getInstance().nameIsUnique("b2")));
    }
    
}