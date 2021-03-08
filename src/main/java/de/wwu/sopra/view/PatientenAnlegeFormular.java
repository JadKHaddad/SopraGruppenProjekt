package de.wwu.sopra.view;

import de.wwu.sopra.controller.data.PatientenVerwaltung;
import de.wwu.sopra.model.Patient;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
/**
 * Verwaltet die Ansicht des PatientenAnlegenFormulars
 * @author Gruppe 5
 */
public class PatientenAnlegeFormular extends View {

    GridPane pane;

    TextField vorname, name, adresse, alter;
    
    private boolean alterKorrekt, nameKorrekt, vornameKorrekt, adresseKorrekt;
    /**
     * Erstellt ein PatientenAnlegeFormular
     * @param tabSteuerung die Tabsteuerung
     */
    public PatientenAnlegeFormular(TabSteuerung tabSteuerung) {
        pane = new GridPane();

        pane.setVgap(6);
        pane.setHgap(32);


        pane.add(new Text("Vorname"), 0, 0);
        pane.add(new Text("Name"), 0, 1);
        pane.add(new Text("Adresse"), 0, 2);
        pane.add(new Text("Alter"), 0, 3);


        vorname = new TextField();
        name = new TextField();
        adresse = new TextField();
        alter = new TextField();


        pane.add(vorname, 1, 0);
        pane.add(name, 1, 1);
        pane.add(adresse, 1, 2);
        pane.add(alter, 1, 3);

        Button anlegen = new Button("Anlegen");
        anlegen.setDisable(true);
        anlegen.setTooltip(new Tooltip("Neues Patienten anlegen"));
        
       
        
        anlegen.setOnAction(event -> {
            PatientenVerwaltung.getInstance().addPatient(new Patient(name.getText(), vorname.getText(), Integer.parseInt(alter.getText()), adresse.getText())); 
            
            tabSteuerung.pop();
        });
        
                
        pane.add(anlegen, 1, 4);

        
        String intPattern = "(\\d+)";

        alter.textProperty().addListener((observable, oldValue, newValue) -> {
            alterKorrekt = newValue.matches(intPattern);
            anlegen.setDisable(!(alterKorrekt && nameKorrekt && vornameKorrekt && adresseKorrekt));
            
        });
        
        name.textProperty().addListener((observable, oldValue, newValue) -> {
            nameKorrekt = !name.getText().isEmpty();
            anlegen.setDisable(!(alterKorrekt && nameKorrekt && vornameKorrekt && adresseKorrekt));
        });
        
        vorname.textProperty().addListener((observable, oldValue, newValue) -> {
            vornameKorrekt = !vorname.getText().isEmpty();
            anlegen.setDisable(!(alterKorrekt && nameKorrekt && vornameKorrekt && adresseKorrekt));
            
        });
        
        adresse.textProperty().addListener((observable, oldValue, newValue) -> {
            adresseKorrekt = !adresse.getText().isEmpty();
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
     * Gibt den Namen wieder, der bei den Breadcrumbs angezeigt wird
     */
    @Override
    public String getName() {
        return "Neuen Patienten anlegen";
    }

}
