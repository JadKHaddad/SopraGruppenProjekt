package de.wwu.sopra.view;

import javafx.scene.Node;

/**
 * Kann auf eine TabSteuerung gepushed werden, damit der View in dem Tab und bei den Breadcrumbs angezeigt wird.
 */
public abstract class View {

    /**
     * @return Die Hauptnode des Fensters.
     */
    public abstract Node getNode();

    /**
     * @return Der Name, der bei den Breadcrumbs angezeigt werden sollen.
     */
    public abstract String getName();

    /**
     * Wird aufgerufen, wenn ein View geöffnet wird.
     */
    public void onOpen() {
    }

    /**
     * Wird aufgerufen, wenn ein View geschlossen wird.
     */
    public void onClose() {
    }

}
