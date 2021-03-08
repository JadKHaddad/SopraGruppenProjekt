package de.wwu.sopra.view;

import de.wwu.sopra.controller.RaumSteuerung;
import de.wwu.sopra.controller.data.LagerVerwaltung;
import de.wwu.sopra.model.Raum;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Verwaltet die Ansicht des RaumBearbeitenFormulars
 * 
 * @author Gruppe 5
 */
public class RaumBearbeitenFormular extends View {

    private GridPane grid;
    private Raum eigenerRaum;

    boolean gueltigeNamenEingabe = true;
    boolean gueltigeFlaecheEingabe = true;
    boolean gueltigeHoeheEingabe = true;

    /**
     * Erstellt ein RaumBearbeitenFormular
     * 
     * @param tabSteuerung die Tabsteuerung
     * @param raum         der Raum
     */
    public RaumBearbeitenFormular(TabSteuerung tabSteuerung, Raum raum) {
        eigenerRaum = raum;
        grid = new GridPane();

        grid.setVgap(8);
        grid.setHgap(8);

        Text nameText = new Text("Name ");
        Text flaecheText = new Text("Fläche in m^2");
        Text hoeheText = new Text("Höhe in m");

        TextField nameTextField = new TextField(raum.getName());
        TextField flaecheTextField = new TextField("" + raum.getQuadratmeter());
        TextField hoeheTextField = new TextField("" + raum.getHoehe());
        Text falscheFlaeche = new Text("ungültige Eingabe");
        falscheFlaeche.setFill(Color.RED);
        falscheFlaeche.setVisible(false);
        Text falscheHoehe = new Text("ungültige Eingabe");
        falscheHoehe.setFill(Color.RED);
        falscheHoehe.setVisible(false);
        Text falscherName = new Text("Name darf nicht leer sein");
        falscherName.setFill(Color.RED);
        falscherName.setVisible(false);

        Button speichernButton = new Button("Speichern");
        Button abbrechenButton = new Button("Abbrechen");
        speichernButton.setDisable(false);

        String decimalPattern = "(\\d+)(\\.\\d+)?";

        flaecheTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            gueltigeFlaecheEingabe = newValue.matches(decimalPattern);
            falscheFlaeche.setVisible(!gueltigeFlaecheEingabe);
            speichernButton.setDisable(!(gueltigeFlaecheEingabe && gueltigeHoeheEingabe && gueltigeNamenEingabe));
        });
        hoeheTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            gueltigeHoeheEingabe = newValue.matches(decimalPattern);
            falscheHoehe.setVisible(!gueltigeHoeheEingabe);
            speichernButton.setDisable(!(gueltigeFlaecheEingabe && gueltigeHoeheEingabe && gueltigeNamenEingabe));

        });
        nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue.isEmpty()) {
                falscherName.setText("Name darf nicht leer sein");
                falscherName.setVisible(true);
                gueltigeNamenEingabe = false;
            } else if (RaumSteuerung.getInstance().istRaumnameVerwendet(newValue, raum)) {
                falscherName.setText("Name vorhanden");
                falscherName.setVisible(true);
                gueltigeNamenEingabe = false;
            } else {
                falscherName.setVisible(false);
                gueltigeNamenEingabe = true;
            }

            speichernButton.setDisable(!(gueltigeFlaecheEingabe && gueltigeHoeheEingabe && gueltigeNamenEingabe));

        });
        speichernButton.setOnAction(event -> {
            String neuenNamen = nameTextField.getText();
            float neueFlaecheFloat = Float.valueOf(flaecheTextField.getText().trim()).floatValue();
            float neueHoeheFloat = Float.valueOf(hoeheTextField.getText().trim()).floatValue();

            raum.setName(neuenNamen);
            raum.setQuadratmeter(neueFlaecheFloat);
            raum.setHoehe(neueHoeheFloat);
            tabSteuerung.pop();

        });
        abbrechenButton.setOnAction(event -> {
            tabSteuerung.pop();

        });
        grid.add(nameText, 0, 0);
        grid.add(nameTextField, 1, 0);
        grid.add(falscherName, 2, 0);
        grid.add(flaecheText, 0, 2);
        grid.add(flaecheTextField, 1, 2);
        grid.add(falscheFlaeche, 2, 2);
        grid.add(hoeheText, 0, 3);
        grid.add(hoeheTextField, 1, 3);
        grid.add(falscheHoehe, 2, 3);
        grid.add(speichernButton, 0, 4);
        grid.add(abbrechenButton, 1, 4);

    }

    /**
     * Gibt die Hauptnode des Fensters zurueck
     */
    @Override
    public Node getNode() {

        return grid;
    }

    /**
     * Gibt den Namen wieder, der in den Breadcrumbs angezeigt wird
     */
    @Override
    public String getName() {

        return eigenerRaum.getName();

    }
}
