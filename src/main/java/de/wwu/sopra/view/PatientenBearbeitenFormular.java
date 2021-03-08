package de.wwu.sopra.view;

import de.wwu.sopra.model.Patient;
import de.wwu.sopra.model.Studienteilnehmer;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
/**
 * Verwaltet die Ansicht auf das PatientenBEarbeitenFormular
 * @author Gruppe 5
 */
public class PatientenBearbeitenFormular extends View {

    GridPane pane;
    Patient patient;
    TextField vorname, name, adresse, alter;
    private boolean alterKorrekt = true, nameKorrekt = true, vornameKorrekt = true, adresseKorrekt = true;
    /**
     * Erstellt ein PatientenBearbeitenFormular
     * @param tabSteuerung Die Tabsteuerung
     * @param patient die Patienten
     */
    public PatientenBearbeitenFormular(TabSteuerung tabSteuerung, Patient patient) {
        pane = new GridPane();
        this.patient = patient;
        pane.setVgap(6);
        pane.setHgap(32);


        pane.add(new Text("Vorname"), 0, 0);
        pane.add(new Text("Name"), 0, 1);
        pane.add(new Text("Adresse"), 0, 2);
        pane.add(new Text("Alter"), 0, 3);


        vorname = new TextField(patient.getVorname());
        name = new TextField(patient.getName());
        adresse = new TextField(patient.getAnschrift());
        alter = new TextField("" + patient.getAlter());

        pane.add(vorname, 1, 0);
        pane.add(name, 1, 1);
        pane.add(adresse, 1, 2);
        pane.add(alter, 1, 3);

        Button anlegen = new Button("Speichern");
        anlegen.setTooltip(new Tooltip("Änderungen speichern"));
        anlegen.setOnAction(event -> {
            
            patient.setAlter(Integer.parseInt(alter.getText()));
            patient.setAnschrift(adresse.getText());
            patient.setName(name.getText());
            patient.setVorname(vorname.getText());

            tabSteuerung.pop();
        });
        pane.add(anlegen, 1, 4);

        
        
        String intPattern = "(\\d+)";

        alter.textProperty().addListener((observable, oldValue, newValue) -> {
            alterKorrekt = newValue.matches(intPattern);
            anlegen.setDisable(!(alterKorrekt && nameKorrekt && vornameKorrekt && adresseKorrekt));
        });
        
        name.textProperty().addListener((observable, oldValue, newValue) -> {
            nameKorrekt = !newValue.isEmpty();
            anlegen.setDisable(!(alterKorrekt && nameKorrekt && vornameKorrekt && adresseKorrekt));
        });
        
        vorname.textProperty().addListener((observable, oldValue, newValue) -> {
            vornameKorrekt = !newValue.isEmpty();
            anlegen.setDisable(!(alterKorrekt && nameKorrekt && vornameKorrekt && adresseKorrekt));
        });
        
        adresse.textProperty().addListener((observable, oldValue, newValue) -> {
            adresseKorrekt = !newValue.isEmpty();
            anlegen.setDisable(!(alterKorrekt && nameKorrekt && vornameKorrekt && adresseKorrekt));
        });

    }
    /**
     * Gibt die Hauptnode des Fensters zurueck
     */
    @Override
    public Node getNode() {
        return pane;
    }
    /**
     * Gibt den Namen wieder, der in den Breadcrumbs angezeigt wird
     */
    @Override
    public String getName() {
        return "Patienten bearbeiten";
    }
}