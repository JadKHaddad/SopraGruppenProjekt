package de.wwu.sopra.view;

import javax.swing.event.ChangeListener;

import de.wwu.sopra.model.ProbenInfo;
import de.wwu.sopra.model.ProbenPlatz;
import de.wwu.sopra.model.ProbenStatus;
import de.wwu.sopra.model.Rack;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ProbeBearbeitenFormular extends View {

    GridPane pane;
    private boolean gueltigeMenge = true;
    private boolean statusgeaendert = false;
    private ProbenStatus neuenProbenStatus;
    String decimalPattern = "(\\d+)(\\.\\d+)?";

    public ProbeBearbeitenFormular(TabSteuerung tabSteuerung, ProbenPlatz platz, Rack rack) {
        pane = new GridPane();
        pane.setHgap(32);
        pane.setVgap(6);

        Text mengeText = new Text("Menge");
        TextField mengeTextField = new TextField("" + platz.getProbe().getInfo().getMenge());
        Text falscheMengeText = new Text("ungültige Eingabe");
        falscheMengeText.setFill(Color.RED);
        falscheMengeText.setVisible(false);

        Text statusText = new Text("Status");
        final ComboBox<String> comboBox = new ComboBox<String>();
        comboBox.getItems().add(ProbenStatus.VERBRAUCHT.getName());
        comboBox.getItems().add(ProbenStatus.EINGELAGERT.getName());
        comboBox.getItems().add(ProbenStatus.KURZFRISTIG_ENTNOMMEN.getName());
        comboBox.getItems().add(ProbenStatus.DAUERHAFT_ENTNOMMEN.getName());
        comboBox.getItems().add(ProbenStatus.VERLOREN.getName());
        comboBox.setPromptText(platz.getProbe().getStatus().getName());
        Button zurueck = new Button("Zurück");
        zurueck.setOnAction(event -> {
            tabSteuerung.pop();
        });
        Button speichern = new Button("Speichern");

        pane.add(mengeText, 0, 1);
        pane.add(mengeTextField, 1, 1);
        pane.add(falscheMengeText, 2, 1);

        pane.add(statusText, 0, 2);
        pane.add(comboBox, 1, 2);

        pane.add(speichern, 0, 3);
        pane.add(zurueck, 1, 3);

        mengeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            gueltigeMenge = newValue.matches(decimalPattern) && !(Float.parseFloat(newValue) == 0);
            falscheMengeText.setVisible(!gueltigeMenge);
            speichern.setDisable(!gueltigeMenge);
        });

        speichern.setOnAction(event -> {
            float neueMenge = Float.parseFloat(mengeTextField.getText());
            ProbenInfo probenInfo = new ProbenInfo(neueMenge, platz.getProbe().getInfo().getKategorie(), platz.getProbe().getInfo().getBehaeltertyp());
            platz.getProbe().setInfo(probenInfo);
            if (statusgeaendert) {
                platz.getProbe().setStatus(neuenProbenStatus);
            }
            tabSteuerung.pop();

        });


       
            comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                statusgeaendert = true;
                if (newValue.equals(ProbenStatus.VERBRAUCHT.getName())) {
                    neuenProbenStatus = ProbenStatus.VERBRAUCHT;
                } else if (newValue.equals(ProbenStatus.EINGELAGERT.getName())) {
                    neuenProbenStatus = ProbenStatus.EINGELAGERT;
                } else if (newValue.equals(ProbenStatus.KURZFRISTIG_ENTNOMMEN.getName())) {
                    neuenProbenStatus = ProbenStatus.KURZFRISTIG_ENTNOMMEN;
                } else if (newValue.equals(ProbenStatus.DAUERHAFT_ENTNOMMEN.getName())) {
                    neuenProbenStatus = ProbenStatus.DAUERHAFT_ENTNOMMEN;
                } else if (newValue.equals(ProbenStatus.VERLOREN.getName())) {
                    neuenProbenStatus = ProbenStatus.VERLOREN;
                }
                
            });

    }

    @Override
    public Node getNode() {

        return pane;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "Probe bearbeiten";
    }

}
