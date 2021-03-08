package de.wwu.sopra.view;

import de.wwu.sopra.controller.RaumSteuerung;
import de.wwu.sopra.controller.data.LagerVerwaltung;
import de.wwu.sopra.model.Raum;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class RaumErstellenFormular extends View {

    private TabSteuerung tabSteuerung;

    private GridPane pane;

    private TextField nameField, flaecheField, hoeheField;
    boolean gueltigeNamenEingabe = false;
    boolean gueltigeFlaecheEingabe = false;
    boolean gueltigeHoeheEingabe = false;

    /**
     * Erstellt ein neues RaumErstellenFormular
     * @param tabSteuerung die Tabsteuerung
     */
    public RaumErstellenFormular(TabSteuerung tabSteuerung) {
        pane = new GridPane();
        pane.setHgap(32);
        pane.setVgap(6);

        pane.add(new Label("Name"), 0, 0);
        nameField = new TextField();
        pane.add(nameField, 1, 0);

        pane.add(new Label("Fläche in m^2"), 0, 1);
        flaecheField = new TextField();
        pane.add(flaecheField, 1, 1);

        pane.add(new Label("Höhe in m"), 0, 2);
        hoeheField = new TextField();
        pane.add(hoeheField, 1, 2);

        Text falscheFlaeche = new Text("Ungültige Eingabe.");
        falscheFlaeche.setFill(Color.CRIMSON);
        falscheFlaeche.setVisible(false);
        Text falscheHoehe = new Text("Ungültige Eingabe.");
        falscheHoehe.setFill(Color.CRIMSON);
        falscheHoehe.setVisible(false);
        Text falscherName = new Text();
        falscherName.setFill(Color.CRIMSON);
        falscherName.setVisible(false);
        pane.add(falscherName, 2, 0);
        pane.add(falscheFlaeche, 2, 1);
        pane.add(falscheHoehe, 2, 2);

        Button anlegenButton = new Button("Anlegen");
        anlegenButton.setTooltip(new Tooltip("Raum anlegen."));
        anlegenButton.setDisable(true);
        pane.add(anlegenButton, 0, 3);

        String decimalPattern = "(\\d+)(\\.\\d+)?";

        flaecheField.textProperty().addListener((observable, oldValue, newValue) -> {
            gueltigeFlaecheEingabe = newValue.matches(decimalPattern);
            falscheFlaeche.setVisible(!gueltigeFlaecheEingabe);
            anlegenButton.setDisable(!(gueltigeFlaecheEingabe && gueltigeHoeheEingabe && gueltigeNamenEingabe));
        });
        hoeheField.textProperty().addListener((observable, oldValue, newValue) -> {
            gueltigeHoeheEingabe = newValue.matches(decimalPattern);
            falscheHoehe.setVisible(!gueltigeHoeheEingabe);
            anlegenButton.setDisable(!(gueltigeFlaecheEingabe && gueltigeHoeheEingabe && gueltigeNamenEingabe));

        });
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                falscherName.setText("Name darf nicht leer sein.");
                falscherName.setVisible(true);
                gueltigeNamenEingabe = false;
            } else if (RaumSteuerung.getInstance().istRaumnameVerwendet(newValue, null)) {
                falscherName.setText("Name bereits vorhanden.");
                falscherName.setVisible(true);
                gueltigeNamenEingabe = false;
            } else {
                falscherName.setVisible(false);
                gueltigeNamenEingabe = true;
            }

            anlegenButton.setDisable(!(gueltigeFlaecheEingabe && gueltigeHoeheEingabe && gueltigeNamenEingabe));

        });
        anlegenButton.setOnAction(event -> {
            String neuenNamen = nameField.getText();
            float neueFlaecheFloat = Float.parseFloat(flaecheField.getText().trim());
            float neueHoeheFloat = Float.parseFloat(hoeheField.getText().trim());

            LagerVerwaltung.getInstance().addRaum(new Raum(neuenNamen, neueFlaecheFloat, neueHoeheFloat));
            tabSteuerung.pop();
        });
        Button abbrechenButton = new Button("Abbrechen");
        abbrechenButton.setTooltip(new Tooltip("Abbrechen"));
        abbrechenButton.setOnAction(event -> {
            tabSteuerung.pop();

        });
        pane.add(abbrechenButton, 1, 3);
    }

    /**
     * @return Die Hauptnode des Fensters.
     */
    @Override
    public Node getNode() {
        return pane;
    }

    /**
     * @return Der Name, der bei den Breadcrumbs angezeigt werden sollen.
     */
    @Override
    public String getName() {
        return "Raum erstellen";
    }
}
