package de.wwu.sopra.view;

import de.wwu.sopra.model.PatientenTermin;
import de.wwu.sopra.model.Visite;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * Verwaltet die Ansicht des VisitenInformationsFormulars
 * @author Gruppe 5
 */
public class VisitenInformationsFormular extends View {

    GridPane pane;
    Visite visite;
    private TableView<PatientenTermin> patientenTermine;
    
    /**
     * Erstellt ein VisitenInformationsFormular
     * @param tabSteuerung die Tabsteuerung
     * @param visite die Visite
     */
    public VisitenInformationsFormular(TabSteuerung tabSteuerung, Visite visite) {
        pane = new GridPane();
        this.visite = visite;
        pane.setHgap(32);
        pane.setVgap(6);

        Text teiln = new Text("Eingetragene Teilnehmer:");
        Text abgesagt = new Text("Visite abgesagt  ");
        new ImageView(new Image(getClass().getResourceAsStream("/add.png")));
        Text abgenommen = new Text("       Visite abgenommen  ");
        teiln.setStyle("-fx-font-weight: bold");
        teiln.setStyle("-fx-font-size: 18px;");

        pane.add(teiln, 0, 0);

        HBox hbAbesagt = new HBox();
        hbAbesagt.getChildren().add(abgesagt);
        hbAbesagt.getChildren().add(new ImageView(new Image(getClass().getResourceAsStream("/remove.png"))));
        hbAbesagt.getChildren().add(abgenommen);
        hbAbesagt.getChildren().add(new ImageView(new Image(getClass().getResourceAsStream("/ok.png"))));

        pane.add(hbAbesagt, 0, 2);

        patientenTermine = new TableView<PatientenTermin>();
        pane.add(patientenTermine, 0, 4);

        
        Button zurueck = new Button("Zurück");
        pane.add(zurueck, 0, 5);
        zurueck.setTooltip(new Tooltip("Zurück zur Auswahl"));
        zurueck.setOnAction(event -> {
            tabSteuerung.pop();
        });
        
        TableColumn<PatientenTermin, String> patientenVorNameCol = new TableColumn<PatientenTermin, String>("Vorname");
        TableColumn<PatientenTermin, String> patientenNameCol = new TableColumn<PatientenTermin, String>("Nachname");
        TableColumn<PatientenTermin, ImageView> statusCol = new TableColumn<PatientenTermin, ImageView>("Status");

        patientenVorNameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PatientenTermin, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(CellDataFeatures<PatientenTermin, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getStudienTeilnehmer().getPatient().getVorname());
            }
        });


        patientenNameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PatientenTermin, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(CellDataFeatures<PatientenTermin, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getStudienTeilnehmer().getPatient().getName());
            }
        });


        statusCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PatientenTermin, ImageView>, ObservableValue<ImageView>>() {

            @Override
            public ObservableValue<ImageView> call(CellDataFeatures<PatientenTermin, ImageView> param) {
                if (param.getValue().isAbgesagt())
                    return new ReadOnlyObjectWrapper<ImageView>(new ImageView(new Image(getClass().getResourceAsStream("/remove.png"))));
                else {
                    if (param.getValue().isDurchgefuehrt())
                        return new ReadOnlyObjectWrapper<ImageView>(new ImageView(new Image(getClass().getResourceAsStream("/ok.png"))));
                    else
                        return new ReadOnlyObjectWrapper<ImageView>(new ImageView());
                }


            }
        });

        patientenTermine.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    tabSteuerung.push(new VisitenDurchfuehrungsFormular(tabSteuerung, patientenTermine.getSelectionModel().getSelectedItem()));
                }
            }
        });



        ObservableList<PatientenTermin> list = FXCollections.observableList(visite.getPatientenTermine());
        patientenTermine.setItems(list);

        patientenTermine.getColumns().add(patientenVorNameCol);
        patientenTermine.getColumns().add(patientenNameCol);
        patientenTermine.getColumns().add(statusCol);


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
        return "Visitenübersicht: " + visite.getName();
    }
    
    /**
     * Verwaltet, was passieren soll wenn dieses Fenster geöffnet wird
     */
    @Override
    public void onOpen() {
        patientenTermine.refresh();
    }

}

