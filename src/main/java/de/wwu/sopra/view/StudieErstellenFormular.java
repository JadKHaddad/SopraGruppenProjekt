package de.wwu.sopra.view;

import de.wwu.sopra.controller.StudienSteuerung;
import de.wwu.sopra.controller.data.BenutzerVerwaltung;
import de.wwu.sopra.controller.data.StudienVerwaltung;
import de.wwu.sopra.model.Benutzer;
import de.wwu.sopra.model.Studie;
import de.wwu.sopra.model.Visite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Verwaltet die Ansicht auf ein StudienErstellenFormular
 * 
 * @author Gruppe 5
 */
public class StudieErstellenFormular extends View {

    private GridPane pane;
    private boolean gueltigeNamenEingabe = false;
    private boolean gueltigeAnzahlEingabe = false;
    private ArrayList<Visite> visiten;
    private ArrayList<Benutzer> nurses;
    Studie studie;
    private TableView<Visite> visiteTable;
    private TextField nameField, anzTeilnehmerField;

    /**
     * Erstellt ein Studien ErstellenFormular
     * 
     * @param tabSteuerung die Tabsteuerung
     * @param altestudie die zu bearbeitende Studie
     */
    public StudieErstellenFormular(TabSteuerung tabSteuerung, Studie altestudie) {
        this.studie = altestudie;
        if (studie != null) {
            visiten = new ArrayList<>(studie.getVisiten());
            nurses = new ArrayList<>(studie.getBenutzer());
        } else {
            visiten = new ArrayList<Visite>();
            nurses = new ArrayList<Benutzer>();
        }

        pane = new GridPane();
        pane.setHgap(32);
        pane.setVgap(6);

        pane.add(new Text("Name"), 0, 0);
        if (studie == null)
            nameField = new TextField();
        else
            nameField = new TextField(studie.getName());

        pane.add(nameField, 1, 0);

        pane.add(new Text("Anzahl Teilnehmer"), 0, 1);

        if (studie == null)
            anzTeilnehmerField = new TextField();
        else
            anzTeilnehmerField = new TextField("" + studie.getAnzTeilnehmer());

        pane.add(anzTeilnehmerField, 1, 1);
        // ungültige eingabe text

        Text falscheAnzahl = new Text("Ungültige Eingabe.");
        falscheAnzahl.setFill(Color.CRIMSON);
        falscheAnzahl.setVisible(false);
        pane.add(falscheAnzahl, 2, 1);

        Text falscherName = new Text("Name darf nicht leer sein.");
        falscherName.setFill(Color.CRIMSON);
        falscherName.setVisible(false);
        pane.add(falscherName, 2, 0);

        Button nurseButton = new Button("Zuständige Study-Nurses auswählen");
        nurseButton.setTooltip(new Tooltip("Zuständige Study-Nurses auswählen"));
        nurseButton.setOnAction(event -> {
            tabSteuerung.push(new NurseListeFormular(tabSteuerung, nurses, studie));
        });
        // button action

        pane.add(nurseButton, 0, 2);

        Button visiteButton = new Button("Neue Visite anlegen",
                new ImageView(new Image(getClass().getResourceAsStream("/add.png"))));
        visiteButton.setTooltip(new Tooltip("Neue Visite anlegen"));
        visiteButton.setOnAction(event -> {
            VisiteHinzuFormular visiteHinzuFormular = new VisiteHinzuFormular(tabSteuerung, visiten);
            tabSteuerung.push(visiteHinzuFormular);
        });

        // button action

        pane.add(visiteButton, 0, 3);

        visiteTable = new TableView<Visite>();
        pane.add(visiteTable, 0, 4);

        TableColumn<Visite, String> visiteNameCol = new TableColumn<Visite, String>("Visitenname");
        TableColumn<Visite, Button> removeCol = new TableColumn<Visite, Button>();
        removeCol.setSortable(false);

        visiteNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        removeCol.setCellFactory(ActionButtonTableCell
                .forTableColumn(new Image(getClass().getResourceAsStream("/minus.png")), "Löschen", (Visite v) -> {
                    visiten.remove(v);
                    visiteTable.refresh();
                    return v;
                }));

        // Display row data
        ObservableList<Visite> list = FXCollections.observableList(visiten);
        visiteTable.setItems(list);

        visiteTable.getColumns().add(visiteNameCol);
        visiteTable.getColumns().add(removeCol);

        Button anlegenButton;
        if (studie == null) {
            anlegenButton = new Button("Anlegen");
            anlegenButton.setTooltip(new Tooltip("Visite anlegen"));
        } else {
            anlegenButton = new Button("Speichern");
            anlegenButton.setTooltip(new Tooltip("Änderungen speichern"));
        }

        if (studie == null)
            anlegenButton.setDisable(true);
        else {
            gueltigeAnzahlEingabe = true;
            gueltigeNamenEingabe = true;
        }

        Button zurueck = new Button("Abbrechen");
        zurueck.setTooltip(new Tooltip("Erstellung Abbrechen"));
        zurueck.setOnAction(event -> {
            tabSteuerung.pop();
        });

        HBox hbox = new HBox();
        hbox.getChildren().add(anlegenButton);
        hbox.setSpacing(6);
        hbox.getChildren().add(zurueck);

        pane.add(hbox, 0, 5);

        // textLabelListener

        String decimalPattern = "([0-9]*)";

        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            gueltigeNamenEingabe = !newValue.isEmpty();
            falscherName.setVisible(!gueltigeNamenEingabe);
            anlegenButton.setDisable(!(gueltigeAnzahlEingabe && gueltigeNamenEingabe));

        });
        anzTeilnehmerField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                gueltigeAnzahlEingabe = false;
                falscheAnzahl.setVisible(true);
                anlegenButton.setDisable(!(gueltigeNamenEingabe && gueltigeAnzahlEingabe));
            } else if (newValue.matches(decimalPattern)) {
                gueltigeAnzahlEingabe = true;
                falscheAnzahl.setVisible(false);
                anlegenButton.setDisable(!(gueltigeNamenEingabe && gueltigeAnzahlEingabe));
            } else {
                gueltigeAnzahlEingabe = false;
                falscheAnzahl.setVisible(true);
                anlegenButton.setDisable(!(gueltigeNamenEingabe && gueltigeAnzahlEingabe));
            }

        });

        // button action
        anlegenButton.setOnAction(event -> {
            if (studie != null) {
                StudienSteuerung.getInstance().entferneStudie(studie);
            }

            StudienSteuerung.getInstance().erstelleStudie(nameField.getText(), Integer.parseInt(anzTeilnehmerField.getText()), visiten, nurses);

            tabSteuerung.pop();
        });

    }

    /**
     * Gitb die Hauptnode des Fensters zurueck
     */
    @Override
    public Node getNode() {
        return pane;
    }

    /**
     * Gibt den Namen wieder, der bei den Vreadcrumbs angezeigt wird
     */
    @Override
    public String getName() {
        return "Studie anlegen";
    }

    /**
     * Verwaltet was passieren soll, wenn dieses Fenster geöffnet wird
     */
    @Override
    public void onOpen() {
        visiteTable.refresh();
    }

}
