/**
 * 
 */
package de.wwu.sopra.view;

import java.util.ArrayList;
import java.util.Set;

import de.wwu.sopra.controller.ProbenSteuerung;
import de.wwu.sopra.controller.RackSteuerung;
import de.wwu.sopra.controller.data.BenachrichtigungVerwaltung;
import de.wwu.sopra.controller.data.PatientenVerwaltung;
import de.wwu.sopra.controller.data.ProbenVerwaltung;
import de.wwu.sopra.model.Patient;
import de.wwu.sopra.model.Probe;
import de.wwu.sopra.model.ProbenInfo;
import de.wwu.sopra.model.ProbenStatus;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * @author Gruppe 5
 *
 */
public class BenachrichtigungFormular extends View {

    private GridPane gridPane;
    private TableView<Probe> neueProbenTable;
    private TableView<Probe> loeschProbenTable;
    BenachrichtigungVerwaltung benachrichtigungVerwaltung = BenachrichtigungVerwaltung.getInstance();

    public BenachrichtigungFormular(TabSteuerung benachrichtigungTabSteuerung) {

        ProbenVerwaltung probenVerwaltung = ProbenVerwaltung.getInstance();

        gridPane = new GridPane();
        gridPane.setHgap(32);
        gridPane.setVgap(6);

        neueProbenTable = new TableView<Probe>();
        loeschProbenTable = new TableView<Probe>();

        gridPane.add(neueProbenTable, 0, 1);
        gridPane.add(loeschProbenTable, 1, 1);

      
        neueProbenTable.setMinWidth(325);
        loeschProbenTable.setMinWidth(325);

        TableColumn<Probe, String> neueProbeIdCol = new TableColumn<Probe, String>("Probe ID");
        TableColumn<Probe, String> loeschProbeIdCol = new TableColumn<Probe, String>("Proben ID");

        TableColumn<Probe, String> neueProbePlatzCol = new TableColumn<Probe, String>("Platz");
        TableColumn<Probe, String> loeschProbePlatzCol = new TableColumn<Probe, String>("Platz");
        
        
        neueProbePlatzCol.setMinWidth(200);
        loeschProbePlatzCol.setMinWidth(200);

        neueProbeIdCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Probe, String>, ObservableValue<String>>() {

                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Probe, String> param) {
                        return new ReadOnlyObjectWrapper<String>("" + param.getValue().getBid());
                    }
                });

        loeschProbeIdCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Probe, String>, ObservableValue<String>>() {

                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Probe, String> param) {
                        return new ReadOnlyObjectWrapper<String>("" + param.getValue().getBid());
                    }
                });

        // Platzhalter fuer einen schoeneren String
        neueProbePlatzCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Probe, String>, ObservableValue<String>>() {

                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Probe, String> param) {
                        return new ReadOnlyObjectWrapper<String>("" + param.getValue().getProbenplatz() + "\nauf Probenplatz: " + RackSteuerung.getInstance().getNameForProbenPlatz(param.getValue().getProbenplatz()));
                    }
                });

        
        loeschProbePlatzCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Probe, String>, ObservableValue<String>>() {

                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Probe, String> param) {
                        return new ReadOnlyObjectWrapper<String>("" + param.getValue().getProbenplatz() + "\nauf Probenplatz: " + RackSteuerung.getInstance().getNameForProbenPlatz(param.getValue().getProbenplatz()));
                    }
                });
        
        ObservableList<Probe> neueProbenList = FXCollections
                .observableList(new ArrayList<Probe>(benachrichtigungVerwaltung.getNachrichtNeueProbe()));
        neueProbenTable.setItems(neueProbenList);

        ObservableList<Probe> loeschProbenList = FXCollections
                .observableList(new ArrayList<Probe>(benachrichtigungVerwaltung.getNachrichtLoeschProbe()));
        loeschProbenTable.setItems(loeschProbenList);

        neueProbenTable.getColumns().add(neueProbeIdCol);
        neueProbenTable.getColumns().add(neueProbePlatzCol);

        loeschProbenTable.getColumns().add(loeschProbeIdCol);
        loeschProbenTable.getColumns().add(loeschProbePlatzCol);

        Button neueProbeButton = new Button("Ausgeführt");
        neueProbeButton.setTooltip(new Tooltip("Ausgeführt"));
        neueProbeButton.setOnAction(event -> {

            while (!benachrichtigungVerwaltung.getNachrichtNeueProbe().isEmpty()) {
                benachrichtigungVerwaltung.getNachrichtNeueProbe().get(0).setStatus(ProbenStatus.EINGELAGERT);
            }
            
            ObservableList<Probe> neueneueProbenList = FXCollections
                    .observableList(new ArrayList<Probe>(benachrichtigungVerwaltung.getNachrichtNeueProbe()));
            neueProbenTable.setItems(neueneueProbenList);
            


            neueProbenTable.refresh();

        });
        Button loeschProbeButton = new Button("Ausgeführt");
        loeschProbeButton.setTooltip(new Tooltip("Ausgeführt"));
        loeschProbeButton.setOnAction(event -> {
            
            while (!benachrichtigungVerwaltung.getNachrichtLoeschProbe().isEmpty()) {
                ProbenSteuerung.getInstance().entferneProbe(benachrichtigungVerwaltung.getNachrichtLoeschProbe().get(0));
            }
            
            ObservableList<Probe> neueloeschProbenList = FXCollections
                    .observableList(new ArrayList<Probe>(benachrichtigungVerwaltung.getNachrichtLoeschProbe()));
            loeschProbenTable.setItems(neueloeschProbenList);


            loeschProbenTable.refresh();

        });

        

        Text hinzufuegen = new Text("Neue Proben:");
        Text entfernen = new Text("Zu vernichtende Proben:");

        gridPane.add(neueProbeButton, 0, 2);
        gridPane.add(loeschProbeButton, 1, 2);
        gridPane.add(hinzufuegen, 0, 0);
        gridPane.add(entfernen, 1, 0);

    }

    @Override
    public void onOpen() {
        ObservableList<Probe> neueProbenList = FXCollections
                .observableList(new ArrayList<Probe>(benachrichtigungVerwaltung.getNachrichtNeueProbe()));
        neueProbenTable.setItems(neueProbenList);

        ObservableList<Probe> loeschProbenList = FXCollections
                .observableList(new ArrayList<Probe>(benachrichtigungVerwaltung.getNachrichtLoeschProbe()));
        loeschProbenTable.setItems(loeschProbenList);

        loeschProbenTable.refresh();

        neueProbenTable.refresh();
    }

    @Override
    public Node getNode() {

        return gridPane;
    }

    @Override
    public String getName() {

        return "Benachrichtigung";
    }

}
