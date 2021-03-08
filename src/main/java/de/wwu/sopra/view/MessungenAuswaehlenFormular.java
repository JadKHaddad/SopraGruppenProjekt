/**
 *
 */
package de.wwu.sopra.view;

import de.wwu.sopra.controller.data.MesstypVerwaltung;
import de.wwu.sopra.model.Messtyp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * Verwaltet die Ansicht des MessungenAuswaehlenFormulars
 * @author Gruppe 5
 */
public class MessungenAuswaehlenFormular extends View {

    private TabSteuerung tabSteuerung;
    private GridPane pane;
    
    /**
     * Erstellt das MessungenAuswaehlenFormular
     * @param tabSteuerung die Tabsteuerung
     * @param messtypen die Messtypen
     */
    public MessungenAuswaehlenFormular(TabSteuerung tabSteuerung, ArrayList<Messtyp> messtypen) {

        pane = new GridPane();
        pane.setHgap(32);
        pane.setVgap(6);

        Text text1 = new Text("Mögliche Messungen:");
        Text text2 = new Text("Ausgewählte Messungen:"); 

        pane.add(text1, 0, 0);
        pane.add(text2, 1, 0);

        TableView<Messtyp> messtypTable = new TableView<Messtyp>();
        pane.add(messtypTable, 0, 1);

        TableView<Messtyp> messtypTable1 = new TableView<Messtyp>();
        pane.add(messtypTable1, 1, 1);

        //Erstellt eine Tabelle
        TableColumn<Messtyp, String> benutzerNameCol = new TableColumn<Messtyp, String>("Messtyp");
        TableColumn<Messtyp, Button> removeCol = new TableColumn<Messtyp, Button>();
        removeCol.setSortable(false);

        benutzerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        removeCol.setCellFactory(ActionButtonTableCell
            .forTableColumn(new Image(getClass().getResourceAsStream("/minus.png")), "Löschen", (Messtyp m) -> {
                messtypen.remove(m);
                messtypTable1.refresh();
                return m;
            }));


        //Erstellt eine Tabelle
        TableColumn<Messtyp, String> messtypNameCol1 = new TableColumn<Messtyp, String>("Messtyp");
        TableColumn<Messtyp, Button> addCol = new TableColumn<Messtyp, Button>();
        addCol.setSortable(false);


        messtypNameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        addCol.setCellFactory(ActionButtonTableCell
            .forTableColumn(new Image(getClass().getResourceAsStream("/add.png")), "Hinzu", (Messtyp m1) -> {
                messtypen.add(m1);
                messtypTable1.refresh();
                return m1;
            }));

        
        ObservableList<Messtyp> list = FXCollections.observableList(MesstypVerwaltung.getInstance().getMesstypen());
        messtypTable.setItems(list);

        messtypTable.getColumns().add(messtypNameCol1);
        messtypTable.getColumns().add(addCol);

        
        ObservableList<Messtyp> list1 = FXCollections.observableList(messtypen);
        messtypTable1.setItems(list1);

        messtypTable1.getColumns().add(benutzerNameCol);
        messtypTable1.getColumns().add(removeCol);

        //Änderungen anwenden und zum vorherigen Fenster zurückkehren
        Button button = new Button("Anwenden");
        button.setTooltip(new Tooltip("Gewählte Nurses zur Studie hinzufügen"));

        button.setOnAction(event -> {
            tabSteuerung.pop();
        });

        pane.add(button, 1, 2);
    }
    
    /**
     * Gibt die Hauptnode des Fensters zurueck
     */
    @Override
    public Node getNode() {

        return pane;
    }
    /**
     * Gibt den Namen wieder, der in den Breadcrumbs angezeigt werden soll
     */
    @Override
    public String getName() {

        return "Messungen auswählen";
    }

}
