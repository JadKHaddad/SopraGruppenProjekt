package de.wwu.sopra.view;

import de.wwu.sopra.controller.KuehlschrankSteuerung;
import de.wwu.sopra.controller.RaumSteuerung;
import de.wwu.sopra.controller.data.LagerVerwaltung;
import de.wwu.sopra.model.Kuehlschrank;
import de.wwu.sopra.model.Raum;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class KuehlschrankErstellenFormular extends View  {

    private GridPane pane;
    private boolean richtigName = false;
    private boolean richtigBreite = false;
    private boolean richtigTiefe = false;
    private boolean richtigHoehe = false;
    private boolean richtigTemperatur = false;
    private boolean richtigAnzahl = false;

    public KuehlschrankErstellenFormular(TabSteuerung tabSteuerung, Raum raum) {

        pane = new GridPane();
        pane.setHgap(32);
        pane.setVgap(6);

        Text nameText = new Text("Name");
        Text breiteText = new Text("Breite in m");
        Text tiefeText = new Text("Tiefe in m");
        Text hoeheText = new Text("Höhe in m");
        Text temperaturText = new Text("Temperatur in Grad Celsius");
        Text segmentAnzahlText = new Text("Segmentanzahl");

        TextField nameField = new TextField();
        TextField breiteField = new TextField();
        TextField tiefeField = new TextField();
        TextField hoeheField = new TextField();
        TextField temperaturField = new TextField();
        TextField segmentAnzahlField = new TextField();

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
        Text falschAnzahl = new Text("Nicht mehr als 10 Segmentplätze vorhanden.");
        falschAnzahl.setVisible(false);
        falschAnzahl.setFill(Color.CRIMSON);

        pane.add(falschName, 2, 0);
        pane.add(falschBreite, 2, 1);
        pane.add(falschTiefe, 2, 2);
        pane.add(falschHoehe, 2, 3);
        pane.add(falschTemperatur, 2, 4);
        pane.add(falschAnzahl, 2, 5);



        Button anlegen = new Button("Anlegen");
        anlegen.setTooltip(new Tooltip("Neuen Kühlschrank anlegen."));
        Button abbrechen = new Button("Abbrechen");
        abbrechen.setTooltip(new Tooltip("Abbrechen."));
        anlegen.setDisable(true);

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
        pane.add(segmentAnzahlText, 0, 5);
        pane.add(segmentAnzahlField, 1, 5);
        pane.add(anlegen, 0, 6);
        pane.add(abbrechen, 1, 6);

        String decimalPattern = "(\\d+)(\\.\\d+)?";
        String integerPattern = "(10|\\d)";



        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                falschName.setText("Name darf nicht leer sein.");
                falschName.setVisible(true);
                richtigName = false;
            } else if (KuehlschrankSteuerung.getInstance().istKuehlschrankVorhanden(newValue, null, raum)) {
                falschName.setText("Name bereits vorhanden.");
                falschName.setVisible(true);
                richtigName = false;
            } else {
                falschName.setVisible(false);
                richtigName = true;
            }

            anlegen.setDisable(!(richtigAnzahl && richtigBreite && richtigHoehe && richtigName && richtigTemperatur && richtigTiefe));
        });



        breiteField.textProperty().addListener((observable, oldValue, newValue) -> {
            richtigBreite = newValue.matches(decimalPattern);
            falschBreite.setVisible(!richtigBreite);
            anlegen.setDisable(!(richtigAnzahl && richtigBreite && richtigHoehe && richtigName && richtigTemperatur && richtigTiefe));
        });

        hoeheField.textProperty().addListener((observable, oldValue, newValue) -> {
            richtigHoehe = newValue.matches(decimalPattern) && Float.parseFloat(newValue) <= raum.getHoehe();
            if (newValue.matches(decimalPattern)){
                if (Float.parseFloat(newValue) > raum.getHoehe()){
                    falschHoehe.setText("Kühlschrank höher als Raumhöhe");
                }
            }else {
                falschHoehe.setText("Ungültige Eingabe. ");
            }
            falschHoehe.setVisible(!richtigHoehe || Float.parseFloat(newValue) > raum.getHoehe());
            anlegen.setDisable(!(richtigAnzahl && richtigBreite && richtigHoehe && richtigName && richtigTemperatur && richtigTiefe));
        });

        temperaturField.textProperty().addListener((observable, oldValue, newValue) -> {
            richtigTemperatur = newValue.matches("-?(\\d+)(\\.\\d+)?");
            falschTemperatur.setVisible(!richtigTemperatur);
            anlegen.setDisable(!(richtigAnzahl && richtigBreite && richtigHoehe && richtigName && richtigTemperatur && richtigTiefe));
        });

        tiefeField.textProperty().addListener((observable, oldValue, newValue) -> {
            richtigTiefe = newValue.matches(decimalPattern);
            falschTiefe.setVisible(!richtigTiefe);
            anlegen.setDisable(!(richtigAnzahl && richtigBreite && richtigHoehe && richtigName && richtigTemperatur && richtigTiefe));
        });

        segmentAnzahlField.textProperty().addListener((observable, oldValue, newValue) -> {
            richtigAnzahl = newValue.matches(integerPattern);
            falschAnzahl.setVisible(!richtigAnzahl);
            anlegen.setDisable(!(richtigAnzahl && richtigBreite && richtigHoehe && richtigName && richtigTemperatur && richtigTiefe));
        });


        anlegen.setOnAction(event -> {
            String neuenNamen = nameField.getText();
            float neueBreiteFloat = Float.parseFloat(breiteField.getText().trim());
            float neueHoeheFloat = Float.parseFloat(hoeheField.getText().trim());
            float neueTiefeFloat = Float.parseFloat(tiefeField.getText().trim());
            float neueTemperaturFloat = Float.parseFloat(temperaturField.getText().trim());
            int neueAnzahlInteger = Integer.parseInt(segmentAnzahlField.getText().trim());

            if((((neueBreiteFloat)*(neueTiefeFloat)) + RaumSteuerung.getInstance().getBenutzteFlaeche(raum))<= raum.getQuadratmeter()){
                raum.addKuehlschrank(new Kuehlschrank(neuenNamen, neueBreiteFloat, neueTiefeFloat, neueHoeheFloat, neueTemperaturFloat, neueAnzahlInteger, raum));
                tabSteuerung.pop();
            }else{
                new InfoPopup("Raum voll", "Kühlschrank passt nicht mehr in den Raum rein", () -> {});
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

        return "Kühlschrank hinzufügen";
    }

}
