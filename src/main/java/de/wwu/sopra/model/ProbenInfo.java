/**
 *
 */
package de.wwu.sopra.model;

/**
 * Probeninfo wird definiert
 * @author Gruppe 5
 *
 */
public class ProbenInfo {
    private float menge;
    private ProbenKategorie kategorie;
    private Behaeltertyp behaelterTyp;

    /**
     * Erstellt eine neue Probenkategorie.
     * @param d Menge der ProbenKategorie
     * @param kategorie Eine Probenkategorie
     * @param behaelterTyp der Behaeltertyp
     */
    public ProbenInfo(float d, ProbenKategorie kategorie, Behaeltertyp behaelterTyp) {
        this.behaelterTyp = behaelterTyp;
        this.menge = d;
        this.kategorie = kategorie;
    }
    
    /**
     * gitb den Behaeltertyp der Probeninfo zurueck
     * @return den Behaeltertyp
     */
    public Behaeltertyp getBehaeltertyp() {
        return behaelterTyp;
    }
    
    /**
     * weist der Probeninfo einen neues Behaeltertyp zu
     * @param behaelterTyp der neue Behaeltertyp
     */
    public void setBehaeltertyp(Behaeltertyp behaelterTyp) {
        this.behaelterTyp = behaelterTyp;

    }
    
    /**
     * Gibt die Menge zurueck.
     * @return menge Menge der Probenkategorie
     */
    public float getMenge() {
        return menge;
    }

    /**
     * Setzt die Menge auf den uebergebenen Wert.
     * @param menge Menge der Probenkategorie
     */
    public void setMenge(float menge) {
        this.menge = menge;
    }

    /**
     * Gibt die Kategorie zurueck.
     * @return kategorie Eine Probenkategorie
     */
    public ProbenKategorie getKategorie() {
        return kategorie;
    }

    /**
     * Setzt die Kategorie.
     * @param kategorie Eine Probenkategorie
     */
    public void setKategorie(ProbenKategorie kategorie) {
        this.kategorie = kategorie;
    }


}
