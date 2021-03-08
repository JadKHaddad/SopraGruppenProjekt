/**
 *
 */
package de.wwu.sopra.view;

import de.wwu.sopra.controller.data.BehaelterVerwaltung;
import de.wwu.sopra.controller.data.DeckeltypVerwaltung;
import de.wwu.sopra.controller.data.ProbenKategorieVerwaltung;
import de.wwu.sopra.model.Behaeltertyp;
import de.wwu.sopra.model.Deckeltyp;
import de.wwu.sopra.model.ProbenInfo;
import de.wwu.sopra.model.ProbenKategorie;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.ArrayList;

/**
 * Verwaltet die Ansicht des ProbenAuswahlFormulars
 * 
 * @author Gruppe 5
 */
public class ProbenAuswahlFormular extends View {

    private TabSteuerung tabSteuerung;
    private GridPane pane;
    private TextField anzahlField;
    private TextField mengeField;
    private boolean gueltigeMengenEingabe, gueltigeAnzahlEingabe, kategorieAusgewaehlt, behaelterAusgewaelt;

    /**
     * Erstellt ein ProbenAuswahlFomular
     * @param tabSteuerung die Tabsteuerung
     * @param proben die Proben
     */
    public ProbenAuswahlFormular(TabSteuerung tabSteuerung, ArrayList<ProbenInfo> proben) {

        pane = new GridPane();
        pane.setHgap(32);
        pane.setVgap(6);

        Text text = new Text("Probe hinzufügen:");
        pane.add(text, 0, 0);

        HBox hbox = new HBox();
        hbox.setSpacing(6);
        hbox.setAlignment(Pos.BASELINE_CENTER);

        anzahlField = new TextField();
        anzahlField.setPromptText("Anzahl");
        Text text1 = new Text("x");

        TableView<ProbenInfo> probenTable = new TableView<ProbenInfo>();
        pane.add(probenTable, 0, 3);

     
          
        ObservableList<ProbenKategorie> options =
            FXCollections.observableArrayList(
                ProbenKategorieVerwaltung.getInstance().getProbenKategorieSet()
            );
        final ComboBox<ProbenKategorie> comboBox = new ComboBox<ProbenKategorie>(options);
 
          
        comboBox.setPromptText("Probenkategorie");

        
        ObservableList<Behaeltertyp> behaelter =
                FXCollections.observableArrayList(new ArrayList<Behaeltertyp>(BehaelterVerwaltung.getInstance().getBehaeltertypSet())
                );
        
        final ComboBox<Behaeltertyp> comboBoxBehaelter = new ComboBox<Behaeltertyp>(behaelter);
        comboBoxBehaelter.setPromptText("Behälteryp");
        
        mengeField = new TextField();
        mengeField.setPromptText("Menge");
        Button button = new Button("", new ImageView(new Image(getClass().getResourceAsStream("/add.png"))));
        button.setDisable(true);
        button.setTooltip(new Tooltip("Probe hinzufügen"));
        button.setOnAction(event -> {
            for (int i = 0; i < Integer.parseInt(anzahlField.getText()); i++) {
                ProbenInfo probenInfo = new ProbenInfo(Float.parseFloat(mengeField.getText()), comboBox.getSelectionModel().getSelectedItem(), comboBoxBehaelter.getSelectionModel().getSelectedItem());
                proben.add(probenInfo);
                probenTable.refresh();
            }
        });

        

        hbox.getChildren().add(anzahlField);
        hbox.getChildren().add(text1);
        hbox.getChildren().add(comboBox);
        hbox.getChildren().add(mengeField);
        hbox.getChildren().add(comboBoxBehaelter);
        hbox.getChildren().add(button);

        pane.add(hbox, 0, 1);
        Text text4 = new Text("Ausgewählte Proben:");
        pane.add(text4, 0, 2);


        TableColumn<ProbenInfo, Button> removeCol = new TableColumn<ProbenInfo, Button>("Löschen");
        TableColumn<ProbenInfo, String> probenNameCol = new TableColumn<ProbenInfo, String>("Name");
        TableColumn<ProbenInfo, String> probenEinheitCol = new TableColumn<ProbenInfo, String>("Menge");

        String decimalPattern = "(\\d+)(\\.\\d+)?";
        String intPattern = "(\\d+)";

        mengeField.textProperty().addListener((observable, oldValue, newValue) -> {
            gueltigeMengenEingabe = newValue.matches(decimalPattern);
            button.setDisable(!(gueltigeMengenEingabe && gueltigeAnzahlEingabe && kategorieAusgewaehlt && behaelterAusgewaelt));
        });

        anzahlField.textProperty().addListener((observable, oldValue, newValue) -> {
            gueltigeAnzahlEingabe = newValue.matches(intPattern);
            button.setDisable(!(gueltigeMengenEingabe && gueltigeAnzahlEingabe && kategorieAusgewaehlt && behaelterAusgewaelt));
        });

        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            kategorieAusgewaehlt = true;
            button.setDisable(!(gueltigeMengenEingabe && gueltigeAnzahlEingabe && kategorieAusgewaehlt && behaelterAusgewaelt));
        });
        
        comboBoxBehaelter.valueProperty().addListener((observable, oldValue, newValue) -> {
            behaelterAusgewaelt = true;
            button.setDisable(!(gueltigeMengenEingabe && gueltigeAnzahlEingabe && kategorieAusgewaehlt && behaelterAusgewaelt));
        });


        removeCol.setCellFactory(ActionButtonTableCell
            .forTableColumn(new Image(getClass().getResourceAsStream("/remove.png")), "Löschen", (ProbenInfo p) -> {
                proben.remove(p);
                probenTable.refresh();
                return p; 
            }));

        //Ruft Name der Kategorie eines Eintrags in der Tabelle auf
        probenNameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProbenInfo, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(CellDataFeatures<ProbenInfo, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getKategorie().getName());
            }
        });
        //Ruft Einheit der Kategorie eines Eintrags in der Tabelle auf
        probenEinheitCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProbenInfo, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(CellDataFeatures<ProbenInfo, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getMenge() + " " + param.getValue().getKategorie().getEinheit());
            }
        });

        ObservableList<ProbenInfo> list = FXCollections.observableList(proben);
        probenTable.setItems(list);

        probenTable.getColumns().add(probenNameCol);
        probenTable.getColumns().add(probenEinheitCol);
        probenTable.getColumns().add(removeCol);


        Button button1 = new Button("Anwenden");

        button1.setTooltip(new Tooltip("Gewählte Proben der Studie hinzufügen"));

        button1.setOnAction(event -> {
            tabSteuerung.pop();
        });
        pane.add(button1, 0, 4);
    }

    /**
     * Gitb die Hauptnode des Fensters zurueck
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
        return "Proben auswählen";
    }

}
