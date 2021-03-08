package de.wwu.sopra.controller.data;

import de.wwu.sopra.model.Behaeltertyp;
import de.wwu.sopra.model.ProbenKategorie;

import java.util.HashSet;
import java.util.Set;

/**
 * Verwaltungsklasse der Probekategorien
 * @author Gruppe 5
 */

public class ProbenKategorieVerwaltung {

    private static ProbenKategorieVerwaltung instance;
    private Set<ProbenKategorie> probenKategorieSet = new HashSet<ProbenKategorie>();

    private ProbenKategorieVerwaltung() {

    }

    /**
     * ProbeKatogerieVerwaltung als Singleton um sicherzustellen, dass nur eine
     * ProbeKategorieVerwaltung mit korrektem, synchronisiertem Inhalt existiert
     * @return die ProbenKategorieVerwaltung instance
     */
    public static synchronized ProbenKategorieVerwaltung getInstance() {
        if (ProbenKategorieVerwaltung.instance == null) {
            ProbenKategorieVerwaltung.instance = new ProbenKategorieVerwaltung();
        }
        return ProbenKategorieVerwaltung.instance;
    }

    /**
     * Fuegt eine Probenkategorie dem Set hinzu
     * @param probenKategorie die hinzuzufuegende Probenkategorie
     */
    public void addProbenKategorie(ProbenKategorie probenKategorie) {
        if (probenKategorie != null) {
            probenKategorieSet.add(probenKategorie);
        }
    }

    /**
     * Entfernt eine Probenkategorie aus dem Set
     * @param probenKategorie die zu entfernende Probekategorie
     */
    public void removeProbenKategorie(ProbenKategorie probenKategorie) {
        if (probenKategorie != null) {
            probenKategorieSet.remove(probenKategorie);
        }
    }

    /**
     * Checkt, ob eine Probenkategorie im Set enthalten ist
     * @param probenKategorie die Kategorie, die gecheckt werden soll
     * @return true falls die Probenkategorie enthalten ist, ansonsten false
     */
    public boolean hasProbenKategorie(ProbenKategorie probenKategorie) {
        return probenKategorieSet.contains(probenKategorie);
    }

    /**
     * gibt das derzeitige Probenkategorie Set zurueck
     * @return das derzeitige ProbenKategorieSet
     */
    public Set<ProbenKategorie> getProbenKategorieSet() {
        return probenKategorieSet;
    }

    /**
     * Setzt das ProbenKategorie Set neu
     * @param probenKategorien das neue Probenkategorie Set
     */
    public void setProbenKategorieSet(Set<ProbenKategorie> probenKategorien) {
        probenKategorieSet = probenKategorien;
    }
    
    /**
     * Prueft, ob es schon eine Probenkategorie mit einem bestimmten Namen gibt
     * @param name Name fuer den geprueft werden soll, ob er schon vergeben ist
     * @return true, wenn der Name noch nicht vergeben ist, sonst false
     */
    public boolean nameIsUnique(String name) {
        boolean unique = true;
        for(ProbenKategorie b: probenKategorieSet) {
            if(b.getName().equals(name)) {
                unique=false;
            }
        }
        return unique;
    }

}
