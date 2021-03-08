package de.wwu.sopra.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testet die Visite
 * @author Gruppe 5
 */
public class VisiteTest {
    
    
    Messtyp messtyp;
    
    @BeforeEach
    public void setUp() {
        messtyp = new Messtyp("name", "einheit");
    }
    
    /**
     * teste addMesstyp und hasMesstyp
     */
    @Test
    public void testAddMesstypHasMesstyp() {
        Visite v = new Visite(null, 0, null);
        v.addMesstyp(messtyp);
        assertEquals(v.hasMesstyp(messtyp), true);
    }

    /**
     * testet removeMesstyp
     */
    @Test
    public void testRemoveMesstyp() {
        Visite v = new Visite(null, 0, null);
        v.addMesstyp(messtyp);
        assertEquals(v.hasMesstyp(messtyp), true);
        v.removeMesstyp(messtyp);
        assertEquals(v.hasMesstyp(messtyp), false);
    }

    /**
     * testet get Studie
     */
    @Test
    public void testGetStudie() {
        Studie s = new Studie(null, 0);
        Visite v = new Visite(null, 0, s);
        assertEquals(s, v.getStudie());
    }

    /**
     * testet setStudie
     */
    @Test
    public void testSetStudie() {
        Studie s1 = new Studie("s1", 0);
        Studie s2 = new Studie("s2", 0);
        Studie s3 = new Studie("s3", 0);
        Visite v1 = new Visite("v1", 0, null);
        Visite v2 = new Visite("v2", 0, null);
        v1.setStudie(s1);
        v1.setStudie(s2);
        v2.setStudie(s3);
        v2.setStudie(s3);
        assertEquals(s2, v1.getStudie());
        assertEquals(s3, v2.getStudie());
    }

    /**
     * testet getName
     */
    @Test
    public void testGetName() {
        Visite v = new Visite("Hallo", 0, null);
        assertEquals("Hallo", v.getName());
    }

    /**
     * testet setName
     */
    @Test
    public void testSetName() {
        Visite v = new Visite("Hallo", 0, null);
        v.setName("Hi");
        assertEquals("Hi", v.getName());
    }

    /**
     * testet getStudienstart
     */
    @Test
    public void testGetStudienstart() {
        Visite v = new Visite("Hallo", 0, null);
        assertEquals(0, v.getStudienstart());
    }

    /**
     * testet setStudienstart
     */
    @Test
    public void testSetStudienstart() {
        Visite v = new Visite("Hallo", 0, null);
        v.setStudienstart(1);
        assertEquals(1, v.getStudienstart());
    }

    /**
     * testet addPatientenTermin und hasPatientenTermin
     */
    @Test
    public void testAddPatientenTerminHasPatientenTermin() {
        Visite v1 = new Visite("Hallo", 0, null);
        Visite v2 = new Visite("Tuess", 0, null);
        PatientenTermin pt1 = new PatientenTermin(null, null, null);
        PatientenTermin pt2 = new PatientenTermin(v1, null, null);
        PatientenTermin pt3  = new PatientenTermin(v2, null, null);
        v1.addPatientenTermin(pt3);
        v1.addPatientenTermin(pt2);
        v2.addPatientenTermin(pt2);
        v1.addPatientenTermin(pt1);
        v1.addPatientenTermin(pt1);
        assertEquals(v1.hasPatientenTermin(pt1), true);
        assertEquals(v1.hasPatientenTermin(pt2), false);
        assertEquals(v2.hasPatientenTermin(pt2), true);
        assertEquals(pt2.getVisite(),v2);
        assertEquals(pt1.getVisite(),v1);
        assertEquals(pt3.getVisite(),v1);
        
        
    }

    /**
     * testet removePatientenTermin
     */
    @Test
    public void testRemovePatientenTermin() {
        Visite v = new Visite("Hallo", 0, null);
        PatientenTermin pt = new PatientenTermin(v, null, null);
        v.addPatientenTermin(pt);
        assertEquals(v.hasPatientenTermin(pt), true);
        v.removePatientenTermin(pt);
        assertEquals(v.hasPatientenTermin(pt), false);
    }

    /**
     * testet addProbenInfo und hasProbenInfo
     */
    @Test
    public void testAddProbenInfoHasProbenInfo() {
        Visite v = new Visite("Hallo", 0, null);
        ProbenInfo i = new ProbenInfo(0, null, null);
        v.addProbenInfo(i);
        assertEquals(v.hasProbenInfo(i), true);
    }
 
    /**
     * testet removeProbenInfo
     */
    @Test
    public void testRemoveProbenInfo() {
        Visite v = new Visite("Hallo", 0, null);
        ProbenInfo i = new ProbenInfo(0, null, null);
        v.addProbenInfo(i);
        assertEquals(v.hasProbenInfo(i), true);
        v.removeProbenInfo(i);
        assertEquals(v.hasProbenInfo(i), false);
    }
    
    /**
     * Testet getMesstypen
     */
    @Test
    public void testGetMesstypen() {
        Visite v = new Visite("Hallo", 0, null);
        Messtyp m = new Messtyp("Herzfrequenz", "Schläge pro Minute");
        v.addMesstyp(m);
        assertFalse(!(v.getMesstypen().contains(m)));
    }
    
    /**
     * Testet getPatientenTermin
     */
    @Test
    public void testGetPatientenTermin() {
        Visite v = new Visite("Hallo", 0, null);
        PatientenTermin pt1 = new PatientenTermin(v, null, null);
        PatientenTermin pt2 = new PatientenTermin(v, null, null);
        v.addPatientenTermin(pt1);
        v.addPatientenTermin(pt2);
        assertTrue((v.getPatientenTermine().contains(pt1)));
        assertTrue((v.getPatientenTermine().contains(pt2)));
    }
    
    /**
     * Testet getProbenInfos
     */
    @Test
    public void testGetProbenInfos() {
        Visite v = new Visite("Hallo", 0, null);
        ProbenKategorie pk = new ProbenKategorie("", "", 5f, 6f);
        ProbenInfo pi = new ProbenInfo(5f, pk, null);       
        v.addProbenInfo(pi);
        assertFalse(!(v.getProbenInfos().contains(pi)));
    }
}








