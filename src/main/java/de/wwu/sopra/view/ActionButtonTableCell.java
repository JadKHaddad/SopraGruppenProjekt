package de.wwu.sopra.view;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
/**
 * Regelt die Darstellung von Buttons in einer Tabelle
 * @author Gruppe 5
 */
public class ActionButtonTableCell<S> extends TableCell<S, Button> {

    private final Button actionButton;
    private BiConsumer<S, Button> buttonMethode;
    /**
     * Erstellt eine neue ActionButtonTableCell-Steuerungklasse
     * @param image das Bild des Buttons
     * @param tooltip der Tooltip des Buttons
     * @param function die Funktion des Buttons
     * @param buttonMethode Funktion die Button und generisches Item nimmt und den Button bearbeiten kann
     */
    public ActionButtonTableCell(Image image, String tooltip, Function<S, S> function, BiConsumer<S, Button> buttonMethode) {
        this.getStyleClass().add("action-button-table-cell");
        this.actionButton = new Button("", new ImageView(image));
        this.actionButton.setTooltip(new Tooltip(tooltip));
        this.actionButton.setOnAction((ActionEvent e) -> {
            function.apply(getCurrentItem());
        });
        this.actionButton.setMaxWidth(Double.MAX_VALUE);
        this.buttonMethode = buttonMethode;
    }
    
    /**
     * Erstellt eine neue ActionButtonTableCell-Steuerungklasse
     * @param image das Bild des Buttons
     * @param tooltip der Tooltip des Buttons
     * @param function die Funktion des Buttons
     */
    public ActionButtonTableCell(Image image, String tooltip, Function<S, S> function) {
        this(image, tooltip, function, null);
    }

    /**
     * Gibt das Current Item wieder
     * @return das Current Item
     */
    public S getCurrentItem() {
        return (S) getTableView().getItems().get(getIndex());
    }
    /**
     * Leget einen Neuen ActionbuttontableCell
     * @param <S> der Gernerische Parameter
     * @param image das Bild
     * @param tooltip der Tooltip
     * @param function die Funktion
     * @param buttonMethode Funktion die Button und generisches Item nimmt und den Button bearbeiten kann
     * @return einen ActionbuttonTableCell
     */
    public static <S> Callback<TableColumn<S, Button>, TableCell<S, Button>> forTableColumn(Image image, String tooltip,
            Function<S, S> function, BiConsumer<S, Button> buttonMethode) {
        return param -> new ActionButtonTableCell<S>(image, tooltip, function, buttonMethode);
    }
    
    /**
     * Leget einen Neuen ActionbuttontableCell
     * @param <S> der Gernerische Parameter
     * @param image das Bild
     * @param tooltip der Tooltip
     * @param function die Funktion
     * @return einen ActionbuttonTableCell
     */
    public static <S> Callback<TableColumn<S, Button>, TableCell<S, Button>> forTableColumn(Image image, String tooltip,
            Function<S, S> function) {
        return param -> new ActionButtonTableCell<S>(image, tooltip, function, null);
    }
    
    /**
     * Updatet das Item
     */
    @Override
    public void updateItem(Button item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(actionButton);
            if (this.buttonMethode != null) {
                if (actionButton != null && getCurrentItem() != null) {
                    this.buttonMethode.accept(getCurrentItem(), actionButton);
                }

            }
        }
    }
}