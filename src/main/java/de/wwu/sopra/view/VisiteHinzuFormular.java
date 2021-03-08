/**
 *
 */
package de.wwu.sopra.view;

import de.wwu.sopra.model.Messtyp;
import de.wwu.sopra.model.ProbenInfo;
import de.wwu.sopra.model.Visite;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * Verwaltet die Ansicht des VisitenHinzuFormulars
 * @author Gruppe 5
 */
public class VisiteHinzuFormular extends View {

    private TabSteuerung tabSteuerung;

    private GridPane pane;
    private TextField nameField;
    private ArrayList<ProbenInfo> proben;
    private ArrayList<Messtyp> messtypen;
    private boolean nameKorrekt, tageKorrekt;
    
    /**
     * Erstellt ein VisitenHinzuFomular
     * @param tabSteuerung die Tabsteurung
     * @param visiten Die Visiten
     */
    public VisiteHinzuFormular(TabSteuerung tabSteuerung, ArrayList<Visite> visiten) {
        
        proben = new ArrayList<ProbenInfo>();
        messtypen = new ArrayList<Messtyp>();

        pane = new GridPane();
        pane.setHgap(32);
        pane.setVgap(6);

        pane.add(new Text("Name"), 0, 0);
        nameField = new TextField();
        pane.add(nameField, 1, 0);

        pane.add(new Text("Startet"), 0, 1);
        TextField tageField = new TextField();
        pane.add(tageField, 1, 1);
        pane.add(new Text("Tage nach Studienstart"), 2, 1);

        Button button = new Button("Messungen wählen");
        button.setTooltip(new Tooltip("Messung auswählen"));

        MessungenAuswaehlenFormular messungenAuswaehlenFormular = new MessungenAuswaehlenFormular(tabSteuerung, messtypen);
        button.setOnAction(event -> {
            
            tabSteuerung.push(messungenAuswaehlenFormular);
        });

        Button button1 = new Button("Probe auswählen");
        button1.setTooltip(new Tooltip("Zu entznehmende Probe auswählen"));

        ProbenAuswahlFormular probenAuswahlFormular = new ProbenAuswahlFormular(tabSteuerung, proben);
        button1.setOnAction(event -> {
            tabSteuerung.push(probenAuswahlFormular);
        });

        Button button2 = new Button("Anlegen");
        button2.setDisable(true);
        button2.setTooltip(new Tooltip("Neue Visite hinzufüegen"));

        
        
        String intPattern = "(\\d+)";

        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            nameKorrekt = !nameField.getText().isEmpty();
            button2.setDisable(!(nameKorrekt && tageKorrekt));
        });

        tageField.textProperty().addListener((observable, oldValue, newValue) -> {
            tageKorrekt = newValue.matches(intPattern);
            button2.setDisable(!(nameKorrekt && tageKorrekt));
        });

        button2.setOnAction(event -> {
            Visite v = new Visite(nameField.getText(), Integer.parseInt(tageField.getText()), null);
            for (int i = 0; i < proben.size(); i++)
                v.addProbenInfo(proben.get(i));
            for (int i = 0; i < messtypen.size(); i++)
                v.addMesstyp(messtypen.get(i));
            visiten.add(v);
            tabSteuerung.pop();

        });

        
        Button zurueck = new Button("Abbrechen");
        pane.add(zurueck, 1, 8);
        zurueck.setTooltip(new Tooltip("Zurück zur Auswahl"));
        zurueck.setOnAction(event -> {
            tabSteuerung.pop();
        });
        
        pane.add(button2, 0, 8);
        pane.add(button1, 1, 2);
        pane.add(button, 1, 3);

    }

    public void setProbenListe() {

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

        return "Visite hinzufügen";
    }

}
