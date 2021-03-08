package de.wwu.sopra.controller.data;
/**
 * Gibt Id aus (fuer Racks und Proben) die alle unterschiedlich sind
 * @author Gruppe 5
 *
 */
public class BidVerwaltung {
    private static BidVerwaltung instance;
    
    private int counter = 0;
    
    private BidVerwaltung() {
        
        
    }
    /**
     *BidVerwaltung als Singleton um sicherzustellen, dass nur eine
     * BidVerwaltung mit korrektem, synchronisiertem Inhalt existiert
     * @return die BidVerwaltung instance
     */
    public static synchronized BidVerwaltung getInstance() {
        if(instance == null)
            instance = new BidVerwaltung();
        return instance;
    }
    
    /**
     * gibt eine neue Bid zurueck 
     * @return die neue Bid
     */
    public int getBid() {
        
        return ++counter;
    }
    
    /**
     * setzt den Counter 
     * @param counter der zu setztende Counter
     */
    public void setCounter(int counter) {
        this.counter = counter;
    }
    
    /**
     * gibt den Counter zuruck
     * @return den Counter
     */
    public int getCounter() {
        return counter;
    }
}
