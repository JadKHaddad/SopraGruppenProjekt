/**
 *
 */
package de.wwu.sopra.view;

import de.wwu.sopra.controller.BenutzerSteuerung;
import de.wwu.sopra.controller.data.BenutzerVerwaltung;
import de.wwu.sopra.model.Benutzer;
import de.wwu.sopra.model.Rolle;
import de.wwu.sopra.model.Studie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 * Verwaltet die Ansicht des NurseListeFormulars
 * 
 * @author Gruppe 5
 */
public class NurseListeFormular extends View {

    private TabSteuerung tabSteuerung;
    private GridPane pane;

    /**
     * Erstellt ein NurseListeFormular
     * 
     * @param tabSteuerung die Tabsteuerung
     * @param nurses       die Nurses
     * @param studie  die Studie zu der die Nurses gerhoeren
     */
    public NurseListeFormular(TabSteuerung tabSteuerung, ArrayList<Benutzer> nurses, Studie studie) {
        BenutzerVerwaltung benutzerVerwaltung = BenutzerVerwaltung.getInstance();

        LinkedList<Benutzer> verfuegbareNurses = new LinkedList<>(benutzerVerwaltung.getBenutzerSet());

        Iterator<Benutzer> it = verfuegbareNurses.iterator();
        while(it.hasNext()) {
            Benutzer be = it.next();
            if (!be.hasRolle(Rolle.STUDY_NURSE))
                it.remove();
        }

        for (Benutzer be : nurses)
            verfuegbareNurses.remove(be);

        pane = new GridPane();
        pane.setHgap(32);
        pane.setVgap(6);

        Text text1 = new Text("Verfügbare Nurses:");
        Text text2 = new Text("Ausgewählte Nurses:");

        pane.add(text1, 0, 0);
        pane.add(text2, 1, 0);

        TableView<Benutzer> benutzerTable = new TableView<Benutzer>();
        TableView<Benutzer> benutzerTable1 = new TableView<Benutzer>();

        pane.add(benutzerTable1, 1, 1);

        // Erstellt eine Tabelle
        TableColumn<Benutzer, String> benutzerNameCol1 = new TableColumn<Benutzer, String>("Name");
        TableColumn<Benutzer, Button> addCol = new TableColumn<Benutzer, Button>();
        addCol.setSortable(false);
        TableColumn<Benutzer, String> benutzerNameCol2 = new TableColumn<Benutzer, String>("Vorname");

        pane.add(benutzerTable, 0, 1);
        // Erstellt eine Tabelle
        TableColumn<Benutzer, String> benutzerNameCol = new TableColumn<Benutzer, String>("Name");
        TableColumn<Benutzer, String> benutzerNameCol3 = new TableColumn<Benutzer, String>("Vorname");
        TableColumn<Benutzer, Button> removeCol = new TableColumn<Benutzer, Button>();
        removeCol.setSortable(false);
        benutzerNameCol3.setCellValueFactory(new PropertyValueFactory<>("vorname"));

        benutzerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        removeCol.setCellFactory(ActionButtonTableCell
                .forTableColumn(new Image(getClass().getResourceAsStream("/minus.png")), "Löschen", (Benutzer be) -> {
                    nurses.remove(be);
                    verfuegbareNurses.add(be);
                    benutzerTable.refresh();
                    benutzerTable1.refresh();
                    return be;
                }));

        benutzerNameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        benutzerNameCol2.setCellValueFactory(new PropertyValueFactory<>("vorname"));
        addCol.setCellFactory(ActionButtonTableCell
                .forTableColumn(new Image(getClass().getResourceAsStream("/add.png")), "Hinzufügen", (Benutzer be) -> {
                    nurses.add(be);
                    verfuegbareNurses.remove(be);
                    benutzerTable.refresh();
                    benutzerTable1.refresh();
                    return be;
                }));

        ObservableList<Benutzer> list = FXCollections.observableList(verfuegbareNurses);
        benutzerTable.setItems(list);

        benutzerTable.getColumns().add(benutzerNameCol1);
        benutzerTable.getColumns().add(benutzerNameCol2);
        benutzerTable.getColumns().add(addCol);
        // Testet die Anzeige für die Tabelle 2

        ObservableList<Benutzer> list1 = FXCollections.observableList(nurses);
        benutzerTable1.setItems(list1);

        benutzerTable1.getColumns().add(benutzerNameCol);
        benutzerTable1.getColumns().add(benutzerNameCol3);
        benutzerTable1.getColumns().add(removeCol);

        // Änderungen anwenden und zum vorherigen Fenster zurückkehren
        Button button = new Button("Anwenden");
        button.setTooltip(new Tooltip("Gewählte Nurses zur Studie hinzufügen"));

        button.setOnAction(event -> {
            tabSteuerung.pop();
        });

        pane.add(button, 1, 2);

    }

    /**
     * Gibt die Hauptnode des Fensters zurueck
     */
    @Override
    public Node getNode() {

        return pane;
    }

    /**
     * GIbt den Namen wieder, der in den Breadcrumbs angezeigt werden soll
     */
    @Override
    public String getName() {

        return "Study Nurse auswählen";
    }

}
