package de.wwu.sopra.view;

import de.wwu.sopra.controller.BenutzerSteuerung;
import de.wwu.sopra.controller.StudienSteuerung;
import de.wwu.sopra.controller.data.BenutzerVerwaltung;
import de.wwu.sopra.controller.data.StudienVerwaltung;
import de.wwu.sopra.model.Benutzer;
import de.wwu.sopra.model.Patient;
import de.wwu.sopra.model.Rolle;
import de.wwu.sopra.model.Studie;
import de.wwu.sopra.model.Studienteilnehmer;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Verwaltet die Ansicht des StudienFormulars
 *
 * @author Gruppe 5
 */
public class StudienFormular extends View {

    private GridPane gridPane;
    private TableView<Studie> studieTable;
    private ArrayList<Studie> studien;
    private StudienVerwaltung sv = StudienVerwaltung.getInstance();
    private boolean isLeiter = BenutzerSteuerung.getInstance().getAktiverBenutzer().hasRolle(Rolle.BIOBANKLEITER);

    /**
     * Erstellt ein StudienFormular
     *
     * @param tabSteuerung die tabsteuerung
     */
    public StudienFormular(TabSteuerung tabSteuerung) {
        gridPane = new GridPane();

        Button button = new Button("Neue Studie anlegen",
                new ImageView(new Image(getClass().getResourceAsStream("/add.png"))));
        button.setTooltip(new Tooltip("Neuen Studie anlegen"));

        button.setOnAction(event -> {
            // Studie anlegen
            StudieErstellenFormular studieErstellenFormular = new StudieErstellenFormular(tabSteuerung, null);
            tabSteuerung.push(studieErstellenFormular);
        });

        // Studie bearbeiten
        if (isLeiter)
            gridPane.add(button, 0, 0);
        gridPane.setVgap(8);
        studieTable = new TableView<Studie>();
        gridPane.add(studieTable, 0, 1);

        TableColumn<Studie, String> studienNameCol = new TableColumn<Studie, String>("Studienname");
        TableColumn<Studie, Button> removeCol = new TableColumn<Studie, Button>();
        removeCol.setSortable(false);
        TableColumn<Studie, Button> editCol = new TableColumn<Studie, Button>();
        editCol.setSortable(false);
        TableColumn<Studie, Button> startCol = new TableColumn<Studie, Button>();
        startCol.setSortable(false);

        studienNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        removeCol.setCellFactory(ActionButtonTableCell
                .forTableColumn(new Image(getClass().getResourceAsStream("/remove.png")), "Löschen", (Studie s) -> {
                    StudienSteuerung.getInstance().entferneStudie(s);
                    refresh();
                    return s;
                }));



        startCol.setCellFactory(ActionButtonTableCell
                .forTableColumn(new Image(getClass().getResourceAsStream("/start.png")), "Start", (Studie studie) -> {
                    if(studie.getVisiten().size() == 0 ) {
                        new InfoPopup("Starten nicht möglich", "Das Starten einer Studie ohne Visiten ist nicht möglich." + "\n\n" + "Bitte fügen Sie der Studie zunächst mindestens eine Visite hinzu.", () -> {});
                        return studie;
                    }
                    if(studie.getBenutzer().size() == 0) {
                        new InfoPopup("Starten nicht möglich", "Das Starten einer Studie ohne ohne zugewiesene Nurses ist nicht möglich." + "\n\n" + "Bitte weisen Sie der Studie zunächst mindestens eine Nurse zu.", () -> {});
                        return studie;
                    }
                    if (!StudienSteuerung.getInstance().starteStudie(studie)) {
                        new InfoPopup("Zu wenig Probenplätze",
                            "Es konnten nicht genug Probenplätze für die Studie reserviert werden oder\n es ist kein Kühlschrank mit passender Temperatur vorhanden.", () -> {});
                    }
                    studieTable.refresh();
                    return studie;
                }, (studie, bt) -> { bt.setDisable(!studie.isBearbeitbar()); }
                ));

        editCol.setCellFactory(ActionButtonTableCell
                .forTableColumn(new Image(getClass().getResourceAsStream("/edit.png")), "Bearbeiten", (Studie studie) -> {
                    tabSteuerung.push(new StudieErstellenFormular(tabSteuerung, studie));
                    return studie;
                }, (studie, editButton) -> {
                    editButton.setDisable(!studie.isBearbeitbar());
                }));

        // Display row data

        refresh();

        studieTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    if (!studieTable.getSelectionModel().getSelectedItem().isBearbeitbar())
                        tabSteuerung.push(new EinzelStudienFormular(tabSteuerung,
                                studieTable.getSelectionModel().getSelectedItem()));
                    else
                        new InfoPopup("Noch nicht gestartet", "Diese Studie ist noch nicht gestartet." + "\n\n" + "Die Studie kann nur von einem berechtigten Benutzer gestartet werden (einem Biobankleiter)", () -> {});
                }
            }
        });

        studieTable.getColumns().add(studienNameCol);
        if (isLeiter) {
            studieTable.getColumns().add(editCol);
            studieTable.getColumns().add(removeCol);
            studieTable.getColumns().add(startCol);
        }
    }

    /**
     * Gibt die Hauptnode des Fensters zurueck
     */
    @Override
    public Node getNode() {
        return gridPane;
    }

    /**
     * Gibt den Namen wieder, der in den Breadcrumbs angezeig wird
     */
    @Override
    public String getName() {
        return "Studien";
    }

    /**
     * Verwaltet was passieren soll wenn dieses Fenster geöffnet werden soll
     */
    @Override
    public void onOpen() {
        refresh();
    }

    public void refresh() {
        if (isLeiter)
            studien = new ArrayList<Studie>(sv.getStudienSet());
        else
            studien = BenutzerSteuerung.getInstance().getAktiverBenutzer().getStudien();

        ObservableList<Studie> list = FXCollections.observableList(studien);
        studieTable.setItems(list);
        studieTable.refresh();
    }

}
