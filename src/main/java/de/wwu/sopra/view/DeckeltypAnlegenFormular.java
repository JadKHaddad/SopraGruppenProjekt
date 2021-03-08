/**
 * 
 */
package de.wwu.sopra.view;

import java.util.ArrayList;

import de.wwu.sopra.controller.data.BehaelterVerwaltung;
import de.wwu.sopra.controller.data.DeckeltypVerwaltung;
import de.wwu.sopra.model.Deckeltyp;
import de.wwu.sopra.model.Messtyp;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * @author Gruppe 5
 *
 */
public class DeckeltypAnlegenFormular extends View {
    
    private TabSteuerung tabsteuerung;
    DeckeltypVerwaltung deckeltypVerwaltung = DeckeltypVerwaltung.getInstance();
    private GridPane gpane;
    private TextField nameField;
    private boolean gueltigerName;
    
    
    public DeckeltypAnlegenFormular(TabSteuerung tabSteuerung) {
        
        gpane = new GridPane();
        
        gpane.setHgap(32);
        gpane.setVgap(6);
        
        GridPane pane = new GridPane();
        
        pane.setHgap(32);
        pane.setVgap(6);
        
        pane.add(new Label("Name"), 0, 0);
        nameField = new TextField();  
        
        
        Button anlegenButton = new Button("Anlegen");
        anlegenButton.setDisable(true);
        anlegenButton.setTooltip(new Tooltip("Neuen Deckeltyp hinzufügen"));
        
        Text errormessageName = new Text("Das Feld ist leer oder der Name ist bereits vergeben.");
        errormessageName.setVisible(gueltigerName);
        errormessageName.setFill(Color.CRIMSON); 
        pane.add(errormessageName, 3, 0);
        
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            DeckeltypVerwaltung deckeltypVerwaltung = DeckeltypVerwaltung.getInstance();
            gueltigerName = (!newValue.isBlank())&& deckeltypVerwaltung.nameIsUnique(newValue);
            errormessageName.setVisible(!gueltigerName);
            anlegenButton.setDisable(!gueltigerName);
        });
        
        anlegenButton.setOnAction(event -> {
            DeckeltypVerwaltung.getInstance().getDeckeltypen().add(new Deckeltyp (nameField.getText()));
            new InfoPopup("Erfolgreich", "Der neue Deckeltyp wurde erfolgreich angelegt!", () -> {});
            tabSteuerung.pop();
        }); 
        
        
        Button zurueckButton = new Button("Zurück");
        zurueckButton.setTooltip(new Tooltip("Zurück"));
        zurueckButton.setOnAction(event -> {
            tabSteuerung.pop();
        });
        pane.add(nameField, 1, 0);
        pane.add(anlegenButton, 0, 1);
        pane.add(zurueckButton, 1, 1);
        
     TableView<Deckeltyp> messtypTable = new TableView<Deckeltyp>();
        
        TableColumn<Deckeltyp, String> nameCol = new TableColumn<Deckeltyp, String>("Name");
        
        
        nameCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Deckeltyp, String>, ObservableValue<String>>() {

                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Deckeltyp, String> param) {
                        return new ReadOnlyObjectWrapper<String>("" + param.getValue().getName());
                    }
                });


        
        ObservableList<Deckeltyp> messtypList = FXCollections
                .observableList(new ArrayList<Deckeltyp>(deckeltypVerwaltung.getDeckeltypen()));
        messtypTable.setItems(messtypList);
        
        messtypTable.getColumns().add(nameCol);
     
        
        Text ueberschrift = new Text("Bereits angelegte Deckeltypen: ");
        gpane.add(pane, 0, 0);
        gpane.add(ueberschrift, 0, 1);
        gpane.add(messtypTable, 0, 2);
        
        
    }

    @Override
    public Node getNode() {
        
        return gpane;
    }

    @Override
    public String getName() {
        
        return "Neuen Deckeltyp anlegen";
    }
    
    

}
