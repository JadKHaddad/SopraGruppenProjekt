package de.wwu.sopra.view;

import java.util.ArrayList;

import de.wwu.sopra.controller.ProbenSteuerung;
import de.wwu.sopra.controller.RackSteuerung;
import de.wwu.sopra.controller.data.BehaelterVerwaltung;
import de.wwu.sopra.controller.data.BidVerwaltung;
import de.wwu.sopra.controller.data.ProbenKategorieVerwaltung;
import de.wwu.sopra.controller.data.ProbenVerwaltung;
import de.wwu.sopra.model.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class ProbeWeiterverarbeitenFormular extends View {

    GridPane gridPane;
    Probe probe;
    boolean gueltigeMengenEingabe, kategorieAusgewaehlt, behaelterAusgewaelt;

    public ProbeWeiterverarbeitenFormular(TabSteuerung tabSteuerung, Probe probe) {
        this.probe = probe;
        GridPane pane = new GridPane();
        gridPane = new GridPane();
        gridPane.setHgap(32);
        gridPane.setVgap(6);
        pane.setHgap(32);
        pane.setVgap(6);
        gridPane.add(pane, 0, 1);
        Text ueberschrift = new Text("Neue Probe aus der alten Probe erstellen:");
        ueberschrift.setStyle("-fx-font-weight: bold");
        ueberschrift.setStyle("-fx-font-size: 18px;");

        gridPane.add(ueberschrift, 0, 0);

        ObservableList<ProbenKategorie> kategorie = FXCollections
                .observableArrayList(ProbenKategorieVerwaltung.getInstance().getProbenKategorieSet());
        final ComboBox<ProbenKategorie> comboBox = new ComboBox<ProbenKategorie>(kategorie);

        comboBox.setPromptText("Probenkategorie");

        ObservableList<Behaeltertyp> behaelter = FXCollections.observableArrayList(
                new ArrayList<Behaeltertyp>(BehaelterVerwaltung.getInstance().getBehaeltertypSet()));

        final ComboBox<Behaeltertyp> comboBoxBehaelter = new ComboBox<Behaeltertyp>(behaelter);
        comboBoxBehaelter.setPromptText("Behälteryp");

        TextField mengeField = new TextField();
        mengeField.setPromptText("Menge");

        Button button = new Button("Neue Probe erzeugen");
        button.setDisable(true);
        String decimalPattern = "(\\d+)(\\.\\d+)?";
        String intPattern = "(\\d+)";

        mengeField.textProperty().addListener((observable, oldValue, newValue) -> {
            gueltigeMengenEingabe = newValue.matches(decimalPattern);
            button.setDisable(!(gueltigeMengenEingabe && kategorieAusgewaehlt && behaelterAusgewaelt));
        });

        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            kategorieAusgewaehlt = true;
            button.setDisable(!(gueltigeMengenEingabe && kategorieAusgewaehlt && behaelterAusgewaelt));
        });

        comboBoxBehaelter.valueProperty().addListener((observable, oldValue, newValue) -> {
            behaelterAusgewaelt = true;
            button.setDisable(!(gueltigeMengenEingabe && kategorieAusgewaehlt && behaelterAusgewaelt));
        });

        pane.add(new Text("Kategorie: "), 0, 0);
        pane.add(new Text("Menge: "), 0, 1);
        pane.add(new Text("Behältertyp: "), 0, 2);

        pane.add(comboBox, 1, 0);
        pane.add(mengeField, 1, 1);
        pane.add(comboBoxBehaelter, 1, 2);

        pane.add(button, 0, 3);

        Button abbrechen = new Button("Abbrechen");
        pane.add(abbrechen, 1, 3);

        abbrechen.setOnAction(event -> {
            tabSteuerung.pop();
        });

        button.setOnAction(event -> {
            Probe neueProbe = new Probe(BidVerwaltung.getInstance().getBid(), probe.getEntnahmeDatum(),
                    new ProbenInfo(Float.parseFloat(mengeField.getText()),
                            comboBox.getSelectionModel().getSelectedItem(),
                            comboBoxBehaelter.getSelectionModel().getSelectedItem()),
                    ProbenStatus.EINGELAGERT, null, probe.getTermin(),
                    comboBoxBehaelter.getSelectionModel().getSelectedItem());

            ProbenPlatz probenPlatz = ProbenSteuerung.getInstance().fuegeNeueVerarbeiteteProbeEin(neueProbe, probe);
            if (probenPlatz != null) {
                new InfoPopup("Erfolgreich",
                    "Die neue Probe wurde erstellt." + "\n\n" + "Lagern Sie diese bitte in Rack: " + probenPlatz.getRack().getBid() + "\n Am Platz: " + RackSteuerung.getInstance().getNameForProbenPlatz(probenPlatz) +" ein.",
                    () -> {});
                tabSteuerung.pop();
            } else {
                new InfoPopup("Fehler", "Ein Platz für die neue Probe konnte nicht gefunden werden." + "\n\n"
                    + "Entweder gibt es nicht ausreichend Probenplätze oder keine freien Plätze in Kühlschränken mit passender Temperatur.",
                    () -> {});
            }
        });
    }

    @Override
    public Node getNode() {
        return gridPane;
    }

    @Override
    public String getName() {
        return "Weiterverarbeitung von Probe: " + probe.getBid();
    }

}
