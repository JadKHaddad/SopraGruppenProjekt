/**
 * 
 */
package de.wwu.sopra.view;

import java.util.ArrayList;

import de.wwu.sopra.controller.data.BehaelterVerwaltung;
import de.wwu.sopra.controller.data.MesstypVerwaltung;
import de.wwu.sopra.model.Behaeltertyp;
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
public class MesstypAnlegenFormular extends View{
    
    private GridPane gpane;
    private boolean gueltigerName, gueltigeEinheit;
    MesstypVerwaltung messtypVerwaltung = MesstypVerwaltung.getInstance();
    
    public MesstypAnlegenFormular (TabSteuerung tabSteuerung) {
        
        gpane = new GridPane();
        GridPane pane = new GridPane();
        
        gpane.setHgap(32);
        gpane.setVgap(6);
        pane.setHgap(32);
        pane.setVgap(6);

        TextField nameField = new TextField();
        TextField einheitField = new TextField();
                
        pane.add(new Label("Name:"), 0, 0);
        pane.add(nameField, 1, 0);
       
        
        pane.add(new Label("Einheit:"), 0, 1);
        pane.add(einheitField, 1, 1);
        
        
        Button anlegen = new Button("Anlegen");
        anlegen.setTooltip(new Tooltip("Anlegen"));
        anlegen.setDisable(true);
        
        Text errormessageName = new Text("Das Feld ist leer oder der Name ist bereits vergeben.");
        errormessageName.setVisible(gueltigerName);
        errormessageName.setFill(Color.CRIMSON); 
        pane.add(errormessageName, 3, 0);
        
        Text errormessageEinheit = new Text("Das Feld ist leer.");
        errormessageEinheit.setVisible(gueltigeEinheit);
        errormessageEinheit.setFill(Color.CRIMSON);
        pane.add(errormessageEinheit, 3, 1);
        
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            MesstypVerwaltung messtypVerwaltung = MesstypVerwaltung.getInstance();
            gueltigerName = (!newValue.isBlank())&&messtypVerwaltung.nameIsUnique(newValue);
            errormessageName.setVisible(!gueltigerName);
            anlegen.setDisable(!(gueltigerName && gueltigeEinheit));
        });
        
        einheitField.textProperty().addListener((observable, oldValue, newValue) -> {
            
            gueltigeEinheit = (!newValue.isBlank());
            errormessageEinheit.setVisible(!gueltigeEinheit);
            anlegen.setDisable(!(gueltigerName && gueltigeEinheit));
        });

  
        
        
        
        anlegen.setOnAction(event -> {
            Messtyp messtyp = new Messtyp(nameField.getText(), einheitField.getText());
            
            MesstypVerwaltung.getInstance().getMesstypen().add(messtyp);
            new InfoPopup("Erfolgreich", "Der neue Messtyp wurde erfolgreich angelegt!", () -> {});

            
            tabSteuerung.pop();
        });
        
        Button zurueck = new Button("Zurück");
        zurueck.setTooltip(new Tooltip("Zurück"));
        
        zurueck.setOnAction(event -> {
            tabSteuerung.pop();
        });
        
        
        pane.add(anlegen, 0, 2);
        pane.add(zurueck, 1, 2);
        
        TableView<Messtyp> messtypTable = new TableView<Messtyp>();
        
        TableColumn<Messtyp, String> nameCol = new TableColumn<Messtyp, String>("Name");
        TableColumn<Messtyp, String> einheitCol = new TableColumn<Messtyp, String>("Einheit");
        
        
        

        nameCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Messtyp, String>, ObservableValue<String>>() {

                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Messtyp, String> param) {
                        return new ReadOnlyObjectWrapper<String>("" + param.getValue().getName());
                    }
                });
        

        einheitCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Messtyp, String>, ObservableValue<String>>() {

                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Messtyp, String> param) {
                        return new ReadOnlyObjectWrapper<String>("" + param.getValue().getEinheit());
                    }
                });
        

        
        ObservableList<Messtyp> messtypList = FXCollections
                .observableList(new ArrayList<Messtyp>(messtypVerwaltung.getMesstypen()));
        messtypTable.setItems(messtypList);
        
        messtypTable.getColumns().add(nameCol);
        messtypTable.getColumns().add(einheitCol);
     
        
        Text ueberschrift = new Text("Bereits angelegte Messtypen: ");
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
        
        return "Messtyp Anlegen";
    }

}
