package de.wwu.sopra.view;

import de.wwu.sopra.model.Studie;
import de.wwu.sopra.model.Studienteilnehmer;
import de.wwu.sopra.model.Visite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;


/**
 * Darstellung eines EinzelStudienFormulars
 * @author Gruppe 5
 *
 */
public class EinzelStudienFormular extends View {
    private GridPane pane;
    Studie studie;

    /**
     * Erstellt ein EinzelStudienFormular
     * @param tabSteuerung die Tabsteuerung
     * @param studie die Studie des Formulars
     */
    public EinzelStudienFormular(TabSteuerung tabSteuerung, Studie studie) {

        pane = new GridPane();
        this.studie = studie;

        Text name = new Text(this.studie.getName());
        name.setStyle("-fx-font-weight: bold");
        name.setStyle("-fx-font-size: 18px;");

        pane.add(name, 0, 0);
        pane.setVgap(10);

        Button patienten = new Button("Patienten verwalten");
        patienten.setTooltip(new Tooltip("Patienten auswählen und neue Patienten anlegen"));
        patienten.setOnAction(event -> {
            tabSteuerung.push(new PatientenAuswahlFormular(tabSteuerung, this.studie));
        });
        pane.add(patienten, 0, 1);

        pane.setVgap(10);

        Text visiteText = new Text("Visite durchführen:");
        pane.add(visiteText, 0, 3);

        TableView<Visite> visitenTable = new TableView<Visite>();
        pane.add(visitenTable, 0, 4);
        
        Button zurueck = new Button("Zurück");
        pane.add(zurueck, 0, 5);
        zurueck.setTooltip(new Tooltip("Zurück zur Auswahl"));
        zurueck.setOnAction(event -> {
            tabSteuerung.pop();
        });
        

        TableColumn<Visite, String> visitenNameCol = new TableColumn<Visite, String>("Visitenname");

        visitenNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        ObservableList<Visite> list = FXCollections.observableList(studie.getVisiten());
        visitenTable.setItems(list);

        visitenTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    tabSteuerung.push(new VisitenInformationsFormular(tabSteuerung, visitenTable.getSelectionModel().getSelectedItem()));
                }
            }
        });

        visitenTable.getColumns().add(visitenNameCol);

    }
    /**
     * @return Die Hauptnode des Fensters
     */
    @Override
    public Node getNode() {
        return pane;
    }
    /**
     * @return Der Name des Formulars, der bei den Breadcrumbs angezeigt werden soll.
     */
    @Override
    public String getName() {
        return "Studie: " + studie.getName();
    }

}
