package de.wwu.sopra.view;

import de.wwu.sopra.controller.KuehlschrankSteuerung;
import de.wwu.sopra.controller.RaumSteuerung;
import de.wwu.sopra.controller.data.LagerVerwaltung;
import de.wwu.sopra.model.Kuehlschrank;
import de.wwu.sopra.model.Raum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.LinkedList;

public class KuehlschrankBearbeitenFormular extends View {

    private GridPane pane;
    private Kuehlschrank eigenerKuehlschrank;
    private boolean richtigName = true;
    private boolean richtigBreite = true;
    private boolean richtigTiefe = true;
    private boolean richtigHoehe = true;
    private boolean richtigTemperatur = true;

    public KuehlschrankBearbeitenFormular(TabSteuerung tabSteuerung, Kuehlschrank kuehlschrank) {
        eigenerKuehlschrank = kuehlschrank;
        pane = new GridPane();

        pane.setHgap(32);
        pane.setVgap(6);

        float alteBreite = eigenerKuehlschrank.getBreite();
        float alteTiefe = eigenerKuehlschrank.getTiefe();
        LinkedList<Raum> option = new LinkedList<>(LagerVerwaltung.getInstance().getRaumSet());
        ObservableList<Raum> options = FXCollections.observableList(option);

        Text nameText = new Text("Name");
        Text breiteText = new Text("Breite in m");
        Text tiefeText = new Text("Tiefe in m");
        Text hoeheText = new Text("Höhe in m");
        Text temperaturText = new Text("Temperatur ");
        Text raumText = new Text("Raum ");

        TextField nameField = new TextField(kuehlschrank.getName());
        TextField breiteField = new TextField("" + kuehlschrank.getBreite());
        TextField tiefeField = new TextField("" + kuehlschrank.getTiefe());
        TextField hoeheField = new TextField("" + kuehlschrank.getHoehe());
        TextField temperaturField = new TextField("" + kuehlschrank.getTemperatur());
        final ComboBox<Raum> comboBox = new ComboBox<>(options);
        comboBox.getSelectionModel().select(eigenerKuehlschrank.getRaum());

        Text falschName = new Text();
        falschName.setFill(Color.CRIMSON);
        falschName.setVisible(false);
        Text falschBreite = new Text("Ungültige Eingabe.");
        falschBreite.setVisible(false);
        falschBreite.setFill(Color.CRIMSON);
        Text falschTiefe = new Text("Ungültige Eingabe.");
        falschTiefe.setVisible(false);
        falschTiefe.setFill(Color.CRIMSON);
        Text falschHoehe = new Text("Ungültige Eingabe.");
        falschHoehe.setVisible(false);
        falschHoehe.setFill(Color.CRIMSON);
        Text falschTemperatur = new Text("Ungültige Eingabe.");
        falschTemperatur.setVisible(false);
        falschTemperatur.setFill(Color.CRIMSON);

        pane.add(falschName, 2, 0);
        pane.add(falschBreite, 2, 1);
        pane.add(falschTiefe, 2, 2);
        pane.add(falschHoehe, 2, 3);
        pane.add(falschTemperatur, 2, 4);

        Button speichern = new Button("Speichern");
        Button abbrechen = new Button("Abbrechen");

        pane.add(nameText, 0, 0);
        pane.add(nameField, 1, 0);
        pane.add(breiteText, 0, 1);
        pane.add(breiteField, 1, 1);
        pane.add(tiefeText, 0, 2);
        pane.add(tiefeField, 1, 2);
        pane.add(hoeheText, 0, 3);
        pane.add(hoeheField, 1, 3);
        pane.add(temperaturText, 0, 4);
        pane.add(temperaturField, 1, 4);
        pane.add(raumText, 0, 5);
        pane.add(comboBox, 1, 5);
        pane.add(speichern, 0, 7);
        pane.add(abbrechen, 1, 7);


        String decimalPattern = "(\\d+)(\\.\\d+)?";

        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                falschName.setText("Name darf nicht leer sein.");
                falschName.setVisible(true);
                richtigName = false;
            } else if (KuehlschrankSteuerung.getInstance().istKuehlschrankVorhanden(newValue, kuehlschrank,
                    kuehlschrank.getRaum())) {
                falschName.setText("Name bereits vorhanden.");
                falschName.setVisible(true);
                richtigName = false;
            } else {
                falschName.setVisible(false);
                richtigName = true;
            }

            speichern.setDisable(!(richtigBreite && richtigHoehe && richtigName && richtigTemperatur && richtigTiefe));
        });

        breiteField.textProperty().addListener((observable, oldValue, newValue) -> {
            richtigBreite = newValue.matches(decimalPattern);
            falschBreite.setVisible(!richtigBreite);
            speichern.setDisable(!(richtigBreite && richtigHoehe && richtigName && richtigTemperatur && richtigTiefe));
        });

        hoeheField.textProperty().addListener((observable, oldValue, newValue) -> {
            richtigHoehe = newValue.matches(decimalPattern) && Float.parseFloat(newValue) <= eigenerKuehlschrank.getRaum().getHoehe();
            if (newValue.matches(decimalPattern)){
                if (Float.parseFloat(newValue) > eigenerKuehlschrank.getRaum().getHoehe()){
                    falschHoehe.setText("Kühlschrank höher als Raumhöhe");
                }
            }else {
                falschHoehe.setText("Ungültige Eingabe. ");
            }
            falschHoehe.setVisible(!richtigHoehe || Float.parseFloat(newValue) > eigenerKuehlschrank.getRaum().getHoehe());
            speichern.setDisable(!(richtigBreite && richtigHoehe && richtigName && richtigTemperatur && richtigTiefe));
        });

        temperaturField.textProperty().addListener((observable, oldValue, newValue) -> {
            richtigTemperatur = newValue.matches("-?(\\d+)(\\.\\d+)?");
            falschTemperatur.setVisible(!richtigTemperatur);
            speichern.setDisable(!(richtigBreite && richtigHoehe && richtigName && richtigTemperatur && richtigTiefe));
        });

        tiefeField.textProperty().addListener((observable, oldValue, newValue) -> {
            richtigTiefe = newValue.matches(decimalPattern);
            falschTiefe.setVisible(!richtigTiefe);
            speichern.setDisable(!(richtigBreite && richtigHoehe && richtigName && richtigTemperatur && richtigTiefe));
        });

        speichern.setOnAction(event -> {
            String neuenNamen = nameField.getText();
            float neueBreiteFloat = Float.parseFloat(breiteField.getText().trim());
            float neueHoeheFloat = Float.parseFloat(hoeheField.getText().trim());
            float neueTiefeFloat = Float.parseFloat(tiefeField.getText().trim());
            float neueTemperaturFloat = Float.parseFloat(temperaturField.getText().trim());
            Raum raum = (Raum) comboBox.getSelectionModel().getSelectedItem();
            if (raum.equals(kuehlschrank.getRaum())){
                if(((((neueBreiteFloat) * (neueTiefeFloat))) + RaumSteuerung.getInstance().getBenutzteFlaeche(kuehlschrank.getRaum()) - ((alteBreite) * (alteTiefe))) <= kuehlschrank.getRaum().getQuadratmeter()){
                    kuehlschrank.setName(neuenNamen);
                    kuehlschrank.setBreite(neueBreiteFloat);
                    kuehlschrank.setHoehe(neueHoeheFloat);
                    kuehlschrank.setTiefe(neueTiefeFloat);
                    kuehlschrank.setTemperatur(neueTemperaturFloat);
                    tabSteuerung.pop();
                }else{
                    new InfoPopup("Raum voll", "Kühlschrank passt nicht mehr in den Raum rein", () -> {});
                }
            }else{
                if((((((neueBreiteFloat) * (neueTiefeFloat))) + RaumSteuerung.getInstance().getBenutzteFlaeche(raum)) <= raum.getQuadratmeter()) && neueHoeheFloat <= raum.getHoehe()){
                    kuehlschrank.setName(neuenNamen);
                    kuehlschrank.setBreite(neueBreiteFloat);
                    kuehlschrank.setHoehe(neueHoeheFloat);
                    kuehlschrank.setTiefe(neueTiefeFloat);
                    kuehlschrank.setTemperatur(neueTemperaturFloat);
                    kuehlschrank.setRaum(raum);
                    tabSteuerung.pop();
                }else{
                    new InfoPopup("Raum voll", "Kühlschrank passt nicht mehr in den Raum rein", () -> {});
                }
            }


        });

        abbrechen.setOnAction(event -> {
            tabSteuerung.pop();
        });

    }

    @Override
    public Node getNode() {
        return pane;
    }

    @Override
    public String getName() {

        return eigenerKuehlschrank.getName();
    }

}
