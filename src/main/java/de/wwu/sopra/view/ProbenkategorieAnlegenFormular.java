package de.wwu.sopra.view;

import java.util.ArrayList;

import javax.swing.event.ChangeListener;

import de.wwu.sopra.controller.data.ProbenKategorieVerwaltung;
import de.wwu.sopra.model.Probe;
import de.wwu.sopra.model.ProbenKategorie;
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
 * Verwaltet die Ansicht des ProbenKategorieAnlegenFormulars
 * 
 * @author Gruppe 5
 */
public class ProbenkategorieAnlegenFormular extends View {

    private TabSteuerung tabSteuerung;
    
    private GridPane mainGPane;
    private TextField nameField, mintempField, maxtempField, einheitField;
    private boolean gueltigerNameEingabe, gueltigemintempEingabe, gueltigemaxtempEingabe,
            gueltigeeinheitEingabe, minMaxTempCheck = false; 
    String decimalPattern = "(\\d+)(\\.\\d+)?";
    ProbenKategorieVerwaltung probenkategorieVerwaltung = ProbenKategorieVerwaltung.getInstance();

    /**
     * Erstellt ein ProbenKategorieAnlegenFormular
     * 
     * @param tabSteuerung die Tabsteuerung
     */
    public ProbenkategorieAnlegenFormular(TabSteuerung tabSteuerung) {
        
        GridPane pane = new GridPane();
        mainGPane = new GridPane();
        
        mainGPane.add(pane, 0, 0);
        mainGPane.setHgap(32);
        mainGPane.setVgap(6);
        
        pane.setHgap(32);
        pane.setVgap(6);
        
        Text ueberschrift = new Text("Bereits angelegte Probenkategorien:");
        
        pane.add(new Label("Name"), 0, 0);
        nameField = new TextField();
        pane.add(nameField, 1, 0);

        Text errormessageName = new Text("Das Feld ist leer oder der Name ist bereits vergeben.");
        errormessageName.setVisible(gueltigerNameEingabe);
        errormessageName.setFill(Color.CRIMSON);
        pane.add(errormessageName, 3, 0);

        Text errormessageminTemp = new Text("Nur Dezimalzahlen sind erlaubt.");
        errormessageminTemp.setVisible(gueltigemintempEingabe);
        errormessageminTemp.setFill(Color.CRIMSON);
        pane.add(errormessageminTemp, 3, 1);

        Text errormessagemaxtemp = new Text("Nur Dezimalzahlen sind erlaubt.");
        errormessagemaxtemp.setVisible(gueltigemaxtempEingabe);
        errormessagemaxtemp.setFill(Color.CRIMSON);
        pane.add(errormessagemaxtemp, 3, 2);

        Text errormessageEinheit = new Text("Darf nicht leer sein");
        errormessageEinheit.setVisible(gueltigeeinheitEingabe);
        errormessageEinheit.setFill(Color.CRIMSON);
        pane.add(errormessageEinheit, 3, 3);
        
        Text errormessageTemp = new Text("Minimal Temperatur muss unter der maximal Temperatur liegen.");
        errormessageTemp.setVisible(minMaxTempCheck);
        errormessageTemp.setFill(Color.CRIMSON);
        pane.add(errormessageTemp, 3, 2);
        
        pane.add(new Label("min. Lagertemperatur"), 0, 1);
        mintempField = new TextField();
        pane.add(mintempField, 1, 1);

        pane.add(new Label("max. Lagertemperatur"), 0, 2);
        maxtempField = new TextField();
        pane.add(maxtempField, 1, 2);

        pane.add(new Label("Einheit"), 0, 3);
        einheitField = new TextField();
        pane.add(einheitField, 1, 3);

        Button button = new Button("Anlegen");
        button.setTooltip(new Tooltip("Neue Probekategorie anlegen"));

        button.setOnAction(event -> {
            ProbenKategorieVerwaltung.getInstance()
                    .addProbenKategorie(new ProbenKategorie(nameField.getText(), einheitField.getText(),
                            Float.parseFloat(maxtempField.getText()), Float.parseFloat(mintempField.getText())));
            new InfoPopup("Erfolgreich", "Die neue Probenkategorie wurde erfolgreich angelegt!", () -> {});

            tabSteuerung.pop();
        });

        Button zurueck = new Button("Zurück");
        pane.add(zurueck, 1, 5);
        zurueck.setTooltip(new Tooltip("Zurück zum Administrationsfenster"));
        zurueck.setOnAction(event -> {
            tabSteuerung.pop();
        });
        pane.add(button, 0, 5);
        button.setDisable(true);
        
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            ProbenKategorieVerwaltung pv = ProbenKategorieVerwaltung.getInstance();
            gueltigerNameEingabe = (!(newValue.isBlank())) && pv.nameIsUnique(newValue);
            errormessageName.setVisible(!gueltigerNameEingabe);
            button.setDisable(!(gueltigerNameEingabe && gueltigemintempEingabe && gueltigemaxtempEingabe
                    && gueltigeeinheitEingabe));
        });
        maxtempField.textProperty().addListener((observable, oldValue, newValue) -> {
            gueltigemaxtempEingabe = newValue.matches(decimalPattern);
           errormessageTemp.setVisible(false);
            errormessagemaxtemp.setVisible(!gueltigemaxtempEingabe);
            if(gueltigemaxtempEingabe && gueltigemintempEingabe) {
                if(Float.parseFloat(maxtempField.getText()) < Float.parseFloat(mintempField.getText())) {
                    errormessageTemp.setVisible(true);
                }
            }
            button.setDisable(!(gueltigerNameEingabe && gueltigemintempEingabe && gueltigemaxtempEingabe
                    && gueltigeeinheitEingabe));
        });
        mintempField.textProperty().addListener((observable, oldValue, newValue) -> {
            gueltigemintempEingabe = newValue.matches(decimalPattern);
           errormessageTemp.setVisible(false);
            errormessageminTemp.setVisible(!gueltigemintempEingabe);
            if(gueltigemaxtempEingabe && gueltigemintempEingabe) {
                if(Float.parseFloat(maxtempField.getText()) < Float.parseFloat(mintempField.getText())) {
                    errormessageTemp.setVisible(true);
                }
            }
            button.setDisable(!(gueltigerNameEingabe && gueltigemintempEingabe && gueltigemaxtempEingabe
                    && gueltigeeinheitEingabe));
        });
        einheitField.textProperty().addListener((observable, oldValue, newValue) -> {
            gueltigeeinheitEingabe = !(newValue.isBlank());
            errormessageEinheit.setVisible(!gueltigeeinheitEingabe);
            button.setDisable(!(gueltigerNameEingabe && gueltigemintempEingabe && gueltigemaxtempEingabe
                    && gueltigeeinheitEingabe));
        });
        
        TableView<ProbenKategorie> kategorienTable = new TableView<ProbenKategorie>();
        
        TableColumn<ProbenKategorie, String> nameCol = new TableColumn<ProbenKategorie, String>("Name");
        TableColumn<ProbenKategorie, String> einheitCol = new TableColumn<ProbenKategorie, String>("Einheit");
        TableColumn<ProbenKategorie, String> minTempCol = new TableColumn<ProbenKategorie, String>("min. Temp");
        TableColumn<ProbenKategorie, String> maxTempCol = new TableColumn<ProbenKategorie, String>("max. Temp");
        
        

        nameCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ProbenKategorie, String>, ObservableValue<String>>() {

                    @Override
                    public ObservableValue<String> call(CellDataFeatures<ProbenKategorie, String> param) {
                        return new ReadOnlyObjectWrapper<String>("" + param.getValue().getName());
                    }
                });
        

        einheitCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ProbenKategorie, String>, ObservableValue<String>>() {

                    @Override
                    public ObservableValue<String> call(CellDataFeatures<ProbenKategorie, String> param) {
                        return new ReadOnlyObjectWrapper<String>("" + param.getValue().getEinheit());
                    }
                });
        

        minTempCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ProbenKategorie, String>, ObservableValue<String>>() {

                    @Override
                    public ObservableValue<String> call(CellDataFeatures<ProbenKategorie, String> param) {
                        return new ReadOnlyObjectWrapper<String>("" + param.getValue().getMinLagerTemp());
                    }
                });
        

        maxTempCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ProbenKategorie, String>, ObservableValue<String>>() {

                    @Override
                    public ObservableValue<String> call(CellDataFeatures<ProbenKategorie, String> param) {
                        return new ReadOnlyObjectWrapper<String>("" + param.getValue().getMaxLagerTemp());
                    }
                });
        
        ObservableList<ProbenKategorie> kategorienList = FXCollections
                .observableList(new ArrayList<ProbenKategorie>(ProbenKategorieVerwaltung.getInstance().getProbenKategorieSet()));
        kategorienTable.setItems(kategorienList);
        
        kategorienTable.getColumns().add(nameCol);
        kategorienTable.getColumns().add(einheitCol);
        kategorienTable.getColumns().add(minTempCol);
        kategorienTable.getColumns().add(maxTempCol);
        
        mainGPane.add(ueberschrift, 0, 1);
        mainGPane.add(kategorienTable, 0, 2);
        
    }

    /**
     * @return Die Hauptnode des Fensters.
     */
    @Override
    public Node getNode() {
        return mainGPane;
    }

    /**
     * @return Der Name, der bei den Breadcrumbs angezeigt werden sollen.
     */
    @Override
    public String getName() {
        return "Probenkategorie anlegen";
    }
}