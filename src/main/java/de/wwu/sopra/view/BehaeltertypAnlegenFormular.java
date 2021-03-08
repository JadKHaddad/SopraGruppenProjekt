/**
 *
 */
package de.wwu.sopra.view;

import java.util.ArrayList;

import de.wwu.sopra.controller.data.BehaelterVerwaltung;
import de.wwu.sopra.controller.data.BenutzerVerwaltung;
import de.wwu.sopra.controller.data.DeckeltypVerwaltung;
import de.wwu.sopra.controller.data.ProbenKategorieVerwaltung;
import de.wwu.sopra.model.Behaeltertyp;
import de.wwu.sopra.model.Deckeltyp;
import de.wwu.sopra.model.ProbenKategorie;
import de.wwu.sopra.model.Rolle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;


/**
 * Darstellung des BehaeltertypAnlegenFormulars
 * @author Gruppe 5
 *
 */
public class BehaeltertypAnlegenFormular extends View {

    private TabSteuerung tabSteuerung;
    BehaelterVerwaltung behaelterVerwaltung = BehaelterVerwaltung.getInstance();
    private GridPane gpane;
    private TextField nameField, radiusField, hoeheField;
    private Text nameMeldung, radiusMeldung, hoeheMeldung, deckelMeldung, meldung;
    private boolean gueltigerName, gueltigerRadius, gueltigeHoehe, gueltigerDeckel;
    /**
     * Erstellt ein BehaeltertypAnlegenFormular
     * @param tabSteuerung die tabsteuerung
     */
    public BehaeltertypAnlegenFormular(TabSteuerung tabSteuerung) {
        
        //GUI erstellen
        gpane = new GridPane();
        gpane.setHgap(32);
        gpane.setVgap(6);
        
        GridPane pane = new GridPane();
        pane.setHgap(32);
        pane.setVgap(6);

        pane.add(new Label("Name"), 0, 0);
        nameField = new TextField();
        pane.add(nameField, 1, 0);
        nameMeldung = new Text(" ");
        nameMeldung.setFill(Color.CRIMSON);
        pane.add(nameMeldung, 2,0);

        pane.add(new Label("Radius in mm"), 0, 1);
        radiusField = new TextField();
        pane.add(radiusField, 1, 1);
        radiusMeldung = new Text(" ");
        radiusMeldung.setFill(Color.CRIMSON);
        pane.add(radiusMeldung, 2, 1);

        pane.add(new Label("Höhe in mm"), 0, 2);
        hoeheField = new TextField();
        pane.add(hoeheField, 1, 2);  
        hoeheMeldung = new Text(" ");
        hoeheMeldung.setFill(Color.CRIMSON);
        pane.add(hoeheMeldung, 2, 2);

        pane.add(new Label("Deckeltyp"), 0, 3);
        
        ObservableList<Deckeltyp> options =
                FXCollections.observableArrayList(
                    DeckeltypVerwaltung.getInstance().getDeckeltypen()
                );
        
        final ComboBox<Deckeltyp> comboBox = new ComboBox<Deckeltyp>(options);
        
        pane.add(comboBox, 1, 3);
        comboBox.setPromptText("Bitte auswählen");
        deckelMeldung = new Text(" ");
        deckelMeldung.setFill(Color.CRIMSON);
        pane.add(deckelMeldung, 2, 4);

        Button anlegenButton = new Button("Anlegen");
        anlegenButton.setDisable(true);
        anlegenButton.setTooltip(new Tooltip("Neuen Behältertyp hinzufügen"));
        
        //Listener auf die Textfelder hinzufuegen
         String decimalPattern = "(\\d+)(\\.\\d+)?";
        
         nameField.textProperty().addListener((observable, oldValue, newValue) -> {
             BehaelterVerwaltung bv = BehaelterVerwaltung.getInstance();
             gueltigerName = (!newValue.isBlank())&&bv.nameIsUnique(newValue);

             anlegenButton.setDisable(!(gueltigerName && gueltigerRadius && gueltigeHoehe && gueltigerDeckel));
             if(gueltigerName) {
                 nameMeldung.setText("");
             }
             else {
                 nameMeldung.setText("Das Feld ist leer oder der Name ist bereits vergeben.");
             }
         });
          
         radiusField.textProperty().addListener((observable, oldValue, newValue) -> {
             boolean isDecimal = newValue.matches(decimalPattern);
             gueltigerRadius = isDecimal && Float.parseFloat(radiusField.getText()) != 0;
             anlegenButton.setDisable(!(gueltigerName && gueltigerRadius && gueltigeHoehe && gueltigerDeckel));
             if(gueltigerRadius) {
                 radiusMeldung.setText("");
             } else if (!isDecimal){
                 radiusMeldung.setText("Nur Dezimalzahlen erlaubt.");
             }
             else {
                 radiusMeldung.setText("Nur Zahlen größer 0 erlaubt.");
             }
         });
         
         hoeheField.textProperty().addListener((observable, oldValue, newValue) -> {
             boolean isDecimal = newValue.matches(decimalPattern);
             gueltigeHoehe = isDecimal && Float.parseFloat(hoeheField.getText()) != 0;
             anlegenButton.setDisable(!(gueltigerName && gueltigerRadius && gueltigeHoehe && gueltigerDeckel));
             if(gueltigeHoehe) {
                 hoeheMeldung.setText("");
             }else if (!isDecimal){
                 hoeheMeldung.setText("Nur Dezimalzahlen erlaubt.");
             }
             else {
                 hoeheMeldung.setText("Nur Zahlen größer 0 erlaubt.");
             }
         });
         
         comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
             gueltigerDeckel = true;
             anlegenButton.setDisable(!(gueltigerName && gueltigerRadius && gueltigeHoehe && gueltigerDeckel));
             if(gueltigerDeckel) {
                 deckelMeldung.setText("");
             }
             else {
                 deckelMeldung.setText("Keine gültige Auswahl.");
             }
         });
         
         
         
         //Funktionalitaet auf den Button setzen
        anlegenButton.setOnAction(event -> {
            String name = nameField.getText();
            float radius = Float.parseFloat(radiusField.getText());
            float hoehe = Float.parseFloat(hoeheField.getText());
            float volumen = (float) (Math.PI * radius * radius * hoehe);
            BehaelterVerwaltung bv = BehaelterVerwaltung.getInstance();
            Deckeltyp deckeltyp = comboBox.getSelectionModel().getSelectedItem();
            Behaeltertyp behaeltertyp = new Behaeltertyp(name, radius,  hoehe, volumen, deckeltyp);
            bv.addBehaeltertyp(behaeltertyp);
            new InfoPopup("Erfolgreich", "Der neue Behältertyp wurde erfolgreich angelegt!", () -> {});
            tabSteuerung.pop();         
        });

        pane.add(anlegenButton, 0, 4);

        Button zurueckButton = new Button("Zurück");

        zurueckButton.setOnAction(event -> {
            tabSteuerung.pop();
        });

        pane.add(zurueckButton, 1, 4);
        
        TableView<Behaeltertyp> behaelterTable = new TableView<Behaeltertyp>();
        
        TableColumn<Behaeltertyp, String> nameCol = new TableColumn<Behaeltertyp, String>("Name");
        TableColumn<Behaeltertyp, String> radiusCol = new TableColumn<Behaeltertyp, String>("Radius");
        TableColumn<Behaeltertyp, String> hoeheCol = new TableColumn<Behaeltertyp, String>("Höhe");
        TableColumn<Behaeltertyp, String> deckeltypCol = new TableColumn<Behaeltertyp, String>("Deckeltyp");
        
        

        nameCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Behaeltertyp, String>, ObservableValue<String>>() {

                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Behaeltertyp, String> param) {
                        return new ReadOnlyObjectWrapper<String>("" + param.getValue().getName());
                    }
                });
        

        radiusCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Behaeltertyp, String>, ObservableValue<String>>() {

                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Behaeltertyp, String> param) {
                        return new ReadOnlyObjectWrapper<String>("" + param.getValue().getRadius());
                    }
                });
        

        hoeheCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Behaeltertyp, String>, ObservableValue<String>>() {

                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Behaeltertyp, String> param) {
                        return new ReadOnlyObjectWrapper<String>("" + param.getValue().getHoehe());
                    }
                });
        

        deckeltypCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Behaeltertyp, String>, ObservableValue<String>>() {

                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Behaeltertyp, String> param) {
                        return new ReadOnlyObjectWrapper<String>("" + param.getValue().getDeckel().getName());
                    }
                });
        
        ObservableList<Behaeltertyp> behaelterList = FXCollections
                .observableList(new ArrayList<Behaeltertyp>(behaelterVerwaltung.getBehaeltertypSet()));
        behaelterTable.setItems(behaelterList);
        
        behaelterTable.getColumns().add(nameCol);
        behaelterTable.getColumns().add(radiusCol);
        behaelterTable.getColumns().add(hoeheCol);
        behaelterTable.getColumns().add(deckeltypCol);
        
        Text ueberschrift = new Text("Bereits angelegte Behältertypen: ");
        gpane.add(pane, 0, 0);
        gpane.add(ueberschrift, 0, 1);
        gpane.add(behaelterTable, 0, 2);

    }

    /**
     * @return Die Hauptnode des Fensters.
     */
    @Override
    public Node getNode() {
        return gpane;
    }

    /**
     * @return Der Name des Formulars, der bei den Breadcrumbs angezeigt werden sollen.
     */
    @Override
    public String getName() {
        return "Behältertyp anlegen";
    }
}
