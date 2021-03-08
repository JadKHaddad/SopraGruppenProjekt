/**
 *
 */
package de.wwu.sopra.model;

/**
 * Porbenkategorie wird definiert
 * @author Gruppe 5
 *
 */
public class ProbenKategorie {

    private String name;
    private String einheit;
    private float maxLagerTemp;
    private float minLagerTemp;


    /**
     * Erstellt eine neue Probenkategorie.
     * @param name Name der Kategorie
     * @param einheit Einheit der Kategorie
     * @param maxLagerTemp maximal Temperatur bei der gelagert werden darf.
     * @param minLagerTemp minimal Temperatur bei der gelagert werden darf.
     */
    public ProbenKategorie(String name, String einheit, float maxLagerTemp, float minLagerTemp) {

        this.name = name;
        this.einheit = einheit;
        this.maxLagerTemp = maxLagerTemp;
        this.minLagerTemp = minLagerTemp;

    }

    /**
     * Gibt Namen der Probenkategorie zurueck.
     * @return name Name der ProbenKategorie
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen einer Probenkategorie.
     * @param name Name der ProbenKategorie
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gibt die Einheit der ProbenKategorie zurueck.
     * @return einheit Einheit der ProbenKategorie
     */
    public String getEinheit() {
        return einheit;
    }

    /**
     * Setzt die Einheit einer Probenkategorie
     * @param einheit Einheit der ProbenKategorie
     */
    public void setEinheit(String einheit) {
        this.einheit = einheit;
    }

    /**
     * Gibt maximale Lagertemperatur zurueck.
     * @return maxLagerTemp Die maximale Lagertemperatur unter der Proben dieser Kategorie gelagert werden duerfen.
     */
    public float getMaxLagerTemp() {
        return maxLagerTemp;
    }

    /**
     * Setzt die maximale Lagertemperatur einer Probenkategorie.
     * @param maxLagerTemp Die maximale Lagertemperatur unter der Proben dieser Kategorie gelagert werden duerfen.
     */
    public void setMaxLagerTemp(float maxLagerTemp) {
        this.maxLagerTemp = maxLagerTemp;
    }

    /**
     * Gibt minimale Lagertemperatur zurueck.
     * @return minLagerTemp Die minimale Temperatur unter der Proben dieser Kategorie gelagert werden duerfen.
     */
    public float getMinLagerTemp() {
        return minLagerTemp;
    }

    /**
     * Setzt die minimale Lagertemperatur.
     * @param minLagerTemp Die minimale Temperatur unter der Proben dieser Kategorie gelagert werden duerfen.
     */
    public void setMinLagerTemp(float minLagerTemp) {
        this.minLagerTemp = minLagerTemp;
    }
    
    /**
     * Gibt einen String aus GUI technischen Gruenden wieder
     */
    @Override
    public String toString() {
        return name;
    }


}
