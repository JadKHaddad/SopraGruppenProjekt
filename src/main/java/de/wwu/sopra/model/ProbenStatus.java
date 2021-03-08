/**
 *
 */
package de.wwu.sopra.model;

/**
 * Enums, die die unterschiedlichen Status von Proben wiedergeben.
 * 
 * @author Gruppe 5
 */
public enum ProbenStatus {
    EINGELAGERT("Eingelagert"), VERSCHIFFT("Verschifft"), VERBRAUCHT("Verbraucht"), VERLOREN("Verloren"),
    KURZFRISTIG_ENTNOMMEN("Kurzfristig entnommen"), DAUERHAFT_ENTNOMMEN("Dauerhaft entnommen"), NEU("Neu"),
    SOLL_VERNICHTET_WERDEN("Soll vernichtet werden");

    private String name;

    ProbenStatus(String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }


}
