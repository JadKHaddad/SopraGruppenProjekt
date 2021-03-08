package de.wwu.sopra.view;

import de.wwu.sopra.controller.data.BenutzerVerwaltung;
import de.wwu.sopra.model.Benutzer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.LinkedList;
import java.util.Set;
/**
 * Verwaltet die Ansicht des PersonalverwaltungsFormulars
 * @author Gruppe 5
 */
public class PersonalverwaltungFormular extends View {

    private GridPane gridPane;

    private TableView<Benutzer> benutzerTable;

    /**
     * Erzeugt das PersonalverwaltungsFormulars.
     * @param tabSteuerung die Steuerung
     */
    public PersonalverwaltungFormular(TabSteuerung tabSteuerung) {
        gridPane = new GridPane();
        Button button = new Button("Neuen Benutzer hinzufügen", new ImageView(new Image(getClass().getResourceAsStream("/add.png"))));
        button.setTooltip(new Tooltip("Neuen Benutzer hinzufügen"));
        
        button.setOnAction(event -> {
            BenutzerErstellenFormular benutzerErstellenFormular = new BenutzerErstellenFormular(tabSteuerung);
            tabSteuerung.push(benutzerErstellenFormular);
        });
        gridPane.add(button, 0, 0);
        gridPane.setVgap(8);
        benutzerTable = new TableView<Benutzer>();
        gridPane.add(benutzerTable, 0, 1);


        TableColumn<Benutzer, String> userLastNameCol = new TableColumn<Benutzer, String>("Name");
        TableColumn<Benutzer, Float> userSirNameCol = new TableColumn<Benutzer, Float>("Vorname");
        TableColumn<Benutzer, Float> userNameCol = new TableColumn<Benutzer, Float>("Benutzername");

        TableColumn<Benutzer, Button> removeCol = new TableColumn<Benutzer, Button>();
        removeCol.setSortable(false);
        TableColumn<Benutzer, Button> editCol = new TableColumn<Benutzer, Button>();
        editCol.setSortable(false);

        // Defines how to fill data for each cell.
        // Get value from property of UserAccount.
        userLastNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        userSirNameCol.setCellValueFactory(new PropertyValueFactory<>("vorname"));
        userNameCol.setCellValueFactory(new PropertyValueFactory<>("benutzerName"));

        removeCol.setCellFactory(ActionButtonTableCell.forTableColumn(
            new Image(getClass().getResourceAsStream("/remove.png")), "Löschen", (Benutzer b) -> {
                BenutzerVerwaltung.getInstance().removeBenutzer(b);
                Set<Benutzer> benutzerSet = BenutzerVerwaltung.getInstance().getBenutzerSet();

                ObservableList<Benutzer> list = FXCollections.observableList(new LinkedList<>(benutzerSet));
                benutzerTable.setItems(list);
                return b;
            }));
        editCol.setCellFactory(ActionButtonTableCell.forTableColumn(
            new Image(getClass().getResourceAsStream("/edit.png")), "Bearbeiten", (Benutzer b) -> {
                BenutzerBearbeitenFormular benutzerBearbeitenFormular = new BenutzerBearbeitenFormular(tabSteuerung, b);
                tabSteuerung.push(benutzerBearbeitenFormular);
                benutzerTable.refresh();
                return b;
            }));

        Set<Benutzer> benutzerSet = BenutzerVerwaltung.getInstance().getBenutzerSet();

        ObservableList<Benutzer> list = FXCollections.observableList(new LinkedList<>(benutzerSet));
        benutzerTable.setItems(list);



        benutzerTable.getColumns().add(userLastNameCol);
        benutzerTable.getColumns().add(userSirNameCol);
        benutzerTable.getColumns().add(userNameCol);

        benutzerTable.getColumns().add(editCol);
        benutzerTable.getColumns().add(removeCol);
    }

    /**
     * Gibt die Hauptnode des Fensters zurueck
     */
    public Node getNode() {
        return gridPane;
    }

    /**
     * Gibt den Namen an, der in den Breadcrumbs angezeigt wird
     */
    @Override
    public String getName() {
        return "Benutzer";
    }
    
    /**
     * Verwaltet, was passieren soll wen dieses Formular geöffnet wird
     */
    @Override
    public void onOpen() {
        Set<Benutzer> benutzerSet = BenutzerVerwaltung.getInstance().getBenutzerSet();

        ObservableList<Benutzer> list = FXCollections.observableList(new LinkedList<>(benutzerSet));
        benutzerTable.setItems(list);
        benutzerTable.refresh();
    }
}
